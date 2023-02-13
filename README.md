# Steps to take if the application does not run after first pull

## Download the JavaFX SDK

Download the JavaFX SDK [here](https://gluonhq.com/products/javafx/). Move the `javafx-sdk-19.0.2.1` folder as needed so that it is in the same directory as the `SDP` folder (not inside of it). Let the group know so that the JVM args can be changed in `launch.json`.

## Dependencies

You may have to add dependencies manually. In the `Java Projects` Explorer, go to `Referenced Libraries` and click `+`. Add all of the `.jar` files that are in the `javafx-sdk-19.0.2.1/lib` folder.

# Getting Started

Welcome to the VS Code Java world. Here is a guideline to help you get started to write Java code in Visual Studio Code.

## Folder Structure

The workspace contains two folders by default, where:

- `src`: the folder to maintain sources
- `lib`: the folder to maintain dependencies

Meanwhile, the compiled output files will be generated in the `bin` folder by default.

> If you want to customize the folder structure, open `.vscode/settings.json` and update the related settings there.

## Dependency Management

The `JAVA PROJECTS` view allows you to manage your dependencies. More details can be found [here](https://github.com/microsoft/vscode-java-dependency#manage-dependencies).