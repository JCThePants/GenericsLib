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

package com.jcwhatever.nucleus.collections.timed;

/**
 * Specifies the time scale of a time unit of
 * measure.
 */
public enum TimeScale {
    /**
     * Time scale is in milliseconds.
     */
    MILLISECONDS (1),
    /**
     * Time scale is in ticks. (50 milliseconds per unit)
     */
    TICKS        (50),
    /**
     * Time scale is in seconds.
     */
    SECONDS      (1000),
    /**
     * Time scale is in minutes.
     */
    MINUTES      (1000 * 60),
    /**
     * Time scale is in hours.
      */
    HOURS        (1000 * 60 * 60),
    /**
     * Time scale is in days.
     */
    DAYS         (1000 * 60 * 60 * 24);

    private final int _timeFactor;

    TimeScale (int timeFactor) {
        _timeFactor = timeFactor;
    }

    /**
     * Get the factor to apply to a unit of time
     * of the time scale to convert it to milliseconds.
     */
    public int getTimeFactor() {
        return _timeFactor;
    }
}
