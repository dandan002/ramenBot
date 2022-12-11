public class Upgrades {
    // color terminal output variables
    public static final String RED_TEXT = "\u001B[31m";
    public static final String CYAN_TEXT = "\u001B[36m";
    public static final String RESET = "\u001B[0m";

    // list of upgrades and other upgrade attributes
    private final String[] upgrades = {"Instagram Shoutout", "New Paint", "Sign Flipper", "Youtube Ad",
            "New Furniture", "Better Appliances"};
    private final int[] upgradeCosts = {10, 20, 30, 50, 100, 250};

    private final int MAX_UPGRADE_LVL = 5;

    // instance variable holding user upgrade levels
    private int[] userUpgradeLvl;
    private User user;

    public Upgrades(User user) {
        this.user = user;

        userUpgradeLvl = new int[upgrades.length];

        for (int i = 0; i < upgrades.length; i++) {
            userUpgradeLvl[i] = 0;
        }
    }

    public void buyUpgrade(int index) {
        // checking if user input is valid
        if (index < 0 || index > upgrades.length) {
            System.out.println(RED_TEXT + "\nUh oh! Your input is invalid. Please try again." + RESET);
        }
        // checking if user has enough money to buy upgrade
        if(user.getBalance() < upgradeCosts[index]) {
            System.out.println(RED_TEXT + "\nYou do not have enough money to buy this upgrade." + RESET);
        }
        else {
            // check if upgrade level is maxed
            if (userUpgradeLvl[index] == 5) {
                System.out.println("\n" + RED_TEXT + upgrades[index] + " is already at the max level." + RESET);
            } else {
                userUpgradeLvl[index]++;
                user.changeBalance(user.getBalance() - upgradeCosts[index]); // changing user balance
            }
        }
    }

    // prints the upgrades menu
    public String printUpgrades() {
        StringBuilder str = new StringBuilder();
        str.append(CYAN_TEXT + "\nUpgrades:" + RESET);

        // looping through all the values
        for (int i = 0; i < upgrades.length; i++) {
            // displays upgrades available for purchase in cyan
            if (userUpgradeLvl[i] != MAX_UPGRADE_LVL) {
                str.append("\n" + (i + 1) + ") " + upgrades[i] + CYAN_TEXT + " (" + userUpgradeLvl[i] + "/"
                        + MAX_UPGRADE_LVL + ")" + RESET + " - $" + upgradeCosts[i]);
            }
            // displays maxed out upgrades in default color
            else {
                str.append("\n" + (i + 1) + ") " + upgrades[i] + " (" + userUpgradeLvl[i] + "/"
                        + MAX_UPGRADE_LVL + ") - $" + upgradeCosts[i]);
            }
        }

        return str.toString();
    }

    public static void main(String[] args) {

    }
}
