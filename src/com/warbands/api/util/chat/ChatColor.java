package com.warbands.api.util.chat;

/**
 * Created by Tristan on 22/10/2014.
 *
 * @author Tristan
 */
public enum ChatColor {
    BLACK {
        public final int textColour = 0x000000;
        public final int baseColour = 0x000000;
    },
    DARK_BLUE {
        public final int textColour = 0x0000AA;
        public final int baseColour = 0x00002A;
    },
    DARK_GREEN {
        public final int textColour = 0x00AA00;
        public final int baseColour = 0x002A00;
    },
    DARK_AQUA {
        public final int textColour = 0x00AAAA;
        public final int baseColour = 0x002A2A;
    },
    DARK_RED {
        public final int textColour = 0xAA0000;
        public final int baseColour = 0x2A0000;
    },
    DARK_PURPLE {
        public final int textColour = 0xAA00AA;
        public final int baseColour = 0x2A002A;
    },
    GOLD {
        public final int textColour = 0xFFAA00;
        public final int baseColour = 0x2A2A00;
    },
    GRAY {
        public final int textColour = 0xAAAAAA;
        public final int baseColour = 0x2A2A2A;
    },
    DARK_GRAY {
        public final int textColour = 0x555555;
        public final int baseColour = 0x151515;
    },
    BLUE {
        public final int textColour = 0x5555FF;
        public final int baseColour = 0x15153F;
    },
    GREEN {
        public final int textColour = 0x55FF55;
        public final int baseColour = 0x153F15;
    },
    AQUA {
        public final int textColour = 0x55FFFF;
        public final int baseColour = 0x153F3F;
    },
    RED {
        public final int textColour = 0xFF5555;
        public final int baseColour = 0x3F1515;
    },
    LIGHT_PURPLE {
        public final int textColour = 0xFF55FF;
        public final int baseColour = 0x3F153F;
    },
    YELLOW {
        public final int textColour = 0xFFFF55;
        public final int baseColour = 0x3F3F15;
    },
    WHITE {
        public final int textColour = 0xFFFFFF;
        public final int baseColour = 0x3F3F3F;
    };


    public int textColour;

    public int baseColour;
}
