package trinhomahahilo;

import java.util.ArrayList;

/**
 * @file Player
 * @author DucTrinh
 * @assignment Project 6: Omaha Hi/Lo
 * @date 03/21/2017
 * @description This class provides the implementation for a Player object. 
 * Each Player has a player number which shows their order, a starting balance 
 * of $3000, a boolean showing whether the player is still in the game and an
 * ArrayList of Cards to show their cards on hand.
 */

public class Player {

    //Private instance variables for a Player object
    private int playerNumber;
    private int balance;
    private boolean isFolded;
    private ArrayList<Card> playerHand;
    private ArrayList<Card> highHand;
    private ArrayList<Card> lowHand;
    private int betAmount;
    private int raiseNum;
            
    //Constructor for a Player object
    public Player(ArrayList<Card> cardsDrawn){
        balance = 3000;
        isFolded = false;
        this.playerHand = cardsDrawn;
        highHand = new ArrayList<Card>();
        lowHand = new ArrayList<Card>();
        betAmount = 0;
        raiseNum = 0;
    }
    
    //Get the player balance
    public int getBalance(){
        return balance;
    }
    
    //Decrease the player's balance by the amount given in the parameter
    public void loseMoney(int money){
        balance = balance - money;
    }
    
    //Increase the player's balance by the amount given in the parameter
    public void gainMoney(int money){
        balance = balance + money;
    }
    
    //Change the player's status to folded
    public void fold(){
        isFolded = true;
    }
    
    //Return whether the player is still in the game or not
    public boolean isFolded(){
        return isFolded;
    }
    
    //Increase the player's bet amount
    public void increaseBet(int money){
        betAmount+=money;
    }
    
    //Return the player's bet amount
    public int getBet(){
        return betAmount;
    }
    
    //Increment the player's number of raises
    public void increaseRaiseNum(){
        raiseNum++;
    }
    
    //Return the player's number of raises
    public int getRaiseNum(){
        return raiseNum;
    }
    
    //Reset the player's number of raises
    public void resetRaiseNum(){
        raiseNum = 0;
    }
    
    //Return the player's hand
    public ArrayList<Card> getHand(){
        return playerHand;
    }
    
    //Set a new player's high hand
    public void setHighHand(ArrayList<Card> chosenHand){
        highHand.addAll(chosenHand);
    }
    
    //Set a new player's low hand
    public void setLowHand(ArrayList<Card> chosenHand){
        lowHand.addAll(chosenHand);
    }
    
    //Return the player's high hand
    public ArrayList<Card> getHighHand(){
        return highHand;
    }
    
    //Return the player's low hand
    public ArrayList<Card> getLowHand(){
        return lowHand;
    }
    
    //Return whether the player has a high hand
    public boolean hasHighHand(){
        return !highHand.isEmpty();
    }
    
    //Return whether the player has a low hand
    public boolean hasLowHand(){
        return !lowHand.isEmpty();
    }
    
    //Prepare the player for a new game
    public void reset(ArrayList<Card> newHand){
        playerHand.clear();
        playerHand.addAll(newHand);
        highHand.clear();
        lowHand.clear();
        isFolded = false;
        betAmount = 0;
        resetRaiseNum();
    }
}
