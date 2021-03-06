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

package com.jcwhatever.nucleus.managed.actionbar;

import com.jcwhatever.nucleus.utils.text.dynamic.IDynamicText;

import org.bukkit.entity.Player;

import java.util.Collection;

/**
 * A {@link IPersistentActionBar} that is automatically removed
 * when the specified duration ends.
 */
public interface IActionBar {

    /**
     * Get the action bar text.
     */
    IDynamicText getText();

    /**
     * Show the action bar to a player.
     *
     * <p>Displays using {@link ActionBarPriority#DEFAULT}.</p>
     *
     * <p>If the player is viewing 1 or more {@link IPersistentActionBar}'s,
     * a {@link ITimedActionBar} is shown instead to ensure the text is given
     * a proper time slice among the persisted bars.</p>
     *
     * @param player  The player to show the bar to.
     */
    void showTo(Player player);

    /**
     * Show the action bar to a player.
     *
     * <p>If the player is viewing 1 or more {@link IPersistentActionBar}'s,
     * a {@link ITimedActionBar} is shown instead to ensure the text is given
     * a proper time slice among the persisted bars.</p>
     *
     * @param player    The player to show the bar to.
     * @param priority  The action bar priority.
     */
    void showTo(Player player, ActionBarPriority priority);

    /**
     * Show the action bar to a collection of players.
     *
     * <p>Displays using {@link ActionBarPriority#DEFAULT}.</p>
     *
     * <p>If the player is viewing 1 or more {@link IPersistentActionBar}'s,
     * a {@link ITimedActionBar} is shown instead to ensure the text is given
     * a proper time slice among the persisted bars.</p>
     *
     * @param players  The players to show the bar to.
     */
    void showTo(Collection<? extends Player> players);

    /**
     * Show the action bar to a collection of players.
     *
     * <p>If the player is viewing 1 or more {@link IPersistentActionBar}'s,
     * a {@link ITimedActionBar} is shown instead to ensure the text is given
     * a proper time slice among the persisted bars.</p>
     *
     * @param players   The players to show the bar to.
     * @param priority  The action bar priority.
     */
    void showTo(Collection<? extends Player> players, ActionBarPriority priority);
}
