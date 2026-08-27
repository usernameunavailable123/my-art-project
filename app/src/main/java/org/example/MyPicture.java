package org.example;

public class MyPicture {

    public static void drawPicture(double width, double height) {
        // Sky
        SimpleGraphics.fillBackground("blue");

        // Ground
        SimpleGraphics.setFillColor("green");
        SimpleGraphics.fillRectangle(0, 350, width, 50);

        // Flower stem
        SimpleGraphics.setOutlineColor("green");
        SimpleGraphics.setLineThickness(5);
        SimpleGraphics.drawStem(200, 225, 200, 350);

        // Leaves
        SimpleGraphics.setFillColor("green");

        // Left leaf
        SimpleGraphics.fillOval(150, 275, 50, 25);

        // Right leaf
        SimpleGraphics.fillOval(200, 290, 50, 25);

        // Flower petals
        SimpleGraphics.setOutlineColor("green");
        SimpleGraphics.drawPetals(200, 200, 12, 60);

        SimpleGraphics.setFillColor("white");
        SimpleGraphics.fillPetals(200, 200, 12, 60);

        // Flower center
        SimpleGraphics.setFillColor("#FFFF00");
        SimpleGraphics.fillCircle(200, 200, 25);
    }

    public static void main(String[] args) {
        SimpleGraphics.start(MyPicture::drawPicture, 600, 400);
    }
}