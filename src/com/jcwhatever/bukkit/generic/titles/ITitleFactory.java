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

package com.jcwhatever.bukkit.generic.titles;

import com.jcwhatever.bukkit.generic.utils.text.TextComponent;

import java.util.List;
import javax.annotation.Nullable;

/**
 * Factory to create {@link ITitle} instances.
 */
public interface ITitleFactory<T extends ITitle> {

    /**
     * Create a new title.
     *
     * @param title        The title text.
     * @param subTitle     The sub title text.
     * @param fadeInTime   The time spent fading in. -1 for default.
     * @param stayTime     The time the text stays visible. -1 for default.
     * @param fadeOutTime  The time spent fading out. -1 for default.
     */
    T create(List<TextComponent> title, @Nullable List<TextComponent> subTitle,
             int fadeInTime, int stayTime, int fadeOutTime);
}
