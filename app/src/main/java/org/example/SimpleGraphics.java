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

/**
 * SimpleGraphics
 * ----------------------
 * A small drawing library built on top of JavaFX's Canvas. It plays the same role as
 * simple_graphics.py in the Python version of this project: functions for drawing basic
 * shapes, adjusting drawing state (fill color, outline color, line thickness), and a
 * couple of color helpers.
 *
 * Usage: write your scene in a separate class (e.g. MyPicture.java) inside a method with
 * the signature drawPicture(double width, double height), then start the window with:
 *
 *     SimpleGraphics.start(MyPicture::drawPicture, 600, 400);
 *
 * If you want to ADD new drawing functions (e.g. fillStar, drawHexagon), add them to this
 * file. Everyday scene-drawing code belongs in MyPicture.java instead.
 */
public class SimpleGraphics extends Application {

    // ----- internal state (students shouldn't need to touch this section) -----
    private static GraphicsContext gc;
    private static PictureDrawer pictureDrawer;
    private static double canvasWidth = 600;
    private static double canvasHeight = 400;

    /** A function that draws a picture, given the canvas width and height. */
    @FunctionalInterface
    public interface PictureDrawer {
        void draw(double width, double height);
    }

    /**
     * Launches the drawing window and calls your drawPicture function once the canvas is ready.
     * Example: SimpleGraphics.start(MyPicture::drawPicture, 600, 400);
     */
    public static void start(PictureDrawer drawer, double width, double height) {
        pictureDrawer = drawer;
        canvasWidth = width;
        canvasHeight = height;
        launch(); // hands control to JavaFX, which will call start(Stage) below
    }

    /** Called automatically by JavaFX after start(...) above calls launch(). */
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

    // ----- functions that adjust drawing state -----
    // Call these before using the drawing functions below.

    /** Sets the inside color for shapes drawn after this point. */
    public static void setFillColor(String colorName) {
        gc.setFill(toColor(colorName));
    }

    /** Sets the border color for shapes drawn after this point. */
    public static void setOutlineColor(String colorName) {
        gc.setStroke(toColor(colorName));
    }

    /** Sets the thickness of lines and shape borders. */
    public static void setLineThickness(double thickness) {
        gc.setLineWidth(thickness);
    }

    // ----- drawing functions -----
    // Call these with appropriate arguments to add shapes to the canvas.

    /** Fills the entire canvas with one solid color. */
    public static void fillBackground(String colorName) {
        gc.setFill(toColor(colorName));
        gc.fillRect(0, 0, canvasWidth, canvasHeight);
    }

    /** Draws a line connecting point (x1, y1) to point (x2, y2). */
    public static void drawLine(double x1, double y1, double x2, double y2) {
        gc.strokeLine(x1, y1, x2, y2);
    }

    /** Draws a solid circle given its center point and radius. */
    public static void fillCircle(double centerX, double centerY, double radius) {
        gc.fillOval(centerX - radius, centerY - radius, radius * 2, radius * 2);
    }

    /** Draws an empty circle outline given its center point and radius. */
    public static void drawCircle(double centerX, double centerY, double radius) {
        gc.strokeOval(centerX - radius, centerY - radius, radius * 2, radius * 2);
    }

    /** Draws a solid (filled) triangle given its three corner points. */
    public static void fillTriangle(double x1, double y1, double x2, double y2, double x3, double y3) {
        gc.fillPolygon(new double[]{x1, x2, x3}, new double[]{y1, y2, y3}, 3);
    }

    /** Draws an outlined (unfilled) triangle given its three corner points. */
    public static void drawTriangle(double x1, double y1, double x2, double y2, double x3, double y3) {
        gc.strokePolygon(new double[]{x1, x2, x3}, new double[]{y1, y2, y3}, 3);
    }

    /** Draws a filled rectangle given its top-left corner, width, and height. */
    public static void fillRectangle(double x, double y, double width, double height) {
        gc.fillRect(x, y, width, height);
    }

    /** Draws an outlined rectangle given its top-left corner, width, and height. */
    public static void drawRectangle(double x, double y, double width, double height) {
        gc.strokeRect(x, y, width, height);
    }

    /**
     * Draws a curve that connects a list of points, in order. Points are given as a
     * list of double[]{x, y} pairs, e.g.:
     *   List.of(new double[]{100, 150}, new double[]{300, 200}, new double[]{200, 350})
     * (This draws straight segments between points; using true bezier curves is a
     * good extension if you want smoother bends -- see GraphicsContext.bezierCurveTo.)
     */
    public static void drawZigZagCurve(List<double[]> points) {
        if (points.size() < 2) return;
        gc.beginPath();
        gc.moveTo(points.get(0)[0], points.get(0)[1]);
        for (int i = 1; i < points.size(); i++) {
            double[] p = points.get(i);
            gc.lineTo(p[0], p[1]);
        }
        gc.stroke();
    }

    /**
     * Draws a smooth curve that bends through a list of points, in order (the curve
     * passes exactly through every point given, not just near them). Points are given
     * as a list of double[]{x, y} pairs, e.g.:
     *   List.of(new double[]{100, 150}, new double[]{300, 200}, new double[]{200, 350})
     */
    public static void drawSmoothCurve(List<double[]> points) {
        int n = points.size();
        if (n < 2) return;
        if (n == 2) {
            gc.strokeLine(points.get(0)[0], points.get(0)[1], points.get(1)[0], points.get(1)[1]);
            return;
        }
 
        gc.beginPath();
        gc.moveTo(points.get(0)[0], points.get(0)[1]);
 
        // Catmull-Rom spline through the points, drawn as a sequence of cubic bezier
        // segments (one per gap between consecutive points). At the ends, the first/last
        // point is reused as its own "extra" neighbor so the curve doesn't overshoot.
        for (int i = 0; i < n - 1; i++) {
            double[] p0 = points.get(Math.max(i - 1, 0));
            double[] p1 = points.get(i);
            double[] p2 = points.get(i + 1);
            double[] p3 = points.get(Math.min(i + 2, n - 1));
 
            double cp1x = p1[0] + (p2[0] - p0[0]) / 6.0;
            double cp1y = p1[1] + (p2[1] - p0[1]) / 6.0;
            double cp2x = p2[0] - (p3[0] - p1[0]) / 6.0;
            double cp2y = p2[1] - (p3[1] - p1[1]) / 6.0;
 
            gc.bezierCurveTo(cp1x, cp1y, cp2x, cp2y, p2[0], p2[1]);
        }
 
        gc.stroke();
    }

    // ----- color helpers -----

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

    /** Converts a color name (e.g. "red") or hex code (e.g. "#c7c1c1") into a JavaFX Color. */
    private static Color toColor(String colorName) {
        if (colorName.startsWith("#")) {
            return Color.web(colorName);
        }
        Color c = NAMED_COLORS.get(colorName.toLowerCase());
        if (c == null) {
            throw new IllegalArgumentException(
                "Unknown color name: \"" + colorName
                + "\". Try a hex code like \"#c7c1c1\" instead, or add it to NAMED_COLORS.");
        }
        return c;
    }
}
