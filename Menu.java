public class Menu {

    // defining all possible menu items
    private final String[] menuItems = {"Beef Ramen", "Seafood Ramen", "Tonkotsu Ramen", "Miso Ramen", "Spicy Miso Ramen", "Soy Sauce (Shoyu) Ramen", "Wasabi Shoyu Ramen", "Pork Rice Bowl", "Japanese Curry Rice Bowl"};

    // returns all possible menu items (as an array) 
    public String[] returnMenu() {
        return menuItems;
    }    

    // returns possible menu item at user-specified index
    public String returnMenuItem(int index) {
        return menuItems[index];
    }

    public static void main(String[] args) {
        
    }
}
