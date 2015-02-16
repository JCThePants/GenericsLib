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

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.material.MaterialData;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;

/**
 * Sign utilities
 */
public final class SignUtils {

    private SignUtils() {}

    /**
     * Get the facing direction of a sign.
     *
     * @param block  The block that is a sign.
     *
     * @return  Null if the block is not a sign.
     */
    @Nullable
    public static BlockFace getSignFacing(Block block) {
        PreCon.notNull(block);

        Sign sign = getSign(block);
        if (sign == null)
            return null;
        return getSignFacing(sign);
    }

    /**
     * Get the facing direction of a sign.
     *
     * @param sign  The sign to check.
     */
    public static BlockFace getSignFacing(Sign sign) {
        PreCon.notNull(sign);

        MaterialData materialData = sign.getData();

        org.bukkit.material.Sign matSign = (org.bukkit.material.Sign)materialData;

        return matSign.getFacing();
    }

    /**
     * Set the direction a sign is facing.
     *
     * @param block   The block that is a sign.
     * @param face    The new facing direction.
     * @param update  True to update the block.
     *
     * @return  True if the block is a sign and the direction is set.
     */
    public static boolean setSignFacing(Block block, BlockFace face, boolean update) {
        PreCon.notNull(block);
        PreCon.notNull(face);

        Sign sign = getSign(block);
        return sign != null && setSignFacing(sign, face, update);
    }

    /**
     * Set the direction a sign is facing.
     *
     * @param sign    The sign to change.
     * @param face    The new facing direction.
     * @param update  True to update the block.
     *
     * @return True if the sign is updated or true if update parameter is false.
     */
    public static boolean setSignFacing(Sign sign, BlockFace face, boolean update) {
        PreCon.notNull(sign);
        PreCon.notNull(face);

        MaterialData materialData = sign.getData();

        org.bukkit.material.Sign matSign = (org.bukkit.material.Sign)materialData;

        matSign.setFacingDirection(face);

        return !update || sign.update(true);
    }

    /**
     * Get the attached face direction of a sign.
     *
     * @param block  The block that is a sign.
     *
     * @return  Null if the block is not a sign.
     */
    @Nullable
    public static BlockFace getSignAttachedFace(Block block) {
        PreCon.notNull(block);

        Sign sign = getSign(block);
        if (sign == null)
            return null;

        return getSignAttachedFace(sign);
    }

    /**
     * Get the attached face direction of a sign.
     *
     * @param sign  The sign to check.
     */
    public static BlockFace getSignAttachedFace(Sign sign) {
        MaterialData materialData = sign.getData();

        org.bukkit.material.Sign matSign = (org.bukkit.material.Sign)materialData;

        return matSign.getAttachedFace();
    }

    /**
     * Create a new {@link org.bukkit.material.MaterialData} instance representing a sign.
     *
     * @param type    The material type. Must be a sign type.
     * @param facing  The facing direction of the sign.
     */
    public static MaterialData createSignData(Material type, BlockFace facing) {
        PreCon.notNull(type);
        PreCon.notNull(facing);

        org.bukkit.material.Sign matSign = new org.bukkit.material.Sign(type);
        matSign.setFacingDirection(facing);
        return matSign;
    }

    /**
     * Set the text lines of a block that is a sign.
     *
     * @param block  The block that is a sign.
     * @param lines  The lines to set. There must be exactly 4 lines provided.
     */
    public static void setLines(Block block, String... lines) {
        PreCon.notNull(block);
        PreCon.notNull(lines);
        PreCon.isValid(lines.length == 4);

        Sign sign = getSign(block);
        if (sign == null)
            return;

        setLines(sign, lines);
    }

    /**
     * Set the text lines of a sign.
     *
     * @param sign   The sign to change.
     * @param lines  The lines to set. There must be exactly 4 lines provided.
     */
    public static void setLines(Sign sign, String... lines) {
        PreCon.notNull(sign);
        PreCon.notNull(lines);
        PreCon.isValid(lines.length == 4);

        for (int i=0; i < lines.length && i < 4; i++) {
            sign.setLine(i, lines[i]);
        }
    }

    /**
     * Get the sign block state from a block.
     *
     * @param block  The block that is a sign.
     *
     * @return  Null if the block is not a sign.
     */
    @Nullable
    public static Sign getSign(Block block) {
        PreCon.notNull(block);

        BlockState state = block.getState();
        return (state instanceof Sign) ? (Sign)state : null;
    }

    /**
     * Get the sign adjacent of the provided block in the specified
     * direction.
     *
     * @param source     The block to look from.
     * @param direction  The direction to look.
     *
     * @return  Null if there is no sign adjacent.
     */
    @Nullable
    public static Sign getAdjacentSign(Block source, BlockFace direction) {
        PreCon.notNull(source);
        PreCon.notNull(direction);

        BlockState state = source.getRelative(direction).getState();
        if (!(state instanceof Sign))
            return null;

        return (Sign)state;
    }

    /**
     * Gets all signs consecutively adjacent to the source block in the
     * specified direction. Does not include source block in result.
     *
     * @param source     The block to look from.
     * @param direction  The direction to look.
     * @return
     */
    public static List<Sign> getAdjacentSigns(Block source, BlockFace direction) {
        PreCon.notNull(source);
        PreCon.notNull(direction);

        ArrayList<Sign> signs = new ArrayList<>(15);

        BlockState state = source.getState();
        while ((state = state.getBlock().getRelative(direction).getState()) instanceof Sign) {
            signs.add((Sign)state);
        }

        return signs;
    }

    /**
     * Get the block a sign is attached to.
     *
     * @param sign  The sign to check.
     */
    public static Block getSignAttachedBlock(Sign sign) {
        PreCon.notNull(sign);

        org.bukkit.material.Sign matSign = (org.bukkit.material.Sign)sign.getBlock().getState().getData();
        return sign.getBlock().getRelative(matSign.getAttachedFace());
    }

    /**
     * Get the number of consecutively adjacent signs next to a
     * source block in the specified direction. Resulting number
     * does not include the source block.
     *
     * @param source     The block to look from.
     * @param direction  The direction to look.
     * @return
     */
    public static int getSignCount(Block source, BlockFace direction) {
        int i = 0;
        BlockState state = source.getState();
        while ((state = state.getBlock().getRelative(direction).getState()) instanceof Sign) {
            i++;
        }
        return i;
    }

    /**
     * Get the most recent {@link org.bukkit.block.Sign} block state using the
     * outdated {@link org.bukkit.block.Sign} block state.
     *
     * @param sign  The outdated sign block state.
     *
     * @return  Null if the block is no longer a sign.
     */
    @Nullable
    public static Sign getRecent(Sign sign) {
        PreCon.notNull(sign);

        BlockState state = sign.getLocation().getBlock().getState();

        return (state instanceof Sign) ? (Sign)state : null;
    }
}
