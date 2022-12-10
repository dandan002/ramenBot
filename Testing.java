public class Testing {
    // where all our tests are run
    public static void main(String[] args) {

        // testing Menu
        Menu test = new Menu(); // creates new Menu

        // second menu item should be shoyu ramen now
        test.changeItem(3, 7); // changes menu item
        
        // prints out entire menu
        System.out.println(test.displayMenu());

        // prints out possible menu items
        System.out.println(test.displayAll());

        System.out.println();

        test.changeItem(1, 17); // should print an error statement

    }
}

