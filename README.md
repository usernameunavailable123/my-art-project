# SimpleGraphics (Java / JavaFX)

Starter code for creating a static image with JavaFX's Canvas — the Java version of the tessellation
starter used in the Intro to GitHub unit.

## Learning Goals

This project is the vehicle for the Intro to GitHub unit. Working through it, you should:

- Understand how git and GitHub work together, and build a **fork → commit → push/pull → pull request**
  workflow you'll reuse for the rest of the course.
- Get comfortable with core Java syntax, especially **defining and calling functions**.
- Practice using someone else's function library (an "API") to build something of your own — and see how
  breaking a program into small, well-named functions makes it easier to read and reason about.
- *(Extension)* Practice writing a new function of your own and adding it to a shared library for others
  to use.

## What You'll Do

1. **Fork** the starter repository — this makes your own linked copy on GitHub.
2. **Clone** your fork to your computer (or open it in the provided project once your teacher shares the
   Gradle setup).
3. **Read** through `SimpleGraphics.java` to see what drawing functions are already available to
   you.
4. **Write code** in `MyPicture.java`'s `drawPicture(...)` method to draw your assigned piece of the
   tessellation, calling functions from the library.
5. **Run** `MyPicture.java` and check your output against the target image.
6. **Commit** your changes with a clear, descriptive message — what you changed, and why.
7. **Push** your commit and **open a pull request** back into the class's shared repository.
8. **Sync** your fork after your pull request (and everyone else's) is merged, so your copy includes the
   whole class's contributions.
9. *(Round 2)* Repeat a similar task, but drive the same git steps from the **command line** instead of
   the GitHub website.

## What's In This Repository

Two Java files:

### `SimpleGraphics.java`

This is the drawing library — an API of functions for drawing basic shapes, adjusting the state of the
canvas (like fill color or line thickness), and a couple of color helpers. You generally shouldn't need to
edit this file for the tessellation task; you call its functions from `MyPicture.java` instead.

**Functions that adjust drawing state** — call these *before* the drawing functions below, and they apply
to everything you draw afterward, until you change them again:

```java
SimpleGraphics.setFillColor("green");     // inside color for shapes
SimpleGraphics.setOutlineColor("black");  // border color for shapes
SimpleGraphics.setLineThickness(2);       // thickness of lines/borders
```

**Drawing functions:**

```java
SimpleGraphics.fillBackground("white");
SimpleGraphics.drawLine(x1, y1, x2, y2);
SimpleGraphics.fillCircle(centerX, centerY, radius);
SimpleGraphics.drawCircle(centerX, centerY, radius);
SimpleGraphics.fillTriangle(x1, y1, x2, y2, x3, y3);
SimpleGraphics.drawTriangle(x1, y1, x2, y2, x3, y3);
SimpleGraphics.fillRectangle(x, y, width, height);
SimpleGraphics.drawRectangle(x, y, width, height);
SimpleGraphics.drawCurve(pointsList);
```

Colors can be given as names (`"red"`, `"cyan"`, `"magenta"`, ...) or hex codes (`"#c7c1c1"`).

If you want to **enhance the library** — for example, adding a `fillStar(...)` or `drawHexagon(...)`
function — that new function belongs in `SimpleGraphics.java`, not `MyPicture.java`.

### `MyPicture.java`

This is where you write the code to draw your scene. Almost all of your changes should go inside the
`drawPicture(width, height)` method, unless you're defining extra helper methods or variables to keep your
own code organized. It also contains `main`, which is what you'll actually run.

## How to Use the Code

Keep both files in the same project (same package). You don't need an `import` statement between them —
just call `SimpleGraphics.functionName(...)` directly from inside `MyPicture.java`.

```java
public class MyPicture {

    public static void drawPicture(double width, double height) {
        SimpleGraphics.fillBackground("white");

        SimpleGraphics.setFillColor("green");
        SimpleGraphics.fillTriangle(100, 100, 150, 20, 200, 100);
    }

    public static void main(String[] args) {
        SimpleGraphics.start(MyPicture::drawPicture, 600, 400);
    }
}
```

To run it: open the project in your IDE and run `MyPicture.java` (it's the file with `main`). A window
should open showing your drawing. If you change your code, re-run to see the update.

## Using the Gradle Wrapper

This project uses the **Gradle wrapper**, so you don't need to install Gradle or JavaFX yourself — the
wrapper downloads the right versions automatically the first time you use it.

Open the project folder in VS Code, then open a terminal: **Terminal → New Terminal** (or `` Ctrl+` `` /
`` Cmd+` ``). Make sure you're in the project's root folder — the one containing the `gradlew` file — then
run one of the following:

```bash
./gradlew build
```
Compiles your code and checks that everything's error-free. Good first step if you're not sure your code
even compiles.

```bash
./gradlew clean
```
Deletes previous build output. Use this if things are behaving strangely and you want a fresh build —
usually followed by `./gradlew build` or `./gradlew run`.

```bash
./gradlew run
```
Compiles (if needed) and runs the program — this is the one you'll use most. It should open the JavaFX
window with your drawing.

> **Windows note:** if VS Code's integrated terminal is PowerShell or Command Prompt rather than a
> Unix-style shell, use `.\gradlew.bat` (PowerShell) or `gradlew.bat` (Command Prompt) instead of
> `./gradlew` — same task names (`build`, `clean`, `run`) either way.

[Add a sample output image here once you have one to show.]

## Project Goals

Using this starter code, you'll:

- Fork this repository and clone your fork.
- Write code in `MyPicture.java` to draw your assigned piece of the tessellation, using functions from
  `SimpleGraphics.java`.
- Commit and push your changes with clear messages, then open a pull request to contribute your piece back
  to the shared image.