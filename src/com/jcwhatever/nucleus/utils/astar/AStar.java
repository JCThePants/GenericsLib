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

package com.jcwhatever.nucleus.utils.astar;

import com.jcwhatever.nucleus.utils.PreCon;
import com.jcwhatever.nucleus.utils.astar.AStarResult.AStarResultStatus;
import com.jcwhatever.nucleus.utils.astar.IAStarExaminer.PathableResult;
import com.jcwhatever.nucleus.utils.coords.Coords3Di;
import com.jcwhatever.nucleus.utils.coords.ICoords3Di;

/**
 * A-Star based pathfinder engine.
 *
 * @see AStarUtils
 */
public class AStar {

    private final IAStarExaminer _examiner;

    private double _range = 18;
    private int _maxDropHeight = 4;
    private long _maxIterations = 8500;

    /**
     * Constructor.
     *
     * @param examiner  The engines examiner.
     */
    public AStar(IAStarExaminer examiner, IAStarNodeFactory factory) {
        PreCon.notNull(examiner);

        _examiner = examiner;
    }

    /**
     * Get the examiner.
     */
    public IAStarExaminer getExaminer() {
        return _examiner;
    }

    /**
     * Get the search range.
     */
    public double getRange() {
        return _range;
    }

    /**
     * Get the search range squared.
     */
    public double getRangeSquared() {
        return _range * _range;
    }

    /**
     * Set the search range.
     *
     * @param range  The range.
     */
    public void setRange(double range) {
        _range = range;
    }

    /**
     * Get the max drop height.
     */
    public int getMaxDropHeight() {
        return _maxDropHeight;
    }

    /**
     * Set the max drop height.
     *
     * @param height  The max height.
     */
    public void setMaxDropHeight(int height) {
        _maxDropHeight = height;
    }

    /**
     * Get the max amount of iterations that can be performed.
     *
     * <p>A value of -1 indicates infinite iterations allowed.</p>
     */
    public long getMaxIterations() {
        return _maxIterations;
    }

    /**
     * Set the max amount of iterations that can be performed.
     *
     * @param max  The max amount. -1 for infinite.
     */
    public void setMaxIterations(long max) {
        _maxIterations = max;
    }

    /**
     * Search for a valid path from a start point to a destination point.
     *
     * @param start        The start coordinates.
     * @param destination  The destination coordinates.
     * @param container    The node container to use.
     */
    public AStarResult search(ICoords3Di start, ICoords3Di destination, IAStarNodeContainer container) {
        PreCon.notNull(start);
        PreCon.notNull(destination);

        if (Coords3Di.distanceSquared(start, destination) > getRangeSquared()) {
            return new AStarResult(AStarResultStatus.RANGE_EXCEEDED);
        }

        container.reset();

        AStarContext context = new AStarContext(this, container, start, destination);
        AStarNode startNode = new AStarNode(context, start);

        container.open(null, startNode);

        openAdjacent(startNode);

        AStarNode current = null;

        // iterate until destination is found
        // or unable to continue

        long iterations = 0;
        long maxIterations = getMaxIterations();

        while (getExaminer().canSearch(context)) {

            // get and close best candidate for next node to path to.
            current = container.closeBest();
            if (current == null || getExaminer().isDestination(current))
                break;

            // open valid adjacent nodes
            openAdjacent(current);

            iterations ++;

            // do not exceed max iterations
            if (maxIterations > 0 && iterations >= maxIterations) {
                return new AStarResult(AStarResultStatus.ITERATIONS_EXCEEDED);
            }
        }

        return new AStarResult(context, current);
    }

    /**
     * Invoked to search for and open valid adjacent nodes.
     *
     * @param node  The node to search around.
     */
    protected void openAdjacent(AStarNode node) {

        // column validations, work from top down, skip columns that are false
        boolean[][] columns = new boolean[][] {
                { true, true,  true },
                { true, false, true },
                { true, true,  true }
        };

        int dropHeight = -getMaxDropHeight();

        for (byte y = 1; y >= dropHeight; y--) {
            for (byte x = -1; x <= 1; x++) {
                for (byte z = -1; z <= 1; z++) {

                    openCandidate(node, x, y, z, columns);
                }
            }
        }
    }

    /**
     * Invoked to check a node candidate and, if valid, open it.
     *
     * @param parent   The parent path node.
     * @param x        The relative X position from the parent.
     * @param y        The relative Y position from the parent.
     * @param z        The relative Z position from the parent.
     * @param columns  Column validation array. A 3x3 array of booleans.
     */
    protected void openCandidate(AStarNode parent, int x, int y, int z, boolean[][] columns) {

        if (!columns[x + 1][z + 1])
            return;

        // get instance of candidate node
        AStarNode candidate = parent.getRelative(x, y, z);
        AStarContext context = parent.getContext();

        // check range
        if (Coords3Di.distanceSquared(candidate.getCoords(), context.getStartCoords()) > getRangeSquared()) {
            columns[x + 1][z + 1] = false;
            return;
        }

        PathableResult result = getExaminer().isPathable(parent, candidate);

        switch (result) {
            case VALID:
                context.getContainer().open(parent, candidate);
                // fall through, don't check columns where a valid node was already found.
            case INVALID_COLUMN:
                columns[x + 1][z + 1] = false;
                // fall through
            case INVALID_POINT:
                break;
        }
    }
}
