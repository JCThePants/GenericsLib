/* This file is part of GenericsLib for Bukkit, licensed under the MIT License (MIT).
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


package com.jcwhatever.bukkit.generic.events.bukkit;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nullable;

public class AnvilItemRenameEvent extends Event {
	
	private static final HandlerList _handlers = new HandlerList();
	
	private Player _player;
	private AnvilInventory _anvilInventory;
	private ItemStack _item;
	private String _newName;
	private String _oldName;
	
	private boolean _isCancelled;
	
	
	AnvilItemRenameEvent(
            Player player, AnvilInventory anvilInventory, ItemStack item, String newName, @Nullable String oldName) {

		_player = player;
		_anvilInventory = anvilInventory;
		_item = item;
		_newName = newName;
		_oldName = oldName;
	}
	
	public Player getPlayer() {
		return _player;
	}
	
	public AnvilInventory getAnvilInventory() {
		return _anvilInventory;
	}
	
	public ItemStack getRenamedItem() {
		return _item;
	}
	
	public String getNewName() {
		return _newName;
	}
	
	public String getOldName() {
		return _oldName;
	}
	
	public boolean isCancelled() {
		return _isCancelled;
	}
	
	public void setIsCancelled(boolean isCancelled) {
		_isCancelled = isCancelled;
	}
	
	@Override
    public HandlerList getHandlers() {
	    return _handlers;
	}
	 
	public static HandlerList getHandlerList() {
	    return _handlers;
	}
	
	public static AnvilItemRenameEvent callEvent(
            Player player, AnvilInventory anvilInventory, ItemStack item, String newName, @Nullable String oldName) {
		AnvilItemRenameEvent event = new AnvilItemRenameEvent(player, anvilInventory, item, newName, oldName);
		
		if (hasListeners()) {
			Bukkit.getPluginManager().callEvent(event);
		}
		
		return event;
	}

	public static boolean hasListeners() {
		return _handlers.getRegisteredListeners().length > 0;
	}
}
