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

package com.jcwhatever.nucleus.providers.permissions;

import org.bukkit.OfflinePlayer;
import org.bukkit.plugin.Plugin;

import java.util.Collection;

/**
 * A permissions provider with support for group permissions.
 *
 * <p>A provider that supports group permissions should implement this interface. The
 * interface is a mixin and can be used with other economy provider interfaces as needed.</p>
 *
 * <p>Should be implemented by a type that extends {@link com.jcwhatever.nucleus.providers.Provider}.</p>
 *
 * @see IPermissionsProvider
 */
public interface IGroupPermissionsProvider extends IPermissionsProvider {

    /**
     * Add a player to a group permission.
     *
     * @param plugin     The plugin adding the player to the group.
     * @param player     The player to add to the group.
     * @param groupName  The name of the group.
     *
     * @return  True if the player was added.
     */
    boolean addGroup(Plugin plugin, OfflinePlayer player, String groupName);

    /**
     * Remove a player from a group permission.
     *
     * @param plugin     The plugin removing the player from the group.
     * @param player     The player to remove from the group.
     * @param groupName  The name of the group.
     *
     * @return  True if the player was removed.
     */
    boolean removeGroup(Plugin plugin, OfflinePlayer player, String groupName);

    /**
     * Get a string array of group permission names.
     */
    Collection<IPermissionGroup> getGroups();

    /**
     * Get a string array of group permission names.
     *
     * @param output  The output collection to add results to.
     *
     * @return  The output collection.
     */
    <T extends Collection<IPermissionGroup>> T getGroups(T output);

    /**
     * Get a string array of groups the specified player is in.
     *
     * @param player  The player to check.
     */
    Collection<IPermissionGroup> getGroups(OfflinePlayer player);

    /**
     * Get a string array of groups the specified player is in.
     *
     * @param player  The player to check.
     * @param output  The output collection to add results to.
     *
     * @return  The output collection.
     */
    <T extends Collection<IPermissionGroup>> T getGroups(OfflinePlayer player, T output);
}
