# OneHelper

## Overview

OneHelper is a lightweight, JSON-driven CLI tool that automates local project setup by applying predefined workspace modifications.

Instead of manually editing files, configuring environments, setting up IntelliJ run configurations, and applying patches, OneHelper enables a declarative approach—define everything once and execute it in seconds.

---

## Why OneHelper?

Local setup often involves:
- Repetitive manual changes
- Environment-specific tweaks
- IDE configuration setup
- Risk of human error

OneHelper eliminates this by:
- Centralizing setup logic in JSON
- Automating execution
- Ensuring consistency across environments

---

## Core Capabilities

### 1. Line-Based Modifications
- Modify specific lines using:
  - `COMMENT`
  - `UPDATE`
- Supports occurrence-based targeting
- Case-sensitive matching

---

### 2. Whole File Replacement
- Replace entire files using templates from `resources/`

---

### 3. IntelliJ Run Configuration Setup
- Automatically injects run configurations into `workspace.xml`
- Uses template XMLs
- Integrates with IntelliJ's `RunManager`

---

### 4. Git Patch Application
- Apply `.patch` files directly to the project

---

## Key Features

### Declarative Setup
All changes are defined in JSON → predictable, repeatable execution.

### Dynamic Port Resolution
- Ports defined in `resources/ports.json`
- Automatically resolved and injected into configs

```xml
<port value={{APP_SERVER_PORT}}/>
````

---

### Resource Isolation

* All templates must exist inside `resources/`
* Only filenames are required in JSON

```json
"sourcePath": "config-template.xml"
```

---

### Enable / Disable Modifications

* `"enabled": false` → skips execution
* Default → `true` if not specified

---

## Project Structure

```
OneHelper/
├── app/
├── resources/
├── setup.bat
├── run.bat
```

---

## JSON Configuration Structure

```json
{
  "projects": {
    "project-name": {
      "setups": {
        "setup-name": [
          {
            "modificationType": "LINE",
            "filePath": "relative/path",
            "changes": [
              {
                "target": "text",
                "actions": [
                  {
                    "operation": "COMMENT",
                    "occurrences": [2]
                  }
                ]
              }
            ]
          }
        ]
      }
    }
  }
}
```

---

## Modification Types

### LINE

Rules:

* Case-sensitive matching
* `occurrences` optional (applies to all if omitted)
* `UPDATE` requires `value`
* Comment strategy required

---

### WHOLE

Copies full file from `resources/` to target path.

---

### INTELLIJ_IDE_CONFIG

Template format:

```xml
<configurations>
    <configuration>
        <!-- config -->
    </configuration>
</configurations>
```

Injected into:

```
workspace.xml → RunManager
```

---

### GIT_PATCH

Applies patch file from `resources/`

---

## Comment Strategy

Defined in:

```
resources/comment_strategies.json
```

Example:

```json
{
  "java": { "prefix": "//" },
  "html": { "prefix": "<!--", "suffix": "-->" }
}
```

---

## Port Resolution

Defined in:

```
resources/ports.json
```

Example:

```json
{
  "APP_SERVER_PORT": 8080
}
```

Usage:

```xml
<port value={{APP_SERVER_PORT}}/>
```

---

## Usage

### Interactive Mode

Run without arguments:

```
java -jar onehelper.jar
```

You will be prompted to:

* Select project
* Select setup
* Enter project path

---

### Direct Mode

Run with arguments:

```
run.bat projectName setupName projectPath
```

Example:

```
run.bat demo-project dev-setup C:\Projects\Demo
```

---

### Running Multiple Setups

```
call run.bat projectA setup1 C:\Projects\A
call run.bat projectB setup2 C:\Projects\B
```

---

## Sample Configurations

### Full Example

```json
{
  "projects": {
    "demo-project": {
      "setups": {
        "dev-setup": [
          {
            "modificationType": "LINE",
            "filePath": "src/main/java/App.java",
            "changes": [
              {
                "target": "System.out.println(\"Hello\");",
                "actions": [
                  {
                    "operation": "COMMENT"
                  }
                ]
              },
              {
                "target": "int port = 8080;",
                "actions": [
                  {
                    "operation": "UPDATE",
                    "value": "int port = 9090;"
                  }
                ]
              }
            ]
          },
          {
            "modificationType": "WHOLE",
            "filePath": "src/main/resources/application.yml",
            "sourcePath": "application-dev.yml"
          },
          {
            "modificationType": "INTELLIJ_IDE_CONFIG",
            "configurations": [
              {
                "sourcePath": "run-config.xml",
                "enabled": true
              }
            ]
          },
          {
            "modificationType": "GIT_PATCH",
            "sourcePath": "feature.patch"
          }
        ]
      }
    }
  }
}
```

---

## Architecture

### Layers

* Loading Layer
* Validation Layer
* Execution Layer

---

## Design Patterns

* Factory Pattern → creates modification handlers
* Strategy Pattern → validation logic
* Pipeline Pattern → port resolution

---

## Engineering Principles

* Declarative over manual
* Extensible over rigid
* Automated over repetitive
* Clean separation of concerns

---

## Summary

OneHelper transforms local setup into a fast, automated, and reliable process using structured configuration and extensible design.