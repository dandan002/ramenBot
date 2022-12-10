public class Testing {

    public static void main(String[] args) {

        // testing Menu
        Menu test = new Menu(); // creates new Menu

        // second menu item should be shoyu ramen now
        test.changeItem(1, 5); // changes menu item
        
        // prints out entire menu
        for (int i = 0; i < 5; i++) {
            String item = test.returnMenuItem(i);
            System.out.println(item);
        }

    }
}

