# SureHouse DataLogger
- [About](#about)
- [Installation](#installation)
- [Configuration](#configuration)

# About
This application handles the back-end data collection of all of the sensor information recorded throughout the house. Instead of running continously, which is what we initially planned to do, it should be ran preiodically. Therefore, the concept of the application is to quickly query the sensors it needs to record information for, query the information related to the sensors, and then insert it into the database.

# Installation
Installing the application is very simple through git. You can simply frun the following command.

```
git clone https://github.com/garrett9/surehouse_data_collection
```

The downloaded folder will contain the **DataLogger.jar** file in the root directory, which can be ran like so.

```
java -jar DataLogger.jar
```

The application requires a configuration file of database information needed to connect to the SureHouse database. By default, the application looks for the configuration file **config.properties** in the same directory as the application, but you can provide the path to your own configuration file through the following command.

```
java -jar DataLogger.jar /path/to/my/configuration.properties
```

While you are allowed to specify your own configuration file, it must be in the format of a Java **.properties** file, and have the fields mentioned in the next section.

# Configuration
In the root folder of the application, there is a **config.properties.example** file that can be used as a base for creating your own configuration file. You can also see this example below.

```
#This is an example configuration file on how to setup the database info for the application.
DB_NAME=database_name
DB_HOST=localhost
DB_USER=user
DB_PASSWORD=password
```

As you can see, the properties file requires the name of the database to connect to, the IP address, or host, of where the database is located, the username to connect with, as well as the password.
