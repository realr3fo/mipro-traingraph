# Mipro Traingraph

Mipro Traingraph is a Java Spring Boot application that provides a graphical representation of trains running between Helsinki and Leppävaara stations. It fetches live data from the rata.digitraffic.fi API to display train schedules and travel times on a graph.

## Features

- Displays the train graph with current train schedules and travel times between Helsinki and Leppävaara stations.
- Allows switching between distance and time axes on the graph.
- Highlights train lines when hovered over.
- Fetches live data from the rata.digitraffic.fi API.

## Prerequisites

Before running the application, make sure you have the following prerequisites installed:

- Java Development Kit (JDK) 11 or higher
- Apache Maven

## Installation

1. Clone the repository:

   ```shell
   git clone <repository-url>
    ```

2. Navigate to the project directory:
   ```shell
   cd mipro-traingraph
   ```
3. Build the application usign Maven:
   ```shell
   mvn clean install
   ```

## Usage
1. Run the application using Maven:
   ```shell
   mvn spring-boot:run
   ```
2. Access the application in your web browser
   ```shell
   http://localhost:8080
   ```


## Technologies Used
- Java
- Spring Boot
- Thymeleaf
- Maven
- HTML/CSS/JavaScript


## Snippet
// TODO: add snippet