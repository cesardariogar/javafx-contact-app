# javafx-contacts-app
A simple Contacts App using JavaFX and Java SE 11 (excersice from  the "Java Programming Masterclass" course by @timbuchalka)

----

## Installation:

Requirements:
- You need to have the JavaFx library installed.
- Add the following VM Arguments to your build configuration inside your IDE:<br />
  --module-path "[PATH-TO-YOUR-JAVAFX-SDK-VERSION]/lib" <br />
  --add-modules=javafx.controls,javafx.fxml,javafx.web  <br />
  --add-reads javafx.graphics=ALL-UNNAMED  <br />
  --add-opens javafx.controls/com.sun.javafx.charts=ALL-UNNAMED <br />
  --add-opens javafx.graphics/com.sun.javafx.iio=ALL-UNNAMED <br />
  --add-opens javafx.graphics/com.sun.javafx.iio.common=ALL-UNNAMED <br />
  --add-opens javafx.graphics/com.sun.javafx.css=ALL-UNNAMED <br />
  --add-opens javafx.base/com.sun.javafx.runtime=ALL-UNNAMED <br />
- Configure your project SDK to use Java 11.

----

## Contents
- [Libraries and Frameworks](#libraries-and-frameworks)
- [Course of the Project](#course-origin)
----



## Libraries and Frameworks
- [JavaFX](https://openjfx.io/) - JavaFX allows you to create Java applications with a modern, hardware-accelerated user interface that is highly portable.
- [JLFGR](http://www.java2s.com/Code/Jar/j/Downloadjlfgr10jar.htm) Jar of misc icons to use in a JavaFX app.



## Course of the Project
- [Java Programming Masterclass for Software Developers](https://www.udemy.com/course/java-the-complete-java-developer-course/) Learn industry "best practices" in Java software   development from a professional Java developer who has worked in the language for 18 years.
