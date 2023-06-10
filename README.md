# EasyRenderer
EasyRenderer is a quick but highly inefficient solution for graphics.

### Advantages
- Easy to setup
- Easy to work with
- Easy to understand
- No external libraries required
- Has many fancy and useless functionalities

### Disadvantages
- Poor performance
- Single Threaded
- The autor (me) is braindead

## Built in example (EPILEPSY WARNING)

![](https://raw.githubusercontent.com/paulothar/EasyRenderer/master/resources/exampleRunning.png)

## Actual usage example (3D Engine (not included))

![](https://raw.githubusercontent.com/paulothar/EasyRenderer/master/resources/actualusage.png)

# Classes and their methods

## EasyDisplay
Threaded AWT Component used to render the image in a window component.

### start()
Starts the thread execution.

### stop()
Kills the thread execution.

### run()
Thread "run" implementation (SHOULD NOT BE CALLED).

### tick()
Ticks any user implemented functionality (Such as ingame time).

## EasyRender
Object that contains the bitmap to be rendered, and some drawing methods, for convenience.

### draw()
Fills the bitmap with a static rgb gradiant image.<br>
The gradiant flows from top to bottom.

### draw(float movement)
Fills the bitmap with a moving rgb gradiant image, based on a <strong>normalized</strong> movement value.<br>
The gradiant flows from top to bottom.<br><br>
Positive movement means the image will flow upwards.<br>
Negative movement means the image will flow downwards.

### draw(int color)
Fills the bitmap with a single color.

### draw(int[] pattern)
Fills the specified pixels on the bitmap with a static rgb gradiant image.<br>
The gradiant flows from top to bottom.

### draw(int[] pattern,float movement)
Fills the specified pixels on the bitmap with a moving rgb gradiant image, based on a <strong>normalized</strong> movement value.<br>
The gradiant flows from top to bottom.<br><br>
Positive movement means the image will flow upwards.<br>
Negative movement means the image will flow downwards.

### draw(int[] pattern,int color)
Fills the specified pixels on the bitmap with a single color.

### draw(EasyRender render,int x,int y)
Fills the specified area with the contents of another Render

## EasyScreen
Screen object, exactly the same of EasyRender, but with a render() method, to be implemented.

### render()
This method will be called each cycle of the graphics update.<br>
So this must be implemented containing everything that will be done to render the desired image.

## EasyColor
A collection of static methods for generate integer rgb colors.

### rgb(int r,int g,int b)
Translate 3 Integers RGB into a singe integer RGB.<br>
The 3 intake integers will be read with bitwise operations, to not overflow the 255 limit<br>
Output:0x00RRGGBB

### rgbyte(int color)
Takes in a single byte of a integer, and turns into a full integer RGB<br>
Byte Structure : 0bIIRRGGBB<br>
I = 2 bit Intensity<br>
R = 2 bit Red<br>
G = 2 bit Green<br>
B = 2 bit Blue<br>

### rgbytePallete(int pallete,int indexes)
It took too long to write this documentation, so i have no idea what i have done here.

### rgbScale(float normalizedScale)
Generates a color based on a normalized value.<br>
Flow: R->G->B

### cyclicRGBScale(float normalizedScale)
Generates a color based on a normalized value.<br>
Flow: R->G->B->R

### gradient(float scale,int color1,int color2)
Grabs the linear interpolation between two colors, based on a normalized value.

### multiGradient(float scale,int[] colors)
Grabs the linear interpolation between multiple colors, based on a normalized value.

## EasyDrafter
A collection of static methods for creating pixel location patterns.

### buildFillPattern(int maxWidth,int maxHeight)
Builds a simple filling pattern, that occupies every single pixel in the bitmap.

### buildLinePattern(int maxWidth,int maxHeight,int x1,int y1,int x2,int y2)
Builds a simple line pattern, from origin to destination.<br>
It does have out of bounds protection.

### buildUnsortedTrianglePattern(int maxWidth,int maxHeight,int x1, int y1, int x2, int y2, int x3, int y3)
Sorts the triangle, and then calls the buildTrianglePattern.<br>
Builds a filled triangle pattern out of a <strong>SORTED</strong> triangle.

### buildTrianglePattern(int maxWidth,int maxHeight,int x1, int y1, int x2, int y2, int x3, int y3)
Builds a filled triangle pattern out of a <strong>SORTED</strong> triangle.

### buildInfiniteTrianglePattern(int maxWidth,int maxHeight,float XperY1,float XperY2,int size,int x, int y)
Builds a infinite scalable triangle, but i dont remember what each parameter means...
