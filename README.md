# Steps to take before running the application for the first time

## Download the JavaFX SDK

Download the JavaFX SDK [here](https://gluonhq.com/products/javafx/). It does not matter where you place it in your file system.

## Setting up the .vscode folder

You will now have to create a folder named `.vscode` in your directory (add this through VS Code). Then, create two files: `settings.json` and `launch.json`. Put this in `settings.json`:

```json
{
    "java.project.sourcePaths": ["src"],
    "java.project.outputPath": "bin",
    "java.project.referencedLibraries": {
        "include": [
            //javafx directory
        ],
    },
}
```

In `//javafx directory`, put the path from root to your `javafx-sdk-19.0.2.1/lib` folder. Be sure to add `*.jar` at the end of it.

Put this in `launch.json`:

```json
{
    // Use IntelliSense to learn about possible attributes.
    // Hover to view descriptions of existing attributes.
    // For more information, visit: https://go.microsoft.com/fwlink/?linkid=830387
    "version": "0.2.0",
    "configurations": [
    ]
}
```

Try to click "Run" from `Main.java`. It will automatically put a configuration in `launch.json`. Then, so that the runtime can work correctly, add into the configuration:

```json
"vmArgs": "--module-path <javafx directory> --add-modules javafx.controls,javafx.fxml",
```

where `<javafx directory>` is the path to your `javafx-sdk-19.0.2.1/lib` folder from **the SDP directory**.

## Examples of the JSON files

settings.json:
```json
{
    "java.project.sourcePaths": ["src"],
    "java.project.outputPath": "bin",
    "java.project.referencedLibraries": {
        "include": [
            "/Users/ryanbrown/javafx-sdk-19.0.2.1/lib/*.jar"
        ],
    },
}
```

launch.json:
```json
{
    // Use IntelliSense to learn about possible attributes.
    // Hover to view descriptions of existing attributes.
    // For more information, visit: https://go.microsoft.com/fwlink/?linkid=830387
    "version": "0.2.0",
    "configurations": [
      {
            "type": "java",
            "name": "Launch Main",
            "request": "launch",
            "mainClass": "circuitryapp.Main",
            "vmArgs": "--module-path ../javafx-sdk-19.0.2.1/lib --add-modules javafx.controls,javafx.fxml",
            "projectName": "SDP_3a1b811c"
      }
      ]
}
```

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
