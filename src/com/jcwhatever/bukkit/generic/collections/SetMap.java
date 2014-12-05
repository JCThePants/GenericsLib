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

package com.jcwhatever.bukkit.generic.collections;

import com.jcwhatever.bukkit.generic.utils.PreCon;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.Nullable;

/**
 * Base implementation for a map that uses sets to store values.
 *
 * @param <K>  Key type
 * @param <V>  Value type
 */
public abstract class SetMap<K, V> implements Map<K, V> {

    /**
     * Determine if the map is empty.
     */
    @Override
    public boolean isEmpty() {
        return getMap().isEmpty();
    }

    /**
     * Clear all items
     */
    @Override
    public void clear() {
        getMap().clear();
    }

    /**
     * Determine if the map contains the specified key.
     *
     * @param key  The key to check.
     */
    @Override
    public boolean containsKey(Object key) {
        PreCon.notNull(key);

        return getMap().containsKey(key);
    }

    /**
     * Determine if the map contains the specified value.
     *
     * @param value  The value to check.
     */
    @Override
    public boolean containsValue(Object value) {
        PreCon.notNull(value);

        for (Set<V> stack : getMap().values()) {

            //noinspection SuspiciousMethodCalls
            if (stack.contains(value))
                return true;
        }
        return false;
    }

    /**
     * Determine if the set represented by the specified
     * key contains the specified value.
     *
     * @param key  The key.
     */
    public boolean containsValue(Object key, V value) {
        PreCon.notNull(key);
        PreCon.notNull(value);

        //noinspection SuspiciousMethodCalls
        Set<V> set = getMap().get(key);
        return set != null && set.contains(value);
    }

    /**
     * Unsupported.
     */
    @Override
    public Set<Entry<K, V>> entrySet() {
        throw new UnsupportedOperationException();
    }

    /**
     * Get a value using the specified key.
     *
     *  <p>The value returned is the first one returned from the set.</p>
     *
     * @param key  The key to check.
     */
    @Override
    @Nullable
    public V get(Object key) {
        PreCon.notNull(key);

        Set<V> set = getMap().get(key);
        if (set != null && !set.isEmpty()) {
            return set.iterator().next();
        }

        return null;
    }

    /**
     * Get all values associated with the specified key.
     *
     * @param key  The key to check.
     */
    public Set<V> getAll(Object key) {
        PreCon.notNull(key);

        //noinspection SuspiciousMethodCalls
        Set<V> set = getMap().get(key);
        if (set == null) {
            return createSet(0);
        }

        return set;
    }


    /**
     * Get the maps keys.
     */
    @Override
    public Set<K> keySet() {
        return getMap().keySet();
    }

    /**
     * Put a value into the map.
     *
     * @param key    The key to use.
     * @param value  The value to add.
     */
    @Override
    public V put(K key, V value) {
        PreCon.notNull(key);
        PreCon.notNull(value);

        Set<V> set = getMap().get(key);
        if (set == null) {
            set = createSet();
            getMap().put(key, set);
        }

        set.add(value);

        return value;
    }

    /**
     * Unsupported.
     */
    @Override
    public void putAll(Map<? extends K, ? extends V> map) {
        throw new UnsupportedOperationException();
    }

    /**
     * All all values in a collection to the specified
     * key.
     *
     * @param key     The key to use.
     * @param values  The values to add.
     */
    public void putAll(K key, Collection<V> values) {
        PreCon.notNull(key);
        PreCon.notNull(values);

        if (values.isEmpty())
            return;

        Set<V> set = getMap().get(key);
        if (set == null) {
            set = createSet();
            getMap().put(key, set);
        }

        set.addAll(values);
    }

    /**
     * Remove value by key. Removes a value from the internal set
     * represented by the key.
     *
     * @param key  The key.
     *
     * @return Returns the removed value, if any.
     */
    @Override
    @Nullable
    public V remove(Object key) {
        PreCon.notNull(key);

        //noinspection SuspiciousMethodCalls
        Set<V> set = getMap().get(key);
        if (set == null) {
            return null;
        }

        if (set.isEmpty()) {
            getMap().remove(key);
            return null;
        }

        V removed = set.iterator().next();
        set.remove(removed);

        return removed;
    }

    /**
     * Remove a value from the set associated with the key.
     *
     * @param key    The key.
     * @param value  The value.
     *
     * @return  True if the collection is modified.
     */
    public boolean removeValue(Object key, V value) {
        PreCon.notNull(key);
        PreCon.notNull(value);

        //noinspection SuspiciousMethodCalls
        Set<V> set = getMap().get(key);
        if (set == null) {
            return false;
        }

        if (set.isEmpty()) {
            //noinspection SuspiciousMethodCalls
            getMap().remove(key);
            return false;
        }

        return set.remove(value);
    }

    /**
     * Remove a value from all sets.
     *
     * @param value  The value.
     *
     * @return  True if the collection is modified.
     */
    public boolean removeValue(V value) {
        PreCon.notNull(value);

        boolean isModified = false;

        List<Entry<K, Set<V>>> entries = new ArrayList<>(getMap().entrySet());

        for (Entry<K, Set<V>> entry : entries) {
            isModified = isModified || entry.getValue().remove(value);
            if (entry.getValue().isEmpty()) {
                getMap().remove(entry.getKey());
            }
        }

        return isModified;
    }

    /**
     * Remove all value of the specified key.
     *
     * @param key  The key.
     *
     * @return Returns the removed set, if any.
     */
    public Set<V> removeAll(Object key) {
        PreCon.notNull(key);

        //noinspection SuspiciousMethodCalls
        Set<V> set = getMap().remove(key);
        if (set == null) {
            return createSet(0);
        }

        return set;
    }

    /**
     * Get the number of entries in the map.
     * Or in other terms the number of keys, or
     * the number of stacks.
     */
    @Override
    public int size() {
        return getMap().size();
    }

    /**
     * Get the number of items in the internal stack
     * represented by the specified key.
     *
     * @param key  The key to check.
     */
    public int keySize(Object key) {
        PreCon.notNull(key);

        //noinspection SuspiciousMethodCalls
        Set<V> set = getMap().get(key);
        if (set == null || set.isEmpty()) {
            return 0;
        }

        return set.size();
    }

    /**
     * Get all values in the map.
     */
    @Override
    public Collection<V> values() {
        Collection<Set<V>> values = getMap().values();
        Set<V> results = createSet(values.size());

        for (Set<V> set : values) {
            results.addAll(set);
        }

        return results;
    }

    protected abstract Map<K, Set<V>> getMap();

    protected abstract Set<V> createSet();

    protected abstract Set<V> createSet(int size);

}