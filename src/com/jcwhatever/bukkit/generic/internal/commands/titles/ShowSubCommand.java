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

package com.jcwhatever.bukkit.generic.internal.commands.titles;

import com.jcwhatever.bukkit.generic.GenericsLib;
import com.jcwhatever.bukkit.generic.commands.AbstractCommand;
import com.jcwhatever.bukkit.generic.commands.CommandInfo;
import com.jcwhatever.bukkit.generic.commands.arguments.CommandArguments;
import com.jcwhatever.bukkit.generic.commands.exceptions.InvalidCommandSenderException;
import com.jcwhatever.bukkit.generic.commands.exceptions.InvalidCommandSenderException.CommandSenderType;
import com.jcwhatever.bukkit.generic.commands.exceptions.InvalidValueException;
import com.jcwhatever.bukkit.generic.internal.Lang;
import com.jcwhatever.bukkit.generic.language.Localizable;
import com.jcwhatever.bukkit.generic.titles.INamedTitle;
import com.jcwhatever.bukkit.generic.utils.PlayerUtils;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandInfo(
        parent="titles",
        command="show",
        staticParams={ "titleName", "playerName=$self" },
        usage="/{plugin-command} {command} show <titleName> [playerName]",
        description="Show a title to yourself or the specified player.")

public class ShowSubCommand extends AbstractCommand {

    @Localizable static final String _PLAYER_NOT_FOUND = "A player named '{0}' was not found.";
    @Localizable static final String _TITLE_NOT_FOUND = "A title named '{0}' was not found.";

    @Override
    public void execute(CommandSender sender, CommandArguments args)
            throws InvalidValueException, InvalidCommandSenderException {

        String name = args.getName("titleName", 32);
        String playerName = args.getString("playerName");

        Player player;

        if (playerName.equals("$self")) {

            InvalidCommandSenderException.check(sender, CommandSenderType.PLAYER);

            player = (Player)sender;
        }
        else {
            player = PlayerUtils.getPlayer(playerName);
            if (player == null) {
                tellError(sender,  Lang.get(_PLAYER_NOT_FOUND, playerName));
                return; // finished
            }
        }

        INamedTitle title = GenericsLib.getTitleManager().getTitle(name);
        if (title == null) {
            tellError(sender, Lang.get(_TITLE_NOT_FOUND, name));
            return; // finished
        }

        title.showTo(player);
    }
}
