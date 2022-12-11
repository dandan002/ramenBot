public class Functions {
    // color terminal output variables
    public static final String PURPLE_TEXT = "\u001B[35m";
    public static final String RESET = "\u001B[0m";

    // list of commands and their descriptions
    private static final String[] commands = {"Work", "Upgrade", "Balance", "Menu", "Help", "Cancel"};
    private static final String[] commandDesc = {"Work for money", "View all purchasable upgrades",
            "View user balance",
            "View menu options and user menu", "View all commands for Ramen Bot",
            "Cancel the command"};

    // displays a help menu
    public static String help() {
        StringBuilder str = new StringBuilder();
        str.append(PURPLE_TEXT + "\n** List of Commands **" + RESET);
        // print out each command and description
        for (int i = 0; i < commands.length; i++) {
            str.append("\n" + PURPLE_TEXT + commands[i] + RESET + " - " + commandDesc[i]);
        }

        return str.toString();
    }

    public static void main(String[] args) {

    }
}
