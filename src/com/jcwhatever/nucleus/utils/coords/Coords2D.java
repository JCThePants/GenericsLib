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

package com.jcwhatever.nucleus.utils.coords;

import com.jcwhatever.nucleus.storage.DeserializeException;
import com.jcwhatever.nucleus.storage.IDataNode;
import com.jcwhatever.nucleus.storage.IDataNodeSerializable;
import com.jcwhatever.nucleus.utils.PreCon;
import com.jcwhatever.nucleus.utils.file.IBinarySerializable;
import com.jcwhatever.nucleus.utils.file.NucleusByteReader;
import com.jcwhatever.nucleus.utils.file.NucleusByteWriter;

import org.bukkit.Chunk;
import org.bukkit.World;

import java.io.IOException;

/**
 * 2D immutable coordinates.
 */
public class Coords2D implements IDataNodeSerializable, IBinarySerializable {

    /**
     * Get a {@link Coords2D} from a {@link org.bukkit.Chunk}.
     *
     * @param chunk  The chunk to convert.
     */
    public static Coords2D fromChunk(Chunk chunk) {
        return new Coords2D(chunk.getX(), chunk.getZ());
    }

    private double _x;
    private double _z;
    private boolean _isImmutable;

    /**
     * Constructor.
     *
     * @param x  The x coordinates.
     * @param z  The z coordinates.
     */
    public Coords2D(double x, double z) {
        _x = x;
        _z = z;
        seal();
    }

    /**
     * Constructor.
     *
     * <p>Clones values from source coordinates.</p>
     *
     * @param source  The source coordinates.
     */
    public Coords2D(Coords2D source) {
        this(source._x, source._z);
    }

    /**
     * Constructor.
     *
     * <p>Clones values from source coordinates and adds delta values.</p>
     *
     * @param source  The source coordinates.
     * @param deltaX  The X coordinate values to add to the source coordinates.
     * @param deltaZ  The Z coordinate values to add to the source coordinates.
     */
    public Coords2D(Coords2D source, double deltaX, double deltaZ) {
        this(source._x + deltaX, source._z + deltaZ);
    }

    /**
     * Protected constructor for serialization.
     */
    protected Coords2D() {}

    /**
     * Determine if object is immutable.
     */
    public boolean isImmutable() {
        return _isImmutable;
    }

    /**
     * Get the X coordinates.
     */
    public double getX() {
        return _x;
    }

    /**
     * Get the Z coordinates.
     */
    public double getZ() {
        return _z;
    }

    /**
     * Get the X coordinate as a floored integer whole number.
     */
    public int getFloorX() {
        return getFloorValue(_x);
    }

    /**
     * Get the Z coordinate as a floored integer whole number.
     */
    public int getFloorZ() {
        return getFloorValue(_z);
    }

    /**
     * Get the distance from this coordinates to another coordinates.
     *
     * @param coords  The other coordinates.
     */
    public double distance(Coords2D coords) {
        PreCon.notNull(coords);

        return Math.sqrt(distanceSquared(coords));
    }

    /**
     * Get the distance from this coordinates to another coordinates.
     *
     * @param coords  The other coordinates.
     */
    public double distance(Coords2Di coords) {
        PreCon.notNull(coords);

        return Math.sqrt(distanceSquared(coords));
    }

    /**
     * Get the distance from this coordinates to another coordinates squared.
     *
     * @param coords  The other coordinates.
     */
    public double distanceSquared(Coords2D coords) {
        PreCon.notNull(coords);

        double deltaX = coords._x - _x;
        double deltaZ = coords._z - _z;

        return deltaX * deltaX + deltaZ * deltaZ;
    }

    /**
     * Get the distance from this coordinates to another coordinates squared.
     *
     * @param coords  The other coordinates.
     */
    public double distanceSquared(Coords2Di coords) {
        PreCon.notNull(coords);

        double deltaX = coords.getX() - _x;
        double deltaZ = coords.getZ() - _z;

        return deltaX * deltaX + deltaZ * deltaZ;
    }

    /**
     * Create delta coordinates by subtracting other coordinates from
     * this coordinates.
     *
     * @param coords  The other coordinates.
     */
    public Coords2D getDelta(Coords2D coords) {
        PreCon.notNull(coords);

        double deltaX = getX() - coords.getX();
        double deltaZ = getZ() - coords.getZ();

        return new Coords2D(deltaX, deltaZ);
    }

    /**
     * Create delta coordinates by subtracting other coordinates from
     * this coordinates.
     *
     * @param coords  The other coordinates.
     * @param output  The {@link MutableCoords2D} to put the results into.
     *
     * @return  The output {@link MutableCoords2D}.
     */
    public MutableCoords2D getDelta(Coords2D coords, MutableCoords2D output) {
        PreCon.notNull(coords);

        double deltaX = getX() - coords.getX();
        double deltaZ = getZ() - coords.getZ();

        output.setX(deltaX);
        output.setZ(deltaZ);

        return output;
    }

    /**
     * Get a {@link org.bukkit.Chunk} from the specified {@link org.bukkit.World}
     * at the coordinates represented by the {@link Coords2D} instance.
     *
     * @param world  The world the chunk is in.
     */
    public Chunk getChunk(World world) {
        PreCon.notNull(world);

        return world.getChunkAt(getFloorX(), getFloorZ());
    }

    /**
     * Create a new {@link Coords2Di} instance using the floor
     * values.
     */
    public Coords2Di to2Di() {
        return new Coords2Di(getFloorX(), getFloorZ());
    }

    /**
     * Create a new {@link Coords3D} instance using the x and
     * z coordinate values and the specified y coordinate values.
     *
     * @param y  The coordinate value.
     */
    public Coords3D to3D(double y) {
        return new Coords3D(getX(), y, getZ());
    }

    /**
     * Create a new {@link Coords3Di} instance using the x and
     * z coordinate values and the specified y coordinate values.
     *
     * @param y  The coordinate value.
     */
    public Coords3Di to3Di(int y) {
        return new Coords3Di(getFloorX(), y, getFloorZ());
    }

    @Override
    public void serialize(IDataNode dataNode) {
        dataNode.set("x", _x);
        dataNode.set("z", _z);
    }

    @Override
    public void deserialize(IDataNode dataNode) throws DeserializeException {
        _x = dataNode.getDouble("x");
        _z = dataNode.getDouble("z");
        seal();
    }

    @Override
    public void serializeToBytes(NucleusByteWriter writer) throws IOException {
        writer.write(_x);
        writer.write(_z);
    }

    @Override
    public void deserializeFromBytes(NucleusByteReader reader)
            throws IOException, ClassNotFoundException, InstantiationException {

        _x = reader.getDouble();
        _z = reader.getDouble();
        seal();
    }

    @Override
    public int hashCode() {
        return (int)_x ^ (int)_z;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;

        if (obj instanceof Coords2D) {
            Coords2D other = (Coords2D)obj;

            return other._x == _x &&
                    other._z == _z;
        }

        return false;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + " { x:" + _x + ", z:" + _z + '}';
    }

    /**
     * Set the X coordinate.
     *
     * @param x  The X coordinate.
     *
     * @throws java.lang.IllegalStateException if the object is immutable.
     */
    protected void setX(double x) {
        if (_isImmutable)
            throw new IllegalStateException("Coordinate is immutable.");

        _x = x;
    }

    /**
     * Set the Z coordinate.
     *
     * @param z  The Z coordinate.
     *
     * @throws java.lang.IllegalStateException if the object is immutable.
     */
    protected void setZ(double z) {
        if (_isImmutable)
            throw new IllegalStateException("Coordinate is immutable.");

        _z = z;
    }

    /**
     * Invoked to make the object immutable.
     */
    protected void seal() {
        _isImmutable = true;
    }

    /**
     * Calculate a floor value.
     *
     * @param value  The value to floor.
     *
     * @return  The floored value.
     */
    protected int getFloorValue(double value) {
        int floor = (int) value;
        return (double) floor == value
                ? floor
                : floor - (int) (Double.doubleToRawLongBits(value) >>> 63);
    }
}
