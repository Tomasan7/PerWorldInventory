package cz.tomasan7.perworldinventory.other;

import cz.tomasan7.perworldinventory.PerWorldInventory;

public class Logger
{
    public static void info (String message)
    {
        PerWorldInventory.getInstance().getLogger().info(message);
    }

    public static void success (String message)
    {
        PerWorldInventory.getInstance().getLogger().info("§2" + message);
    }

    public static void warning (String message)
    {
        PerWorldInventory.getInstance().getLogger().warning(message);
    }

    public static void error (Error error)
    {
        PerWorldInventory.getInstance().getLogger().severe(error.message);
    }

    public static void error (Error error, String additional)
    {
        PerWorldInventory.getInstance().getLogger().severe(removeDotIfExists(error.message) + ": " + "\n" + additional);
    }

    public static void error (String message)
    {
        PerWorldInventory.getInstance().getLogger().severe(message);
    }

    public static void error (String message, String additional)
    {
        PerWorldInventory.getInstance().getLogger().severe(removeDotIfExists(message) + ": " + "\n" + additional);
    }

    /**
     * If input text has a dot '.' at the end, output will be without the dot, otherwise input text will be returned.
     * So: "Hello my friends." | returns "Hello my friends"
     * And: "Hello my friends" | returns "Hello my friends"
     *
     * @param text The input text to work with.
     * @return The edited text.
     */
    private static String removeDotIfExists (String text)
    {
        if (!text.isEmpty() && text.charAt(text.length() - 1) == '.')
            return text.substring(0, text.length() - 1);

        return text;
    }
}
