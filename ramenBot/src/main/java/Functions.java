
/******************************************************************************
 *
 * This program contains the code behind commands like !balance and !work that
 * users call in Discord.
 *
 *****************************************************************************/

import java.util.ArrayList;

import static java.lang.System.currentTimeMillis;

public class Functions {
    // list of commands and their descriptions
    private final String[] commands = { "work", "upgrade", "balance", "menu", 
                                        "help", "cancel" };
    private final String[] commandDesc = { "Work for money",
                                           "View all purchasable upgrades", 
                                           "View user balance",
                                           "View menu options and user menu", 
                                           "View all commands for Ramen Bot",
                                           "Cancel the command" };

    // upgrades
    private final String[] upgrades = { "Instagram Shoutout", "New Paint",
                                        "Sign Flipper", "Youtube Ad", 
                                        "New Furniture", "Better Appliances" };
    private final int[] upgradeCosts = { 10, 20, 30, 50, 100, 250 };
    private final int MAX_UPGRADE_LVL = 5;

    // passed database connection variable.
    private MongoConnection user;

    public Functions(MongoConnection current) {
        user = current;
    }

    // displays a help menu
    public String help(String prefix) {
        StringBuilder str = new StringBuilder();
        str.append("\n**List of Commands**\n");
        // print out each command and description
        for (int i = 0; i < commands.length; i++) {
            str.append(prefix + commands[i] + " - " + commandDesc[i] + "\n");
        }
        return str.toString();
    }

    // earn a random amount of money when you work
    public String work() {
        double timeElapsed = (currentTimeMillis() - user.getLastTime());
        double oneHr = 3600000;
        if (timeElapsed < oneHr) {
            int minsLeft = (int) ((oneHr - timeElapsed) / 60000);
            return "You need to wait " + minsLeft + " minutes!";
        }
        int bowlsSold = (int) (Math.random() * 21 * user.getMultiplier());
        double moneyEarned = bowlsSold * 12;
        user.changeBalance(user.getBalance() + moneyEarned);
        user.changeRamenCooked(user.getRamenCooked() + bowlsSold);
        user.markTime();
        return "You sold " + bowlsSold + " bowls of ramen and earned $" 
                + moneyEarned;
    }

    // level up
    public String levelUp() {
        int newLevel = user.getUserLevel() + 1;
        double newMultiplier = user.getMultiplier() + 0.2;
        user.changeRamenCooked(0);
        user.changeMultiplier(newMultiplier);
        user.changeLevel(newLevel);
        int maxCook = (int) (newMultiplier * 21);
        return "You leveled up to lvl. " + newLevel + 
                "! You can now cook a max of " + maxCook + " bowls " + "a day!";
    }

    // return balance
    public String balance() {
        return "You have $" + user.getBalance() + " in the bank!";
    }

    // view menu
    public String menu() {
        StringBuilder menu = new StringBuilder();
        menu.append("\n**Your Menu**");
        // looping through all the values
        ArrayList<String> currentMenu = user.getMenu();
        for (int i = 0; i < currentMenu.size(); i++) {
            menu.append("\n" + (i + 1) + ") " + currentMenu.get(i));
        }
        return menu.toString();
    }

    public static void main(String[] args) {

    }
}
