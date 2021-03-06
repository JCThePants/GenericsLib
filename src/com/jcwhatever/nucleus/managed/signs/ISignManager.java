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

package com.jcwhatever.nucleus.managed.signs;

import com.jcwhatever.nucleus.Nucleus;

import org.bukkit.Location;
import org.bukkit.block.Sign;

import java.util.Collection;
import javax.annotation.Nullable;

/**
 * Interface for the global sign manager.
 *
 * @see Nucleus#getSignManager
 */
public interface ISignManager {

    /**
     * Register a sign handler.
     *
     * @param signHandler  The sign handler to register.
     *
     * @return  True if registered, false if a sign handler with the same name is
     * already registered.
     */
    boolean registerHandler(SignHandler signHandler);

    /**
     * Unregister a sign handler.
     *
     * @param name  The sign handler name.
     *
     * @return  True if found and unregistered.
     */
    boolean unregisterHandler(String name);

    /**
     * Get a sign handler by name.
     *
     * @param name  The name.
     */
    SignHandler getSignHandler(String name);

    /**
     * Get all sign handlers.
     */
    Collection<SignHandler> getSignHandlers();

    /**
     * Get all sign handlers.
     *
     * @param output  The output collection to place results into.
     *
     * @return  The output collection.
     */
    <T extends Collection<SignHandler>> T getSignHandlers(T output);

    /**
     * Get all signs saved to the config handled by the specified
     * sign handler.
     *
     * @param signHandlerName  The name of the sign handler.
     */
    Collection<ISignContainer> getSigns(String signHandlerName);

    /**
     * Get all signs saved to the config handled by the specified
     * sign handler.
     *
     * @param signHandlerName  The name of the sign handler.
     * @param output           The output collection to put results into.
     *
     * @return  The output collection.
     */
    <T extends Collection<ISignContainer>> T getSigns(String signHandlerName, T output);

    /**
     * Get the text lines for a handled sign from the config.
     *
     * @param sign  The sign to check.
     *
     * @return  Null if no handler or sign is found in config.
     */
    @Nullable
    String[] getSavedLines(Sign sign);

    /**
     * Restore a sign from the specified sign handler at the specified location
     * using config settings.
     *
     * <p>The sign is restored after a 1 tick scheduled delay.</p>
     *
     * @param signHandlerName  The name of the sign handler.
     * @param location         The location of the sign.
     *
     * @return  True if completed.
     */
    boolean restoreSign(String signHandlerName, Location location);

    /**
     * Restore signs from the specified sign handler using config settings.
     *
     * <p>The sign is restored after a 1 tick scheduled delay.</p>
     *
     * @param signHandlerName  The name of the sign handler.
     *
     * @return  True if restore completed.
     */
    boolean restoreSigns(String signHandlerName);
}
