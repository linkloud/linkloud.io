import { COLORS } from "./colors";
import { FONT_SIZE } from "./font-size";

export const LIGHT_THEME = {
  COLOR: {
    WHITE: COLORS.WHITE,
    BLACK: COLORS.BLACK,
    TEXT: {
      HIGH: COLORS.OPACITY.BLACK.HIGH,
      MEDIUM: COLORS.OPACITY.BLACK.MEDIUM,
      LOW: COLORS.OPACITY.BLACK.LOW,
      INVERTED: COLORS.WHITE,
      DISABLED: COLORS.OPACITY.BLACK.LOW,
    },
    PRIMARY: {
      HIGH: COLORS.BRIGHT_BLUE[600],
      MEDIUM: COLORS.BRIGHT_BLUE[300],
      LOW: COLORS.BRIGHT_BLUE[100],
    },
    ACCENT: {
      HIGH: COLORS.SKY[400],
      MEDIUM: COLORS.SKY[300],
      LOW: COLORS.SKY[100],
    },
    SUC: {
      MEDIUM: COLORS.GREEN[600],
      LOW: COLORS.GREEN[100],
    },
    INFO: {
      MEDIUM: COLORS.SKY[500],
      LOW: COLORS.SKY[100],
    },
    WARN: {
      MEDIUM: COLORS.RED[500],
      LOW: COLORS.RED[100],
    },
    BORDER: {
      HIGH: COLORS.GRAY[700],
      MEDIUM: COLORS.GRAY[500],
      LOW: COLORS.GRAY[300],
    },
    ACTIVE: COLORS.GRAY[200],
  },
  FONT: {
    PRIMARY: "PretendardVariableWoff2",
  },
  FONT_SIZE: {
    TITLE_LG: FONT_SIZE["24px"],
    TITLE_MD: FONT_SIZE["20px"],
    TITLE_SM: FONT_SIZE["18px"],
    BODY: FONT_SIZE["16px"],
    DESCRIPTION: FONT_SIZE["14px"],
    CAPTION: FONT_SIZE["12px"],
  },
};

//TODO: DARK