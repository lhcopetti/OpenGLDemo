# OpenGLDemo
A Demo project with some simple use cases with OpenGL


### Installation and Execution

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


[eclipse-errors]: https://github.com/lhcopetti/OpenGLDemo/raw/develop/DOCs/EclipseErrors.png "Eclipse Erros"
[lombok-installation]: https://github.com/lhcopetti/OpenGLDemo/raw/develop/DOCs/LombokInstallation.png "Lombok Installation"
[ok-lombok-installation]: https://github.com/lhcopetti/OpenGLDemo/raw/develop/DOCs/InstallationLombokSuccessful.png "Successful Lombok Installation"