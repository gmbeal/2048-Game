Gabriel Beal
cs8bwakv
2/25/18

1. In order to create a director fooDir with a subdirectory barDir inside use
   the command: mkdir -p fooDir/barDir

2. The wildcard character * is used to represent any number of characters. For
   example when combined with the javac command you can write javac * .java(no
   space between the * and .java) and it will compile any file that ends with
   .java in that directory since the * represents any amount of characters
   coming before the .java.

3. In order to open all java source codes in a gvim window with their own tabs
   use the command: gvim -p * .java (again no space between * and .java, it
   highlights all my following text when I do it.)

4. Static methods allow the user to call the method without using a calling
   object. Non-static methods have to be called on an object i.e
   this.doSomething but static methods can be called just using the class name.
   An example of this is the Math class. Most of the methods are static and can
   for used for example: Math.Abs(-10) which will return the absolute value of
   -10.

5. Instead of creating one class called shapeDrawer and inside of that creating
   methods that can draw different shapes this can be improved by creating
   subclasses such as circle and square. Inside these subclasses you can create
   methods such as a draw method. Constructors can also set the size and color
   of the shapes. The benefit as this is not being locked into one shape/color
   instead you can create as many different shapes with any color and be able to
   still draw them and put them together in an array for example since they all
   extend from ShapeDrawer.


   Testing: Testing the GUI involved a lot of visual testing. Since the
   Board.java code was already completed and working we just had to test the
   visual GUI compenents. In order to do this I tested the board by using
   different keystrokes and making sure that they perform as expected compared
   the the actual game. I also tried to match the color scheme visually as much
   as I could.
