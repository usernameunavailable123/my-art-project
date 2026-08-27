package org.example;

import java.util.List;

/**
 * MyPicture.java
 * --------------
 * Write the code to draw your scene here. Most of your changes should go inside
 * the drawPicture method below, unless you're defining additional methods or
 * variables to help organize your code.
 *
 * If you want to enhance the functionality of the drawing library itself (e.g. add
 * a new shape function), put that in SimpleGraphics.java instead.
 */
public class MyPicture {


    public static void drawPicture(double width, double height) {
        SimpleGraphics.fillBackground("blue");
        SimpleGraphics.setOutlineColor("green");
        SimpleGraphics.drawStem(200, 225, 200, 350);
        SimpleGraphics.drawPetals(200, 200, 12, 60);
        SimpleGraphics.setFillColor("white");
        SimpleGraphics.fillPetals(200, 200, 12, 60);
        // Fill the background
        SimpleGraphics.setFillColor("#FFFF00");
        SimpleGraphics.fillCircle(200, 200, 25);
        //this code creates a circle for the pistil of the flower. 

        
        

      
    }

    public static void main(String[] args) {
        // Launch the window; only edit the starting canvas dimensions if you'd like to.
        SimpleGraphics.start(MyPicture::drawPicture, 600, 400);
    }
}
