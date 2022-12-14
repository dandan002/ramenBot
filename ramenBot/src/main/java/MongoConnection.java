import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.conversions.Bson;

import static com.mongodb.client.model.Filters.eq;

// NOTE: It would be customary to make the user, collection, and database reusable class variables,
// but MongoDB does not allow for multiple document updates in a single client.
public class MongoConnection {
    private String id = "mongodb+srv://dandan010:dkssud010@cluster0." +
            "tswr0hm.mongodb.net/?retryWrites=true&w=majority";
    private String guildId;
    private String userId;

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
        if (!exists) {
            col.insertOne(new Document()
                    .append("_id", USER_ID)
                    .append("prefix", "!")
                    .append("userLevel", 1)
                    .append("workReady", true)
                    .append("currentWorkflow", "normal")
                    .append("multiplier", 0.0)
                    .append("upgradesDone", 0)
                    .append("balance", 100.0));
        }
        mongoClient.close();
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

    // get user level
    public int getUserLevel() {
        MongoClient mongoClient = MongoClients.create(id);
        Document user = mongoClient.getDatabase("RancidRamen").
                getCollection(guildId).find(eq("_id", userId)).first();
        int level = user.getInteger("userLevel");
        mongoClient.close();
        return level;
    }

    // get workReady status
    public boolean getWorkStatus() {
        MongoClient mongoClient = MongoClients.create(id);
        Document user = mongoClient.getDatabase("RancidRamen").
                getCollection(guildId).find(eq("_id", userId)).first();
        boolean work = user.getBoolean("workReady");
        mongoClient.close();
        return work;
    }

    // get workflow
    public String getCurrentWorkflow() {
        MongoClient mongoClient = MongoClients.create(id);
        Document user = mongoClient.getDatabase("RancidRamen").
                getCollection(guildId).find(eq("_id", userId)).first();
        String workflow = user.getString("currentWorkflow");
        mongoClient.close();
        return workflow;
    }

    // get upgrades done
    public int getUpgradesDone() {
        MongoClient mongoClient = MongoClients.create(id);
        Document user = mongoClient.getDatabase("RancidRamen").
                getCollection(guildId).find(eq("_id", userId)).first();
        int upgrades = user.getInteger("upgradesDone");
        mongoClient.close();
        return upgrades;
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

    //levels up the user
    public void levelUp() {
        MongoClient mongoClient = MongoClients.create(id);
        MongoCollection<Document> col = mongoClient.getDatabase("RancidRamen").getCollection(guildId);
        Document user = col.find(eq("_id", userId)).first();
        Bson update = Updates.combine(Updates.set("userLevel", getUserLevel() + 1),
                Updates.set("multiplier", getMultiplier() + 1),
                Updates.set("upgradesDone", 0));
        UpdateOptions options = new UpdateOptions().upsert(true);
        UpdateResult result = col.updateOne(user, update, options);
        System.out.println("Modified levelup entries: " + result.getModifiedCount());
        System.out.println("Upserted id: " + result.getUpsertedId());
        mongoClient.close();
    }

    //changes user workflow
    public void changeWorkflow() {
        MongoClient mongoClient = MongoClients.create(id);
        MongoCollection<Document> col = mongoClient.getDatabase("RancidRamen").getCollection(guildId);
        Document user = col.find(eq("_id", userId)).first();
        if (getCurrentWorkflow().equals("normal")) {
            col.updateOne(user, Updates.set("currentWorkflow", "vc"));
        } else {
            col.updateOne(user, Updates.set("currentWorkflow", "normal"));
        }
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

    //adds 1 to # of upgrades done
    public void changeUpgradesDone() {
        MongoClient mongoClient = MongoClients.create(id);
        MongoCollection<Document> col = mongoClient.getDatabase("RancidRamen").getCollection(guildId);
        Document user = col.find(eq("_id", userId)).first();
        col.updateOne(user, Updates.set("upgradesDone", getUpgradesDone() + 1));
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
        hi.levelUp();
        System.out.println(hi);
        hi.changeBalance(101.00);
        System.out.println(hi.getBalance());
        hi.changeUpgradesDone();
        System.out.println(hi.getCurrentWorkflow());
        System.out.println(hi.getMultiplier());
        System.out.println(hi.getWorkStatus());
    }
}
