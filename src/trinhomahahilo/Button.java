package trinhomahahilo;

import objectdraw.*;
import java.awt.Color;

/**
 * @file Button
 * @author DucTrinh
 * @assignment Project 6: Omaha Hi/Lo
 * @date 03/20/2017
 * @description This class extends the FilledRoundedRect class and provides 
 * the implementation for a Button object which consists of two 
 * FilledRoundedRect objects and one Text object
 */

public class Button extends FilledRoundedRect{

    //Instance variable for a Button object
    private FilledRoundedRect fill;
    private Text message;
    
    //Constructor for a Button object
    public Button(double x, double y, double width, double height, double 
        arcWidth, double arcHeight, String text, double xText, double yText, 
        int fontSize, Color fillColor, DrawingCanvas canvas){
        super(x, y, width, height, arcWidth, arcHeight, canvas);
        fill = new FilledRoundedRect(x + 5, y + 5, width - 10, height - 10, 
            arcWidth - 5, arcHeight - 5, canvas);
        fill.setColor(fillColor);
        message = new Text(text, xText, yText, canvas);
        message.setFontSize(fontSize);
        message.setBold(true);
    }
    
    //Set color for the text message in a Button object
    public void setTextColor(java.awt.Color textColor){
        message.setColor(textColor);
    }
    
    //Move the message text for a Button object
    public void moveText(double x, double y){
        message.moveTo(x, y);
    }
    
    //Set the text for a Button object
    public void setText(String text){
        message.setText(text);
    }
    
    //Hide a Button object from view temporarily
    public void hide(){
        super.hide();
        fill.hide();
        message.hide();
    }
    
    //Show a Button object if it has been hidden
    public void show(){
        super.show();
        fill.show();
        message.show();
    }
}


