package xyz.kayaaa.xenon.bukkit.tools.spigot;

import lombok.experimental.UtilityClass;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import xyz.kayaaa.xenon.shared.punishment.PunishmentType;

import java.util.HashMap;
import java.util.Map;

@UtilityClass
public class ColorMapping {

    public static final Map<ChatColor, Integer> dyeMap = new HashMap<ChatColor, Integer>() {{
        put(ChatColor.WHITE, 15);
        put(ChatColor.GOLD, 14);
        put(ChatColor.AQUA, 12);
        put(ChatColor.YELLOW, 11);
        put(ChatColor.GREEN, 10);
        put(ChatColor.LIGHT_PURPLE, 9);
        put(ChatColor.GRAY, 8);
        put(ChatColor.DARK_GRAY, 7);
        put(ChatColor.DARK_AQUA, 6);
        put(ChatColor.DARK_PURPLE, 5);
        put(ChatColor.BLUE, 4);
        put(ChatColor.DARK_GREEN, 2);
        put(ChatColor.RED, 1);
        put(ChatColor.DARK_RED, 1);
        put(ChatColor.BLACK, 0);
    }};

    public short getItemDurability(String rankColor) {
        if (rankColor == null || rankColor.isEmpty()) return 0;
        if (rankColor.contains("§") || rankColor.contains("&")) {
            char codeChar = rankColor.charAt(1);
            ChatColor color = ChatColor.getByChar(codeChar);

            if (color == null || !color.isColor()) return 0;

            rankColor = color.name();
        }

        ChatColor color = ChatColor.valueOf(rankColor.toUpperCase());
        Integer durability = dyeMap.get(color);
        return durability != null ? durability.shortValue() : 0;
    }

    public DyeColor getColor(PunishmentType type) {
        switch (type) {
            case BAN:
                return DyeColor.RED;
            case MUTE:
                return DyeColor.GREEN;
            case KICK:
                return DyeColor.ORANGE;
            case BLACKLIST:
                return DyeColor.PURPLE;
            default:
                return DyeColor.WHITE;
        }
    }

    public ChatColor dyeColorToChatColor(DyeColor dyeColor) {
        switch (dyeColor) {
            case ORANGE:
            case BROWN:
                return ChatColor.GOLD;
            case MAGENTA:
            case PINK:
                return ChatColor.LIGHT_PURPLE;
            case LIGHT_BLUE:
                return ChatColor.BLUE;
            case YELLOW:
                return ChatColor.YELLOW;
            case LIME:
                return ChatColor.GREEN;
            case GRAY:
                return ChatColor.DARK_GRAY;
            case SILVER:
                return ChatColor.GRAY;
            case CYAN:
                return ChatColor.DARK_AQUA;
            case PURPLE:
                return ChatColor.DARK_PURPLE;
            case BLUE:
                return ChatColor.DARK_BLUE;
            case GREEN:
                return ChatColor.DARK_GREEN;
            case RED:
                return ChatColor.RED;
            case BLACK:
                return ChatColor.BLACK;
            default:
                return ChatColor.WHITE;
        }
    }

}
