
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
    private String[] commands = {"work", "upgrade", "balance", "menu", "help", "rob"};
    private String[] commandDesc = {"Work for money", "View all purchasable upgrades",
            "View user balance",
            "View menu options and user menu", "View all commands for Ramen Bot",
            "Attempt to rob someone else"};
    double oneHr = 3600000;

    // list of menu items
    private final String[] MENU_ITEMS = {"Beef Ramen", "Seafood Ramen", "Tonkotsu Ramen", "Miso Ramen",
            "Spicy Miso Ramen", "Shoyu Ramen", "Wasabi Shoyu Ramen", "Pork Rice Bowl"};

    // upgrades
    private final String[] upgrades = {"Instagram Shoutout", "New Paint", "Sign Flipper", "Youtube Ad",
            "New Furniture", "Better Appliances"};
    private final int[] upgradeCosts = {10, 20, 30, 50, 100, 250};
    private final int MAX_UPGRADE_LVL = 5;

    // passed database connection variable.
    private MongoConnection user;

    public Functions(MongoConnection current) {
        user = current;
    }

    // displays a help menu
    public String help(String prefix) {
        StringBuilder str = new StringBuilder();
        str.append("\n** List of Commands **\n");
        // print out each command and description
        for (int i = 0; i < commands.length; i++) {
            str.append(prefix + commands[i] + " - " + commandDesc[i] + "\n");
        }
        return str.toString();
    }


    // earn a random amount of money when you work
    public String work() {
        double timeElapsed = (currentTimeMillis() - user.getLastTime());
        if (timeElapsed < oneHr) {
            int minsLeft = (int) ((oneHr - timeElapsed) / 60000);
            return "You need to wait " + minsLeft + " minutes!";
        }
        int bowlsSold = (int) (Math.random() * 21 * user.getMultiplier());
        double moneyEarned = bowlsSold * 12;
        user.changeBalance(user.getBalance() + moneyEarned);
        user.changeRamenCooked(user.getRamenCooked() + bowlsSold);
        user.markTime();
        return "You sold " + bowlsSold + " bowls of ramen and earned $" + moneyEarned;
    }

    // level up
    public String levelUp() {
        int newLevel = user.getUserLevel() + 1;
        double newMultiplier = user.getMultiplier() + 0.2;
        user.changeRamenCooked(0);
        user.changeMultiplier(newMultiplier);
        user.changeLevel(newLevel);
        int maxCook = (int) (newMultiplier * 21);
        if (newLevel > 20) {
            return "You've reached the max level!";
        }
        StringBuilder str = new StringBuilder();
        str.append("You leveled up to lvl. " + newLevel + "! You can now cook a max of " +
                maxCook + " bowls a day!");
        if (newLevel == 20) {
            str.append("\n" + "You're at the max level!");
        }
        if (newLevel % 5 == 0) {
            String add = MENU_ITEMS[user.getMenu().size()];
            user.unlockRamen(add);
            str.append("\n" + "You unlocked " + add + ".");
        }
        return str.toString();
    }

    // return balance
    public String balance() {
        return "You have $" + user.getBalance() + " in the bank!";
    }

    // view menu
    public String menu(String prefix) {
        StringBuilder menu = new StringBuilder();
        menu.append("\n** Your Menu **");
        // looping through all the values
        ArrayList<String> currentMenu = user.getMenu();
        for (int i = 0; i < currentMenu.size(); i++) {
            menu.append("\n" + (i + 1) + ") " + currentMenu.get(i));
        }
        return menu.toString();
    }

    // rob
    public String rob(MongoConnection target, String name) {
        double timeElapsed = (currentTimeMillis() - user.getLastTime());
        if (timeElapsed < oneHr) {
            int minsLeft = (int) ((oneHr - timeElapsed) / 60000);
            return "You need to wait " + minsLeft + " minutes!";
        }
        user.markTime();
        if (Math.random() > 0.5) {
            double currentTargetBalance = target.getBalance();
            int amountStolen = (int) (Math.random() * currentTargetBalance);
            target.changeBalance(currentTargetBalance - amountStolen);
            user.changeBalance(user.getBalance() + amountStolen);
            return "You stole $" + amountStolen + " from " + name + "!";
        } else {
            user.changeBalance(user.getBalance() - 300);
            return "You got caught! You were fined $300.";
        }
    }

    // see upgrades
    public String upgrades(String prefix) {
        StringBuilder upgrades = new StringBuilder();
        upgrades.append("\n** Your Upgrades **");
        // looping through all the values
        ArrayList<Integer> currentUpgrades = user.getUpgradeLevels();
        for (int i = 0; i < currentUpgrades.size(); i++) {
            upgrades.append("\n" + (i + 1) + ") " + this.upgrades[i] + " lvl. "
                    + currentUpgrades.get(i) + " costs " + upgradeCosts[i] + " to upgrade.");
        }
        upgrades.append("\n\n" + "To upgrade a function, type " +
                prefix + "upgrade <UPGRADE NUMBER>");
        return upgrades.toString();
    }

    public String upgrade(int index) {
        String currentUpgrade = this.upgrades[index];
        if (user.getUpgradeLevels().get(index) < MAX_UPGRADE_LVL) {
            int cost = upgradeCosts[index];
            double balance = user.getBalance();
            if (balance < cost) {
                return "Not enough money!";
            }
            user.upgrade(index);
            user.changeBalance(balance - cost);
            user.changeMultiplier(user.getMultiplier() + 0.1);
            return this.upgrades[index] + " is now level " + user.getUpgradeLevels().get(index);
        } else {
            return currentUpgrade + " is already maxed out!";
        }
    }

    public static void main(String[] args) {
        MongoConnection kai = new MongoConnection("testUser1", "testServer1");
        Functions kaiw = new Functions(kai);
        System.out.println(kaiw.help("!")); // prints all callable functions and their descriptions
        System.out.println(kaiw.work()); // prints "You sold (random number from 1 to 20) bowls
        // of ramen and earned $(random number * 12)"

        kai.changeLevel(19);
        System.out.println(kaiw.levelUp()); // prints
        // You leveled up to lvl. 20! You can now cook a max of 25 bowls a day!
        // You're at the max level!
        // You unlocked Spicy Miso Ramen!

        System.out.println(kaiw.balance()); // prints You have $(balance) in the bank!
        System.out.println(kaiw.menu("!")); // prints all current menu items

        MongoConnection daniel = new MongoConnection("testUser2", "testServer1");
        System.out.println(kaiw.rob(daniel, "testUser2")); // prints either a successful
        // robbery message or "You got caught" message

        System.out.println(kaiw.upgrades("!")); // prints all current upgrades and their levels
        System.out.println(kaiw.upgrade(0)); // prints "Instagram Shoutout is now level 2"
    }
}
