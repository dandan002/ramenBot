public class Upgrades {
    // color terminal output variables
    public static final String RED_TEXT = "\u001B[31m";
    public static final String CYAN_TEXT = "\u001B[36m";
    public static final String RESET = "\u001B[0m";

    // list of upgrades and other upgrade attributes
    private final String[] upgrades = { "Instagram Shoutout", "New Paint", "Sign Flipper", "Youtube Ad",
            "New Furniture", "Better Appliances" };
    private final int[] upgradeBoost = { 5, 10, 12, 15, 20, 50 };
    private final int[] upgradeCosts = { 10, 20, 30, 50, 100, 250 };

    private final int MAX_UPGRADE_LVL = 5;

    // instance variable holding user upgrade levels
    private int[] userUpgradeLvl;

    public Upgrades() {
        userUpgradeLvl = new int[upgrades.length];

        for (int i = 0; i < upgrades.length; i++) {
            userUpgradeLvl[i] = 0;
        }
    }

    private void buyUpgrade(int index) {
        if (index < 0 || index > upgrades.length) {
            System.out.println(RED_TEXT + upgrades[index] + " is already at the max level." + RESET);
        }

        if (userUpgradeLvl[index] == 5) {
            System.out.println(RED_TEXT + upgrades[index] + " is already at the max level." + RESET);
        } else {
            userUpgradeLvl[index]++;
            // how am i supposed to take the cost out of balance
        }
    }

    private String printUpgrades() {
        StringBuilder str = new StringBuilder();
        str.append(CYAN_TEXT + "\nUpgrades:" + RESET);

        // looping through all the values
        for (int i = 0; i < upgrades.length; i++) {
            str.append("\n" + (i + 1) + ") " + upgrades[i] + " - $" + upgradeCosts[i] + " (" + userUpgradeLvl[i] + "/"
                    + MAX_UPGRADE_LVL + ")");
        }

        return str.toString();
    }

    public static void main(String[] args) {

    }
}
