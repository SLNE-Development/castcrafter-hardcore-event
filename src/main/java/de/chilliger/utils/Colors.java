package de.chilliger.utils;

import lombok.AllArgsConstructor;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class Colors {
    public static final String RED = "<red>";
    public static final String GRAY = "<gray>";
    public static final String AQUA = "<aqua>";
    public static final String GREEN = "<green>";
    public static final String YELLOW = "<yellow>";
    public static final String LIGHT_PURPLE = "<light_purple>";
    public static final String DARK_GRAY = "<dark_gray>";
    public static final String GOLD = "<gold>";
    public static final String BLUE = "<blue>";
    public static final String HIGHLIGHTER = "<aqua>";
    public static final String GRADIENT = "<gradient:#6efaee:#2a2b58>";
    public static final String WHITE = "<white>";
    public static final String DARK_RED = "<dark_red>";
    public static final String DARK_BLUE = "<dark_blue>";
    public static final String DARK_GREEN = "<dark_green>";
    public static final String DARK_AQUA = "<dark_aqua>";
    public static final String BOLD = "<b>";
    public static final String DARK_PURPLE = "<dark_purple>";
    public static final String BLACK = "<black>";

    private static final MiniMessage miniMessage = MiniMessage.miniMessage();

    public static Component formatComponent(String... strings) {

        List<Component> components = new ArrayList<>();


        for (String value : strings) {

            components.add(miniMessage.deserialize(value));

        }

        return Component.text().append(components).asComponent();

    }

    public static String stripColor(Component input) {
        return PlainTextComponentSerializer.plainText().serialize(input);
    }
}
