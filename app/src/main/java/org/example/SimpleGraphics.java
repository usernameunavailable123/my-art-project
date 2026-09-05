package org.example;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SimpleGraphics extends Application {

    private static GraphicsContext gc;
    private static PictureDrawer pictureDrawer;
    private static double canvasWidth = 600;
    private static double canvasHeight = 400;

    @FunctionalInterface
    public interface PictureDrawer {
        void draw(double width, double height);
    }

    public static void start(PictureDrawer drawer, double width, double height) {
        pictureDrawer = drawer;
        canvasWidth = width;
        canvasHeight = height;
        launch();
    }

    public static void drawPetals(double centerX, double centerY, int petalCount, double petalLength) {
        double angleStep = 360.0 / petalCount;

        for (int i = 0; i < petalCount; i++) {
            double angle = i * angleStep;

            gc.save();
            gc.translate(centerX, centerY);
            gc.rotate(angle);
            gc.strokeOval(0, -petalLength / 4, petalLength, petalLength / 2);
            gc.restore();
        }
    }

    public static void drawStem(double x1, double y1, double x2, double y2) {
        gc.setLineWidth(5);
        gc.strokeLine(x1, y1, x2, y2);
    }

    public static void fillPetals(double centerX, double centerY, int petalCount, double petalLength) {
        double angleStep = 360.0 / petalCount;

        for (int i = 0; i < petalCount; i++) {
            double angle = i * angleStep;

            gc.save();
            gc.translate(centerX, centerY);
            gc.rotate(angle);
            gc.fillOval(0, -petalLength / 4, petalLength, petalLength / 2);
            gc.restore();
        }
    }

    @Override
    public void start(Stage stage) {
        Canvas canvas = new Canvas(canvasWidth, canvasHeight);
        gc = canvas.getGraphicsContext2D();

        StackPane root = new StackPane(canvas);
        stage.setScene(new Scene(root, canvasWidth, canvasHeight));
        stage.setTitle("Simple Graphics Starter");
        stage.show();

        if (pictureDrawer != null) {
            pictureDrawer.draw(canvasWidth, canvasHeight);
        }
    }

    // ----- Drawing state -----

    public static void setFillColor(String colorName) {
        gc.setFill(toColor(colorName));
    }

    public static void setOutlineColor(String colorName) {
        gc.setStroke(toColor(colorName));
    }

    public static void setLineThickness(double thickness) {
        gc.setLineWidth(thickness);
    }

    // ----- Drawing functions -----

    public static void fillBackground(String colorName) {
        gc.setFill(toColor(colorName));
        gc.fillRect(0, 0, canvasWidth, canvasHeight);
    }

    public static void drawLine(double x1, double y1, double x2, double y2) {
        gc.strokeLine(x1, y1, x2, y2);
    }

    public static void fillCircle(double centerX, double centerY, double radius) {
        gc.fillOval(
            centerX - radius,
            centerY - radius,
            radius * 2,
            radius * 2
        );
    }

    public static void drawCircle(double centerX, double centerY, double radius) {
        gc.strokeOval(
            centerX - radius,
            centerY - radius,
            radius * 2,
            radius * 2
        );
    }

    // THIS IS NOW IN THE CORRECT PLACE
    public static void fillOval(double x, double y, double width, double height) {
        gc.fillOval(x, y, width, height);
    }

    public static void fillTriangle(
            double x1, double y1,
            double x2, double y2,
            double x3, double y3) {

        gc.fillPolygon(
            new double[]{x1, x2, x3},
            new double[]{y1, y2, y3},
            3
        );
    }

    public static void drawTriangle(
            double x1, double y1,
            double x2, double y2,
            double x3, double y3) {

        gc.strokePolygon(
            new double[]{x1, x2, x3},
            new double[]{y1, y2, y3},
            3
        );
    }

    public static void fillRectangle(
            double x, double y,
            double width, double height) {

        gc.fillRect(x, y, width, height);
    }

    public static void drawRectangle(
            double x, double y,
            double width, double height) {

        gc.strokeRect(x, y, width, height);
    }

    public static void drawZigZagCurve(List<double[]> points) {

        if (points.size() < 2) {
            return;
        }

        gc.beginPath();

        gc.moveTo(
            points.get(0)[0],
            points.get(0)[1]
        );

        for (int i = 1; i < points.size(); i++) {

            double[] p = points.get(i);

            gc.lineTo(
                p[0],
                p[1]
            );
        }

        gc.stroke();
    }

    public static void drawSmoothCurve(List<double[]> points) {

        int n = points.size();

        if (n < 2) {
            return;
        }

        if (n == 2) {

            gc.strokeLine(
                points.get(0)[0],
                points.get(0)[1],
                points.get(1)[0],
                points.get(1)[1]
            );

            return;
        }

        gc.beginPath();

        gc.moveTo(
            points.get(0)[0],
            points.get(0)[1]
        );

        for (int i = 0; i < n - 1; i++) {

            double[] p0 = points.get(Math.max(i - 1, 0));
            double[] p1 = points.get(i);
            double[] p2 = points.get(i + 1);
            double[] p3 = points.get(Math.min(i + 2, n - 1));

            double cp1x =
                p1[0] + (p2[0] - p0[0]) / 6.0;

            double cp1y =
                p1[1] + (p2[1] - p0[1]) / 6.0;

            double cp2x =
                p2[0] - (p3[0] - p1[0]) / 6.0;

            double cp2y =
                p2[1] - (p3[1] - p1[1]) / 6.0;

            gc.bezierCurveTo(
                cp1x,
                cp1y,
                cp2x,
                cp2y,
                p2[0],
                p2[1]
            );
        }

        gc.stroke();
    }

    // ----- Colors -----

    private static final Map<String, Color> NAMED_COLORS = new HashMap<>();

    static {
        NAMED_COLORS.put("red", Color.RED);
        NAMED_COLORS.put("green", Color.GREEN);
        NAMED_COLORS.put("blue", Color.BLUE);
        NAMED_COLORS.put("cyan", Color.CYAN);
        NAMED_COLORS.put("magenta", Color.MAGENTA);
        NAMED_COLORS.put("yellow", Color.YELLOW);
        NAMED_COLORS.put("white", Color.WHITE);
        NAMED_COLORS.put("black", Color.BLACK);
        NAMED_COLORS.put("gray", Color.GRAY);
        NAMED_COLORS.put("orange", Color.ORANGE);
        NAMED_COLORS.put("purple", Color.PURPLE);
        NAMED_COLORS.put("pink", Color.PINK);
        NAMED_COLORS.put("brown", Color.BROWN);
    }

    private static Color toColor(String colorName) {

        if (colorName.startsWith("#")) {
            return Color.web(colorName);
        }

        Color c = NAMED_COLORS.get(
            colorName.toLowerCase()
        );

        if (c == null) {
            throw new IllegalArgumentException(
                "Unknown color name: \"" + colorName +
                "\". Try a hex code like \"#c7c1c1\" instead, " +
                "or add it to NAMED_COLORS."
            );
        }

        return c;
    }
}