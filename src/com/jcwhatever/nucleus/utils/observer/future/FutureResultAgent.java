/*
 * This file is part of NucleusFramework for Bukkit, licensed under the MIT License (MIT).
 *
 * Copyright (c) JCThePants (www.jcwhatever.com)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.jcwhatever.nucleus.utils.observer.future;

import com.jcwhatever.nucleus.utils.PreCon;
import com.jcwhatever.nucleus.utils.observer.ISubscriber;
import com.jcwhatever.nucleus.utils.observer.SubscriberAgent;
import com.jcwhatever.nucleus.utils.observer.update.NamedUpdateAgents;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;

/**
 * Used for returning asynchronous results from a method.
 *
 * @see FutureResultSubscriber
 * @see IFutureResult
 */
public class FutureResultAgent<R> extends SubscriberAgent {

    private final FutureResult<R> _future;
    private final NamedUpdateAgents _updateAgents = new NamedUpdateAgents();

    private Result<R> _finalResult;
    private boolean _hasFutureSubscribers;

    /**
     * Constructor.
     */
    public FutureResultAgent() {
        _future = new FutureResult<>(this);
    }

    @Override
    public boolean hasSubscribers() {
        return super.hasSubscribers() || _hasFutureSubscribers;
    }

    /**
     * Send result to subscribers.
     *
     * @param result  The result.
     */
    public void sendResult(Result<R> result) {
        PreCon.notNull(result);

        if (result.isComplete()) {
            _finalResult = result;
        }

        if (!hasSubscribers())
            return;

        if (result.isSuccess()) {
            sendSuccess(result);
        }
        else if (result.isComplete()) {

            if (result.isCancelled()) {
                sendCancel(result);
            }
            else {
                sendError(result);
            }
        }

        sendResultOnly(result);
    }

    /**
     * Declare the result cancelled.
     *
     * <p>The same as invoking {@link #sendResult} with a generic
     * cancel result.</p>
     *
     * <p>The result object and message are null.</p>
     *
     * @return The agents future.
     */
    public FutureResult<R> cancel() {
        return cancel(null, null);
    }

    /**
     * Declare the result cancelled.
     *
     * <p>The same as invoking {@link #sendResult} with a generic
     * cancel result.</p>
     *
     * <p>The result message is null.</p>
     *
     * @param result   The result object.
     *
     * @return The agents future.
     */
    public FutureResult<R> cancel(@Nullable R result) {
        return cancel(result, null);
    }

    /**
     * Declare the result cancelled.
     *
     * <p>The same as calling {@link #sendResult} with a generic
     * cancel result.</p>
     *
     * @param result   The result object.
     * @param message  The message to send with the result.
     *
     * @return The agents future.
     */
    public FutureResult<R> cancel(@Nullable R result, @Nullable String message) {

        sendResult(new ResultBuilder<R>()
                .cancel()
                .result(result)
                .message(message)
                .build());

        return getFuture();
    }

    /**
     * Declare an error in the result.
     *
     * <p>The same as invoking {@link #sendResult} with a generic
     * error result.</p>
     *
     * <p>The result object and message are null.</p>
     *
     * @return The agents future.
     */
    public FutureResult<R> error() {
        return error(null, null);
    }

    /**
     * Declare an error in the result.
     *
     * <p>The same as invoking {@link #sendResult} with a generic
     * error result.</p>
     *
     * <p>The result message is null.</p>
     *
     * @param result   The result object.
     *
     * @return The agents future.
     */
    public FutureResult<R> error(@Nullable R result) {
        return error(result, null);
    }

    /**
     * Declare an error in the result.
     *
     * <p>The same as invoking {@link #sendResult} with a generic
     * error result.</p>
     *
     * @param result   The result object.
     * @param message  The message to send with the result.
     *
     * @return The agents future.
     */
    public FutureResult<R> error(@Nullable R result, @Nullable String message) {

        sendResult(new ResultBuilder<R>()
                .error()
                .result(result)
                .message(message)
                .build());

        return getFuture();
    }

    /**
     * Declare the result a success.
     *
     * <p>The same as invoking {@link #sendResult} with a generic
     * success result.</p>
     *
     * <p>The result object and message are null.</p>
     *
     * @return The agents future.
     */
    public FutureResult<R> success() {
        return success(null, null);
    }

    /**
     * Declare the result a success.
     *
     * <p>The same as invoking {@link #sendResult} with a generic
     * success result.</p>
     *
     * <p>The result message is null.</p>
     *
     * @param result   The result object.
     *
     * @return The agents future.
     */
    public FutureResult<R> success(@Nullable R result) {
        return success(result, null);
    }

    /**
     * Declare the result a success.
     *
     * <p>The same as invoking {@link #sendResult} with a generic
     * success result.</p>
     *
     * @param result   The result object.
     * @param message  The message to send with the result.
     *
     * @return The agents future.
     */
    public FutureResult<R> success(@Nullable R result, @Nullable String message) {

        sendResult(new ResultBuilder<R>()
                .success()
                .result(result)
                .message(message)
                .build());

        return getFuture();
    }

    /**
     * Get a future that can be returned so that a method caller can
     * attach {@link com.jcwhatever.nucleus.utils.observer.update.IUpdateSubscriber}'s.
     */
    public FutureResult<R> getFuture() {
        return _future;
    }

    protected void sendResultOnly(Result<R> result) {

        List<ISubscriber> list = new ArrayList<>(subscribers());

        for (ISubscriber subscriber : list) {
            if (subscriber instanceof FutureResultSubscriber) {
                //noinspection unchecked
                ((FutureResultSubscriber<R>) subscriber).on(result);
            }
        }

        _updateAgents.update("onResult", result);
    }

    protected void sendSuccess(Result<R> result) {

        List<ISubscriber> list = new ArrayList<>(subscribers());

        for (ISubscriber subscriber : list) {
            if (subscriber instanceof FutureResultSubscriber) {
                //noinspection unchecked
                ((FutureResultSubscriber<R>) subscriber).onSuccess(result);
            }
        }

        _updateAgents.update("onSuccess", result);
    }

    protected void sendError(Result<R> result) {

        List<ISubscriber> list = new ArrayList<>(subscribers());

        for (ISubscriber subscriber : list) {
            if (subscriber instanceof FutureResultSubscriber) {
                //noinspection unchecked
                ((FutureResultSubscriber<R>) subscriber).onError(result);
            }
        }

        _updateAgents.update("onError", result);
    }

    protected void sendCancel(Result<R> result) {

        List<ISubscriber> list = new ArrayList<>(subscribers());

        for (ISubscriber subscriber : list) {
            if (subscriber instanceof FutureResultSubscriber) {
                //noinspection unchecked
                ((FutureResultSubscriber<R>) subscriber).onCancel(result);
            }
        }

        _updateAgents.update("onCancel", result);
    }

    private static class FutureResult<R> implements IFutureResult<R> {

        FutureResultAgent<R> parent;

        private FutureResult(FutureResultAgent<R> parent) {
            this.parent = parent;
        }

        @Override
        public FutureResult<R> onResult(FutureResultSubscriber<R> subscriber) {
            PreCon.notNull(subscriber);

            addSubscriber("onResult", subscriber, parent._finalResult != null);

            return this;
        }

        @Override
        public FutureResult<R> onSuccess(FutureResultSubscriber<R> subscriber) {
            PreCon.notNull(subscriber);

            addSubscriber("onSuccess", subscriber, parent._finalResult != null &&
                    parent._finalResult.isSuccess());

            return this;
        }

        @Override
        public FutureResult<R> onCancel(FutureResultSubscriber<R> subscriber) {
            PreCon.notNull(subscriber);

            addSubscriber("onCancel", subscriber, parent._finalResult != null &&
                    parent._finalResult.isCancelled());

            return this;
        }

        @Override
        public FutureResult<R> onError(FutureResultSubscriber<R> subscriber) {
            PreCon.notNull(subscriber);

            addSubscriber("onError", subscriber, parent._finalResult != null &&
                    !parent._finalResult.isSuccess() && !parent._finalResult.isCancelled());

            return this;
        }

        private void addSubscriber(String agentName,
                                   FutureResultSubscriber<R> subscriber, boolean updateNow) {

            if (updateNow) {
                subscriber.on(parent._finalResult);
            }

            parent._updateAgents.getAgent(agentName).addSubscriber(subscriber);
            parent._hasFutureSubscribers = true;
        }
    }
}