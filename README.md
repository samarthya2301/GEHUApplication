# GRAPHIC ERA ANDROID

Graphic Era Android is an android application for Graphic Era Hill University students. This application provides the android version of the already existing web ERP of Graphic Era Hill University. The application will provide functionalities such as logging in, viewing students' dashboards, viewing exam results and fees, downloading semester-wise fee receipts, and results and changing passwords.

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

Use Git Bash after installing [Git](https://git-scm.com/downloads) to clone the repository to your local machine. Replace **YOURPATH** with the path that you want to keep the project on, in your local machine.

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

## Important File Creation
Two files are needed to be created before the project can run.

Server file for backend - Create a new file in the 'backend' folder like this:-
```bash
cd YOURPATH/GEHUApplication/backend/
touch server.js
```

Add the following code snippet to 'server.js' file:-
```javascript
const SERVER_IP = "YOUR_IP_ADDRESS"
module.exports = SERVER_IP
```

Server file for android frontend - Create a new file in 'java' folder like this:-
```bash
cd D:/programming_work/AndroidStudioProjects/GEHUApplication/app/src/main/java/com/samarthya/gehuapplication/
touch Server.java
```

Add the following code snippet to the 'Server.java' file. Make sure to use the same IP_ADDRESS as in 'server.js' and same PORT_NUMBER as in 'constants.js' in the 'backend' folder:-

```java
package com.samarthya.gehuapplication;

public class Server {
	public static final String SOCKET_ADDRESS = "IP_ADDRESS:3000";
}

```

## Android Gradle Dependencies
Open the build.gradle(.app) file in Android Studio. This file can also be opened by opening 'GEHUApplication/app/build.gradle'. The dependencies for the project can be added here. Replace the dependencies block with this code snippet to get the updated functionalities:-
```gradle
dependencies {

	implementation 'androidx.appcompat:appcompat:1.4.2'
	implementation 'com.google.android.material:material:1.6.1'
	implementation 'androidx.constraintlayout:constraintlayout:2.1.4'
	implementation 'androidx.lifecycle:lifecycle-livedata-ktx:2.4.1'
	implementation 'androidx.lifecycle:lifecycle-viewmodel-ktx:2.4.1'
	implementation 'androidx.navigation:navigation-fragment:2.4.2'
	implementation 'androidx.navigation:navigation-ui:2.4.2'
	implementation 'androidx.legacy:legacy-support-v4:1.0.0'
	implementation 'com.github.bumptech.glide:glide:4.13.2'
	annotationProcessor 'com.github.bumptech.glide:compiler:4.13.2'
	testImplementation 'junit:junit:4.13.2'
	androidTestImplementation 'androidx.test.ext:junit:1.1.3'
	androidTestImplementation 'androidx.test.espresso:espresso-core:3.4.0'

}
```

## Usage

```python
import foobar

# returns 'words'
foobar.pluralize('word')

# returns 'geese'
foobar.pluralize('goose')

# returns 'phenomenon'
foobar.singularize('phenomena')
```

## Contributing
For ideas, changes and optimization, feel free to drop a mail [here](mailto:samarthya2301@gmail.com?subject=Changes%20Regarding%20Graphic%20Era%20Android).

## License
[MIT](https://choosealicense.com/licenses/mit/)