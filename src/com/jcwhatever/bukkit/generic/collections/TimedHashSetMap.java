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

import com.jcwhatever.bukkit.generic.GenericsLib;
import com.jcwhatever.bukkit.generic.scheduler.ScheduledTask;
import com.jcwhatever.bukkit.generic.utils.DateUtils;
import com.jcwhatever.bukkit.generic.utils.PreCon;
import com.jcwhatever.bukkit.generic.utils.Scheduler;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;
import javax.annotation.Nullable;

/**
 * A {@code HashSetMap} where each key value has an individual lifespan that when reached, causes the item
 * to be removed.
 *
 * <p>The lifespan can only be reset by re-adding an item.</p>
 *
 * <p>Items can be added using the default lifespan time or a lifespan can be specified per item.</p>
 */
public class TimedHashSetMap<K, V> extends HashSetMap<K, V>
        implements ITimedMap<K, V>, ITimedCallbacks<K, TimedHashSetMap<K, V>> {

    private static Map<TimedHashSetMap, Void> _instances = new WeakHashMap<>(10);
    private static ScheduledTask _janitor;

    private final Map<K, Date> _expireMap;
    private final int _timeFactor;
    private final int _defaultTime;
    private final Object _sync = new Object();

    private List<LifespanEndAction<K>> _onLifespanEnd = new ArrayList<>(5);
    private List<CollectionEmptyAction<TimedHashSetMap<K, V>>> _onEmpty = new ArrayList<>(5);

    /**
     * Constructor. Default lifespan is 20 ticks.
     *
     * <p>Map capacity starts at 10 elements</p>
     *
     * <p>Set capacity starts at 10 elements.</p>
     */
    public TimedHashSetMap() {
        this(10, 10, 20, TimeScale.TICKS);
    }

    /**
     * Constructor. Default lifespan is 20 ticks.
     *
     * <p>Set capacity starts at 10 elements.</p>
     *
     * @param mapSize  The initial capacity of the map.
     */
    public TimedHashSetMap(int mapSize) {
        this(mapSize, 10, 20, TimeScale.TICKS);
    }

    /**
     * Constructor.
     *
     * @param mapSize          The initial capacity of the map.
     * @param setSize          The initial capacity of sets.
     * @param defaultLifespan  The lifespan used when one is not specified.
     * @param timeScale        The lifespan timescale.
     */
    public TimedHashSetMap(int mapSize, int setSize, int defaultLifespan, TimeScale timeScale) {
        super(mapSize, setSize);

        _defaultTime = defaultLifespan;
        _expireMap = new HashMap<>(mapSize);
        _instances.put(this, null);

        _timeFactor = timeScale.getTimeFactor();

        if (_janitor == null) {
            _janitor = Scheduler.runTaskRepeatAsync(GenericsLib.getPlugin(), 1, 20, new Runnable() {
                @Override
                public void run() {

                    List<TimedHashSetMap> maps = new ArrayList<>(_instances.keySet());

                    for (TimedHashSetMap map : maps) {
                        synchronized (map._sync) {
                            map.cleanup();
                        }
                    }
                }
            });
        }
    }

    @Override
    public void clear() {
        super.clear();
        _expireMap.clear();

        onEmpty();
    }

    /**
     * Put an item into the map using the specified lifespan.
     *
     * @param key       The item key.
     * @param value     The item to add.
     * @param lifespan  The items lifespan.
     */
    @Override
    @Nullable
    public V put(final K key, final V value, int lifespan) {
        PreCon.notNull(key);
        PreCon.notNull(value);
        PreCon.positiveNumber(lifespan);

        if (lifespan == 0)
            return null;

        synchronized (_sync) {

            if (lifespan < 0) {
                return super.put(key, value);
            }

            V previous = super.put(key, value);
            _expireMap.put(key, getExpires(lifespan));
            return previous;
        }
    }

    @Override
    public int size() {
        synchronized (_sync) {
            cleanup();
            return super.size();
        }
    }

    @Override
    public boolean isEmpty() {
        synchronized (_sync) {
            cleanup();
            return super.isEmpty();
        }
    }

    @Override
    public boolean containsKey(Object key) {
        PreCon.notNull(key);

        synchronized (_sync) {
            return !isExpired(key, true) &&
                    super.containsKey(key);
        }
    }

    @Override
    public boolean containsValue(Object value) {
        PreCon.notNull(value);

        synchronized (_sync) {
            cleanup();
            return super.containsValue(value);
        }
    }

    @Override
    @Nullable
    public V get(Object key) {
        PreCon.notNull(key);

        synchronized (_sync) {
            if (isExpired(key, true)) {
                return null;
            }
            return super.get(key);
        }
    }

    /**
     * Put an item into the map using the default lifespan.
     *
     * @param key    The item key.
     * @param value  The item to add.
     */
    @Override
    @Nullable
    public V put(K key, V value) {
        PreCon.notNull(key);
        PreCon.notNull(value);

        return put(key, value, _defaultTime);
    }

    /**
     * Put a map of items into the map using the specified lifespan.
     *
     * @param entries   The map to add.
     * @param lifespan  The lifespan of the added items.
     */
    @Override
    public void putAll(Map<? extends K, ? extends V> entries, int lifespan) {
        PreCon.notNull(entries);
        PreCon.positiveNumber(lifespan);

        synchronized (_sync) {

            for (Map.Entry<? extends K, ? extends V> entry : entries.entrySet()) {
                put(entry.getKey(), entry.getValue(), lifespan);
            }
        }
    }

    /**
     * Put a map of items into the map using the default lifespan.
     *
     * @param entries  The map to add.
     */
    @Override
    public void putAll(Map<? extends K, ? extends V> entries) {
        PreCon.notNull(entries);

        putAll(entries, _defaultTime);
    }

    @Override
    @Nullable
    public V remove(Object key) {
        PreCon.notNull(key);

        V value;

        synchronized (_sync) {

            value = super.remove(key);
            if (value != null) {
                _expireMap.remove(key);
            }
        }

        onEmpty();

        return value;
    }

    /**
     * Add a handler to be called whenever an items lifespan ends.
     *
     * @param callback  The handler to call.
     */
    @Override
    public void addOnLifespanEnd(LifespanEndAction<K> callback) {
        PreCon.notNull(callback);

        _onLifespanEnd.add(callback);
    }

    /**
     * Remove a handler.
     *
     * @param callback  The handler to remove.
     */
    @Override
    public void removeOnLifespanEnd(LifespanEndAction<K> callback) {
        PreCon.notNull(callback);

        _onLifespanEnd.remove(callback);
    }

    /**
     * Add a handler to be called whenever the collection becomes empty.
     *
     * @param callback  The handler to call
     */
    @Override
    public void addOnCollectionEmpty(CollectionEmptyAction<TimedHashSetMap<K, V>> callback) {
        PreCon.notNull(callback);

        _onEmpty.add(callback);
    }

    /**
     * Remove a handler.
     *
     * @param callback  The handler to remove.
     */
    @Override
    public void removeOnCollectionEmpty(CollectionEmptyAction<TimedHashSetMap<K, V>> callback) {
        PreCon.notNull(callback);

        _onEmpty.remove(callback);
    }

    private void onEmpty() {
        if (!isEmpty() || _onEmpty.isEmpty())
            return;

        for (CollectionEmptyAction<TimedHashSetMap<K, V>> action : _onEmpty) {
            action.onEmpty(this);
        }
    }

    private void onLifespanEnd(K key) {
        for (LifespanEndAction<K> action : _onLifespanEnd) {
            action.onEnd(key);
        }
    }

    private boolean isExpired(Date date) {
        return date.compareTo(new Date()) <= 0;
    }

    private boolean isExpired(Object entry, boolean removeIfExpired) {
        //noinspection SuspiciousMethodCalls
        Date expires = _expireMap.get(entry);
        if (expires == null)
            return true;

        if (isExpired(expires)) {
            if (removeIfExpired) {
                //noinspection SuspiciousMethodCalls
                _map.remove(entry);
                //noinspection SuspiciousMethodCalls
                _expireMap.remove(entry);
            }
            return true;
        }

        return false;
    }

    private Date getExpires(int lifespan) {
        return DateUtils.addMilliseconds(new Date(), _timeFactor * lifespan);
    }

    private void cleanup() {

        Iterator<Entry<K, Date>> iterator = _expireMap.entrySet().iterator();

        while (iterator.hasNext()) {
            Entry<K, Date> entry = iterator.next();
            if (isExpired(entry.getValue())) {
                iterator.remove();
                _map.remove(entry.getKey());
            }
        }
    }
}