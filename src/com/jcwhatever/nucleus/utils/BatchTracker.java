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


package com.jcwhatever.nucleus.utils;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Used to provide internal batch execution functionality
 * within a class.
 */
public class BatchTracker {

    private AtomicInteger _batchOperations = new AtomicInteger();

    /**
     * Begin a batch operation.
     */
    public int start() {
        return _batchOperations.incrementAndGet();
    }

    /**
     * End a batch operation.
     */
    public int end() {

        if (_batchOperations.intValue() == 0)
            throw new IllegalStateException("There are no batch operations running.");

        return _batchOperations.decrementAndGet();
    }

    /**
     * Determine if there is at least 1 batch operation running.
     */
    public boolean isRunning() {
        return _batchOperations.intValue() > 0;
    }

    /**
     * Get the number of batch operations currently
     * running.
     */
    public int getBatchCount() {
        return _batchOperations.intValue();
    }
}
