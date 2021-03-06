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

package com.jcwhatever.nucleus.providers.playerlookup;

import com.jcwhatever.nucleus.providers.IProvider;
import com.jcwhatever.nucleus.providers.Provider;
import com.jcwhatever.nucleus.utils.observer.future.IFutureResult;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Date;
import java.util.UUID;

/**
 * Interface for a player lookup provider.
 *
 * <p>Should be implemented by a type that extends {@link Provider}.</p>
 */
public interface IPlayerLookupProvider extends IProvider {

    /**
     * Get a players unique ID from their player name.
     *
     * <p>Should return the name of the player even if they are not logged into the
     * server so long as the player has previously logged into the server. The
     * implementation may also provide the name of players who have not logged
     * in before, but this is not required or guaranteed to be the case.</p>
     *
     * @param playerName  The player name.
     *
     * @return  The player ID or null if not found.
     */
    @Nullable
    UUID getPlayerId(String playerName);

    /**
     * Get a player name from a unique player ID.
     *
     * <p>Should return the name of the player even if they are not logged into the
     * server so long as the player has previously logged into the server. The
     * implementation may also provide the name of players who have not logged
     * in before, but this is not required or guaranteed to be the case.</p>
     *
     * @param playerId  The ID of the player.
     *
     * @return  The player name or null if not found.
     */
    @Nullable
    String getPlayerName(UUID playerId);

    /**
     * Get the Date the player first logged in successfully.
     *
     * @param playerId  The ID of the player.
     *
     * @return  The date or null if the player was not found.
     */
    @Nullable
    Date getFirstLogin(UUID playerId);

    /**
     * Get the last Date the player logged in successfully.
     *
     * @param playerId  The ID of the player.
     *
     * @return  The date or null if the player was not found.
     */
    @Nullable
    Date getLastLogin(UUID playerId);

    /**
     * Get the number of times a player has successfully logged into the server.
     *
     * @param playerId  The ID of the player.
     *
     * @return The number of times or 0 if a player with the specified ID
     * was not found.
     */
    int getLoginCount(UUID playerId);

    /**
     * Search stored players for players whose name contains the specified search
     * text.
     *
     * @param searchText  The search text.
     * @param maxResults  The max number of results to return.
     *
     * @return  A collection of IDs of matched players.
     */
    IFutureResult<Collection<UUID>> searchNames(String searchText, int maxResults);
}
