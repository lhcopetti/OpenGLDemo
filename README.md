# OpenGLDemo
A Demo project with some simple use cases of OpenGL

### GameScenes

This application is divided into scenes, each of which, has a particular objective: drawing a triangle, rotating an object and etc. The reason for this separation is because I wanted to have different and isolated openGL usages, so that I can reason and learn about them individually. Having clear and concise examples of simple scenes alongside more complex ones, leads me to more easily perceive the differences and evolution between them.

Below, you will find an explanation and a short gif showing what each scene is about. Although they were shot individually, all the scenes are part of the same application. Look at 'Keyboard (User Input)' section to see how to navigate between scenes.


##### TriangleScene

The most simple scene. Draws a triangle shape in the screen. The nice characteristic of this is that it uses the openGL API directly, without using any of the high level constructs that are present in this project. This gives a nice opportunity for the reader to compare the usage between raw API calls to encapsulated openGL objects.

![alt-text][triangle]

##### SquareScene

Displays a static Square shaped object. Also uses raw openGL API calls.

![alt-text][square]

##### PentagonScene

Displays a rotating Pentagon shaped object.

![alt-text][pentagon]

##### FloorScene

Draws a floor of interconnected triangles. This is the first scene to use the concept of Mesh.

![alt-text][floor]

##### HeightMapScene

Builds on top of the concept present on the previous scene by including variable height to the triangle vertices. The map for this scene is dynamic and is loaded from black and white images stored on "src/main/resources/gray/". The rgb value for each pixel is then used to calculate its appropiate height in the scene. It is also important to mention that the mesh itself is scaled down to fit nicely in the screen, so don't worry about the size!

![alt-text][heightmap]

##### SinScene

This is also a height map scene that for each draw cycle, sends the shader the current time. The time value is then used to compute the height of the given pixel using the sin function.

![alt-text][sin]

##### SphereScene

This is also a mesh of triangles, however, the challenge was to connect all the edges, forming a sphere. It uses polar coordinates to ease the calculation and positioning of the triangles.

![alt-text][sphere]

##### CubeScene

Draws a 3D Cube with incidence of a light source. The most interesting part of this scene lies on its shaders: 'cube_shader.vert' and 'cube_shader.frag'.

![alt-text][cube]

### Keyboard (User Input)

Switch scenes: Left and Right arrow

Height Map Scene:

* `Key P`: Increase step size (smaller resolution)
* `Key M`: Decrease step size (bigger resolution)
* `Key , (Comma)`: Previous image
* `Key . (Period)`: Next image

Height Map, Sin and Sphere scenes:

* `Keys W, A, S and D`: Controls the angle of the object being displayed


### Installation

#### Lombok

This demo uses Lombok, a great project that simplifies a lot of boilerplate Java code. I really recommend you taking a look at it, it is awesome. 

So, when you open eclipse, after cloning this repository, you might find some errors like below:

![alt text][eclipse-errors]

> Pay close attention to this particular line

```
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Angle
```

The reason is that annotations like the one shown above are not being interpreted and thus eclipse can't find the constructor for the given class. This can be easily fixed with the installation of the lombok project in your IDE. Follow the steps below:

* Download the latest version of [Lombok](https://projectlombok.org/)
* Execute it with: `java -jar lombok.jar`
* Follow the instructions and point your installed IDE of choice (I use eclipse).

![alt text][lombok-installation]

![alt text][ok-lombok-installation]

* Restart and eclipse and rebuild the project. 
* The errors should go away!


[triangle]: https://github.com/lhcopetti/OpenGLDemo/raw/develop/DOCs/Triangle.png "Triangle"
[square]: https://github.com/lhcopetti/OpenGLDemo/raw/develop/DOCs/Square.png "Square"
[pentagon]: https://github.com/lhcopetti/OpenGLDemo/raw/develop/DOCs/Pentagon.gif "Pentagon"
[floor]: https://github.com/lhcopetti/OpenGLDemo/raw/develop/DOCs/FloorMesh.png "Floor"
[heightmap]: https://github.com/lhcopetti/OpenGLDemo/raw/develop/DOCs/HeightMap.gif "Height Map"
[sin]: https://github.com/lhcopetti/OpenGLDemo/raw/develop/DOCs/Sin.gif "Sin"
[sphere]: https://github.com/lhcopetti/OpenGLDemo/raw/develop/DOCs/Sphere.gif "Sphere"
[cube]: https://github.com/lhcopetti/OpenGLDemo/raw/develop/DOCs/Cube.gif "Cube"

[eclipse-errors]: https://github.com/lhcopetti/OpenGLDemo/raw/develop/DOCs/EclipseErrors.png "Eclipse Erros"
[lombok-installation]: https://github.com/lhcopetti/OpenGLDemo/raw/develop/DOCs/LombokInstallation.png "Lombok Installation"
[ok-lombok-installation]: https://github.com/lhcopetti/OpenGLDemo/raw/develop/DOCs/InstallationLombokSuccessful.png "Successful Lombok Installation"
