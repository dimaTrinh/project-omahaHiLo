package trinhomahahilo;

import java.awt.Image;
import objectdraw.*;
import java.awt.Color;

/**
 * @file Card
 * @author DucTrinh
 * @assignment Project 6: Omaha Hi/Lo
 * @date 03/27/2017
 * @description This class extends the VisibleImage class and provides the 
 * implementation for a Card object. A Card object has variables containing
 * the card value and its suit.
 */

public class Card extends VisibleImage{

    //Instance variables for the Card class
    private int cardValue;
    private String suit;
    private FilledRect border;
    
    //Constructor for a Card object
    public Card(Image image, double x, double y, int cardValue, String suit, 
        DrawingCanvas canvas){
        super(image, x, y, canvas);
        border = new FilledRect(x - 3, y - 3, 79, 103, canvas);
        border.setColor(Color.RED);
        border.sendBackward();
        this.cardValue = cardValue;
        this.suit = suit;
        hide(false);
    }
    
    //Return the card value
    public int getValue(){
        return cardValue;
    }
    
    //Return the card suit
    public String getSuit(){
        return suit;
    }
    
    //Hide the card with or without border
    public void hide(boolean onlyBorder){
        if (!onlyBorder){
            border.hide();
            super.hide();
        }
        else{
            border.hide();
        }
    }
    
    //Show the card with or without border
    public void show(boolean withBorder){
        if (withBorder){
            border.show();
        }
        super.show();
    }
    
    //Return the border status
    public boolean isNotChosen(){
        return border.isHidden();
    }
    
    //Move the card
    public void moveTo(double x, double y){
        border.moveTo(x - 3, y - 3);
        super.moveTo(x, y);
    }
}
