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

package com.jcwhatever.nucleus.internal.managed.nms;

import com.jcwhatever.nucleus.managed.reflection.IReflectedInstance;
import com.jcwhatever.nucleus.managed.reflection.IReflection;
import com.jcwhatever.nucleus.utils.nms.INmsParticleEffectHandler;
import com.jcwhatever.nucleus.utils.text.components.IChatMessage;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.InventoryView;
import org.bukkit.util.Vector;

import javax.annotation.Nullable;
import java.util.Collection;

/**
 * Interface for a lower level NMS handler designed for a specific
 * Minecraft version.
 */
interface INms {

    /**
     * Determine if the instance is available for use.
     */
    boolean isAvailable();

    /**
     * Set the instances availability state.
     */
    void setAvailable(boolean isAvailable);

    /**
     * Get reflection.
     */
    IReflection getReflection();

    /**
     * Send an NMS packet.
     *
     * @param player  The player to send the packet to.
     * @param packet  The packet to send.
     */
    void sendPacket(Player player, Object packet);

    /**
     * Send an NMS packet.
     *
     * @param connection  The connection of the player to send the packet to.
     * @param packet      The packet to send.
     */
    void sendPacket(IReflectedInstance connection, Object packet);

    /**
     * Get the players NMS connection.
     *
     * @param player The player.
     */
    IReflectedInstance getConnection(Player player);

    /**
     * Get the net.minecraft.server.EntityPlayer nms object of the player.
     *
     * @param player  The player.
     */
    IReflectedInstance getEntityPlayer(Player player);


    /**
     * Get a new title packet instance for setting title times.
     *
     * @param fadeIn   The fade in delay.
     * @param stay     The stay delay.
     * @param fadeOut  The fade out delay.
     */
    Object getTitlePacketTimes(int fadeIn, int stay, int fadeOut);

    /**
     * Get a new title packet instance for setting the sub title.
     *
     * @param subTitle  The sub title.
     */
    Object getTitlePacketSub(CharSequence subTitle);

    /**
     * Get a new title packet instance for setting the title.
     *
     * @param title  The title.
     */
    Object getTitlePacket(CharSequence title);

    /**
     * Get a new named sound effect packet instance.
     *
     * @param soundName  The name of the sound.
     * @param x          The source X coordinate.
     * @param y          The source Y coordinate.
     * @param z          The source Z coordinate.
     * @param volume     The volume.
     * @param pitch      The pitch.
     */
    Object getNamedSoundPacket(String soundName,
                                      double x, double y, double z, float volume, float pitch);

    /**
     * Get a new particle packet instance.
     *
     * @param particleType  The particle type.
     * @param force         True to force player to see the particle even if particle settings are on off.
     * @param x             The X coordinate.
     * @param y             The Y coordinate.
     * @param z             The Z coordinate
     * @param offsetX       The X data.
     * @param offsetY       The Y data.
     * @param offsetZ       The Z data.
     * @param data          Extra data.
     * @param count         The particle count.
     */
    Object getParticlePacket(
            INmsParticleEffectHandler.INmsParticleType particleType, boolean force,
            double x, double y, double z,
            double offsetX, double offsetY, double offsetZ,
            float data, int count);

    /**
     * Get a new list header footer title packet instance.
     *
     * @param headerText  The header text.
     * @param footerText  The footer text.
     */
    Object getHeaderFooterPacket(@Nullable CharSequence headerText, @Nullable CharSequence footerText);


    /**
     * Get a new action bar packet instance.
     *
     * @param text  The action bar text.
     */
    Object getActionBarPacket(CharSequence text);

    /**
     * Open an anvil view.
     *
     * @param player    The player to show the view to.
     * @param position  Option position of the anvil block.
     *
     * @return  The inventory view or null if could not show view or view was
     * cancelled by an event.
     */
    @Nullable
    InventoryView openAnvilInventory(Player player, @Nullable Block position);

    /**
     * Get a lightning packet.
     *
     * @param strikeLocation  The location of the lightning strike.
     */
    Object getLightningPacket(Location strikeLocation);


    /**
     * Determine if an entity is visible.
     *
     * @param entity  The entity.
     */
    boolean isEntityVisible(Entity entity);

    /**
     * Set an entities visibility state.
     *
     * @param entity     The entity to set.
     * @param isVisible  The visibility state.
     */
    void setEntityVisible(Entity entity, boolean isVisible);

    /**
     * Copy an entity velocity to the specified output vector.
     *
     * @param entity  The entity.
     * @param output  The output velocity.
     */
    void getVelocity(Entity entity, Vector output);

    /**
     * Set an entities yaw angle.
     *
     * @param entity  The entity.
     * @param yaw     The yaw angle.
     */
    void setYaw(Entity entity, float yaw);

    /**
     * Set an entities pitch angle.
     *
     * @param entity  The entity.
     * @param pitch   The pitch angle.
     */
    void setPitch(Entity entity, float pitch);

    /**
     * Set an entity's path step height.
     *
     * @param entity  The entity.
     * @param height  The step height.
     */
    void setStepHeight(Entity entity, float height);

    /**
     * Generate chat message from text.
     *
     * @param text  The text to convert.
     */
    IChatMessage getMessage(String text);

    /**
     * Send a chat message to a player.
     *
     * @param player   The player.
     * @param message  The chat message.
     */
    void send(Player player, IChatMessage message);

    /**
     * Send a chat message to a collection of players.
     *
     * @param players  The players.
     * @param message  The chat message.
     */
    void send(Collection<? extends Player> players, IChatMessage message);

    /**
     * Get the forward motion applied by the specified vehicle passenger.
     *
     * @param passenger  The passenger.
     */
    float getVehicleForwardMotion(LivingEntity passenger);

    /**
     * Set the forward motion applied by the specified vehicle passenger.
     *
     * @param passenger  The passenger.
     * @param value      The forward motion value. 0 is no motion, negative values are reverse,
     *                   positive values are forward.
     */
    void setVehicleForwardMotion(LivingEntity passenger, float value);

    /**
     * Get the lateral motion applied by the specified vehicle passenger.
     *
     * @param passenger  The passenger.
     */
    float getVehicleLateralMotion(LivingEntity passenger);

    /**
     * Set the lateral motion applied by the specified vehicle passenger.
     *
     * @param passenger  The passenger.
     * @param value      The lateral motion value. 0 is no motion, negative values are left,
     *                   positive values are right.
     */
    void setVehicleLateralMotion(LivingEntity passenger, float value);

    /**
     * Determine if the vehicle passenger has requested a vehicle jump by pressing Space button.
     *
     * @param passenger  The passenger.
     */
    boolean isVehicleJumpPressed(LivingEntity passenger);

    /**
     * Determine if the specified vehicle passenger is pressing the jump button (SPACE).
     *
     * @param passenger  The passenger.
     * @param isPressed  True to set jump flag to pressed, otherwise false.
     */
    void setVehicleJumpPressed(LivingEntity passenger, boolean isPressed);

    /**
     * Determine if the specified vehicle passenger is pressing the dismount button (L.SHIFT)
     *
     * @param passenger  The passenger.
     */
    boolean isVehicleDismountPressed(LivingEntity passenger);

    /**
     * Set the specified vehicle passenger dismount flag.
     *
     * @param passenger     The vehicle passenger.
     * @param isPressed  True to set dismount pressed, otherwise false.
     */
    void setVehicleDismountPressed(LivingEntity passenger, boolean isPressed);

    /**
     * Determine if the specified passenger is allowed to dismount vehicle.
     *
     * @param passenger  The passenger.
     */
    boolean canDismount(LivingEntity passenger);

    /**
     * Set flag that allows passenger to dismount vehicle.
     *
     * @param passenger    The passenger.
     * @param canDismount  True to allow dismounting from vehicle, otherwise false.
     */
    void setCanDismount(LivingEntity passenger, boolean canDismount);

    /**
     * Remove arrows from an entity.
     *
     * @param entity  The entity to remove arrows from.
     */
    void removeArrows(Entity entity);

    /**
     * Determine if arrows can get stuck in an entity.
     *
     * @param entity  The entity to check.
     */
    boolean canArrowsStick(Entity entity);

    /**
     * Set allow arrows to get stuck in an entity.
     *
     * @param entity     The entity.
     * @param isAllowed  True to allow arrows to get stuck.
     */
    void setCanArrowsStick(Entity entity, boolean isAllowed);
}
