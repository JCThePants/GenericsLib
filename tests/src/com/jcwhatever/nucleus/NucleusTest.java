package com.jcwhatever.nucleus;

import com.jcwhatever.v1_8_R3.BukkitTester;
import com.jcwhatever.nucleus.providers.storage.DataStorageUtil;

/**
 * Helper to initialize Nucleus and Bukkit for testing.
 */
public class NucleusTest {

    public static void init() {

        if (!BukkitTester.init())
            return;

        DataStorageUtil.setTestMode();

        BukkitPlugin plugin = BukkitTester.initPlugin("NucleusFramework", "v0", BukkitPlugin.class);

        BukkitTester.getServer().getPluginManager().enablePlugin(plugin);

        plugin.setDebugging(true);

        // pause to allow providers to be loaded
        BukkitTester.pause(40);
    }
}
