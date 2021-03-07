package org.vsu.experimental;

public interface Command {

    Printable printable = null;

    void execute(CommandInput input);

    String getName();

    default boolean isAStringArgument(String str) {
        return str.startsWith("'") && str.endsWith("'");
    }

    default boolean isAPath(String path) {
        return path.contains("/");
    }

    default String removeBrackets(String str) {
        if (str.length() > 3) {
            return str.substring(1, str.length() - 1);
        } else {
            return str;
        }
    }
}
