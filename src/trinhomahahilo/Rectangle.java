package trinhomahahilo;

import java.awt.Color;
import objectdraw.*;

/**
 * @file Rectangle
 * @author DucTrinh
 * @assignment Project 6: Omaha Hi/Lo
 * @date 03/20/2017
 * @description This class extends the FilledRect class and provides 
 * implementation for a Rectangle object which consists of two FilledRect 
 * objects
 */

public class Rectangle extends FilledRect{

    //Instance variable for a Rectangle object
    private FilledRect fill;
    private Text message;
    
    //Constructor for a Rectangle
    public Rectangle(double x, double y, double width, double height, 
        String text, double xText, double yText, int fontSize, Color 
        fillColor, DrawingCanvas canvas){
        super(x, y, width, height, canvas);
        fill = new FilledRect(x + 5, y + 5, width - 10, height - 10, canvas);
        message = new Text(text, x, y, canvas);
        message.moveTo(xText, yText);
        fill.setColor(fillColor);
        message.setFontSize(fontSize);
        message.setBold(true);
    }
    
    //Set color for the text message in a Rectangle object
    public void setTextColor(java.awt.Color textColor){
        message.setColor(textColor);
    }
    
    //Move the message text for a Rectangle object
    public void moveText(double x, double y){
        message.moveTo(x, y);
    }
    
    //Set the text for a Rectangle object
    public void setText(String text){
        message.setText(text);
    }
    
    //Hide a Rectangle object from view temporarily
    public void hide(){
        super.hide();
        fill.hide();
        message.hide();
    }
    
    //Show a Rectangle object if it has been hidden
    public void show(){
        super.show();
        fill.show();
        message.show();
    }
}


