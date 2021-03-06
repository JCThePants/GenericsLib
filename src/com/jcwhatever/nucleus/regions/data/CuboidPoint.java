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

package com.jcwhatever.nucleus.regions.data;

import com.jcwhatever.nucleus.providers.regionselect.IRegionSelection;
import com.jcwhatever.nucleus.regions.SimpleRegionSelection;
import com.jcwhatever.nucleus.utils.PreCon;

import org.bukkit.Location;

import javax.annotation.Nullable;

/**
 * Specifies one of 8 possible points in a cuboid region.
 *
 * <p>Each component in a points location is defined as MIN or MAX, the
 * smallest or largest value on the coordinates system.</p>
 *
 * <p>The coordinate order of the 3 MIN/MAX components in each constant
 * name is: X Y Z</p>
 */
public enum CuboidPoint {

    MIN_MIN_MIN(CuboidPointType.MIN, CuboidPointType.MIN, CuboidPointType.MIN),
    MIN_MAX_MIN(CuboidPointType.MIN, CuboidPointType.MAX, CuboidPointType.MIN),
    MIN_MAX_MAX(CuboidPointType.MIN, CuboidPointType.MAX, CuboidPointType.MAX),
    MIN_MIN_MAX(CuboidPointType.MIN, CuboidPointType.MIN, CuboidPointType.MAX),
    MAX_MIN_MIN(CuboidPointType.MAX, CuboidPointType.MIN, CuboidPointType.MIN),
    MAX_MAX_MIN(CuboidPointType.MAX, CuboidPointType.MAX, CuboidPointType.MIN),
    MAX_MAX_MAX(CuboidPointType.MAX, CuboidPointType.MAX, CuboidPointType.MAX),
    MAX_MIN_MAX(CuboidPointType.MAX, CuboidPointType.MIN, CuboidPointType.MAX);

    public enum CuboidPointType {
        MIN,
        MAX
    }

    private final CuboidPointType _x;
    private final CuboidPointType _y;
    private final CuboidPointType _z;

    CuboidPoint(CuboidPointType x, CuboidPointType y, CuboidPointType z) {
        _x = x;
        _y = y;
        _z = z;
    }

    /**
     * Get the {@link CuboidPoint} constants X coordinate type.
     */
    public CuboidPointType getTypeX() {
        return _x;
    }

    /**
     * Get the {@link CuboidPoint} constants Y coordinate type.
     */
    public CuboidPointType getTypeY() {
        return _y;
    }

    /**
     * Get the {@link CuboidPoint} constants Z coordinate type.
     */
    public CuboidPointType getTypeZ() {
        return _z;
    }

    /**
     * Get the location represented by the {@link CuboidPoint} from the
     * specified {@link SimpleRegionSelection}.
     *
     * @param selection  The region selection to get a location from.
     */
    public Location getLocation(IRegionSelection selection) {
        PreCon.notNull(selection);

        return new Location(selection.getWorld(), getX(selection), getY(selection), getZ(selection));
    }

    /**
     * Get the X coordinate represented by the {@link CuboidPoint} from the
     * specified {@link SimpleRegionSelection}.
     *
     * @param selection  The region selection to get an X coordinate from.
     */
    public int getX(IRegionSelection selection) {
        return getValue(_x, selection.getXStart(), selection.getXEnd());
    }

    /**
     * Get the Y coordinate represented by the {@link CuboidPoint} from the
     * specified {@link SimpleRegionSelection}.
     *
     * @param selection  The region selection to get a Y coordinate from.
     */
    public int getY(IRegionSelection selection) {
        return getValue(_y, selection.getYStart(), selection.getYEnd());
    }

    /**
     * Get the Z coordinate represented by the {@link CuboidPoint} from the
     * specified {@link SimpleRegionSelection}.
     *
     * @param selection  The region selection to get a Z coordinate from.
     */
    public int getZ(IRegionSelection selection) {
        return getValue(_z, selection.getZStart(), selection.getZEnd());
    }

    /**
     * Choose between two values the one that is best represented by
     * the specified {@link CuboidPointType}.
     *
     * @param type    The point type.
     * @param value1  The first coordinate value.
     * @param value2  The seconds coordinate value.
     */
    public static int getValue(CuboidPointType type, int value1, int value2) {
        PreCon.notNull(type);

        switch (type) {
            case MIN:
                return Math.min(value1, value2);
            case MAX:
                return Math.max(value1, value2);
            default:
                throw new AssertionError();
        }
    }

    /**
     * Get a {@link CuboidPoint} that represents the specified
     * location.
     *
     * @param location   The location to check.
     * @param selection  The region selection to check.
     *
     * @return  Null if the location is not any of the regions points.
     */
    @Nullable
    public static CuboidPoint getCuboidPoint(Location location, IRegionSelection selection) {

        for (CuboidPoint point : CuboidPoint.values()) {

            int pointX = point.getX(selection);
            int pointY = point.getY(selection);
            int pointZ = point.getZ(selection);

            if (location.getBlockX() == pointX &&
                    location.getBlockY() == pointY &&
                    location.getBlockZ() == pointZ) {
                return point;
            }
        }

        return null;
    }
}
