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

package com.jcwhatever.nucleus.internal.managed.nms;

import com.jcwhatever.nucleus.utils.PreCon;
import com.jcwhatever.nucleus.utils.nms.INmsPotionHandler;
import com.jcwhatever.nucleus.utils.potions.PotionUtils;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;
import org.bukkit.potion.PotionType;

import javax.annotation.Nullable;

/**
 * Minecraft Potions handler.
 */
class NmsPotionHandler implements INmsPotionHandler {

    // Potion strings found in net.minecraft.server.PotionBrewer
    private static final String SUGAR = "-0+1-2-3&4-4+13";
    private static final String MAGMA_CREAM = "+0+1-2-3&4-4+13";
    private static final String SPECKLED_MELON = "+0-1+2-3&4-4+13";
    private static final String SPIDER_EYE = "-0-1+2-3&4-4+13";
    private static final String FERMENTED_SPIDER_EYE = "-0+3-4+13";
    private static final String BLAZE_POWDER = "+0-1-2+3&4-4+13";
    private static final String GOLDEN_CARROT = "-0+1+2-3+13&4-4";
    private static final String PUFFER_FISH = "+0-1+2+3+13&4-4";
    private static final String RABBIT_FOOT = "+0+1-2+3&4-4+13";
    private static final String YELLOW_DUST = "+5-6-7";
    private static final String REDSTONE = "-5+6-7";
    private static final String SULPHUR = "+14&13-13";

    @Override
    public boolean isAvailable() {
        return true;
    }

    @Override
    public int getPotionId(PotionType type, int level, boolean isSplash, boolean isExtended) {
        PreCon.notNull(type);

        if (type == PotionType.WATER)
            return 0;

        int id = type.getDamageValue();

        if (level > 1) {
            id |= 32;
        }

        if (isExtended) {
            id |= 64;
        }

        id |= isSplash ? 16384 : 8192;

        return id;
    }

    @Override
    public ItemStack getPotionStack(PotionType type, int level, boolean isSplash, boolean isExtended) {
        PreCon.notNull(type);

        int id = getPotionId(type, level, isSplash, isExtended);
        ItemStack itemStack = new ItemStack(Material.POTION);
        itemStack.setDurability((short)id);

        return itemStack;
    }

    @Nullable
    @Override
    public ItemStack getPotionStack(int potionId) {

        PotionType type = PotionType.getByDamageValue(potionId & 15);
        if (type == null)
            return null;

        int level = (potionId & 32) == 32 ? 2 : 1;
        boolean isSplash = (potionId & 16384) == 16384;
        boolean isExtended = (potionId & 64) == 64;

        return getPotionStack(type, level, isSplash, isExtended);
    }

    @Override
    public int getPotionIdFromRecipe(ItemStack ingredient, ItemStack bottle) {
        PreCon.notNull(ingredient);
        PreCon.notNull(bottle);

        if (!PotionUtils.isPotionIngredient(ingredient))
            return -1;

        if (bottle.getType() != Material.POTION)
            return -1;

        String potionString = getPotionString(ingredient.getData());
        if (potionString == null) {

            if (bottle.getDurability() != 0)
                return -1;

            if (ingredient.getType() == Material.NETHER_STALK)
                return 16;

            if (ingredient.getType() == Material.GLOWSTONE_DUST)
                return 32;

            if (ingredient.getType() == Material.REDSTONE)
                return 64;

            return -1;
        }

        int bottleData = bottle.getDurability();
        int potionId = getPotionData(bottleData, potionString);

        if ((potionId & 32) == 0) {

            // see if a level one potion is valid, if not, make this a level 2 potion
            if (!PotionUtils.isValidPotionId(potionId)) {
                potionId |= 32;
            }
        }

        return potionId;
    }

    @Override
    public boolean isSplash(int potionId) {
        return (potionId & 16384) == 16384;
    }

    @Override
    public int getLevel(int potionId) {
        return (potionId & 32) == 32 ? 2 : 1;
    }

    @Override
    public boolean isExtendedDuration(int potionId) {
        return (potionId & 64) == 64;
    }

    private static String getPotionString(MaterialData data) {
        switch (data.getItemType()) {
            case SUGAR:
                return SUGAR;
            case MAGMA_CREAM:
                return MAGMA_CREAM;
            case SPECKLED_MELON:
                return SPECKLED_MELON;
            case SPIDER_EYE:
                return SPIDER_EYE;
            case FERMENTED_SPIDER_EYE:
                return FERMENTED_SPIDER_EYE;
            case BLAZE_POWDER:
                return BLAZE_POWDER;
            case GOLDEN_CARROT:
                return GOLDEN_CARROT;
            case RAW_FISH:
                if (data.getData() == 3)
                    return PUFFER_FISH;
                return null;
            case RABBIT_FOOT:
                return RABBIT_FOOT;
            case GLOWSTONE_DUST:
                return YELLOW_DUST;
            case REDSTONE:
                return REDSTONE;
            case SULPHUR:
                return SULPHUR;
            default:
                return null;
        }
    }

    // method from net.minecraft.server.PotionBrewer
    private static int getPotionData(int data, String potionString) {
        boolean flag = false;
        boolean flag1 = false;
        boolean flag2 = false;
        boolean flag3 = false;
        int k = 0;

        for (int i = 0; i < potionString.length(); i++) {
            char ch = potionString.charAt(i);

            if (ch >= 48 && ch <= 57) { // 0-9
                k *= 10;
                k += ch - 48;
                flag = true;
            } else if (ch == 33) { // !
                if (flag) {
                    data = getPotionData(data, k, flag2, flag1, flag3);
                    flag3 = false;
                    flag1 = false;
                    flag2 = false;
                    flag = false;
                    k = 0;
                }

                flag1 = true;
            } else if (ch == 45) { // -
                if (flag) {
                    data = getPotionData(data, k, flag2, flag1, flag3);
                    flag3 = false;
                    flag1 = false;
                    flag2 = false;
                    flag = false;
                    k = 0;
                }

                flag2 = true;
            } else if (ch == 43) { // +
                if (flag) {
                    data = getPotionData(data, k, flag2, flag1, flag3);
                    flag3 = false;
                    flag1 = false;
                    flag2 = false;
                    flag = false;
                    k = 0;
                }
            } else if (ch == 38) { // &
                if (flag) {
                    data = getPotionData(data, k, flag2, flag1, flag3);
                    flag3 = false;
                    flag1 = false;
                    flag2 = false;
                    flag = false;
                    k = 0;
                }

                flag3 = true;
            }
        }

        if (flag) {
            data = getPotionData(data, k, flag2, flag1, flag3);
        }

        return data & 32767;
    }

    // method from net.minecraft.server.PotionBrewer
    private static int getPotionData(int i, int j, boolean flag, boolean flag1, boolean flag2) {
        if (flag2) {
            if (!getPotionData(i, j)) {
                return 0;
            }
        } else if (flag) {
            i &= ~(1 << j);
        } else if (flag1) {
            if ((i & 1 << j) == 0) {
                i |= 1 << j;
            } else {
                i &= ~(1 << j);
            }
        } else {
            i |= 1 << j;
        }

        return i;
    }

    // method from net.minecraft.server.PotionBrewer
    private static boolean getPotionData(int i, int j) {
        return (i & 1 << j) != 0;
    }
}
