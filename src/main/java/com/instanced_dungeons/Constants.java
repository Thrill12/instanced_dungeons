package com.instanced_dungeons;

public final class Constants {
    private Constants() {}

    public static final int IMAGE_WIDTH = 176;
    public static final int IMAGE_HEIGHT = 166;

    // Custom dungeon item slot
    public static final int DUNGEON_SLOT_X = 80;
    public static final int DUNGEON_SLOT_Y = 20;

    // Player inventory (3 rows)
    public static final int PLAYER_INV_X = 8;
    public static final int PLAYER_INV_Y = 84;
    public static final int SLOT_SPACING = 18;

    // Hotbar (one row-gap below player inv)
    public static final int HOTBAR_Y = PLAYER_INV_Y + (3 * SLOT_SPACING) + 4; // = 142
}
