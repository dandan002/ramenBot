import java.util.Arrays;

public class Menu {
    // color terminal output variables
    public static final String GREEN_TEXT = "\u001B[32m";
    public static final String RED_TEXT = "\u001B[31m";
    public static final String BLUE_TEXT = "\u001B[34m";
    public static final String RESET = "\u001B[0m";

    // defining all possible menu items
    private final String[] MENU_ITEMS = { "Beef Ramen", "Seafood Ramen", "Tonkotsu Ramen", "Miso Ramen",
            "Spicy Miso Ramen", "Shoyu Ramen", "Wasabi Shoyu Ramen", "Pork Rice Bowl" };

    private String[] userMenu; // holds user menu

    // creates default user menu
    public Menu() {
        userMenu = new String[5]; // menu size must be 5

        // auto-populated with first five possible menu items
        for (int i = 0; i < userMenu.length; i++) {
            userMenu[i] = MENU_ITEMS[i];
        }
    }

    // returns all possible menu items (as an array)
    public String[] returnAll() {
        return MENU_ITEMS;
    }

    // returns user menu as an array
    public String[] returnMenu() {
        return userMenu;
    }

    // returns user menu item at user-specified index
    public String returnMenuItem(int index) {
        return userMenu[index];
    }

    public String returnAllItem(int index) {
        return MENU_ITEMS[index];
    }

    // change item on menu, input is user menu index and all items index
    public boolean changeItem(int ogIndex, int newIndex) {

        // checking user input
        boolean ogValid = ogIndex > userMenu.length || ogIndex < 0;
        boolean newValid = newIndex > MENU_ITEMS.length || newIndex < 0;

        // printing error message
        if (ogValid || newValid) {
            /** System.out.println(RED_TEXT + "\nUh oh! Item number is not valid. Please try again." + RESET); **/
            return false;
        } else {
            // checking if item is already in menu
            for (int i = 0; i < userMenu.length; i++) {
                if (userMenu[i].equals(MENU_ITEMS[newIndex])) {
                    /** System.out.println("\n" + RED_TEXT + MENU_ITEMS[newIndex]
                            + " is already on your menu. Please try again." + RESET); **/
                    return false;
                } else {
                    continue;
                }
            }
            // changing menu item
            userMenu[ogIndex] = MENU_ITEMS[newIndex];
        }
        return true;
    }

    // returns user menu as a String
    public String displayMenu() {
        StringBuilder menu = new StringBuilder();
        menu.append(GREEN_TEXT + "\n** Your Menu **" + RESET);

        // looping through all the values
        for (int i = 0; i < userMenu.length; i++) {
            menu.append("\n" + (i + 1) + ") " + userMenu[i]);
        }

        return menu.toString();
    }

    // returns all possible menu items as a String
    public String displayAll() {
        StringBuilder str = new StringBuilder();
        str.append(BLUE_TEXT + "\n** Possible Items **" + RESET);

        // looping through all the values

        for (int i = 0; i < MENU_ITEMS.length; i++) {
            // displays in blue if item is in user menu already
            if (!Arrays.asList(userMenu).contains(MENU_ITEMS[i])) {
                str.append("\n" + BLUE_TEXT + (i + 1) + ") " + MENU_ITEMS[i] + RESET);
            } else {
                str.append("\n" + (i + 1) + ") " + MENU_ITEMS[i]);
            }
        }

        return str.toString();
    }

    public static void main(String[] args) {

    }
}
