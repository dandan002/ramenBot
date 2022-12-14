public class User {
    //basic variables
    private String userName;
    private String prefix;
    private int userLevel;
    private boolean workReady;
    private Functions currentWorkflow;
    private double multiplier;
    private double balance;
    //level progress checking variables
    private int upgradesDone;

    public User(String name) {
        userName = name;
        prefix = "!";
        userLevel = 1;
        workReady = true;
        currentWorkflow = new Functions();
        upgradesDone = 0;
        balance = 100;
    }

    // get method for prefix
    public String getPrefix() {
        return prefix;
    }

    // set method for prefix
    public void setPrefix(String p) {
        prefix = p;
    }

    //get function for username
    public String getUserName() {
        return userName;
    }

    //get function for user level
    public int getUserLevel() {
        return userLevel;
    }

    //get function for current work status
    public boolean getWorkStatus() {
        return workReady;
    }

    //get function for current workflow
    public Functions getCurrentWorkflow() {
        return currentWorkflow;
    }

    //get function for current score multiplier
    public double getCurrentMultiplier() {
        return multiplier;
    }

    //get function for current balance
    public double getBalance() {
        return balance;
    }

    //get function for # of upgrades
    public int getUpgradesDone() {
        return upgradesDone;
    }

    //levels up the user
    public void levelUp() {
        userLevel++;
        multiplier += 0.2;
        upgradesDone = 0;
    }

    //changes user workflow
    public void changeWorkflow() {
        currentWorkflow = new FunctionsA();
    }

    //changes user balance
    public void changeBalance(double balance) {
        this.balance = balance;
    }

    //adds 1 to # of upgrades done
    public void changeUpgrades() {
        this.upgradesDone++;
    }

    public String toString() {
        return userName;
    }
}
