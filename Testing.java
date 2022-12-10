public class Testing {
    // where all our tests are run
    public static void main(String[] args) {

        // testing Menu
        Menu test = new Menu(); // creates new Menu

        // second menu item should be shoyu ramen now
        test.changeItem(1, 5); // changes menu item
        
        // prints out entire menu
        for (int i = 0; i < test.returnMenu().length; i++) {
            String item = test.returnMenuItem(i);
            System.out.println(item);
        }

        System.out.println();

        // prints out possible menu items
        String[] allItems = test.returnAll();
        for (int i = 0; i < test.returnAll().length; i++) {
            System.out.println(allItems[i]);
        }

        // test.changeItem(1, 17); // should print an error statement

    }
}

