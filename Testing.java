public class Testing {
    // where all our tests are run
    public static void main(String[] args) {

        // testing Menu
        Menu test = new Menu(); // creates new Menu

        // prints out original menu
        System.out.println(test.displayMenu());

        // second menu item should be shoyu ramen now
        test.changeItem(3, 7); // changes menu item

        // prints out entire menu
        System.out.println(test.displayMenu());

        // prints out possible menu items
        System.out.println(test.displayAll());

        test.changeItem(1, 17); // should print an error statement
        test.changeItem(1, 7); // should print a different error

        // testing Functions
        System.out.println(Functions.help());

        // testing Upgrades
        User testUser = new User("Kai");
        Upgrades temp = new Upgrades(testUser);

        // print original state of upgradfes
        System.out.println(temp.printUpgrades());

        temp.buyUpgrade(2); // buy sign flipper
        temp.buyUpgrade(2);
        temp.buyUpgrade(2);
        temp.buyUpgrade(2);
        temp.buyUpgrade(2);
        temp.buyUpgrade(4);
        temp.buyUpgrade(3);
        temp.buyUpgrade(0);
        temp.buyUpgrade(0);

        // print out updated state of upgrades
        System.out.println(temp.printUpgrades());

        temp.buyUpgrade(2); // should print out error message
        temp.buyUpgrade(17); // should print out different error message

    }
}
