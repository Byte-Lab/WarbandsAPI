/*
 * Copyright (C) 2014 Bytelab
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package io.github.bytelab.warbands.api.util.chat;

public enum ChatColor {
    BLACK("black", 0x000000, 0x000000),
    DARK_BLUE("dark_blue", 0x0000AA, 0x00002A),
    DARK_GREEN("dark_green", 0x00AA00, 0x002A00),
    DARK_AQUA("dark_aqua", 0x00AAAA, 0x002A2A),
    DARK_RED("dark_red", 0xAA0000, 0x2A0000),
    DARK_PURPLE("dark_purple", 0xAA00AA, 0x2A002A),
    GOLD("gold", 0xFFAA00, 0x2A2A00),
    GRAY("gray", 0xAAAAAA, 0x2A2A2A),
    DARK_GRAY("dark_gray", 0x555555, 0x151515),
    BLUE("blue", 0x5555FF, 0x15153F),
    GREEN("green", 0x55FF55, 0x153F15),
    AQUA("aqua", 0x55FFFF, 0x153F3F),
    RED("red", 0xFF5555, 0x3F1515),
    LIGHT_PURPLE("light_purple", 0xFF55FF, 0x3F153F),
    YELLOW("yellow", 0xFFFF55, 0x3F3F15),
    WHITE("white", 0xFFFFFF, 0x3F3F3F);

    private String string;
    private int textColour;
    private int baseColour;

    private ChatColor(String string, int textColour, int baseColour) {
        this.textColour = textColour;
        this.baseColour = baseColour;
    }

    public String toString() {
        return string;
    }

    public int getTextColour() {
        return textColour;
    }

    public int getBaseColour() {
        return baseColour;
    }
}
