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


package com.jcwhatever.nucleus.utils.language;

import com.google.common.annotations.Beta;
import com.jcwhatever.nucleus.internal.NucMsg;
import com.jcwhatever.nucleus.mixins.IPluginOwned;
import com.jcwhatever.nucleus.utils.PreCon;
import com.jcwhatever.nucleus.utils.language.parser.Language;
import com.jcwhatever.nucleus.utils.language.parser.LanguageKeys;
import com.jcwhatever.nucleus.utils.language.parser.LanguageParser;
import com.jcwhatever.nucleus.utils.language.parser.LocalizedText;
import com.jcwhatever.nucleus.utils.text.TextUtils;

import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;

/**
 * Manage string language and localization.
 *
 * <p>The language manager is used in conjunction with the NucleusLocalizer jar, a console program
 * that parses all of the <pre>static final String</pre> fields annotated with {@link Localizable}
 * to create a key file which is placed in the /res/ directory of the jar file. {@link LanguageManager}
 * reads the resource file to create a map used to change hard coded text to text specified
 * in another resource file (/res/lang.txt) which is a copy of the key file with the text values
 * changed.</p>
 *
 * <p>A plugin that implements {@link LanguageManager} and is localized should have the following
 * files in its jar file:</p>
 *
 * <p>/res/lang.keys.txt - The key file generated by NucleusLocalizer, do not change.</p>
 * <p>/res/lang.txt - A copy of the key file with modified text. Note: not all entries are required.</p>
 *
 * <p>FURTHER DEVELOPMENT EXPECTED</p>
 */
@Beta
public class LanguageManager implements IPluginOwned {

    private final Plugin _plugin;
    private final Object _owner;

    private Map<String, String> _localizationMap;
    private LanguageKeys _keys;

    /**
     * Constructor.
     *
     * @param plugin  The owning plugin.
     */
    public LanguageManager(Plugin plugin) {
        this(plugin, null);
    }

    /**
     * Constructor.
     *
     * @param plugin  The owning plugin.
     * @param owner   Optional. An object instance from the jar file that owns the language manager.
     *                Otherwise, the plugin is the owner. Use when a plugin loads from multiple jar
     *                files (modules) and a jar has its own language manager.
     */
    public LanguageManager(Plugin plugin, @Nullable Object owner) {
        PreCon.notNull(plugin);

        _plugin = plugin;
        _owner = owner == null ? plugin : owner;

        loadInternalLanguage();
    }

    @Override
    public Plugin getPlugin() {
        return _plugin;
    }

    /**
     * Clear all localizations.
     */
    public void clear() {
        _localizationMap.clear();
    }

    /**
     * Reload internal localizations.
     */
    public void reload() {
        _localizationMap.clear();

        loadInternalLanguage();
    }

    /**
     * Merge a language file into the language manager.
     *
     * @param file  The language file to include
     *
     * @return  True if the file was merged.
     */
    public boolean addFile(File file) throws FileNotFoundException {
        PreCon.notNull(file);
        PreCon.isValid(file.isFile());

        FileInputStream stream = new FileInputStream(file);

        Language language = new Language(stream);

        return mergeLanguage(language);
    }

    /**
     * Localize a text string.
     *
     * <p>The text must be a key that was parsed from the compiled
     * jar file and is an entry in the lang.key.txt file in the plugins
     * resource file.</p>
     *
     * <p>If an entry is not found, the unlocalized text is formatted and returned.</p>
     *
     * @param text    The text to localize.
     * @param params  Optional format arguments.
     */
    @Localized
    public String get(String text, Object... params) {
        PreCon.notNull(text);

        if (text.isEmpty())
            return text;

        if (_keys == null) {
            return format(text, params);
        }

        String localizedText = _localizationMap.get(text);
        if (localizedText == null) {
            return format(text, params);
        }

        return format(localizedText, params);
    }

    /*
     * Load language key and file from owner jar resource file, if any.
     */
    private void loadInternalLanguage() {

        InputStream langStream = _owner.getClass().getResourceAsStream("/res/lang.txt");
        if (langStream == null)
            return;

        Language language = new Language(langStream);

        mergeLanguage(language);
    }

    /*
     * Parse language file from stream.
     */
    @Nullable
    private LanguageParser parseLanguage(InputStream stream) {

        LanguageParser langParser = new LanguageParser(stream);

        try {
            langParser.parseStream();

            return langParser;
        }
        catch (InvalidLocalizedTextException e) {
            e.printStackTrace();
            return null;
        }
    }

    /*
     *  Get or load language keys from resource file.
     */
    @Nullable
    private LanguageKeys getLanguageKeys() {

        if (_keys != null)
            return _keys;

        InputStream langStream = _owner.getClass().getResourceAsStream("/res/lang.keys.txt");
        if (langStream == null)
            return null;

        return _keys = new LanguageKeys(langStream);
    }

    /*
     *  Merge a language into the language manager.
     *  The new language entries overwrite the existing.
     */
    private boolean mergeLanguage(Language language) {

        LanguageKeys keys = getLanguageKeys();
        if (keys == null)
            return false;

        if (!keys.isCompatible(language)) {
            NucMsg.warning(_plugin, "Could not merge language file due to incompatible version.");
            return false;
        }

        _localizationMap = new HashMap<>(keys.size());

        List<LocalizedText> localized = language.getLocalizedText();

        for (LocalizedText text : localized) {

            String key = keys.getText(text.getIndex());
            if (key == null) {
                NucMsg.warning(_plugin, "Failed to find localization key indexed {0}.", text.getIndex());
                continue;
            }

            _localizationMap.put(key, text.getText());
        }

        return true;
    }

    /*
     * text format helper
     */
    private String format(String text, Object... params) {

        text = TextUtils.format(text, params);

        return TextUtils.formatPluginInfo(_plugin, text);
    }
}
