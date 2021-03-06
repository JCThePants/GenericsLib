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

package com.jcwhatever.nucleus.internal.managed.scripting.api;

import com.jcwhatever.nucleus.Nucleus;
import com.jcwhatever.nucleus.mixins.IDisposable;
import com.jcwhatever.nucleus.providers.npc.INpc;
import com.jcwhatever.nucleus.providers.npc.INpcProvider;
import com.jcwhatever.nucleus.providers.npc.INpcRegistry;
import com.jcwhatever.nucleus.providers.npc.ai.actions.NpcScriptAction;
import com.jcwhatever.nucleus.providers.npc.ai.goals.NpcScriptGoal;
import com.jcwhatever.nucleus.utils.PreCon;

import org.bukkit.entity.Entity;
import org.bukkit.plugin.Plugin;

import java.util.ArrayDeque;
import java.util.Deque;
import javax.annotation.Nullable;

public class SAPI_NpcProvider implements IDisposable {

    private final Plugin _plugin;
    private final Deque<INpcRegistry> _registries = new ArrayDeque<>(15);
    private boolean _isDisposed;

    /**
     * Constructor.
     *
     * @param plugin  The owning plugin.
     */
    public SAPI_NpcProvider(Plugin plugin) {
        _plugin = plugin;
    }

    /**
     * Determine if there is an NPC provider installed.
     */
    public boolean hasNpcProvider() {
        return Nucleus.getProviders().getNpcs() != null;
    }

    /**
     * Get the NPC provider.
     */
    public INpcProvider getProvider() {

        INpcProvider provider = Nucleus.getProviders().getNpcs();
        if (provider == null)
            throw new UnsupportedOperationException("Npc provider is not installed.");

        return provider;
    }

    /**
     * Create a new transient NPC registry.
     */
    public INpcRegistry createRegistry(String name) {
        PreCon.notNullOrEmpty(name, "name");

        INpcProvider provider = getProvider();

        INpcRegistry registry = provider.createRegistry(_plugin, name);
        if (registry == null)
            throw new NullPointerException("Failed to create NPC registry. Registry is null.");

        _registries.add(registry);

        return registry;
    }

    /**
     * Determine if an entity is an NPC.
     *
     * @param entity  The entity to check.
     */
    public boolean isNpc(Entity entity) {
        PreCon.notNull(entity);

        INpcProvider provider = getProvider();

        return provider.isNpc(entity);
    }

    /**
     * Get an {@link Entity}'s {@link INpc} instance.
     *
     * @param entity  The entity.
     *
     * @return  The {@link INpc} instance or null if the entity is not an NPC.
     */
    @Nullable
    public INpc getNpc(Entity entity) {
        PreCon.notNull(entity, "entity");

        INpcProvider provider = getProvider();

        return provider.getNpc(entity);
    }

    /**
     * Create a new {@link NpcScriptGoal}.
     *
     * @param name  The name of the goal.
     */
    public NpcScriptGoal createGoal(String name) {
        PreCon.notNull(name);

        return new NpcScriptGoal(name);
    }

    /**
     * Create a new {@link NpcScriptAction}.
     *
     * @param name  The name of the action.
     */
    public NpcScriptAction createAction(String name) {
        return new NpcScriptAction(name);
    }

    @Override
    public boolean isDisposed() {
        return _isDisposed;
    }

    @Override
    public void dispose() {

        while (!_registries.isEmpty()) {

            INpcRegistry registry = _registries.remove();
            registry.dispose();
        }

        _isDisposed = true;
    }
}
