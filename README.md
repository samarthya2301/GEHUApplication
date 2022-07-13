# GRAPHIC ERA ANDROID

Graphic Era Android is an android application for Graphic Era Hill University students. This application provides the android version of the already existing web ERP of Graphic Era Hill University. The application will provide functionalities such as logging in, viewing students' dashboards, viewing exam results and fees, downloading semester-wise fee receipts, and results and changing passwords.


## Project Demonstration

* Presentation Link for [Application](#)
* Working Video Link for [Student 1](#)
* Working Video Link for [Student 2](#)


## Overview

* Frontend: Java and XML
* Backend: Node.js and Express
* Database: MySQL (XAMPP)


## Required Downloads

1. [Android Studio](https://developer.android.com/studio)
2. [Node.js](https://nodejs.org/en/download/)
3. [XAMPP](https://www.apachefriends.org/download.html)
4. [Git](https://git-scm.com/downloads)


## Installation

Use Git Bash ![GitBash]() after installing [Git](https://git-scm.com/downloads) to clone the repository to your local machine. Replace **YOURPATH** with the path that you want to keep the project on, in your local machine.

```bash
cd YOURPATH/
git clone https://github.com/samarthya2301/GEHUApplication
```

Use the Node Package Manager(npm) from terminal to install the project's backend dependencies. [npm](https://www.npmjs.com/) will automatically detect the 'package.json' file. Replace **YOURPATH** with the path where you installed the project.

Packages to be installed are:-
1. [Express](https://www.npmjs.com/package/express) - For server management.
2. [MD5](https://www.npmjs.com/package/md5) - For password hashing.
3. [MySQL](https://www.npmjs.com/package/mysql) - For MySQL database connection and access.

```bash
cd YOURPATH/GEHUApplication/backend/
npm install
```


## Database Creation

* Create a database for the application in MySQL, by using XAMPP.
* Start 'Apache' and 'MySQL' from the XAMPP Control Panel.
* Click on 'Admin' button corresponding to MySQL tag, or directly go to [phpMyAdmin](http://localhost/phpmyadmin/).
* Create a new database by clicking on 'New' and name the database as **students_gehu**.
* Click on the newly created 'students_gehu' database.
* Now click on the 'Import' section on the top and click on the 'Choose File' button.
* When selecting the file, select the file **[students_gehu.sql](https://github.com/samarthya2301/GEHUApplication/blob/main/students_gehu.sql)** in GEHUApplication folder.
* Now the database has been created.


## Important File Creation

Two files are needed to be created before the project can run.

Server file for backend - Create a new file in the 'backend' folder like this:-
```bash
cd YOURPATH/GEHUApplication/backend/
touch server.js
```

Add the following code snippet to 'server.js' file. Replace **IP_ADDRESS** with your local machine's ip address:-
```javascript
const SERVER_IP = "IP_ADDRESS"
module.exports = SERVER_IP
```

Server file for android frontend - Create a new file in 'java' folder like this:-
```bash
cd D:/programming_work/AndroidStudioProjects/GEHUApplication/app/src/main/java/com/samarthya/gehuapplication/
touch Server.java
```

Add the following code snippet to the 'Server.java' file. Replace **IP_ADDRESS** with your local machine's ip address. Make sure to use the same **IP_ADDRESS** as in 'server.js' and same **PORT_NUMBER** as in 'constants.js' in the 'backend' folder. Here, 3000 is the port number that is being used. If you want to change, make sure you change it in 'backend/server.js' as well:-
```java
package com.samarthya.gehuapplication;

public class Server {
	public static final String SOCKET_ADDRESS = "IP_ADDRESS:3000";
}

```


## Usage

The application can run on its own. But to interact with database, the following are needed:-
1. Apache and MySQL should be started before the application runs.
2. The main backend file **[app.js](https://github.com/samarthya2301/GEHUApplication/blob/main/backend/app.js)** should be running before the application runs. This can be done by typing this into the terminal:-
```bash
cd backend/
node app.js
```
3. There is only one student in the database. More students can be added manually in XAMPP.
The login details of the student are as follows:-
	* **Student ID**: 10005
	* **Student Password**: xyz

## Contributing

For ideas, changes and optimization, feel free to drop a mail [here](mailto:samarthya2301@gmail.com?subject=Changes%20Regarding%20Graphic%20Era%20Android).


## License

[MIT License](https://github.com/samarthya2301/GEHUApplication/blob/main/LICENSE.md)