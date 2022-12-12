public class User {
    //basic variables
    private String userName;
    private int userLevel;
    private boolean workReady;
    private FunctionsA currentWorkflow;
    private double multiplier;
    private double balance;
    //level progress checking variables
    private int upgradesDone;

    public User(String name) {
        userName = name;
        userLevel = 1;
        workReady = true;
        currentWorkflow = new Functions();
        upgradesDone = 0;
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
    public FunctionsA getCurrentWorkflow() {
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

    public void changeBalance(double balance) {
        this.balance = balance;
    }

    public void changeUpgrades() {
        this.upgradesDone++;
    }
}
