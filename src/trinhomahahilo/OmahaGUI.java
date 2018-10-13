package trinhomahahilo;

import objectdraw.*;
import java.awt.Color;
import java.util.Random;
import java.awt.Toolkit;
import java.awt.MediaTracker;
import java.awt.Image;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * @file OmahaGui
 * @author DucTrinh
 * @assignment Project 6: Omaha Hi/Lo
 * @date 03/20/2017
 * @description This class provides the GUI to play the Omaha Hi/Lo game 
 * between two to ten players. The game is a variation of traditional poker. 
 * Each player has four private cards and has to use two of those cards and 
 * three of the community cards to form their best high and low hands. The pot 
 * will be split between the player with the best high hand and the player 
 * with the best low hand. If there is no qualified low hands, the player with 
 * the Best high hand win the whole pot.
 */

public class OmahaGUI extends WindowController{

    //Instance variables for the OmahaGUI class
    
    //Variables involving the preflop round
    private Button[] firstOption;
    private Random randomGenerator;

    //Variables involving the general gameplay
    private int numOfPlayers;
    private Player[] players;
    private int currentPlayer;
    private int currentRound;
    
    //Variables involving bidding in the game
    private boolean canCheck;
    private int lastRaised;
    private int firstChecked;
    private boolean hasWinner;
    private boolean hasChecked;
    
    //Variables for the showdown round/comparing the hands
    private ArrayList<Card> communityCards;
    private int communityNum;
    private int privateNum;
    private Button resultBoard;
    private ArrayList<Text> winners;

    //Variables for the message boxes on the GUI
    private Button roundMessage;
    private Button messageBox;
    private Rectangle playerBox;
    private Rectangle playerBalance;
    private Button infoBoard;
    private ArrayList<Text> rules;

    //Variables involving the main gameboard
    private Button playingTable;
    private Oval pot;
    private int potBalance;
    private int highestBid;
    private Button highestBidAnnouncer;

    //Gameplay buttons
    private Button foldButton;
    private Button checkButton;
    private Button raiseButton;
    private Button callButton;
    private Button continueButton;
    private Button pickButton;
    private Button resetButton;

    //Variables involving the ArrayList deck
    private ArrayList<Card> deck;
    private Toolkit toolkit;
    private MediaTracker tracker;
    private int deckCount;

    //GUI buttons
    private Button exitButton;
    private Button infoButton;
    private Button nextGame;

    //Color variables
    final static Color burgundy = new Color(121, 30, 29);
    final static Color orange = new Color(255, 140, 0);
    final static Color darkGreen = new Color(3, 130, 3);
    final static Color newGray = new Color(186, 195, 209);
    final static Color newGreen = new Color (68, 204, 0);
    final static Color newBlue = new Color(81, 197, 255); 
    final static Color newYellow = new Color(239, 221, 111);  
        
    public OmahaGUI(){
        startController(1200, 800);
    }
    
    public void begin(){
        
        randomGenerator = new Random();
        createMessageBoxes(); //Create the message boxes on the GUI
        
        //Create the buttons to choose the number of players
        chooseNumOfPlayers();
        
        createPlayingTable(); //Create the playing table and its components
        
        //Create options for the players during the gameplay
        createGameplayButtons();
        
        createGUIButtons(); //Create the GUI buttons: Exit, Restart, Info
        
        //Create the deck
        createDeck();
        deckCount = 52;        
        communityCards = draw(5);
        
        //Create the info board which shows the main rules of the game
        createInfoBoard();
        
        winners = new ArrayList<Text>(); //Create the winners list
    }
    
    //Create the info board which tells the players the rules of the game
    public void createInfoBoard(){
        infoBoard = new Button(35, 120, 1120, 510, 50, 50, "Omaha Hi/Lo "
            + "Rules", 390, 123, 45, newBlue, canvas);
        addRules();
        infoBoard.hide();
    }
    
    //Create a list of rules for the game
    public void addRules(){
        rules = new ArrayList<Text>();
        rules.add(new Text("- This is a variation of Poker.", 90, 190, 
            canvas));
        rules.add(new Text("- Each player has four private cards and there "
            + "are five community cards.", 90, 230, canvas));
        rules.add(new Text("- Each player forms his best high hand and low "
            + "hand using two private cards", 90, 270, canvas));
        rules.add(new Text("  and three community cards." , 90, 310, canvas));
        rules.add(new Text("- The hand strength for the high hand is the "
            + "same as in traditional Poker.", 90, 350, canvas));
        rules.add(new Text("- The low hand must consist of five unpaired "
            + "cards with ranks at or below 8.", 90, 390, canvas));
        rules.add(new Text("  Aces are considered low. Low hands are counted "
            + "from the top down.", 90, 430, canvas));
        rules.add(new Text("  2-3-4-5-7 is lower than A-2-3-4-8.", 90, 470, 
            canvas));
        rules.add(new Text("- The pot is split between the best hand for "
            + "high and the best hand for low.", 90, 510, canvas));
        rules.add(new Text("   If there is no low hand, the player with the "
            + "high hand get the whole pot.", 90, 550, canvas));
        for (int i = 0; i < rules.size(); i++){
            rules.get(i).setFontSize(30);
            rules.get(i).hide();
        }
    }
    
    //Create message boxes on the GUI
    public void createMessageBoxes(){
        //Create the round announcement message box which declares the current
        //round
        roundMessage = new Button(355, 30, 470, 80, 50, 50, "Pre-game", 
            470, 33, 50, burgundy, canvas);
        roundMessage.setTextColor(Color.WHITE);
        
        //Create the main message board which tells the most recent actions
        messageBox = new Button(120, 125, 915, 80, 50, 50, "Choose the" +
            " number of players", 330, 138, 38, burgundy, canvas);
        messageBox.setTextColor(Color.WHITE);
        
        //Create the player box which tells the current player's name
        playerBox = new Rectangle(0, 658, 180, 80, "Player 1", 20, 675, 35, 
            newGray, canvas);
        playerBox.hide();
        
        //Create the player balance box which tells the current player's 
        //balance
        playerBalance = new Rectangle(1004, 658, 180, 80, "$3000", 1044, 675, 
            35, newGreen,canvas);
        playerBalance.hide();
    }
    
    //Create the buttons to choose the number of players
    public void chooseNumOfPlayers(){
        firstOption = new Button[9];
        
        //Create the first row of buttons 
        for (int i = 0; i < 5; i++){
            firstOption[i] = new Button(i * 225 + 35, 290, 220, 130, 60, 60,
                Integer.toString(i + 2), i * 225 + 125, 310, 75, Color.WHITE,
                canvas);
        }        
        
        //Create the second row of buttons
        for (int i = 5; i < 9; i++){
            firstOption[i] = new Button((i - 4) * 225 - 95, 460, 220, 130, 
                60, 60, Integer.toString(i + 2), (i - 4) * 225 - 7, 480, 75,
                Color.WHITE, canvas);
        }     
        firstOption[8].moveText(870, 480);    
    }    
    
    //Create the playing table and its components
    public void createPlayingTable(){   
        //Create the playing table
        playingTable = new Button(80, 220, 1010, 400, 50, 50, "", 0, 0, 0, 
            darkGreen, canvas);
        playingTable.hide();
        
        //Create announcer for the highest bidding on the table
        highestBid = 0;
        highestBidAnnouncer = new Button(290, 580, 620, 40, 30, 30, 
            "Highest bid: $" + highestBid, 500, 582, 25, newGray, canvas);
        highestBidAnnouncer.hide();
        
        //Create the pot
        pot = new Oval(470, 335, 240, 240, "$0", 540, 405, Color.YELLOW, 
            canvas);
        pot.hide();
        potBalance = 0;
    }
    
    //Create the deck 
    public void createDeck(){
        
        //Stuff to display the imported images
        toolkit = Toolkit.getDefaultToolkit();
        tracker = new MediaTracker(this);
        try {
        // load all the image for later use
            tracker.waitForAll();
        } catch (InterruptedException ex) {}
        
        //Variables to help create the deck
        deck = new ArrayList<Card>();
        Image cardImage;
        String[] suits = {"clubs", "hearts", "diamonds", "spades"};
        
        for (int i = 0; i < 4; i++){
            //Create the number cards
            for (int j = 2; j < 11; j++){
                cardImage = toolkit.getImage("src/trinhomahahilo/Cards/" + j + 
                    suits[i] + ".GIF");
                addCard(cardImage, j, suits[i]);
            }
            
            //Create the jack cards
            cardImage = toolkit.getImage("src/trinhomahahilo/Cards/jack" + 
                suits[i] + ".GIF");
            addCard(cardImage, 11, suits[i]);
            
            //Create the queen cards
            cardImage = toolkit.getImage("src/trinhomahahilo/Cards/queen" +
                suits[i] + ".GIF");
            addCard(cardImage, 12, suits[i]);
            
            //Create the king cards
            cardImage = toolkit.getImage("src/trinhomahahilo/Cards/king" + 
                suits[i] + ".GIF");
            addCard(cardImage, 13, suits[i]);
            
            //Create the ace cards
            cardImage = toolkit.getImage("src/trinhomahahilo/Cards/ace" + 
                suits[i] + ".GIF");
            addCard(cardImage, 1, suits[i]);
        }
    }
    
    //Helper method to add cards to the ArrayList deck
    public void addCard(Image cardImage, int cardValue, String suit){
        tracker.addImage(cardImage, 0);
        deck.add(new Card(cardImage, 500, 815, cardValue, suit, canvas));
    }
    
    //Create the main GUI buttons
    public void createGUIButtons(){
        //Create the exit button
        exitButton = new Button(1070, 30, 70, 70, 40, 40, "X", 1087, 30, 55,
            Color.RED, canvas);
        
        //Create the info button
        infoButton = new Button(50, 30, 70, 70, 40, 40, "i", 77, 30, 55, 
            newBlue, canvas);
        
        //Create the button to proceed to the next game
        nextGame = new Button(470, 630, 240, 100, 40, 40, "Next Game",
            490, 650, 40, newGray, canvas);
        nextGame.hide();
    }
    
    //Create the gameplay buttons
    public void createGameplayButtons(){
        //Create the fold button
        foldButton = new Button(200, 635, 200, 48, 30, 30, "Fold", 265, 638,
            30, orange, canvas);
        foldButton.hide();
        
        //Create the raise button
        raiseButton = new Button(784, 635, 200, 48, 30, 30, "Raise", 840, 
            638, 30, orange, canvas);
        raiseButton.hide();
        
        //Create the check button
        checkButton = new Button(200, 688, 200, 48, 30, 30, "Check", 250, 691, 
            30, orange, canvas);
        checkButton.hide();
        
        //Create the call button
        callButton = new Button(784, 688, 200, 48, 30, 30, "Call", 855, 691,
            30, orange, canvas);
        callButton.hide();
        
        //Create the button used to show the next player's hand
        continueButton = new Button(470, 635, 240, 100, 40, 40, "Continue",
            500, 655, 40, newGray, canvas);
        continueButton.hide();
        
        //Create the button used to pick the chosen hand as your hand
        pickButton = new Button(784, 648, 200, 70, 40, 40, "Pick", 
            843, 655, 37, orange, canvas);
        pickButton.hide();
        
        //Create the button used to reset the chosen hand
        resetButton = new Button(200, 648, 200, 70, 40, 40, "Reset", 
            248, 655, 37, orange, canvas);
        resetButton.hide();
        
        //Create the board to show the results of the game
        resultBoard = new Button(80, 220, 1010, 400, 50, 50, "", 0, 0, 0, 
            newYellow, canvas);
        resultBoard.hide();
    }
    
    //Take out an input number of Cards from the deck and return them in 
    //another ArrayList of Cards
    public ArrayList<Card> draw(int numberOfCard){
        ArrayList<Card> cardsDrawn = new ArrayList<Card>();
        for (int i = 0; i < numberOfCard; i++){
            int cardDrawn = randomGenerator.nextInt(deckCount);
            cardsDrawn.add(deck.get(cardDrawn));
            deck.remove(cardDrawn);
            deckCount--;
        }   
        return cardsDrawn;
    }
    
    //Return the next player in line that has not folded yet
    public void getNextPlayer(){
        currentPlayer = (currentPlayer + 1) % numOfPlayers;
        while(players[currentPlayer].isFolded())
            currentPlayer = (currentPlayer + 1) % numOfPlayers;
    }
    
    //Change the objects' states to prepare for the next player
    public void hidePlayer(){
        for (int i = 0; i < 4; i++){
            players[currentPlayer].getHand().get(i).hide(false);
        }
        continueButton.show();
    }
    
    //Changes the objects' states to match the pre-flop round
    public void transitToPreFlop(){
        
        //Hide the buttons used to choose the number of players
        for (int i = 0; i < 9; i++){
            firstOption[i].hide();
        }
        
        //Random and declare the small blind/starter of the game
        currentPlayer = randomGenerator.nextInt(numOfPlayers);
        messageBox.setText("Number of players is " + numOfPlayers + ". Player"
            + " " + Integer.toString(currentPlayer + 1) + " opens.");
        messageBox.moveText(220, 137);
        
        //Change the state of the objects to match the round
        roundMessage.setText("Pre-flop");
        roundMessage.moveText(490, 35); 
        currentRound = 1;
        canCheck = true;
        lastRaised = 10;
        firstChecked = 10;
        hasChecked = false;
        hasWinner = false;
        
        //Show the playing table and its components
        playingTable.show();
        pot.show();
        highestBidAnnouncer.show();
        
        //Initialize the Array players
        players = new Player[numOfPlayers];
        for (int i = 0; i < numOfPlayers; i++){
            players[i] = new Player(draw(4));
            for (int j = 0; j < 4; j++){
                players[i].getHand().get(j).moveTo(416 + j * 92, 638);
            }
        }
        
        //Show the number and balance of the starter's player
        playerBox.setText("Player " + Integer.toString(currentPlayer + 1));
        playerBox.show();
        playerBalance.show();
        
        //Show the gameplay buttons
        checkButton.show();
        callButton.show();
        foldButton.show();
        raiseButton.show();
        
        //Show the starter player's hand
        for (int i = 0; i < 4; i++){
            players[currentPlayer].getHand().get(i).show(false);
        }
    }
    
    //Return the raise amount for the round
    public int raiseAmount(){
        if (currentRound < 3){
            return 15;
        }
        else {
            return 30;
        }
    }  
    
    //Update the balance of the winners
    public void updateBalance(){
        if (hasLowHand()){
            if (potBalance % 2 == 0){
                players[getHighWinner()].gainMoney(potBalance / 2);
                players[getLowWinner()].gainMoney(potBalance / 2);
            }
            else{
                players[getHighWinner()].gainMoney((potBalance  - 15) / 2 + 
                15);
                players[getLowWinner()].gainMoney((potBalance  - 15) / 2);
            }
        }
        else{
            players[getHighWinner()].gainMoney(potBalance);
        }
        potBalance = 0;
    }
   
    //Return whether all players have bid the same amount
    public boolean checkEqualBet(int starting){
        int bidAmount = players[starting].getBet();
        for (int i = 0; i < numOfPlayers; i++){
            if (players[i].getBet() != bidAmount && !players[i].isFolded()){
                return false;
            }
        }
        return true;
    }
    
    //Check if there is only one person who has not folded yet
    public boolean checkFolded(int starting){
        for (int i = 0; i < numOfPlayers; i++){
            if (!players[i].isFolded() && i != starting){
                return false;
            }
        }
        return true;
    }
    
    //Transit to the next round
    public void transitToNextRound(){
        
        //Change the state of the objects to reset the round
        roundMessage.moveText(535, 35); 
        canCheck = true; 
        lastRaised = 10;
        firstChecked = 10;
        hasChecked = false;
        
        //Reset the number of raises allowed for each player
        for (int i = 0; i < numOfPlayers; i++){
            players[i].resetRaiseNum();
        }
        
        if (currentRound == 1){
            transitToFlop();
        }
        else if (currentRound == 2){
            transitToTurn();
        }
        else if (currentRound == 3){
            transitToRiver();
        }
        else if (currentRound == 4){
            transitToShowdown();
        }
        
        currentRound++;
    }
    
    //Transit to the flop round   
    public void transitToFlop(){
        
        //Declare the round
        roundMessage.setText("Flop");
        
        //Change the message
        messageBox.setText("The first three community cards are dealt");
        messageBox.moveText(220, 137);
        
        //Show the first three cards
        for (int i = 0; i < 3; i++){
            communityCards.get(i).moveTo(380 + i * 92, 230);
            communityCards.get(i).show(false);
        }
    }
    
    //Transit to the turn round
    public void transitToTurn(){
        
        //Declare the round
        roundMessage.setText("Turn");
        
        //Change the message
        messageBox.setText("The fourth community card is dealt");
        messageBox.moveText(270, 137);
        
        //Show the fourth community card
        communityCards.get(3).moveTo(656, 230);
        communityCards.get(3).show(false);
    }        
    
    //Transit to the river round
    public void transitToRiver(){
        
        //Declare the round
        roundMessage.setText("River");
        
        //Change the message
        messageBox.setText("The final community card is dealt");
        messageBox.moveText(280, 137);
        
        //Show the fourth community card
        communityCards.get(4).moveTo(748, 230);
        communityCards.get(4).show(false);
    }     
    
    //Transit to the showdown round
    public void transitToShowdown(){
        
        //Declare the round
        roundMessage.setText("Showdown");
        roundMessage.moveText(455, 35);         
        
        //Change the message
        messageBox.setText("Pick your best high hand");
        messageBox.moveText(370, 137);
        
        //Change the objects
        raiseButton.hide();
        checkButton.hide();
        callButton.hide();
        foldButton.hide();
        
        pickButton.show();
        resetButton.show();
        
        communityNum = 0;
        privateNum = 0;
    }
    
    //Transit to the results
    public void transitToResults(){ 
        //Update the objects to match the round
        currentRound++;
        pickButton.hide();
        resetButton.hide();
        playerBalance.hide();
        playerBox.hide();
        nextGame.show();        
        resultBoard.show();
        
        //Update the message
        messageBox.setText("The winners are now announced");
        messageBox.moveText(290, 137);
        updateBalance();
        
        //Hide the community cards
        for (int i = 0; i < 5; i++){
            communityCards.get(i).hide(false);
        }
        
        //Add the winner of the high hand to the winners list
        winners.add(new Text("The winner of the high hand is player " + 
            Integer.toString(getHighWinner() + 1), 230, 280, canvas));
        winners.add(new Text("Player " + Integer.toString(getHighWinner() + 1) 
            + "'s balance is now: $" + 
            Integer.toString(players[getHighWinner()].getBalance()), 230, 320, 
            canvas));
        
        //Add the winner of the low hand to the winner list
        if (hasLowHand() && (getHighWinner() != getLowWinner())){
            winners.add(new Text("The winner of the low hand is player " + 
                Integer.toString(getLowWinner() + 1), 230, 360, canvas));
            winners.add(new Text("Player " + 
                Integer.toString(getLowWinner() + 1)  + "'s balance is now: $" 
                + Integer.toString(players[getLowWinner()].getBalance()), 230, 
                400, canvas));
        }
        //Or show that there is no qualified low hand
        else if (hasLowHand() && (getHighWinner() == getLowWinner())){
            winners.add(new Text("High hand winner is also low hand winner", 
                230, 360, canvas));
        }
        else{
            winners.add(new Text("There is no qualified low hand", 230, 360, 
                canvas));
        }
        
        for (int i = 0; i < winners.size(); i++){
            winners.get(i).setFontSize(35);
            winners.get(i).setBold(true);
        }
    }
    
    //Transit to the next game
    public void transitToNextGame(){ 
        nextGame.hide();
        
        //Random and declare the small blind/starter of the game
        currentPlayer = randomGenerator.nextInt(numOfPlayers);
        messageBox.setText("Player " + Integer.toString(currentPlayer + 1) + 
            " opens");
        messageBox.moveText(440, 137);
        
        //Clear the list of winners
        if (!hasWinner){
            for (int i = 0; i < winners.size(); i++){
                winners.get(i).removeFromCanvas();
            }
            winners.clear();
        }
        
        //Change the state of the objects to match the round
        roundMessage.setText("Pre-flop");
        roundMessage.moveText(490, 35); 
        currentRound = 1;
        canCheck = true;
        lastRaised = 10;
        firstChecked = 10;
        hasChecked = false;
        hasWinner = false;
        potBalance = 0;
        highestBid = 0;        
        
        //Show the playing table and its components
        playingTable.show();
        pot.show();
        pot.setText("$0");
        adjustPotText();
        highestBidAnnouncer.show();
        highestBidAnnouncer.setText("Highest bid: $0");
        resultBoard.hide();
        
        for (int i = 0; i < 5; i++){
            communityCards.get(i).hide(false);
        }
        
        //Return the cards back to the deck
        deck.addAll(communityCards);
        communityCards.clear();
        for (int i = 0; i < numOfPlayers; i++){
            deck.addAll(players[i].getHand());
        }
        deckCount = 52;
        communityCards.addAll(draw(5));
        
        //Reset the players status and hands
        for (int i = 0; i < numOfPlayers; i++){
            players[i].reset(draw(4));
            for (int j = 0; j < 4; j++){
                players[i].getHand().get(j).moveTo(416 + j * 92, 638);
            }
        }
        
        //Show the number and balance of the starter's player
        playerBox.setText("Player " + Integer.toString(currentPlayer + 1));
        playerBox.show();
        playerBalance.setText("$" + players[currentPlayer].getBalance());
        playerBalance.show();
        
        //Show the gameplay buttons
        checkButton.show();
        callButton.show();
        foldButton.show();
        raiseButton.show();
        
        //Show the starter player's hand
        for (int i = 0; i < 4; i++){
            players[currentPlayer].getHand().get(i).show(false);
        }
    }
    
    //Adjust the pot's text position based on the size of the balance
    public void adjustPotText(){
        if (potBalance >= 1000){
            pot.moveText(465, 405);
        }
        else if (potBalance >= 100){
            pot.moveText(500, 405);
        }
        else if (potBalance > 0){
            pot.moveText(520, 405);
        }
        else if (potBalance == 0){
            pot.moveText(540, 405);
        }
    }
    
    //Helper method to change the player status to folded
    public void fold(){
        if (!players[currentPlayer].isFolded()){
            players[currentPlayer].fold();
            
            //Update the message
            messageBox.setText("Player " + Integer.toString(currentPlayer 
                + 1) + " folded");
            messageBox.moveText(450, 138);
            
            hidePlayer();
        }
    }
    
    //Helper method to skip the player's turn
    public void check(){ 
        if (canCheck){
            
            //Record the person that check first in this round
            if (hasChecked == false){
                firstChecked = currentPlayer;
                hasChecked = true;
            }
            
            //Update the message
            messageBox.setText("Player " + Integer.toString(currentPlayer 
                + 1) + " has checked.");
            messageBox.moveText(395, 138);
            
            hidePlayer();
        }
        //Update the message to announce that you can not check
        else{
            messageBox.setText("You can not check because someone raised");
            messageBox.moveText(210, 138);
        }
    }
    
    //Helper method to raise the bid of a player
    public void raise(){
        //Allow the player to raise if the amount of raises of that player
        //made does not exceed 3
        if (players[currentPlayer].getRaiseNum() < 3){            
            //Calculate how much is the total amount of money the player is 
            //raising
            int totalRaise = highestBid - players[currentPlayer].getBet() +
                raiseAmount();
            
            if (players[currentPlayer].getBalance() - totalRaise < 0){
                messageBox.setText("Do not have enough money to raise");
                messageBox.moveText(260, 137);
            }
            else{
                //No one can check in this round and record this player as the 
                //person who raised last
                canCheck = false;
                lastRaised = currentPlayer;
                
                //Update the pot
                potBalance = potBalance + totalRaise;
                pot.setText("$" + Integer.toString(potBalance));
                adjustPotText();            

                //Update the highest bid per person
                highestBid = highestBid + raiseAmount();
                highestBidAnnouncer.setText("Highest bid: $" + 
                    Integer.toString(highestBid));

                //Update the message
                messageBox.setText("Player " + Integer.toString(currentPlayer 
                    + 1) + " has raised $" + Integer.toString(raiseAmount()));
                messageBox.moveText(380, 138);

                //Update the current player's balance and bid amount
                players[currentPlayer].loseMoney(totalRaise);
                players[currentPlayer].increaseBet(totalRaise);
                playerBalance.setText("$" + 
                    players[currentPlayer].getBalance());
                players[currentPlayer].increaseRaiseNum();

                hidePlayer();
            }
        }
        //Update the message to announce that you have reached the raise limit
        else{
            messageBox.setText("You can only raise three times in one round");
            messageBox.moveText(210, 138);
        }
    }
    
    //Helper method to make a player's bid the same as the highest bid
    public void call(){
        if (lastRaised != 10){
            //Calculate the amount need to make the player's bid equal the 
            //highest bid
            int callAmount = highestBid - players[currentPlayer].getBet();
            
            if ((players[currentPlayer].getBalance() - callAmount) <= 0){
                endGame();
            }
            else{
                //Update the pot
                potBalance = potBalance + callAmount;
                pot.setText("$" + Integer.toString(potBalance));
                adjustPotText();

                //Update the message
                messageBox.setText("Player " + 
                    Integer.toString(currentPlayer + 1) + " has called");
                messageBox.moveText(410, 138);

                //Update the current player's balance and bid amount
                players[currentPlayer].loseMoney(callAmount);
                players[currentPlayer].increaseBet(callAmount);
                playerBalance.setText("$" + 
                    players[currentPlayer].getBalance());

                hidePlayer();
            }
        }
        //Update the message to announce you can not call because no one has
        //raised in this round
        else{
            messageBox.setText("You can not call because no one raised");
            messageBox.moveText(220, 138);
        }
    }
    
    //Announce the winner when there is only one person who has not foled
    public void announceWinner(){
        messageBox.setText("Player " + Integer.toString(currentPlayer + 1) 
            +  " won $" + Integer.toString(potBalance - 
            players[currentPlayer].getBet()));
        messageBox.moveText(430, 138);
        hasWinner = true;

        playerBox.setText("Player " + Integer.toString(currentPlayer + 1));
        players[currentPlayer].gainMoney(potBalance);
        playerBalance.setText("$" + players[currentPlayer].getBalance());
        nextGame.show();
        hidePlayer();
        continueButton.hide();
    }
    
    //Helper method to proceed the game according to different situations
    //when the continue button is clicked
    public void continueGame(){
        //Find the next player
        getNextPlayer();
        
        //Ask the player to pick the hand if it is the Showdown round
        if (currentRound == 5 && !checkFinishChoosing()){
            messageBox.setText("Pick your best high hand");
            messageBox.moveText(370, 137);
        }
        
        //Move to the next round if every player has checked
        else if (firstChecked == currentPlayer && lastRaised == 10){
            transitToNextRound();
        }
        
        //Declare a winner if there is only one person who has not folded
        else if (checkFolded(currentPlayer)){
            announceWinner();
        }
        
        //Move to the next round if every player has called
        else if (currentPlayer == lastRaised && checkEqualBet(currentPlayer)){
            transitToNextRound();
            getNextPlayer();
        }
        
        //Update the message to announce the current player
        else{
            messageBox.setText("Player " + Integer.toString(currentPlayer + 1) 
                +  "'s turn");
            messageBox.moveText(450, 138);
        }
        
        //If every player has finished choosing their hands then move to the
        //results
        if (currentRound == 5 && checkFinishChoosing()){
            transitToResults();
        }
        else if (!checkFolded(currentPlayer)){
            //Show the current player's hand
            for (int i = 0; i < 4; i++){
                players[currentPlayer].getHand().get(i).show(false);
            }

            //Update the boxes to the current player's number and balance
            playerBox.setText("Player " + 
                Integer.toString(currentPlayer + 1));
            playerBalance.setText("$" + players[currentPlayer].getBalance());
        }
    }
    
    //Return whether every player has chosen their hands
    public boolean checkFinishChoosing(){
        for (int i = 0; i < numOfPlayers; i++){
            if (!players[i].isFolded() && !players[i].hasHighHand()){
                return false;
            }
        }
        return true;
    }
    
    //Return the winner of the high hand
    public int getHighWinner(){
        int winner = currentPlayer;
        for (int i = 0; i < numOfPlayers; i++){
            if (!players[i].isFolded()){                
                if (getHighStrength(players[i].getHighHand()) > 
                    getHighStrength(players[winner].getHighHand())){
                    winner = i;
                }
                else if (getHighStrength(players[i].getHighHand()) == 
                    getHighStrength(players[winner].getHighHand()) &&
                    compareHighHands(players[i].getHighHand(), 
                    players[winner].getHighHand()) == 1){
                    winner = i;
                }
            }
        }
        return winner;
    }
    
    //Return the winner of the low hand
    public int getLowWinner(){
        boolean first = true;
        int winner = 0;
        for (int i = 0; i < numOfPlayers; i++){
            if (!players[i].isFolded() && isLow(players[i].getLowHand()) &&
                first){
                winner = i;
                first = false;
            }
            else if (!players[i].isFolded() && isLow(players[i].getLowHand()) 
                && (compareLowHands(players[i].getLowHand(), 
                players[winner].getLowHand()) == 1)){
                winner = i;               
            }
        }
        return winner;
    }
    
    //Return whether there is a qualified low hand
    public boolean hasLowHand(){
        for (int i = 0; i < numOfPlayers; i++){
            if (!players[i].isFolded() && isLow(players[i].getLowHand())){
                return true;
            }
        }
        return false;
    }
    
    //Return the hand strength of a high hand
    public int getHighStrength(ArrayList<Card> playerHand){
        if (isFlush(playerHand) && isStraight(playerHand) && 
            sortHand(playerHand, true)[0] == 10){
            return 9;
        }
        else if (isFlush(playerHand) && isStraight(playerHand)){
            return 8;
        }
        else if (isFourOfAKind(playerHand)){
            return 7;
        }
        else if (hasThreeOfAKind(playerHand) && 
            (getNumPairs(playerHand) == 1)){
            return 6;
        }
        else if (isFlush(playerHand)){
            return 5;
        }
        else if (isStraight(playerHand)){
            return 4;
        }
        else if (hasThreeOfAKind(playerHand)){
            return 3;
        }
        else if (getNumPairs(playerHand) == 2){
            return 2;
        }
        else if (getNumPairs(playerHand) == 1){
            return 1;
        }
        else{
            return 0;
        }
    }
    
    //Return whether the hand is a flush
    public boolean isFlush(ArrayList<Card> playerHand){
        for (int i = 0; i < 4; i++){
            if (!playerHand.get(i).getSuit().equals(
                playerHand.get(i + 1).getSuit())){
                return false;
            }
        }
        return true;
    }
    
    //Return whether the hand is a straight
    public boolean isStraight(ArrayList<Card> playerHand){
        int[] cardValues = sortHand(playerHand, true);
        for (int i = 0; i < 4; i++){
            if (cardValues[i] + 1 != cardValues[i + 1]){
                return false;
            }
        }
        return true;
    }
    
    //Return whether the hand is a four of a kind
    public boolean isFourOfAKind(ArrayList<Card> playerHand){
        int[][] inputCards = numOfInstances(playerHand);
        for (int j = 0; j < 5; j++){
            if (inputCards[1][j] == 4){
                return true;
            }
        }
        return false;
    }
    
    //Return the number of pairs the hand has
    public int getNumPairs(ArrayList<Card> playerHand){
        int[][] inputCards = numOfInstances(playerHand);
        int numberOfPairs = 0;
        for (int j = 0; j < 5; j++){     
            if (inputCards[1][j] == 2){
                numberOfPairs++;
            }
        }   
        return numberOfPairs;
    }
    
    //Return whether the hand has a three of a kind
    public boolean hasThreeOfAKind(ArrayList<Card> playerHand){
        int[][] inputCards = numOfInstances(playerHand);
        for (int j = 0; j < 5; j++){
            if (inputCards[1][j] == 3){
                return true;
            }
        }
        return false;
    }
    
    //Sort a hand into ascending numerical order
    public int[] sortHand(ArrayList<Card> playerHand, boolean isHigh){
        int[] cardValues = new int[5];
        for (int i = 0; i < 5; i++){
            if ((playerHand.get(i).getValue() == 1) && isHigh){
                cardValues[i] = 14;
            }
            else{
                cardValues[i] = playerHand.get(i).getValue();
            }
        }
        Arrays.sort(cardValues);
        return cardValues;
    }
    
    //Compare two high hands
    public int compareHighHands(ArrayList<Card> firstPlayer, 
        ArrayList<Card> secondPlayer){
        int[] firstHand = sortHand(firstPlayer, true);
        int[] secondHand = sortHand(secondPlayer, true);
        for (int i = 4; i >= 0; i--){
            if (firstHand[i] > secondHand[i]){
                return 1;
            }
            else if (secondHand[i] > firstHand[i]){
                return 2;
            }
        }
        return 0;
    } 
    
    //Compare two low hands
    public int compareLowHands(ArrayList<Card> firstPlayer, 
        ArrayList<Card> secondPlayer){
        int[] firstHand = sortHand(firstPlayer, false);
        int[] secondHand = sortHand(secondPlayer, false);
        for (int i = 4; i >= 0; i--){
            if (firstHand[i] < secondHand[i]){
                return 1;
            }
            else if (secondHand[i] < firstHand[i]){
                return 2;
            }
        }
        return 0;
    }
    
    //Return the number of instances a card value appears in a player hand
    public int[][] numOfInstances(ArrayList<Card> playerHand){
        int[][] cards = new int[2][5];
        
        //Fill the array with 0
        for (int i = 0; i < 2; i++){
            for (int j = 0; j < 5; j ++){
                cards[i][j] = 0;
            }
        } 
        
        int k = 0;        
        int[] inputHand = sortHand(playerHand, true);
        int i = 0;
        
        while (i < 4){
            //Fills a column for the curretn value
            cards[0][k] = inputHand[i];
            cards[1][k]++;
            //If the next value is different than the current value, fills  
            //another column on the table
            if (inputHand[i] != inputHand[i + 1]){
                i++;
                k++;
                if (i == 4){
                    cards[0][k] = inputHand[i];
                    cards[1][k]++;
                }
            }
            //If the next value is equal to the current value, increment the 
            //number of instances of the current value
            else{
                i++;
                if (i == 4){
                    cards[1][k]++;
                }
            }
        }
        return cards;
    }
    
    //Return whether the low hand is qualified
    public boolean isLow(ArrayList<Card> playerHand){
        int[] playerCards = sortHand(playerHand, false);
        for (int i = 0; i < 5; i++){
            if (playerCards[i] > 8){
                return false;
            }
        }       
        return true;
    }
    
    //Deselect the cards that are chosen 
    public void deselectCards(){
        for (int i = 0; i < 5; i++){
            if (!communityCards.get(i).isNotChosen())
                communityCards.get(i).hide(true);
            } 
        
        for (int i = 0; i < 4; i++){
            if (!players[currentPlayer].getHand().get(i).isNotChosen())
                players[currentPlayer].getHand().get(i).hide(true);   
        }
        
        communityNum = 0;
        privateNum = 0;
    }
    
    //Change the community card borders when they are clicked
    public void changeCommunityBorders(int i){
        if (communityCards.get(i).isNotChosen() && communityNum < 3){
            communityCards.get(i).show(true);
            communityNum++;
        }
        else if (!communityCards.get(i).isNotChosen()){
            communityCards.get(i).hide(true);
            communityNum--;
        }
    }
    
    //Change the private card borders when they are clicked
    public void changePrivateBorders(int i){
        if (players[currentPlayer].getHand().get(i).isNotChosen()
            && privateNum < 2){
            players[currentPlayer].getHand().get(i).show(true);
            privateNum++;
        }
        else if (!players[currentPlayer].getHand().get(i).isNotChosen()){
            players[currentPlayer].getHand().get(i).hide(true);
            privateNum--;
        }
    }
    
    //Set the currently selected hand as the high/low hand for the player
    public void pickHand(){
        ArrayList<Card> chosenHand = new ArrayList<Card>();
        
        //Add the selected community cards to the hand
        for (int i = 0; i < 5; i++){
            if (!communityCards.get(i).isNotChosen())
                chosenHand.add(communityCards.get(i));
        } 
        
        //Add the selected private cards to the hand
        for (int i = 0; i < 4; i++){
            if (!players[currentPlayer].getHand().get(i).isNotChosen())
                chosenHand.add(players[currentPlayer].getHand().get(i));
        }
        
        //Set the currently selected hand as the high hand
        if (!players[currentPlayer].hasHighHand()){
            players[currentPlayer].setHighHand(chosenHand);
            messageBox.setText("High hand picked, now pick your low hand");
            messageBox.moveText(185, 137);
            deselectCards();
        }
        //Set the currently selected hand as the low hand
        else if (!players[currentPlayer].hasLowHand()){
            players[currentPlayer].setLowHand(chosenHand);
            messageBox.setText("Low hand picked");
            messageBox.moveText(430, 137);
            deselectCards();
            hidePlayer();
        }
    }
    
    //Change the status of the info board base on its visibility
    public void changeInfoBoard(){
        if (infoBoard.isHidden()){
            infoBoard.show();
            for (int i = 0; i < rules.size(); i++){
                rules.get(i).show();
            }
        }
        else{
            infoBoard.hide();
            for (int i = 0; i < rules.size(); i++){
                rules.get(i).hide();
            }
        }
    }
    
    //End the game when a player can not call
    public void endGame(){
        //Adjust the message boxes around the screen
        roundMessage.setText("Game End");
        roundMessage.moveText(455, 35); 
        
        messageBox.setText("Player " + Integer.toString(currentPlayer + 1) + 
            " ran out of money");
        messageBox.moveText(355, 137);
        
        highestBidAnnouncer.setText("Hope you enjoyed the game :)");
        highestBidAnnouncer.moveText(425, 582);
        
        //Hide the unnecessary buttons or boxes around the screen
        hidePlayer();
        continueButton.hide();
        
        foldButton.hide();
        checkButton.hide();
        raiseButton.hide();
        callButton.hide();
        
        playerBox.hide();
        playerBalance.hide();
        pot.setText("$0");
        potBalance = 0;
        adjustPotText();
        
        for (int i = 0; i < 5; i++){
            communityCards.get(i).hide(false);
        }
    }
    
    public void onMouseRelease(Location point){     
        //Set the number of players when the button is clicked and moves to 
        //pre-flop round
        for (int i = 0; i < 9; i++){
            if (firstOption[i].contains(point) && !firstOption[i].isHidden()
                && infoBoard.isHidden()){
                numOfPlayers = i + 2;
                transitToPreFlop();
            }
        }
        
        //Exit the GUI when the exit button is clicked
        if (exitButton.contains(point)) {
            System.exit(0);
        }
        
        //Show or close the info board when the info button is clicked
        else if (infoButton.contains(point) && (currentRound != 6)){
            changeInfoBoard();
        }
        
        //Hide the info board when it is clicked and currently shown
        else if (infoBoard.contains(point) && !infoBoard.isHidden()){
            infoBoard.hide();
            for (int i = 0; i < rules.size(); i++){
                rules.get(i).hide();
            }
        }
        
        //Change the player status to folded when the fold button is clicked
        else if (foldButton.contains(point) && !foldButton.isHidden() && 
            !hasWinner && continueButton.isHidden()){
            fold();
        }
        
        //Skip the player's turn if no one has bid in this round yet
        else if (checkButton.contains(point) && !checkButton.isHidden() 
            && !hasWinner && continueButton.isHidden()){
            check();
        }
        
        //Make the player bid amount equal the current highest bid plus the 
        //raise amount when the raise button is clicked
        else if (raiseButton.contains(point) && !raiseButton.isHidden() 
            && !hasWinner && continueButton.isHidden()){
            raise();
        }
        
        //Makes the player's bid equal the highest bid on the table when the 
        //call button is clicked
        else if (callButton.contains(point) && !callButton.isHidden() && 
            !hasWinner && continueButton.isHidden()){
            call();
        }
        
        //Proceed the game in regard to different situations when the continue 
        //button is clicked
        else if (continueButton.contains(point) && 
            !continueButton.isHidden()){
            continueButton.hide();
            continueGame();
        }
        
        //Deselect the currently selected hand when clicked
        else if (resetButton.contains(point) && !resetButton.isHidden()){
            deselectCards();
        }
        
        //Set the currently selected hand as the high/low hand for the player
        else if (pickButton.contains(point) && !pickButton.isHidden()){
            if ((communityNum + privateNum) == 5){
                pickHand();
            }
            else{
                messageBox.setText("You have not picked 5 cards");
                messageBox.moveText(330, 137);
            }
        }
    
        //Go to the next game when the button is clicked
        else if (nextGame.contains(point) && !nextGame.isHidden()){
            transitToNextGame();
        }
        
        //Change the community card borders when they are clicked
        for (int i = 0; i < 5; i++){
            if (communityCards.get(i).contains(point) && currentRound == 5){
                changeCommunityBorders(i);
            }
        } 
             
        //Change the private card borders when they are clicked
        for (int i = 0; i < 4; i++){
            if (players[currentPlayer].getHand().get(i).contains(point) && 
                currentRound == 5 && 
                !players[currentPlayer].getHand().get(i).isHidden()){
                changePrivateBorders(i);
            }
        }
    }
    
    public static void main(String[] args) {
        OmahaGUI omahagui = new OmahaGUI();
    }
}
