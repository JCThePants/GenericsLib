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

import com.jcwhatever.nucleus.collections.wrap.WrappedArrayList;
import com.jcwhatever.nucleus.utils.converters.IConverter;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nullable;
import java.lang.reflect.Array;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Array utilities.
 */
public final class ArrayUtils {

    private ArrayUtils() {}

    public static final boolean[] EMPTY_BOOLEAN_ARRAY = new boolean[0];
    public static final byte[] EMPTY_BYTE_ARRAY = new byte[0];
    public static final char[] EMPTY_CHAR_ARRAY = new char[0];
    public static final short[] EMPTY_SHORT_ARRAY = new short[0];
    public static final int[] EMPTY_INT_ARRAY = new int[0];
    public static final long[] EMPTY_LONG_ARRAY = new long[0];
    public static final float[] EMPTY_FLOAT_ARRAY = new float[0];
    public static final double[] EMPTY_DOUBLE_ARRAY = new double[0];
    public static final String[] EMPTY_STRING_ARRAY = new String[0];
    public static final ItemStack[] EMPTY_ITEMSTACK_ARRAY = new ItemStack[0];
    public static final Entity[] EMPTY_ENTITY_ARRAY = new Entity[0];
    public static final Object[] EMPTY_OBJECT_ARRAY = new Object[0];

    /**
     * Combine to arrays into a single array.
     *
     * @param array1  The first array.
     * @param array2  The seconds array.
     *
     * @return  A new array consisting of elements from both arrays.
     */
    public static <T> T[] addAll(T[] array1, T[] array2) {
        PreCon.notNull(array1);
        PreCon.notNull(array2);

        int len = array1.length + array2.length;

        @SuppressWarnings("unchecked")
        Class<T> componentClass = (Class<T>) array1.getClass().getComponentType();

        T[] result = newArray(componentClass, len);

        for (int i=0, j=0; i < len; i++) {
            if (i < array1.length) {
                result[i] = array1[i];
            }
            else {
                result[i] = array2[j];
                j++;
            }
        }

        return result;
    }

    /**
     * Copy the source array elements to the destination array starting from the
     * beginning and ending when either the destination runs out of space or the
     * source runs out of elements.
     *
     * @param source       The source array.
     * @param destination  The destination array.
     *
     * @param <T>  The array component type.
     *
     * @return  The destination array.
     */
    public static <T> T[] copyFromStart(T[] source, T[] destination) {
        PreCon.notNull(source);
        PreCon.notNull(destination);

        int size = Math.min(source.length, destination.length);

        System.arraycopy(source, 0, destination, 0, size);

        return destination;
    }

    /**
     * Copy the source array elements to the destination array starting from the
     * beginning and ending when either the destination runs out of space or the
     * source runs out of elements.
     *
     * @param source       The source array.
     * @param destination  The destination array.
     *
     * @return  The destination array.
     */
    public static boolean[] copyFromStart(boolean[] source, boolean[] destination) {
        PreCon.notNull(source);
        PreCon.notNull(destination);

        int size = Math.min(source.length, destination.length);

        System.arraycopy(source, 0, destination, 0, size);

        return destination;
    }

    /**
     * Copy the source array elements to the destination array starting from the
     * beginning and ending when either the destination runs out of space or the
     * source runs out of elements.
     *
     * @param source       The source array.
     * @param destination  The destination array.
     *
     * @return  The destination array.
     */
    public static byte[] copyFromStart(byte[] source, byte[] destination) {
        PreCon.notNull(source);
        PreCon.notNull(destination);

        int size = Math.min(source.length, destination.length);

        System.arraycopy(source, 0, destination, 0, size);

        return destination;
    }

    /**
     * Copy the source array elements to the destination array starting from the
     * beginning and ending when either the destination runs out of space or the
     * source runs out of elements.
     *
     * @param source       The source array.
     * @param destination  The destination array.
     *
     * @return  The destination array.
     */
    public static char[] copyFromStart(char[] source, char[] destination) {
        PreCon.notNull(source);
        PreCon.notNull(destination);

        int size = Math.min(source.length, destination.length);

        System.arraycopy(source, 0, destination, 0, size);

        return destination;
    }

    /**
     * Copy the source array elements to the destination array starting from the
     * beginning and ending when either the destination runs out of space or the
     * source runs out of elements.
     *
     * @param source       The source array.
     * @param destination  The destination array.
     *
     * @return  The destination array.
     */
    public static short[] copyFromStart(short[] source, short[] destination) {
        PreCon.notNull(source);
        PreCon.notNull(destination);

        int size = Math.min(source.length, destination.length);

        System.arraycopy(source, 0, destination, 0, size);

        return destination;
    }

    /**
     * Copy the source array elements to the destination array starting from the
     * beginning and ending when either the destination runs out of space or the
     * source runs out of elements.
     *
     * @param source       The source array.
     * @param destination  The destination array.
     *
     * @return  The destination array.
     */
    public static int[] copyFromStart(int[] source, int[] destination) {
        PreCon.notNull(source);
        PreCon.notNull(destination);

        int size = Math.min(source.length, destination.length);

        System.arraycopy(source, 0, destination, 0, size);

        return destination;
    }

    /**
     * Copy the source array elements to the destination array starting from the
     * beginning and ending when either the destination runs out of space or the
     * source runs out of elements.
     *
     * @param source       The source array.
     * @param destination  The destination array.
     *
     * @return  The destination array.
     */
    public static long[] copyFromStart(long[] source, long[] destination) {
        PreCon.notNull(source);
        PreCon.notNull(destination);

        int size = Math.min(source.length, destination.length);

        System.arraycopy(source, 0, destination, 0, size);

        return destination;
    }

    /**
     * Copy the source array elements to the destination array starting from the
     * beginning and ending when either the destination runs out of space or the
     * source runs out of elements.
     *
     * @param source       The source array.
     * @param destination  The destination array.
     *
     * @return  The destination array.
     */
    public static float[] copyFromStart(float[] source, float[] destination) {
        PreCon.notNull(source);
        PreCon.notNull(destination);

        int size = Math.min(source.length, destination.length);

        System.arraycopy(source, 0, destination, 0, size);

        return destination;
    }

    /**
     * Copy the source array elements to the destination array starting from the
     * beginning and ending when either the destination runs out of space or the
     * source runs out of elements.
     *
     * @param source       The source array.
     * @param destination  The destination array.
     *
     * @return  The destination array.
     */
    public static double[] copyFromStart(double[] source, double[] destination) {
        PreCon.notNull(source);
        PreCon.notNull(destination);

        int size = Math.min(source.length, destination.length);

        System.arraycopy(source, 0, destination, 0, size);

        return destination;
    }

    /**
     * Copy the source array elements to the destination array starting from the end
     * and finishing when either the destination runs out of space or the source runs
     * out of elements.
     *
     * @param source       The source array.
     * @param destination  The destination array.
     *
     * @param <T>  The array component type.
     *
     * @return  The destination array.
     */
    public static <T> T[] copyFromEnd(T[] source, T[] destination) {
        PreCon.notNull(source);
        PreCon.notNull(destination);

        int delta = source.length - destination.length;
        int sourceAdjust = delta < 0 ?  0 : delta;
        int destAdjust = delta < 0 ? -delta : 0;

        System.arraycopy(source, sourceAdjust, destination, destAdjust, destination.length - destAdjust);

        return destination;
    }

    /**
     * Copy the source array elements to the destination array starting from the end
     * and finishing when either the destination runs out of space or the source runs
     * out of elements.
     *
     * @param source       The source array.
     * @param destination  The destination array.
     *
     * @return  The destination array.
     */
    public static boolean[] copyFromEnd(boolean[] source, boolean[] destination) {
        PreCon.notNull(source);
        PreCon.notNull(destination);

        int delta = source.length - destination.length;
        int sourceAdjust = delta < 0 ?  0 : delta;
        int destAdjust = delta < 0 ? -delta : 0;

        System.arraycopy(source, sourceAdjust, destination, destAdjust, destination.length - destAdjust);

        return destination;
    }

    /**
     * Copy the source array elements to the destination array starting from the end
     * and finishing when either the destination runs out of space or the source runs
     * out of elements.
     *
     * @param source       The source array.
     * @param destination  The destination array.
     *
     * @return  The destination array.
     */
    public static byte[] copyFromEnd(byte[] source, byte[] destination) {
        PreCon.notNull(source);
        PreCon.notNull(destination);

        int delta = source.length - destination.length;
        int sourceAdjust = delta < 0 ?  0 : delta;
        int destAdjust = delta < 0 ? -delta : 0;

        System.arraycopy(source, sourceAdjust, destination, destAdjust, destination.length - destAdjust);

        return destination;
    }

    /**
     * Copy the source array elements to the destination array starting from the end
     * and finishing when either the destination runs out of space or the source runs
     * out of elements.
     *
     * @param source       The source array.
     * @param destination  The destination array.
     *
     * @return  The destination array.
     */
    public static char[] copyFromEnd(char[] source, char[] destination) {
        PreCon.notNull(source);
        PreCon.notNull(destination);

        int delta = source.length - destination.length;
        int sourceAdjust = delta < 0 ?  0 : delta;
        int destAdjust = delta < 0 ? -delta : 0;

        System.arraycopy(source, sourceAdjust, destination, destAdjust, destination.length - destAdjust);

        return destination;
    }

    /**
     * Copy the source array elements to the destination array starting from the end
     * and finishing when either the destination runs out of space or the source runs
     * out of elements.
     *
     * @param source       The source array.
     * @param destination  The destination array.
     *
     * @return  The destination array.
     */
    public static short[] copyFromEnd(short[] source, short[] destination) {
        PreCon.notNull(source);
        PreCon.notNull(destination);

        int delta = source.length - destination.length;
        int sourceAdjust = delta < 0 ?  0 : delta;
        int destAdjust = delta < 0 ? -delta : 0;

        System.arraycopy(source, sourceAdjust, destination, destAdjust, destination.length - destAdjust);

        return destination;
    }

    /**
     * Copy the source array elements to the destination array starting from the end
     * and finishing when either the destination runs out of space or the source runs
     * out of elements.
     *
     * @param source       The source array.
     * @param destination  The destination array.
     *
     * @return  The destination array.
     */
    public static int[] copyFromEnd(int[] source, int[] destination) {
        PreCon.notNull(source);
        PreCon.notNull(destination);

        int delta = source.length - destination.length;
        int sourceAdjust = delta < 0 ?  0 : delta;
        int destAdjust = delta < 0 ? -delta : 0;

        System.arraycopy(source, sourceAdjust, destination, destAdjust, destination.length - destAdjust);

        return destination;
    }

    /**
     * Copy the source array elements to the destination array starting from the end
     * and finishing when either the destination runs out of space or the source runs
     * out of elements.
     *
     * @param source       The source array.
     * @param destination  The destination array.
     *
     * @return  The destination array.
     */
    public static long[] copyFromEnd(long[] source, long[] destination) {
        PreCon.notNull(source);
        PreCon.notNull(destination);

        int delta = source.length - destination.length;
        int sourceAdjust = delta < 0 ?  0 : delta;
        int destAdjust = delta < 0 ? -delta : 0;

        System.arraycopy(source, sourceAdjust, destination, destAdjust, destination.length - destAdjust);

        return destination;
    }

    /**
     * Copy the source array elements to the destination array starting from the end
     * and finishing when either the destination runs out of space or the source runs
     * out of elements.
     *
     * @param source       The source array.
     * @param destination  The destination array.
     *
     * @return  The destination array.
     */
    public static float[] copyFromEnd(float[] source, float[] destination) {
        PreCon.notNull(source);
        PreCon.notNull(destination);

        int delta = source.length - destination.length;
        int sourceAdjust = delta < 0 ?  0 : delta;
        int destAdjust = delta < 0 ? -delta : 0;

        System.arraycopy(source, sourceAdjust, destination, destAdjust, destination.length - destAdjust);

        return destination;
    }

    /**
     * Copy the source array elements to the destination array starting from the end
     * and finishing when either the destination runs out of space or the source runs
     * out of elements.
     *
     * @param source       The source array.
     * @param destination  The destination array.
     *
     * @return  The destination array.
     */
    public static double[] copyFromEnd(double[] source, double[] destination) {
        PreCon.notNull(source);
        PreCon.notNull(destination);

        int delta = source.length - destination.length;
        int sourceAdjust = delta < 0 ?  0 : delta;
        int destAdjust = delta < 0 ? -delta : 0;

        System.arraycopy(source, sourceAdjust, destination, destAdjust, destination.length - destAdjust);

        return destination;
    }

    /**
     * Reduce the size of an array by trimming from the beginning and end of the array.
     *
     * @param startAmountToRemove  The number of elements to remove from the start of the array.
     * @param array                The array to trim.
     * @param endAmountToRemove    The number of elements to remove from the end of the array.
     *
     * @param <T>  The component type.
     *
     * @return  A new trimmed array.
     */
    public static <T> T[] reduce(int startAmountToRemove, T[] array, int endAmountToRemove) {
        PreCon.positiveNumber(startAmountToRemove);
        PreCon.notNull(array);
        PreCon.positiveNumber(endAmountToRemove);
        PreCon.isValid(startAmountToRemove + endAmountToRemove <= array.length,
                "Amount to remove is larger than the array.");

        @SuppressWarnings("unchecked")
        Class<T> componentClass = (Class<T>) array.getClass().getComponentType();

        int size = array.length - (startAmountToRemove + endAmountToRemove);

        if (size == 0)
            return newArray(componentClass, 0);

        T[] newArray = newArray(componentClass, size);

        System.arraycopy(array, startAmountToRemove, newArray, 0, newArray.length);

        return newArray;
    }

    /**
     * Reduce the size of an array by trimming from the beginning and end of the array.
     *
     * @param startAmountToRemove  The number of elements to remove from the start of the array.
     * @param array                The array to trim.
     * @param endAmountToRemove    The number of elements to remove from the end of the array.
     *
     * @return  A new trimmed array.
     */
    public static boolean[] reduce(int startAmountToRemove, boolean[] array, int endAmountToRemove) {
        PreCon.positiveNumber(startAmountToRemove);
        PreCon.notNull(array);
        PreCon.positiveNumber(endAmountToRemove);
        PreCon.isValid(startAmountToRemove + endAmountToRemove <= array.length,
                "Amount to remove is larger than the array.");

        int size = array.length - (startAmountToRemove + endAmountToRemove);

        if (size == 0)
            return EMPTY_BOOLEAN_ARRAY;

        boolean[] newArray = new boolean[size];

        System.arraycopy(array, startAmountToRemove, newArray, 0, newArray.length);

        return newArray;
    }

    /**
     * Reduce the size of an array by trimming from the beginning and end of the array.
     *
     * @param startAmountToRemove  The number of elements to remove from the start of the array.
     * @param array                The array to trim.
     * @param endAmountToRemove    The number of elements to remove from the end of the array.
     *
     * @return  A new trimmed array.
     */
    public static byte[] reduce(int startAmountToRemove, byte[] array, int endAmountToRemove) {
        PreCon.positiveNumber(startAmountToRemove);
        PreCon.notNull(array);
        PreCon.positiveNumber(endAmountToRemove);
        PreCon.isValid(startAmountToRemove + endAmountToRemove <= array.length,
                "Amount to remove is larger than the array.");

        int size = array.length - (startAmountToRemove + endAmountToRemove);

        if (size == 0)
            return EMPTY_BYTE_ARRAY;

        byte[] newArray = new byte[size];

        System.arraycopy(array, startAmountToRemove, newArray, 0, newArray.length);

        return newArray;
    }

    /**
     * Reduce the size of an array by trimming from the beginning and end of the array.
     *
     * @param startAmountToRemove  The number of elements to remove from the start of the array.
     * @param array                The array to trim.
     * @param endAmountToRemove    The number of elements to remove from the end of the array.
     *
     * @return  A new trimmed array.
     */
    public static char[] reduce(int startAmountToRemove, char[] array, int endAmountToRemove) {
        PreCon.positiveNumber(startAmountToRemove);
        PreCon.notNull(array);
        PreCon.positiveNumber(endAmountToRemove);
        PreCon.isValid(startAmountToRemove + endAmountToRemove <= array.length,
                "Amount to remove is larger than the array.");

        int size = array.length - (startAmountToRemove + endAmountToRemove);

        if (size == 0)
            return EMPTY_CHAR_ARRAY;

        char[] newArray = new char[size];

        System.arraycopy(array, startAmountToRemove, newArray, 0, newArray.length);

        return newArray;
    }

    /**
     * Reduce the size of an array by trimming from the beginning and end of the array.
     *
     * @param startAmountToRemove  The number of elements to remove from the start of the array.
     * @param array                The array to trim.
     * @param endAmountToRemove    The number of elements to remove from the end of the array.
     *
     * @return  A new trimmed array.
     */
    public static short[] reduce(int startAmountToRemove, short[] array, int endAmountToRemove) {
        PreCon.positiveNumber(startAmountToRemove);
        PreCon.notNull(array);
        PreCon.positiveNumber(endAmountToRemove);
        PreCon.isValid(startAmountToRemove + endAmountToRemove <= array.length,
                "Amount to remove is larger than the array.");

        int size = array.length - (startAmountToRemove + endAmountToRemove);

        if (size == 0)
            return EMPTY_SHORT_ARRAY;

        short[] newArray = new short[size];

        System.arraycopy(array, startAmountToRemove, newArray, 0, newArray.length);

        return newArray;
    }

    /**
     * Reduce the size of an array by trimming from the beginning and end of the array.
     *
     * @param startAmountToRemove  The number of elements to remove from the start of the array.
     * @param array                The array to trim.
     * @param endAmountToRemove    The number of elements to remove from the end of the array.
     *
     * @return  A new trimmed array.
     */
    public static int[] reduce(int startAmountToRemove, int[] array, int endAmountToRemove) {
        PreCon.positiveNumber(startAmountToRemove);
        PreCon.notNull(array);
        PreCon.positiveNumber(endAmountToRemove);
        PreCon.isValid(startAmountToRemove + endAmountToRemove <= array.length,
                "Amount to remove is larger than the array.");

        int size = array.length - (startAmountToRemove + endAmountToRemove);

        if (size == 0)
            return EMPTY_INT_ARRAY;

        int[] newArray = new int[size];

        System.arraycopy(array, startAmountToRemove, newArray, 0, newArray.length);

        return newArray;
    }

    /**
     * Reduce the size of an array by trimming from the beginning and end of the array.
     *
     * @param startAmountToRemove  The number of elements to remove from the start of the array.
     * @param array                The array to trim.
     * @param endAmountToRemove    The number of elements to remove from the end of the array.
     *
     * @return  A new trimmed array.
     */
    public static long[] reduce(int startAmountToRemove, long[] array, int endAmountToRemove) {
        PreCon.positiveNumber(startAmountToRemove);
        PreCon.notNull(array);
        PreCon.positiveNumber(endAmountToRemove);
        PreCon.isValid(startAmountToRemove + endAmountToRemove <= array.length,
                "Amount to remove is larger than the array.");

        int size = array.length - (startAmountToRemove + endAmountToRemove);

        if (size == 0)
            return EMPTY_LONG_ARRAY;

        long[] newArray = new long[size];

        System.arraycopy(array, startAmountToRemove, newArray, 0, newArray.length);

        return newArray;
    }

    /**
     * Reduce the size of an array by trimming from the beginning and end of the array.
     *
     * @param startAmountToRemove  The number of elements to remove from the start of the array.
     * @param array                The array to trim.
     * @param endAmountToRemove    The number of elements to remove from the end of the array.
     *
     * @return  A new trimmed array.
     */
    public static float[] reduce(int startAmountToRemove, float[] array, int endAmountToRemove) {
        PreCon.positiveNumber(startAmountToRemove);
        PreCon.notNull(array);
        PreCon.positiveNumber(endAmountToRemove);
        PreCon.isValid(startAmountToRemove + endAmountToRemove <= array.length,
                "Amount to remove is larger than the array.");

        int size = array.length - (startAmountToRemove + endAmountToRemove);

        if (size == 0)
            return EMPTY_FLOAT_ARRAY;

        float[] newArray = new float[size];

        System.arraycopy(array, startAmountToRemove, newArray, 0, newArray.length);

        return newArray;
    }

    /**
     * Reduce the size of an array by trimming from the beginning and end of the array.
     *
     * @param startAmountToRemove  The number of elements to remove from the start of the array.
     * @param array                The array to trim.
     * @param endAmountToRemove    The number of elements to remove from the end of the array.
     *
     * @return  A new trimmed array.
     */
    public static double[] reduce(int startAmountToRemove, double[] array, int endAmountToRemove) {
        PreCon.positiveNumber(startAmountToRemove);
        PreCon.notNull(array);
        PreCon.positiveNumber(endAmountToRemove);
        PreCon.isValid(startAmountToRemove + endAmountToRemove <= array.length,
                "Amount to remove is larger than the array.");

        int size = array.length - (startAmountToRemove + endAmountToRemove);

        if (size == 0)
            return EMPTY_DOUBLE_ARRAY;

        double[] newArray = new double[size];

        System.arraycopy(array, startAmountToRemove - 1, newArray, 0, newArray.length);

        return newArray;
    }

    /**
     * Reduce the size of an array by trimming from the beginning and end of the array.
     *
     * @param startAmountToRemove  The number of elements to remove from the start of the array.
     * @param array                The array to trim.
     * @param endAmountToRemove    The number of elements to remove from the end of the array.
     *
     * @return  A new trimmed array.
     */
    public static String[] reduce(int startAmountToRemove, String[] array, int endAmountToRemove) {
        PreCon.positiveNumber(startAmountToRemove);
        PreCon.notNull(array);
        PreCon.positiveNumber(endAmountToRemove);
        PreCon.isValid(startAmountToRemove + endAmountToRemove <= array.length,
                "Amount to remove is larger than the array.");

        int size = array.length - (startAmountToRemove + endAmountToRemove);

        if (size == 0)
            return EMPTY_STRING_ARRAY;

        String[] newArray = new String[size];

        System.arraycopy(array, startAmountToRemove, newArray, 0, newArray.length);

        return newArray;
    }

    /**
     * Reduce the size of an array by trimming from the beginning and end of the array.
     *
     * @param startAmountToRemove  The number of elements to remove from the start of the array.
     * @param array                The array to trim.
     * @param endAmountToRemove    The number of elements to remove from the end of the array.
     *
     * @return  A new trimmed array.
     */
    public static ItemStack[] reduce(int startAmountToRemove, ItemStack[] array, int endAmountToRemove) {
        PreCon.positiveNumber(startAmountToRemove);
        PreCon.notNull(array);
        PreCon.positiveNumber(endAmountToRemove);
        PreCon.isValid(startAmountToRemove + endAmountToRemove <= array.length,
                "Amount to remove is larger than the array.");

        int size = array.length - (startAmountToRemove + endAmountToRemove);

        if (size == 0)
            return EMPTY_ITEMSTACK_ARRAY;

        ItemStack[] newArray = new ItemStack[size];

        System.arraycopy(array, startAmountToRemove, newArray, 0, newArray.length);

        return newArray;
    }

    /**
     * Reduce the size of an array by trimming from the beginning and end of the array.
     *
     * @param startAmountToRemove  The number of elements to remove from the start of the array.
     * @param array                The array to trim.
     * @param endAmountToRemove    The number of elements to remove from the end of the array.
     *
     * @return  A new trimmed array.
     */
    public static Entity[] reduce(int startAmountToRemove, Entity[] array, int endAmountToRemove) {
        PreCon.positiveNumber(startAmountToRemove);
        PreCon.notNull(array);
        PreCon.positiveNumber(endAmountToRemove);
        PreCon.isValid(startAmountToRemove + endAmountToRemove <= array.length,
                "Amount to remove is larger than the array.");

        int size = array.length - (startAmountToRemove + endAmountToRemove);

        if (size == 0)
            return EMPTY_ENTITY_ARRAY;

        Entity[] newArray = new Entity[size];

        System.arraycopy(array, startAmountToRemove, newArray, 0, newArray.length);

        return newArray;
    }

    /**
     * Reduce the size of an array by trimming from the beginning of the array.
     *
     * @param amountToRemove  The number of elements to remove.
     * @param array           The array to trim.
     *
     * @param <T>  The component type.
     *
     * @return A new trimmed array.
     */
    public static <T> T[] reduceStart(int amountToRemove, T[] array) {
        PreCon.notNull(array);
        PreCon.isValid(amountToRemove <= array.length, "Amount to remove is larger than the array.");

        @SuppressWarnings("unchecked")
        Class<T> componentClass = (Class<T>) array.getClass().getComponentType();

        if (array.length == amountToRemove)
            return newArray(componentClass, 0);

        int size = array.length - amountToRemove;

        T[] result = newArray(componentClass, size);

        return copyFromEnd(array, result);
    }

    /**
     * Reduce the size of an array by trimming from the beginning of the array.
     *
     * @param amountToRemove  The number of elements to remove.
     * @param array           The array to trim.
     *
     * @return A new trimmed array.
     */
    public static boolean[] reduceStart(int amountToRemove, boolean[] array) {
        PreCon.notNull(array);
        PreCon.isValid(amountToRemove <= array.length, "Amount to remove is larger than the array.");

        if (array.length == amountToRemove)
            return EMPTY_BOOLEAN_ARRAY;

        int size = array.length - amountToRemove;

        return copyFromEnd(array, new boolean[size]);
    }

    /**
     * Reduce the size of an array by trimming from the beginning of the array.
     *
     * @param amountToRemove  The number of elements to remove.
     * @param array           The array to trim.
     *
     * @return A new trimmed array.
     */
    public static byte[] reduceStart(int amountToRemove, byte[] array) {
        PreCon.notNull(array);
        PreCon.isValid(amountToRemove <= array.length, "Amount to remove is larger than the array.");

        if (array.length == amountToRemove)
            return EMPTY_BYTE_ARRAY;

        int size = array.length - amountToRemove;

        return copyFromEnd(array, new byte[size]);
    }

    /**
     * Reduce the size of an array by trimming from the beginning of the array.
     *
     * @param amountToRemove  The number of elements to remove.
     * @param array           The array to trim.
     *
     * @return A new trimmed array.
     */
    public static char[] reduceStart(int amountToRemove, char[] array) {
        PreCon.notNull(array);
        PreCon.isValid(amountToRemove <= array.length, "Amount to remove is larger than the array.");

        if (array.length == amountToRemove)
            return EMPTY_CHAR_ARRAY;

        int size = array.length - amountToRemove;

        return copyFromEnd(array, new char[size]);
    }

    /**
     * Reduce the size of an array by trimming from the beginning of the array.
     *
     * @param amountToRemove  The number of elements to remove.
     * @param array           The array to trim.
     *
     * @return A new trimmed array.
     */
    public static short[] reduceStart(int amountToRemove, short[] array) {
        PreCon.notNull(array);
        PreCon.isValid(amountToRemove <= array.length, "Amount to remove is larger than the array.");

        if (array.length == amountToRemove)
            return EMPTY_SHORT_ARRAY;

        int size = array.length - amountToRemove;

        return copyFromEnd(array, new short[size]);
    }

    /**
     * Reduce the size of an array by trimming from the beginning of the array.
     *
     * @param amountToRemove  The number of elements to remove.
     * @param array           The array to trim.
     *
     * @return A new trimmed array.
     */
    public static int[] reduceStart(int amountToRemove, int[] array) {
        PreCon.notNull(array);
        PreCon.isValid(amountToRemove <= array.length, "Amount to remove is larger than the array.");

        if (array.length == amountToRemove)
            return EMPTY_INT_ARRAY;

        int size = array.length - amountToRemove;

        return copyFromEnd(array, new int[size]);
    }

    /**
     * Reduce the size of an array by trimming from the beginning of the array.
     *
     * @param amountToRemove  The number of elements to remove.
     * @param array           The array to trim.
     *
     * @return A new trimmed array.
     */
    public static long[] reduceStart(int amountToRemove, long[] array) {
        PreCon.notNull(array);
        PreCon.isValid(amountToRemove <= array.length, "Amount to remove is larger than the array.");

        if (array.length == amountToRemove)
            return EMPTY_LONG_ARRAY;

        int size = array.length - amountToRemove;

        return copyFromEnd(array, new long[size]);
    }

    /**
     * Reduce the size of an array by trimming from the beginning of the array.
     *
     * @param amountToRemove  The number of elements to remove.
     * @param array           The array to trim.
     *
     * @return A new trimmed array.
     */
    public static float[] reduceStart(int amountToRemove, float[] array) {
        PreCon.notNull(array);
        PreCon.isValid(amountToRemove <= array.length, "Amount to remove is larger than the array.");

        if (array.length == amountToRemove)
            return EMPTY_FLOAT_ARRAY;

        int size = array.length - amountToRemove;

        return copyFromEnd(array, new float[size]);
    }

    /**
     * Reduce the size of an array by trimming from the beginning of the array.
     *
     * @param amountToRemove  The number of elements to remove.
     * @param array           The array to trim.
     *
     * @return A new trimmed array.
     */
    public static double[] reduceStart(int amountToRemove, double[] array) {
        PreCon.notNull(array);
        PreCon.isValid(amountToRemove <= array.length, "Amount to remove is larger than the array.");

        if (array.length == amountToRemove)
            return EMPTY_DOUBLE_ARRAY;

        int size = array.length - amountToRemove;

        return copyFromEnd(array, new double[size]);
    }

    /**
     * Reduce the size of an array by trimming from the beginning of the array.
     *
     * @param amountToRemove  The number of elements to remove.
     * @param array           The array to trim.
     *
     * @return A new trimmed array.
     */
    public static String[] reduceStart(int amountToRemove, String[] array) {
        PreCon.notNull(array);
        PreCon.isValid(amountToRemove <= array.length, "Amount to remove is larger than the array.");

        if (array.length == amountToRemove)
            return EMPTY_STRING_ARRAY;

        int size = array.length - amountToRemove;

        return copyFromEnd(array, new String[size]);
    }

    /**
     * Reduce the size of an array by trimming from the beginning of the array.
     *
     * @param amountToRemove  The number of elements to remove.
     * @param array           The array to trim.
     *
     * @return A new trimmed array.
     */
    public static ItemStack[] reduceStart(int amountToRemove, ItemStack[] array) {
        PreCon.notNull(array);
        PreCon.isValid(amountToRemove <= array.length, "Amount to remove is larger than the array.");

        if (array.length == amountToRemove)
            return EMPTY_ITEMSTACK_ARRAY;

        int size = array.length - amountToRemove;

        return copyFromEnd(array, new ItemStack[size]);
    }

    /**
     * Reduce the size of an array by trimming from the beginning of the array.
     *
     * @param amountToRemove  The number of elements to remove.
     * @param array           The array to trim.
     *
     * @return A new trimmed array.
     */
    public static Entity[] reduceStart(int amountToRemove, Entity[] array) {
        PreCon.notNull(array);
        PreCon.isValid(amountToRemove <= array.length, "Amount to remove is larger than the array.");

        if (array.length == amountToRemove)
            return EMPTY_ENTITY_ARRAY;

        int size = array.length - amountToRemove;

        return copyFromEnd(array, new Entity[size]);
    }

    /**
     * Reduce the size of an array by trimming from the end of the array.
     *
     * @param array           The array to trim.
     * @param amountToRemove  The number of elements to remove.
     *
     * @param <T>  The array component type.
     *
     * @return  A new trimmed array.
     */
    public static <T> T[] reduceEnd(T[] array, int amountToRemove) {
        PreCon.notNull(array);
        PreCon.isValid(amountToRemove <= array.length, "Amount to remove is larger than the array.");

        @SuppressWarnings("unchecked")
        Class<T> componentClass = (Class<T>) array.getClass().getComponentType();

        if (array.length == amountToRemove)
            return newArray(componentClass, 0);

        int size = array.length - amountToRemove;

        T[] result = newArray(componentClass, size);

        return copyFromStart(array, result);
    }

    /**
     * Reduce the size of an array by trimming from the end of the array.
     *
     * @param array           The array to trim.
     * @param amountToRemove  The number of elements to remove.
     *
     * @return  A new trimmed array.
     */
    public static boolean[] reduceEnd(boolean[] array, int amountToRemove) {
        PreCon.notNull(array);
        PreCon.isValid(amountToRemove <= array.length, "Amount to remove is larger than the array.");

        if (array.length == amountToRemove)
            return EMPTY_BOOLEAN_ARRAY;

        int size = array.length - amountToRemove;

        return copyFromStart(array, new boolean[size]);
    }

    /**
     * Reduce the size of an array by trimming from the end of the array.
     *
     * @param array           The array to trim.
     * @param amountToRemove  The number of elements to remove.
     *
     * @return  A new trimmed array.
     */
    public static byte[] reduceEnd(byte[] array, int amountToRemove) {
        PreCon.notNull(array);
        PreCon.isValid(amountToRemove <= array.length, "Amount to remove is larger than the array.");

        if (array.length == amountToRemove)
            return EMPTY_BYTE_ARRAY;

        int size = array.length - amountToRemove;

        return copyFromStart(array, new byte[size]);
    }

    /**
     * Reduce the size of an array by trimming from the end of the array.
     *
     * @param array           The array to trim.
     * @param amountToRemove  The number of elements to remove.
     *
     * @return  A new trimmed array.
     */
    public static char[] reduceEnd(char[] array, int amountToRemove) {
        PreCon.notNull(array);
        PreCon.isValid(amountToRemove <= array.length, "Amount to remove is larger than the array.");

        if (array.length == amountToRemove)
            return EMPTY_CHAR_ARRAY;

        int size = array.length - amountToRemove;

        return copyFromStart(array, new char[size]);
    }

    /**
     * Reduce the size of an array by trimming from the end of the array.
     *
     * @param array           The array to trim.
     * @param amountToRemove  The number of elements to remove.
     *
     * @return  A new trimmed array.
     */
    public static short[] reduceEnd(short[] array, int amountToRemove) {
        PreCon.notNull(array);
        PreCon.isValid(amountToRemove <= array.length, "Amount to remove is larger than the array.");

        if (array.length == amountToRemove)
            return EMPTY_SHORT_ARRAY;

        int size = array.length - amountToRemove;

        return copyFromStart(array, new short[size]);
    }

    /**
     * Reduce the size of an array by trimming from the end of the array.
     *
     * @param array           The array to trim.
     * @param amountToRemove  The number of elements to remove.
     *
     * @return  A new trimmed array.
     */
    public static int[] reduceEnd(int[] array, int amountToRemove) {
        PreCon.notNull(array);
        PreCon.isValid(amountToRemove <= array.length, "Amount to remove is larger than the array.");

        if (array.length == amountToRemove)
            return EMPTY_INT_ARRAY;

        int size = array.length - amountToRemove;

        return copyFromStart(array, new int[size]);
    }

    /**
     * Reduce the size of an array by trimming from the end of the array.
     *
     * @param array           The array to trim.
     * @param amountToRemove  The number of elements to remove.
     *
     * @return  A new trimmed array.
     */
    public static long[] reduceEnd(long[] array, int amountToRemove) {
        PreCon.notNull(array);
        PreCon.isValid(amountToRemove <= array.length, "Amount to remove is larger than the array.");

        if (array.length == amountToRemove)
            return EMPTY_LONG_ARRAY;

        int size = array.length - amountToRemove;

        return copyFromStart(array, new long[size]);
    }

    /**
     * Reduce the size of an array by trimming from the end of the array.
     *
     * @param array           The array to trim.
     * @param amountToRemove  The number of elements to remove.
     *
     * @return  A new trimmed array.
     */
    public static float[] reduceEnd(float[] array, int amountToRemove) {
        PreCon.notNull(array);
        PreCon.isValid(amountToRemove <= array.length, "Amount to remove is larger than the array.");

        if (array.length == amountToRemove)
            return EMPTY_FLOAT_ARRAY;

        int size = array.length - amountToRemove;

        return copyFromStart(array, new float[size]);
    }

    /**
     * Reduce the size of an array by trimming from the end of the array.
     *
     * @param array           The array to trim.
     * @param amountToRemove  The number of elements to remove.
     *
     * @return  A new trimmed array.
     */
    public static double[] reduceEnd(double[] array, int amountToRemove) {
        PreCon.notNull(array);
        PreCon.isValid(amountToRemove <= array.length, "Amount to remove is larger than the array.");

        if (array.length == amountToRemove)
            return EMPTY_DOUBLE_ARRAY;

        int size = array.length - amountToRemove;

        return copyFromStart(array, new double[size]);
    }

    /**
     * Reduce the size of an array by trimming from the end of the array.
     *
     * @param array           The array to trim.
     * @param amountToRemove  The number of elements to remove.
     *
     * @return  A new trimmed array.
     */
    public static String[] reduceEnd(String[] array, int amountToRemove) {
        PreCon.notNull(array);
        PreCon.isValid(amountToRemove <= array.length, "Amount to remove is larger than the array.");

        if (array.length == amountToRemove)
            return EMPTY_STRING_ARRAY;

        int size = array.length - amountToRemove;

        return copyFromStart(array, new String[size]);
    }

    /**
     * Reduce the size of an array by trimming from the end of the array.
     *
     * @param array           The array to trim.
     * @param amountToRemove  The number of elements to remove.
     *
     * @return  A new trimmed array.
     */
    public static ItemStack[] reduceEnd(ItemStack[] array, int amountToRemove) {
        PreCon.notNull(array);
        PreCon.isValid(amountToRemove <= array.length, "Amount to remove is larger than the array.");

        if (array.length == amountToRemove)
            return EMPTY_ITEMSTACK_ARRAY;

        int size = array.length - amountToRemove;

        return copyFromStart(array, new ItemStack[size]);
    }

    /**
     * Reduce the size of an array by trimming from the end of the array.
     *
     * @param array           The array to trim.
     * @param amountToRemove  The number of elements to remove.
     *
     * @return  A new trimmed array.
     */
    public static Entity[] reduceEnd(Entity[] array, int amountToRemove) {
        PreCon.notNull(array);
        PreCon.isValid(amountToRemove <= array.length, "Amount to remove is larger than the array.");

        if (array.length == amountToRemove)
            return EMPTY_ENTITY_ARRAY;

        int size = array.length - amountToRemove;

        return copyFromStart(array, new Entity[size]);
    }

    /**
     * Convert a primitive wrapper array to a primitive array.
     *
     * @param array  The array to convert.
     *
     * @return A new primitive array.
     */
    public static boolean[] toPrimitive(Boolean[] array) {
        PreCon.notNull(array);

        if (array.length == 0)
            return EMPTY_BOOLEAN_ARRAY;

        boolean[] newArray = new boolean[array.length];

        for (int i=0; i < array.length; i++) {
            Boolean element = array[i];
            newArray[i] = element == null ? false : element;
        }

        return newArray;
    }

    /**
     * Convert a primitive wrapper array to a primitive array.
     *
     * @param array  The array to convert.
     *
     * @return A new primitive array.
     */
    public static byte[] toPrimitive(Byte[] array) {
        PreCon.notNull(array);

        if (array.length == 0)
            return EMPTY_BYTE_ARRAY;

        byte[] newArray = new byte[array.length];

        for (int i=0; i < array.length; i++) {
            Byte element = array[i];
            newArray[i] = element == null ? 0 : element;
        }

        return newArray;
    }

    /**
     * Convert a primitive wrapper array to a primitive array.
     *
     * @param array  The array to convert.
     *
     * @return A new primitive array.
     */
    public static char[] toPrimitive(Character[] array) {
        PreCon.notNull(array);

        if (array.length == 0)
            return EMPTY_CHAR_ARRAY;

        char[] newArray = new char[array.length];

        for (int i=0; i < array.length; i++) {
            Character element = array[i];
            newArray[i] = element == null ? 0 : element;
        }

        return newArray;
    }

    /**
     * Convert a primitive wrapper array to a primitive array.
     *
     * @param array  The array to convert.
     *
     * @return A new primitive array.
     */
    public static short[] toPrimitive(Short[] array) {
        PreCon.notNull(array);

        if (array.length == 0)
            return EMPTY_SHORT_ARRAY;

        short[] newArray = new short[array.length];

        for (int i=0; i < array.length; i++) {
            Short element = array[i];
            newArray[i] = element == null ? 0 : element;
        }

        return newArray;
    }

    /**
     * Convert a primitive wrapper array to a primitive array.
     *
     * @param array  The array to convert.
     *
     * @return A new primitive array.
     */
    public static int[] toPrimitive(Integer[] array) {
        PreCon.notNull(array);

        if (array.length == 0)
            return EMPTY_INT_ARRAY;

        int[] newArray = new int[array.length];

        for (int i=0; i < array.length; i++) {
            Integer element = array[i];
            newArray[i] = element == null ? 0 : element;
        }

        return newArray;
    }

    /**
     * Convert a primitive wrapper array to a primitive array.
     *
     * @param array  The array to convert.
     *
     * @return A new primitive array.
     */
    public static long[] toPrimitive(Long[] array) {
        PreCon.notNull(array);

        if (array.length == 0)
            return EMPTY_LONG_ARRAY;

        long[] newArray = new long[array.length];

        for (int i=0; i < array.length; i++) {
            Long element = array[i];
            newArray[i] = element == null ? 0L : element;
        }

        return newArray;
    }

    /**
     * Convert a primitive wrapper array to a primitive array.
     *
     * @param array  The array to convert.
     *
     * @return A new primitive array.
     */
    public static float[] toPrimitive(Float[] array) {
        PreCon.notNull(array);

        if (array.length == 0)
            return EMPTY_FLOAT_ARRAY;

        float[] newArray = new float[array.length];

        for (int i=0; i < array.length; i++) {
            Float element = array[i];
            newArray[i] = element == null ? 0F : element;
        }

        return newArray;
    }

    /**
     * Convert a primitive wrapper array to a primitive array.
     *
     * @param array  The array to convert.
     *
     * @return A new primitive array.
     */
    public static double[] toPrimitive(Double[] array) {
        PreCon.notNull(array);

        if (array.length == 0)
            return EMPTY_DOUBLE_ARRAY;

        double[] newArray = new double[array.length];

        for (int i=0; i < array.length; i++) {
            Double element = array[i];
            newArray[i] = element == null ? 0 : element;
        }

        return newArray;
    }

    /**
     * Convert a primitive array to a primitive wrapper array.
     *
     * @param array  The array to convert.
     *
     * @return A new primitive wrapper array.
     */
    public static Boolean[] toWrapper(boolean[] array) {
        PreCon.notNull(array);

        Boolean[] newArray = new Boolean[array.length];

        for (int i=0; i < array.length; i++) {
            newArray[i] = array[i];
        }

        return newArray;
    }

    /**
     * Convert a primitive array to a primitive wrapper array.
     *
     * @param array  The array to convert.
     *
     * @return A new primitive wrapper array.
     */
    public static Byte[] toWrapper(byte[] array) {
        PreCon.notNull(array);

        Byte[] newArray = new Byte[array.length];

        for (int i=0; i < array.length; i++) {
            newArray[i] = array[i];
        }

        return newArray;
    }

    /**
     * Convert a primitive array to a primitive wrapper array.
     *
     * @param array  The array to convert.
     *
     * @return A new primitive wrapper array.
     */
    public static Character[] toWrapper(char[] array) {
        PreCon.notNull(array);

        Character[] newArray = new Character[array.length];

        for (int i=0; i < array.length; i++) {
            newArray[i] = array[i];
        }

        return newArray;
    }

    /**
     * Convert a primitive array to a primitive wrapper array.
     *
     * @param array  The array to convert.
     *
     * @return A new primitive wrapper array.
     */
    public static Short[] toWrapper(short[] array) {
        PreCon.notNull(array);

        Short[] newArray = new Short[array.length];

        for (int i=0; i < array.length; i++) {
            newArray[i] = array[i];
        }

        return newArray;
    }

    /**
     * Convert a primitive array to a primitive wrapper array.
     *
     * @param array  The array to convert.
     *
     * @return A new primitive wrapper array.
     */
    public static Integer[] toWrapper(int[] array) {
        PreCon.notNull(array);

        Integer[] newArray = new Integer[array.length];

        for (int i=0; i < array.length; i++) {
            newArray[i] = array[i];
        }

        return newArray;
    }

    /**
     * Convert a primitive array to a primitive wrapper array.
     *
     * @param array  The array to convert.
     *
     * @return A new primitive wrapper array.
     */
    public static Long[] toWrapper(long[] array) {
        PreCon.notNull(array);

        Long[] newArray = new Long[array.length];

        for (int i=0; i < array.length; i++) {
            newArray[i] = array[i];
        }

        return newArray;
    }

    /**
     * Convert a primitive array to a primitive wrapper array.
     *
     * @param array  The array to convert.
     *
     * @return A new primitive wrapper array.
     */
    public static Float[] toWrapper(float[] array) {
        PreCon.notNull(array);

        Float[] newArray = new Float[array.length];

        for (int i=0; i < array.length; i++) {
            newArray[i] = array[i];
        }

        return newArray;
    }

    /**
     * Convert a primitive array to a primitive wrapper array.
     *
     * @param array  The array to convert.
     *
     * @return A new primitive wrapper array.
     */
    public static Double[] toWrapper(double[] array) {
        PreCon.notNull(array);

        Double[] newArray = new Double[array.length];

        for (int i=0; i < array.length; i++) {
            newArray[i] = array[i];
        }

        return newArray;
    }

    /**
     * Get the element at the specified index of the array
     * or return the default value if the array is smaller
     * than the specified index.
     *
     * @param array         The array to get an element from.
     * @param index         The index of the element to get.
     * @param defaultValue  The default value if the array isn't large enough.
     *
     * @param <T>  The array component type.
     *
     * @return  The element at the specified index or the default value.
     */
    public static <T> T get(T[] array, int index, T defaultValue) {
        PreCon.notNull(array);

        return (index > array.length - 1)
                ? defaultValue
                : array[index];
    }

    /**
     * Get the element at the specified index of the array
     * or return the default value if the array is smaller
     * than the specified index.
     *
     * @param array         The array to get an element from.
     * @param index         The index of the element to get.
     * @param defaultValue  The default value if the array isn't large enough.
     *
     * @return  The element at the specified index or the default value.
     */
    public static boolean get(boolean[] array, int index, boolean defaultValue) {
        PreCon.notNull(array);

        return (index > array.length - 1)
                ? defaultValue
                : array[index];
    }

    /**
     * Get the element at the specified index of the array
     * or return the default value if the array is smaller
     * than the specified index.
     *
     * @param array         The array to get an element from.
     * @param index         The index of the element to get.
     * @param defaultValue  The default value if the array isn't large enough.
     *
     * @return  The element at the specified index or the default value.
     */
    public static byte get(byte[] array, int index, byte defaultValue) {
        PreCon.notNull(array);

        return (index > array.length - 1)
                ? defaultValue
                : array[index];
    }

    /**
     * Get the element at the specified index of the array
     * or return the default value if the array is smaller
     * than the specified index.
     *
     * @param array         The array to get an element from.
     * @param index         The index of the element to get.
     * @param defaultValue  The default value if the array isn't large enough.
     *
     * @return  The element at the specified index or the default value.
     */
    public static short get(short[] array, int index, short defaultValue) {
        PreCon.notNull(array);

        return (index > array.length - 1)
                ? defaultValue
                : array[index];
    }

    /**
     * Get the element at the specified index of the array
     * or return the default value if the array is smaller
     * than the specified index.
     *
     * @param array         The array to get an element from.
     * @param index         The index of the element to get.
     * @param defaultValue  The default value if the array isn't large enough.
     *
     * @return  The element at the specified index or the default value.
     */
    public static char get(char[] array, int index, char defaultValue) {
        PreCon.notNull(array);

        return (index > array.length - 1)
                ? defaultValue
                : array[index];
    }

    /**
     * Get the element at the specified index of the array
     * or return the default value if the array is smaller
     * than the specified index.
     *
     * @param array         The array to get an element from.
     * @param index         The index of the element to get.
     * @param defaultValue  The default value if the array isn't large enough.
     *
     * @return  The element at the specified index or the default value.
     */
    public static int get(int[] array, int index, int defaultValue) {
        PreCon.notNull(array);

        return (index > array.length - 1)
                ? defaultValue
                : array[index];
    }

    /**
     * Get the element at the specified index of the array
     * or return the default value if the array is smaller
     * than the specified index.
     *
     * @param array         The array to get an element from.
     * @param index         The index of the element to get.
     * @param defaultValue  The default value if the array isn't large enough.
     *
     * @return  The element at the specified index or the default value.
     */
    public static long get(long[] array, int index, long defaultValue) {
        PreCon.notNull(array);

        return (index > array.length - 1)
                ? defaultValue
                : array[index];
    }

    /**
     * Get the element at the specified index of the array
     * or return the default value if the array is smaller
     * than the specified index.
     *
     * @param array         The array to get an element from.
     * @param index         The index of the element to get.
     * @param defaultValue  The default value if the array isn't large enough.
     *
     * @return  The element at the specified index or the default value.
     */
    public static float get(float[] array, int index, float defaultValue) {
        PreCon.notNull(array);

        return (index > array.length - 1)
                ? defaultValue
                : array[index];
    }

    /**
     * Get the element at the specified index of the array
     * or return the default value if the array is smaller
     * than the specified index.
     *
     * @param array         The array to get an element from.
     * @param index         The index of the element to get.
     * @param defaultValue  The default value if the array isn't large enough.
     *
     * @return  The element at the specified index or the default value.
     */
    public static double get(double[] array, int index, double defaultValue) {
        PreCon.notNull(array);

        return (index > array.length - 1)
                ? defaultValue
                : array[index];
    }

    /**
     * Get the last element in an array.
     *
     * @param array  The array.
     *
     * @param <T>  The array component type.
     */
    public static <T> T last(T[] array) {
        PreCon.notNull(array);
        PreCon.isValid(array.length > 0, "Array has no elements.");

        return array[array.length - 1];
    }

    /**
     * Get the last element in an array.
     *
     * @param array  The array.
     *
     * @param <T>  The array component type.
     */
    public static <T> T last(T[] array, T empty) {
        PreCon.notNull(array);

        if (array.length == 0)
            return empty;

        return array[array.length - 1];
    }

    /**
     * Get the last element in an array.
     *
     * @param array  The array.
     */
    public static boolean last(boolean[] array) {
        PreCon.notNull(array);
        PreCon.isValid(array.length > 0, "Array has no elements.");

        return array[array.length - 1];
    }

    /**
     * Get the last element in an array.
     *
     * @param array  The array.
     * @param empty  The value to return if the array is empty.
     */
    public static boolean last(boolean[] array, boolean empty) {
        PreCon.notNull(array);

        if (array.length == 0)
            return empty;

        return array[array.length - 1];
    }

    /**
     * Get the last element in an array.
     *
     * @param array  The array.
     */
    public static byte last(byte[] array) {
        PreCon.notNull(array);
        PreCon.isValid(array.length > 0, "Array has no elements.");

        return array[array.length - 1];
    }

    /**
     * Get the last element in an array.
     *
     * @param array  The array.
     * @param empty  The value to return if the array is empty.
     */
    public static byte last(byte[] array, byte empty) {
        PreCon.notNull(array);

        if (array.length == 0)
            return empty;

        return array[array.length - 1];
    }

    /**
     * Get the last element in an array.
     *
     * @param array  The array.
     */
    public static char last(char[] array) {
        PreCon.notNull(array);
        PreCon.isValid(array.length > 0, "Array has no elements.");

        return array[array.length - 1];
    }

    /**
     * Get the last element in an array.
     *
     * @param array  The array.
     * @param empty  The value to return if the array is empty.
     */
    public static char last(char[] array, char empty) {
        PreCon.notNull(array);

        if (array.length == 0)
            return empty;

        return array[array.length - 1];
    }

    /**
     * Get the last element in an array.
     *
     * @param array  The array.
     */
    public static short last(short[] array) {
        PreCon.notNull(array);
        PreCon.isValid(array.length > 0, "Array has no elements.");

        return array[array.length - 1];
    }

    /**
     * Get the last element in an array.
     *
     * @param array  The array.
     * @param empty  The value to return if the array is empty.
     */
    public static short last(short[] array, short empty) {
        PreCon.notNull(array);

        if (array.length == 0)
            return empty;

        return array[array.length - 1];
    }

    /**
     * Get the last element in an array.
     *
     * @param array  The array.
     */
    public static int last(int[] array) {
        PreCon.notNull(array);
        PreCon.isValid(array.length > 0, "Array has no elements.");

        return array[array.length - 1];
    }

    /**
     * Get the last element in an array.
     *
     * @param array  The array.
     * @param empty  The value to return if the array is empty.
     */
    public static int last(int[] array, int empty) {
        PreCon.notNull(array);

        if (array.length == 0)
            return empty;

        return array[array.length - 1];
    }

    /**
     * Get the last element in an array.
     *
     * @param array  The array.
     */
    public static long last(long[] array) {
        PreCon.notNull(array);
        PreCon.isValid(array.length > 0, "Array has no elements.");

        return array[array.length - 1];
    }

    /**
     * Get the last element in an array.
     *
     * @param array  The array.
     * @param empty  The value to return if the array is empty.
     */
    public static long last(long[] array, long empty) {
        PreCon.notNull(array);

        if (array.length == 0)
            return empty;

        return array[array.length - 1];
    }

    /**
     * Get the last element in an array.
     *
     * @param array  The array.
     */
    public static float last(float[] array) {
        PreCon.notNull(array);
        PreCon.isValid(array.length > 0, "Array has no elements.");

        return array[array.length - 1];
    }

    /**
     * Get the last element in an array.
     *
     * @param array  The array.
     * @param empty  The value to return if the array is empty.
     */
    public static float last(float[] array, float empty) {
        PreCon.notNull(array);

        if (array.length == 0)
            return empty;

        return array[array.length - 1];
    }

    /**
     * Get the last element in an array.
     *
     * @param array  The array.
     */
    public static double last(double[] array) {
        PreCon.notNull(array);
        PreCon.isValid(array.length > 0, "Array has no elements.");

        return array[array.length - 1];
    }

    /**
     * Get the last element in an array.
     *
     * @param array  The array.
     * @param empty  The value to return if the array is empty.
     */
    public static double last(double[] array, double empty) {
        PreCon.notNull(array);

        if (array.length == 0)
            return empty;

        return array[array.length - 1];
    }

    /**
     * Wraps an array into an array list.
     *
     * @param array  The array to wrap.
     *
     * @param <T>  The array component type.
     */
    public static <T> List<T> asList(T... array) {
        PreCon.notNull(array);

        return new WrappedArrayList<T>(array);
    }

    /**
     * Convert an array to an array list.
     *
     * @param array  The array to convert.
     */
    public static <T> List<T> toList(T... array) {
        PreCon.notNull(array);

        ArrayList<T> result = new ArrayList<>(array.length + 5);

        Collections.addAll(result, array);

        return result;
    }

    /**
     * Convert an array to an array list.
     *
     * @param array  The array to convert.
     */
    public static List<Boolean> toList(boolean... array) {
        PreCon.notNull(array);

        ArrayList<Boolean> result = new ArrayList<>(array.length + 5);

        for (boolean b : array) {
            result.add(b);
        }

        return result;
    }

    /**
     * Convert an array to an array list.
     *
     * @param array  The array to convert.
     */
    public static List<Byte> toList(byte... array) {
        PreCon.notNull(array);

        ArrayList<Byte> result = new ArrayList<>(array.length + 5);

        for (byte b : array) {
            result.add(b);
        }

        return result;
    }

    /**
     * Convert an array to an array list.
     *
     * @param array  The array to convert.
     */
    public static List<Character> toList(char... array) {
        PreCon.notNull(array);

        ArrayList<Character> result = new ArrayList<>(array.length + 5);

        for (char b : array) {
            result.add(b);
        }

        return result;
    }

    /**
     * Convert an array to an array list.
     *
     * @param array  The array to convert.
     */
    public static List<Short> toList(short... array) {
        PreCon.notNull(array);

        ArrayList<Short> result = new ArrayList<>(array.length + 5);

        for (short b : array) {
            result.add(b);
        }

        return result;
    }

    /**
     * Convert an array to an array list.
     *
     * @param array  The array to convert.
     */
    public static List<Integer> toList(int... array) {
        PreCon.notNull(array);

        ArrayList<Integer> result = new ArrayList<>(array.length + 5);

        for (int b : array) {
            result.add(b);
        }

        return result;
    }

    /**
     * Convert an array to an array list.
     *
     * @param array  The array to convert.
     */
    public static List<Long> toList(long... array) {
        PreCon.notNull(array);

        ArrayList<Long> result = new ArrayList<>(array.length + 5);

        for (long b : array) {
            result.add(b);
        }

        return result;
    }

    /**
     * Convert an array to an array list.
     *
     * @param array  The array to convert.
     */
    public static List<Float> toList(float... array) {
        PreCon.notNull(array);

        ArrayList<Float> result = new ArrayList<>(array.length + 5);

        for (float b : array) {
            result.add(b);
        }

        return result;
    }

    /**
     * Convert an array to an array list.
     *
     * @param array  The array to convert.
     */
    public static List<Double> toList(double... array) {
        PreCon.notNull(array);

        ArrayList<Double> result = new ArrayList<>(array.length + 5);

        for (double b : array) {
            result.add(b);
        }

        return result;
    }

    /**
     * Convert an array to a {@link java.util.Deque}.
     *
     * @param array  The array to convert.
     *
     * @param <T>  The array component type.
     */
    public static <T> Deque<T> toDeque(T... array) {
        PreCon.notNull(array);

        Deque<T> result = new ArrayDeque<>(array.length);
        Collections.addAll(result, array);

        return result;
    }

    /**
     * Convert an array to a {@link java.util.Deque}.
     *
     * @param array  The array to convert.
     */
    public static Deque<Boolean> toDeque(boolean... array) {
        PreCon.notNull(array);

        Deque<Boolean> result = new ArrayDeque<>(array.length);
        for (boolean b : array) {
            result.add(b);
        }

        return result;
    }

    /**
     * Convert an array to a {@link java.util.Deque}.
     *
     * @param array  The array to convert.
     */
    public static Deque<Byte> toDeque(byte... array) {
        PreCon.notNull(array);

        Deque<Byte> result = new ArrayDeque<>(array.length);
        for (byte b : array) {
            result.add(b);
        }

        return result;
    }

    /**
     * Convert an array to a {@link java.util.Deque}.
     *
     * @param array  The array to convert.
     */
    public static Deque<Character> toDeque(char... array) {
        PreCon.notNull(array);

        Deque<Character> result = new ArrayDeque<>(array.length);
        for (char b : array) {
            result.add(b);
        }

        return result;
    }

    /**
     * Convert an array to a {@link java.util.Deque}.
     *
     * @param array  The array to convert.
     */
    public static Deque<Short> toDeque(short... array) {
        PreCon.notNull(array);

        Deque<Short> result = new ArrayDeque<>(array.length);
        for (short b : array) {
            result.add(b);
        }

        return result;
    }

    /**
     * Convert an array to a {@link java.util.Deque}.
     *
     * @param array  The array to convert.
     */
    public static Deque<Integer> toDeque(int... array) {
        PreCon.notNull(array);

        Deque<Integer> result = new ArrayDeque<>(array.length);
        for (int b : array) {
            result.add(b);
        }

        return result;
    }

    /**
     * Convert an array to a {@link java.util.Deque}.
     *
     * @param array  The array to convert.
     */
    public static Deque<Long> toDeque(long... array) {
        PreCon.notNull(array);

        Deque<Long> result = new ArrayDeque<>(array.length);
        for (long b : array) {
            result.add(b);
        }

        return result;
    }

    /**
     * Convert an array to a {@link java.util.Deque}.
     *
     * @param array  The array to convert.
     */
    public static Deque<Float> toDeque(float... array) {
        PreCon.notNull(array);

        Deque<Float> result = new ArrayDeque<>(array.length);
        for (float b : array) {
            result.add(b);
        }

        return result;
    }

    /**
     * Convert an array to a {@link java.util.Deque}.
     *
     * @param array  The array to convert.
     */
    public static Deque<Double> toDeque(double... array) {
        PreCon.notNull(array);

        Deque<Double> result = new ArrayDeque<>(array.length);
        for (double b : array) {
            result.add(b);
        }

        return result;
    }

    /**
     * Convert an array to a {@link java.util.Set}.
     *
     * @param array  The array to convert.
     *
     * @param <T>  The array component type.
     */
    public static <T> Set<T> toSet(T... array) {
        PreCon.notNull(array);

        Set<T> result = new HashSet<>(array.length + 5);
        Collections.addAll(result, array);

        return result;
    }

    /**
     * Convert an array to a {@link java.util.Set}.
     *
     * @param array  The array to convert.
     *
     * @param <T>  The array component type.
     */
    public static <T extends Enum<T>> Set<T> toSet(T... array) {
        PreCon.notNull(array);

        @SuppressWarnings("unchecked")
        Set<T> result = EnumSet.noneOf(
                (Class<T>)array.getClass().getComponentType());

        Collections.addAll(result, array);

        return result;
    }

    /**
     * Convert an array to a {@link java.util.Set}.
     *
     * @param array  The array to convert.
     */
    public static Set<Boolean> toSet(boolean... array) {
        PreCon.notNull(array);

        HashSet<Boolean> result = new HashSet<>(2);

        for (boolean b : array) {
            result.add(b);
            if (result.size() == 2)
                break;
        }

        return result;
    }

    /**
     * Convert an array to a {@link java.util.Set}.
     *
     * @param array  The array to convert.
     */
    public static Set<Byte> toSet(byte... array) {
        PreCon.notNull(array);

        HashSet<Byte> result = new HashSet<>(array.length + 5);

        for (byte b : array) {
            result.add(b);
        }

        return result;
    }

    /**
     * Convert an array to a {@link java.util.Set}.
     *
     * @param array  The array to convert.
     */
    public static Set<Character> toSet(char... array) {
        PreCon.notNull(array);

        HashSet<Character> result = new HashSet<>(array.length + 5);

        for (char b : array) {
            result.add(b);
        }

        return result;
    }

    /**
     * Convert an array to a {@link java.util.Set}.
     *
     * @param array  The array to convert.
     */
    public static Set<Short> toSet(short... array) {
        PreCon.notNull(array);

        HashSet<Short> result = new HashSet<>(array.length + 5);

        for (short b : array) {
            result.add(b);
        }

        return result;
    }

    /**
     * Convert an array to a {@link java.util.Set}.
     *
     * @param array  The array to convert.
     */
    public static Set<Integer> toSet(int... array) {
        PreCon.notNull(array);

        HashSet<Integer> result = new HashSet<>(array.length + 5);

        for (int b : array) {
            result.add(b);
        }

        return result;
    }

    /**
     * Convert an array to a {@link java.util.Set}.
     *
     * @param array  The array to convert.
     */
    public static Set<Long> toSet(long... array) {
        PreCon.notNull(array);

        HashSet<Long> result = new HashSet<>(array.length + 5);

        for (long b : array) {
            result.add(b);
        }

        return result;
    }

    /**
     * Convert an array to a {@link java.util.Set}.
     *
     * @param array  The array to convert.
     */
    public static Set<Float> toSet(float... array) {
        PreCon.notNull(array);

        HashSet<Float> result = new HashSet<>(array.length + 5);

        for (float b : array) {
            result.add(b);
        }

        return result;
    }

    /**
     * Convert an array to a {@link java.util.Set}.
     *
     * @param array  The array to convert.
     */
    public static Set<Double> toSet(double... array) {
        PreCon.notNull(array);

        HashSet<Double> result = new HashSet<>(array.length + 5);

        for (double b : array) {
            result.add(b);
        }

        return result;
    }

    /**
     * Create a new array that contains elements from
     * the provided array but without the null elements.
     *
     * <p>The number of null elements can be determined by
     * the difference in size of the new array.</p>
     *
     * @param array  The source array.
     *
     * @param <T>  The array component type.
     *
     * @return A new, possibly smaller array without null elements.
     */
    public static <T> T[] removeNull(T[] array) {
        PreCon.notNull(array);

        List<T> list = new ArrayList<>(array.length);
        for (T element : array) {
            if (element != null) {
                list.add(element);
            }
        }

        @SuppressWarnings("unchecked")
        Class<T> componentClass = (Class<T>) array.getClass().getComponentType();

        T[] newArray = newArray(componentClass, list.size());

        for (int i=0; i < newArray.length; i++) {
            newArray[i] = list.get(i);
        }

        return newArray;
    }

    /**
     * Converts an array of one type into an array of another type.
     *
     * <p>The array sizes do not have to match. The conversion takes place in
     * linear order starting from index 0 until either the end of the output
     * array is reached or the end of the input array is reached.</p>
     *
     * @param inputArray   The input array.
     * @param outputArray  The output array.
     * @param converter    The converter that will convert each element.
     *
     * @param <I>  The input array component type.
     * @param <O>  The output array component type.
     *
     * @return  The output array.
     */
    public static <I, O> O[] convert(I[] inputArray, O[] outputArray, IConverter<I, O> converter) {
        PreCon.notNull(inputArray);
        PreCon.notNull(outputArray);
        PreCon.notNull(converter);

        for (int i=0, iLen=inputArray.length, oLen=outputArray.length; i < iLen && i < oLen; i++) {
            outputArray[i] = converter.convert(inputArray[i]);
        }

        return outputArray;
    }

    /**
     * Reset all array elements to null.
     *
     * @param array  The array to reset.
     *
     * @param <T>  The array component type.
     */
    public static <T> void reset(T[] array) {
        PreCon.notNull(array);

        for (int i=0; i < array.length; i++)
            array[i] = null;
    }

    /**
     * Reset all array elements to false.
     *
     * @param array  The array to reset.
     */
    public static void reset(boolean[] array) {
        PreCon.notNull(array);

        for (int i=0; i < array.length; i++)
            array[i] = false;
    }

    /**
     * Reset all array elements to zero.
     *
     * @param array  The array to reset.
     */
    public static void reset(byte[] array) {
        PreCon.notNull(array);

        for (int i=0; i < array.length; i++)
            array[i] = 0;
    }

    /**
     * Reset all array elements to zero.
     *
     * @param array  The array to reset.
     */
    public static void reset(short[] array) {
        PreCon.notNull(array);

        for (int i=0; i < array.length; i++)
            array[i] = 0;
    }

    /**
     * Reset all array elements to zero.
     *
     * @param array  The array to reset.
     */
    public static void reset(char[] array) {
        PreCon.notNull(array);

        for (int i=0; i < array.length; i++)
            array[i] = 0;
    }

    /**
     * Reset all array elements to zero.
     *
     * @param array  The array to reset.
     */
    public static void reset(int[] array) {
        PreCon.notNull(array);

        for (int i=0; i < array.length; i++)
            array[i] = 0;
    }

    /**
     * Reset all array elements to zero.
     *
     * @param array  The array to reset.
     */
    public static void reset(long[] array) {
        PreCon.notNull(array);

        for (int i=0; i < array.length; i++)
            array[i] = 0;
    }

    /**
     * Reset all array elements to zero.
     *
     * @param array  The array to reset.
     */
    public static void reset(float[] array) {
        PreCon.notNull(array);

        for (int i=0; i < array.length; i++)
            array[i] = 0;
    }

    /**
     * Reset all array elements to zero.
     *
     * @param array  The array to reset.
     */
    public static void reset(double[] array) {
        PreCon.notNull(array);

        for (int i=0; i < array.length; i++)
            array[i] = 0;
    }

    /**
     * Fill an array with a single value.
     *
     * @param array  The array to fill.
     * @param value  The value to fill the array with.
     *
     * @param <T>  The array component type.
     *
     * @return  The filled array.
     */
    public static <T> T[] fill(T[] array, @Nullable T value) {
        PreCon.notNull(array);
        for (int i=0; i < array.length; i++) {
            array[i] = value;
        }
        return array;
    }

    /**
     * Fill an array from the specified index to the specified "from" index (+1) with
     * a single value.
     *
     * @param array      The array to fill.
     * @param value      The value to fill the array with.
     * @param fromIndex  The start index to fill.
     * @param toIndexP1  The end index (+1) to end at.
     *
     * @param <T>  The array component type.
     *
     * @return  The filled array.
     */
    public static <T> T[] fill(T[] array, @Nullable T value, int fromIndex, int toIndexP1) {
        PreCon.notNull(array);
        PreCon.positiveNumber(fromIndex);
        PreCon.lessThanEqual(toIndexP1, array.length);

        for (int i = fromIndex; i < toIndexP1; i++) {
            array[i] =  value;
        }
        return array;
    }

    /**
     * Create a new array of the specified component type.
     *
     * @param componentClass  The component type.
     * @param size            The array size.
     *
     * @param <T>  The component type.
     */
    public static <T> T[] newArray(Class<T> componentClass, int size) {

        @SuppressWarnings("unchecked")
        T[] newArray = (T[])Array.newInstance(componentClass, size);

        return newArray;
    }
}
