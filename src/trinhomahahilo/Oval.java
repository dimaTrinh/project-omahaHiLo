package trinhomahahilo;

import objectdraw.*;
import java.awt.Color;

/**
 * @file Oval
 * @author DucTrinh
 * @assignment Project 6: Omaha Hi/Lo
 * @date 03/21/2017
 * @description This class extends the FilledOval class and provides 
 * implementation for an Oval object which consists of two FilledOval objects
 */

public class Oval extends FilledOval{
    
    //Instance variable for an Oval object
    private FilledOval fill;
    private Text message;
    
    //Constructor for an Oval object
    public Oval(double x, double y, double width, double height, 
        String text, double xText, double yText, Color fillColor, 
        DrawingCanvas canvas){
        super(x, y, width, height, canvas);
        fill = new FilledOval(x + 10, y + 10, width - 20, height - 20, 
            canvas);
        fill.setColor(fillColor);
        message = new Text(text, xText, yText, canvas);
        message.setFontSize(80);
        message.setBold(true);
    }
    
    //Set the text for an Oval object
    public void setText(String text){
        message.setText(text);
    }
    
    //Move the message text for an Oval object
    public void moveText(double x, double y){
        message.moveTo(x, y);
    }
    
    //Hide an Oval object from view temporarily
    public void hide(){
        super.hide();
        fill.hide();
        message.hide();
    }
    
    //Show an Oval object if it has been hidden
    public void show(){
        super.show();
        fill.show();
        message.show();
    }
}
