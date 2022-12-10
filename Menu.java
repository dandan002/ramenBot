public class Menu {

    // defining all possible menu items
    private final String[] menuItems = {"Beef Ramen", "Seafood Ramen", "Tonkotsu Ramen", "Miso Ramen", "Spicy Miso Ramen", "Shoyu Ramen", "Wasabi Shoyu Ramen", "Pork Rice Bowl", "Japanese Curry Rice Bowl"};

    private String[] userMenu; // holds user menu

    // creates default user menu
    public Menu() {
        userMenu = new String[5]; // menu size must be 5

        // auto-populated with first five
        for (int i = 0; i < userMenu.length; i++) {
            this.userMenu[i] = menuItems[i];
        }
    }

    // returns all possible menu items (as an array) 
    public String[] returnMenu() {
        return menuItems;
    }    

    // returns possible menu item at user-specified index
    public String returnMenuItem(int index) {
        return menuItems[index];
    }

    // change item on menu
    public void changeItem(int index) {
        
    }

    public static void main(String[] args) {
        Menu test = new Menu();
        
        for (int i = 0; i < 5; i++) {
            String item = test.returnMenuItem(i);
            System.out.println(item);
        }

    }
}
