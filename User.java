import java.util.*;
import java.time.*;

public class User {
    //basic variables
    private String userName;
    private int userLevel;
    private boolean workReady;
    private FunctionsA currentWorkflow;
    private double currentMultiplier;
    private double balance;

    //level progress checking variables
    private int upgradesDone;

    public User(String name) {
        userName = name;
        userLevel = 1;
        workReady = true;
        currentWorkflow = new Functions();
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
        return currentMultiplier;
    }

    //get function for current balance
    public double getBalance() {
        return currentBalance;
    }

    public void levelUp() {
        level++;
        multiplier += 0.2;
        upgradesDone = 0;
    }

    public void changeWorkflow() {
        currentWorkflow = new FunctionsA;
    }
}
