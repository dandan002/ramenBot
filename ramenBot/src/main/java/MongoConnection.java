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

// NOTE: It would be customary to make the user, collection, and database
// reusable class variables, but MongoDB does not allow for multiple document
// updates in a single client instance.
public class MongoConnection {
    private final String id = "<MONGO KEY REDACTED>"; // holds MongoDB token
    private final String guildId; // holds server ID
    private final String userId; // holds Discord user ID

    // connect to database and check users
    public MongoConnection(String USER_ID, String SERVER_ID) {
        guildId = SERVER_ID;
        userId = USER_ID;
        checkUser(USER_ID, SERVER_ID);
    }

    // checks if a specific user and guild exists in the database and
    // creates one if not
    // takes user and server Discord IDs as input
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
        // check if user (document) exists and create if not
        MongoCollection<Document> col = db.getCollection(SERVER_ID);
        boolean exists = col.countDocuments(eq("_id", USER_ID)) > 0;
        ArrayList<String> menu = new ArrayList<>();
        menu.add("Beef Ramen");
        menu.add("Seafood Ramen");
        menu.add("Tonkotsu Ramen");
        menu.add("Miso Ramen");
        menu.add("Spicy Miso Ramen");

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
                                  .append("workReady", true)
                                  .append("currentWorkflow", "normal")
                                  .append("multiplier", 1.0)
                                  .append("balance", 100.0)
                                  .append("lastTime", currentTimeMillis())
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
                                   getCollection(guildId).
                                   find(eq("_id", userId)).first();
        int bowls = user.getInteger("ramenCooked");
        mongoClient.close();
        return bowls;
    }

    // get prefix
    public String getPrefix() {
        MongoClient mongoClient = MongoClients.create(id);
        Document user = mongoClient.getDatabase("RancidRamen").
                                   getCollection(guildId).
                                   find(eq("_id", userId)).first();
        String prefix = user.getString("prefix");
        mongoClient.close();
        return prefix;
    }

    // get menu
    public ArrayList<String> getMenu() {
        MongoClient mongoClient = MongoClients.create(id);
        Document user = mongoClient.getDatabase("RancidRamen").
                                   getCollection(guildId).
                                   find(eq("_id", userId)).first();
        ArrayList<String> menu = (ArrayList<String>) user.get("menu");
        mongoClient.close();
        return menu;
    }

    // get last time recorded
    public double getLastTime() {
        MongoClient mongoClient = MongoClients.create(id);
        Document user = mongoClient.getDatabase("RancidRamen").
                                   getCollection(guildId).
                                   find(eq("_id", userId)).first();
        double milis = (double) user.getLong("lastTime");
        mongoClient.close();
        return milis;
    }

    // get user level
    public int getUserLevel() {
        MongoClient mongoClient = MongoClients.create(id);
        Document user = mongoClient.getDatabase("RancidRamen").
                                   getCollection(guildId).
                                   find(eq("_id", userId)).first();
        int level = user.getInteger("userLevel");
        mongoClient.close();
        return level;
    }

    // get workReady status
    public boolean getWorkStatus() {
        MongoClient mongoClient = MongoClients.create(id);
        Document user = mongoClient.getDatabase("RancidRamen").
                                   getCollection(guildId).
                                   find(eq("_id", userId)).first();
        boolean work = user.getBoolean("workReady");
        mongoClient.close();
        return work;
    }

    // get workflow
    public String getCurrentWorkflow() {
        MongoClient mongoClient = MongoClients.create(id);
        Document user = mongoClient.getDatabase("RancidRamen").
                                   getCollection(guildId).
                                   find(eq("_id", userId)).first();
        String workflow = user.getString("currentWorkflow");
        mongoClient.close();
        return workflow;
    }

    // get balance
    public double getBalance() {
        MongoClient mongoClient = MongoClients.create(id);
        Document user = mongoClient.getDatabase("RancidRamen").
                                   getCollection(guildId).
                                   find(eq("_id", userId)).first();
        double bal = user.getDouble("balance");
        mongoClient.close();
        return bal;
    }

    // get multiplier
    public double getMultiplier() {
        MongoClient mongoClient = MongoClients.create(id);
        Document user = mongoClient.getDatabase("RancidRamen").
                                   getCollection(guildId).
                                   find(eq("_id", userId)).first();
        double mult = user.getDouble("multiplier");
        mongoClient.close();
        return mult;
    }

    // marks last work time
    public void markTime() {
        MongoClient mongoClient = MongoClients.create(id);
        MongoCollection<Document> col = mongoClient.getDatabase("RancidRamen")
                                                   .getCollection(guildId);
        Document user = col.find(eq("_id", userId)).first();
        col.updateOne(user, Updates.set("lastTime", currentTimeMillis()));
        mongoClient.close();
    }

    // changes user workflow
    public void changeWorkflow() {
        MongoClient mongoClient = MongoClients.create(id);
        MongoCollection<Document> col = mongoClient.getDatabase("RancidRamen")
                                                   .getCollection(guildId);
        Document user = col.find(eq("_id", userId)).first();
        if (getCurrentWorkflow().equals("normal")) {
            col.updateOne(user, Updates.set("currentWorkflow", "vc"));
        }
        else {
            col.updateOne(user, Updates.set("currentWorkflow", "normal"));
        }
        mongoClient.close();
    }

    // set prefix, takes prefix as input
    public void setPrefix(String pref) {
        MongoClient mongoClient = MongoClients.create(id);
        MongoCollection<Document> col = mongoClient.getDatabase("RancidRamen")
                                                   .getCollection(guildId);
        Document user = col.find(eq("_id", userId)).first();
        col.updateOne(user, Updates.set("prefix", pref));
        mongoClient.close();
    }

    // changes user balance
    public void changeBalance(double balance) {
        MongoClient mongoClient = MongoClients.create(id);
        MongoCollection<Document> col = mongoClient.getDatabase("RancidRamen")
                                                   .getCollection(guildId);
        Document user = col.find(eq("_id", userId)).first();
        col.updateOne(user, Updates.set("balance", balance));
        mongoClient.close();
    }

    // changes user level
    public void changeLevel(int level) {
        MongoClient mongoClient = MongoClients.create(id);
        MongoCollection<Document> col = mongoClient.getDatabase("RancidRamen")
                                                   .getCollection(guildId);
        Document user = col.find(eq("_id", userId)).first();
        col.updateOne(user, Updates.set("userLevel", level));
        mongoClient.close();
    }

    // changes multiplier
    public void changeMultiplier(double multi) {
        MongoClient mongoClient = MongoClients.create(id);
        MongoCollection<Document> col = mongoClient.getDatabase("RancidRamen")
                                                   .getCollection(guildId);
        Document user = col.find(eq("_id", userId)).first();
        col.updateOne(user, Updates.set("multiplier", multi));
        mongoClient.close();
    }

    // sets number of ramen cooked
    public void changeRamenCooked(int cookedRamen) {
        MongoClient mongoClient = MongoClients.create(id);
        MongoCollection<Document> col = mongoClient.getDatabase("RancidRamen")
                                                   .getCollection(guildId);
        Document user = col.find(eq("_id", userId)).first();
        col.updateOne(user, Updates.set("ramenCooked", cookedRamen));
        mongoClient.close();
    }

    public String toString() {
        return userId;

    }

    public static void main(String[] args) {
        MongoConnection hi = new MongoConnection("hi", "132453");
        System.out.println(hi.getPrefix());
        hi.changeWorkflow();
        hi.setPrefix("?");
        System.out.println(hi);
        hi.changeBalance(101.00);
        hi.getMenu();
        System.out.println(hi.getBalance());
        System.out.println(hi.getCurrentWorkflow());
        System.out.println(hi.getMultiplier());
        System.out.println(hi.getWorkStatus());
    }
}
