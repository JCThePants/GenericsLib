/*
 * This file is part of GenericsLib for Bukkit, licensed under the MIT License (MIT).
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

package com.jcwhatever.bukkit.generic.jail;

import com.sun.istack.internal.Nullable;

import org.bukkit.plugin.Plugin;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Jail manager interface.
 */
public interface IJailManager {

    void registerJail(Jail jail);

    void unregisterJail(Jail jail);

    @Nullable
    Jail getJail(Plugin plugin, String name);

    List<Jail> getJails();

    @Nullable
    JailSession registerJailSession(Jail jail, UUID playerId, int minutes);

    @Nullable
    JailSession registerJailSession(Jail jail, UUID playerId, Date expires);

    void unregisterJailSession(UUID playerId);

    @Nullable
    JailSession getSession(UUID playerId);

    List<JailSession> getSessions();

    boolean isPrisoner(UUID playerId);

    boolean release(UUID playerId);

    boolean isLateRelease(UUID playerId);
}