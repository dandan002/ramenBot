/******************************************************************************
 *
 * This program connects to the MongoDB Atlas database and creates users
 * with various attributes like balance, user level, and number of ramen
 * bowls cooked in a shift.
 *
 *****************************************************************************/

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Updates;
import org.bson.Document;

import java.util.ArrayList;

import static com.mongodb.client.model.Filters.eq;
import static java.lang.System.currentTimeMillis;

// NOTE: It would be customary to make the user, collection, and database reusable class variables,
// but MongoDB does not allow for multiple document updates in a single client instance.
public class MongoConnection {
    private final String id = "mongodb+srv://dandan010:dkssud010@cluster0." +
            "tswr0hm.mongodb.net/?retryWrites=true&w=majority";
    private final String guildId;
    private final String userId;

    // connect to database and check users.
    public MongoConnection(String USER_ID, String SERVER_ID) {
        guildId = SERVER_ID;
        userId = USER_ID;
        checkUser(USER_ID, SERVER_ID);
    }

    // checks if a specific user and guild exists in the database and creates one if not
    public void checkUser(String USER_ID, String SERVER_ID) {
        // check if collection (server) exists
        MongoClient mongoClient = MongoClients.create(id);
        MongoDatabase db = mongoClient.getDatabase("RancidRamen");
        boolean colExists = false;
        for (String name : db.listCollectionNames()) {
            if (name.equals(SERVER_ID)) {
                colExists = true;
                break;
            }
        }
        // create collection (server) if colExists = false
        if (!colExists) {
            db.createCollection(SERVER_ID);
        }
        //check if user (document) exists and create if not
        MongoCollection<Document> col = db.getCollection(SERVER_ID);
        boolean exists = col.countDocuments(eq("_id", USER_ID)) > 0;
        ArrayList<String> menu = new ArrayList<>();
        menu.add("Beef Ramen");
        menu.add("Seafood Ramen");
        menu.add("Tonkotsu Ramen");
        menu.add("Miso Ramen");

        ArrayList<Integer> upgrades = new ArrayList<>();
        upgrades.add(1);
        upgrades.add(1);
        upgrades.add(1);
        upgrades.add(1);
        upgrades.add(1);
        upgrades.add(1);

        if (!exists) {
            col.insertOne(new Document()
                    .append("_id", USER_ID)
                    .append("prefix", "!")
                    .append("userLevel", 1)
                    .append("multiplier", 1.0)
                    .append("balance", 100.0)
                    .append("lastTime", 1000000000000L)
                    .append("ramenCooked", 0)
                    .append("menu", menu)
                    .append("upgrades", upgrades));
        }
        mongoClient.close();
    }

    // get # of ramen cooked
    public int getRamenCooked() {
        MongoClient mongoClient = MongoClients.create(id);
        Document user = mongoClient.getDatabase("RancidRamen").
                getCollection(guildId).find(eq("_id", userId)).first();
        int bowls = user.getInteger("ramenCooked");
        mongoClient.close();
        return bowls;
    }

    // get prefix
    public String getPrefix() {
        MongoClient mongoClient = MongoClients.create(id);
        Document user = mongoClient.getDatabase("RancidRamen").
                getCollection(guildId).find(eq("_id", userId)).first();
        String prefix = user.getString("prefix");
        mongoClient.close();
        return prefix;
    }

    // get menu
    public ArrayList<String> getMenu() {
        MongoClient mongoClient = MongoClients.create(id);
        Document user = mongoClient.getDatabase("RancidRamen").
                getCollection(guildId).find(eq("_id", userId)).first();
        ArrayList<String> menu = (ArrayList<String>) user.get("menu");
        mongoClient.close();
        return menu;
    }

    // get last time recorded
    public double getLastTime() {
        MongoClient mongoClient = MongoClients.create(id);
        Document user = mongoClient.getDatabase("RancidRamen").
                getCollection(guildId).find(eq("_id", userId)).first();
        double milis = (double) user.getLong("lastTime");
        mongoClient.close();
        return milis;
    }

    // get user level
    public int getUserLevel() {
        MongoClient mongoClient = MongoClients.create(id);
        Document user = mongoClient.getDatabase("RancidRamen").
                getCollection(guildId).find(eq("_id", userId)).first();
        int level = user.getInteger("userLevel");
        mongoClient.close();
        return level;
    }

    // get balance
    public double getBalance() {
        MongoClient mongoClient = MongoClients.create(id);
        Document user = mongoClient.getDatabase("RancidRamen").
                getCollection(guildId).find(eq("_id", userId)).first();
        double bal = user.getDouble("balance");
        mongoClient.close();
        return bal;
    }

    // get multiplier
    public double getMultiplier() {
        MongoClient mongoClient = MongoClients.create(id);
        Document user = mongoClient.getDatabase("RancidRamen").
                getCollection(guildId).find(eq("_id", userId)).first();
        double mult = user.getDouble("multiplier");
        mongoClient.close();
        return mult;
    }

    // unlock Ramen
    public void unlockRamen(String add) {
        MongoClient mongoClient = MongoClients.create(id);
        MongoCollection<Document> col = mongoClient.getDatabase("RancidRamen").
                getCollection(guildId);
        Document user = col.find(eq("_id", userId)).first();
        ArrayList<String> ramen = getMenu();
        ramen.add(add);
        col.updateOne(user, Updates.set("menu", ramen));
        mongoClient.close();
    }

    // get current upgrade levels
    public ArrayList<Integer> getUpgradeLevels() {
        MongoClient mongoClient = MongoClients.create(id);
        Document user = mongoClient.getDatabase("RancidRamen").
                getCollection(guildId).find(eq("_id", userId)).first();
        ArrayList<Integer> upgrades = (ArrayList<Integer>) user.get("upgrades");
        mongoClient.close();
        return upgrades;
    }

    // upgrade
    public void upgrade(int index) {
        MongoClient mongoClient = MongoClients.create(id);
        MongoCollection<Document> col = mongoClient.getDatabase("RancidRamen").
                getCollection(guildId);
        Document user = col.find(eq("_id", userId)).first();
        ArrayList<Integer> upgrades = getUpgradeLevels();
        upgrades.set(index, upgrades.get(index) + 1);
        col.updateOne(user, Updates.set("upgrades", upgrades));
        mongoClient.close();
    }

    // marks last work time
    public void markTime() {
        MongoClient mongoClient = MongoClients.create(id);
        MongoCollection<Document> col = mongoClient.getDatabase("RancidRamen").getCollection(guildId);
        Document user = col.find(eq("_id", userId)).first();
        col.updateOne(user, Updates.set("lastTime", currentTimeMillis()));
        mongoClient.close();
    }

    //set prefix
    public void setPrefix(String pref) {
        MongoClient mongoClient = MongoClients.create(id);
        MongoCollection<Document> col = mongoClient.getDatabase("RancidRamen").getCollection(guildId);
        Document user = col.find(eq("_id", userId)).first();
        col.updateOne(user, Updates.set("prefix", pref));
        mongoClient.close();
    }

    //changes user balance
    public void changeBalance(double balance) {
        MongoClient mongoClient = MongoClients.create(id);
        MongoCollection<Document> col = mongoClient.getDatabase("RancidRamen").getCollection(guildId);
        Document user = col.find(eq("_id", userId)).first();
        col.updateOne(user, Updates.set("balance", balance));
        mongoClient.close();
    }

    // changes user level
    public void changeLevel(int level) {
        MongoClient mongoClient = MongoClients.create(id);
        MongoCollection<Document> col = mongoClient.getDatabase("RancidRamen").getCollection(guildId);
        Document user = col.find(eq("_id", userId)).first();
        col.updateOne(user, Updates.set("userLevel", level));
        mongoClient.close();
    }

    // changes multiplier
    public void changeMultiplier(double multi) {
        MongoClient mongoClient = MongoClients.create(id);
        MongoCollection<Document> col = mongoClient.getDatabase("RancidRamen").getCollection(guildId);
        Document user = col.find(eq("_id", userId)).first();
        col.updateOne(user, Updates.set("multiplier", multi));
        mongoClient.close();
    }

    // sets number of ramen cooked
    public void changeRamenCooked(int cookedRamen) {
        MongoClient mongoClient = MongoClients.create(id);
        MongoCollection<Document> col = mongoClient.getDatabase("RancidRamen").getCollection(guildId);
        Document user = col.find(eq("_id", userId)).first();
        col.updateOne(user, Updates.set("ramenCooked", cookedRamen));
        mongoClient.close();
    }

    public String toString() {
        return userId;
    }

    public static void main(String[] args) {
        MongoConnection testUser = new MongoConnection("testUser", "testServer");
        System.out.println(testUser.getRamenCooked()); // print 0
        System.out.println(testUser.getPrefix()); // print !
        System.out.println(testUser.getMenu()); // prints [Beef Ramen, Seafood Ramen, Tonkotsu Ramen, Miso Ramen]
        System.out.println(testUser.getLastTime()); // prints 1.0E12
        System.out.println(testUser.getUserLevel()); // prints 1
        System.out.println(testUser.getBalance()); // prints 100.0
        System.out.println(testUser.getMultiplier()); // prints 1.0
        System.out.println(testUser.getUpgradeLevels()); // prints [1, 1, 1, 1, 1, 1]
        System.out.println(testUser); // prints testUser

        testUser.unlockRamen("hi");
        System.out.println(testUser.getMenu()); // prints [Beef Ramen, Seafood Ramen, Tonkotsu Ramen, Miso Ramen, hi]

        testUser.upgrade(0);
        System.out.println(testUser.getUpgradeLevels()); // prints [2, 1, 1, 1, 1, 1]

        testUser.markTime();
        System.out.println(testUser.getLastTime()); // prints current system time (in miliseconds)

        testUser.setPrefix("?");
        System.out.println(testUser.getPrefix()); // prints ?

        testUser.changeBalance(50.0);
        System.out.println(testUser.getBalance()); // prints 50.0

        testUser.changeLevel(5);
        System.out.println(testUser.getUserLevel()); // prints 5

        testUser.changeMultiplier(1.50);
        System.out.println(testUser.getMultiplier()); // prints 1.5

        testUser.changeRamenCooked(10);
        System.out.println(testUser.getRamenCooked()); // prints 10
    }
}
