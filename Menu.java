public class Menu {

    // defining all possible menu items
    private String[] menuItems = {"Beef Ramen", "Seafood Ramen", "Tonkotsu Ramen", "Miso Ramen", "Spicy Miso Ramen", "Soy Sauce (Shoyu) Ramen", "Wasabi Shoyu Ramen", "Pork Rice Bowl", "Japanese Curry Rice Bowl"};

    // returns possible menu item at user-specified index 
    public String returnMenu(int index) {
        return menuItems[index];
    }    

    public static void main(String[] args) {

    }
}
