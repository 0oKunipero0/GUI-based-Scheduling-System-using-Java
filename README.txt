============================================

APPLICATION TITLE: Appointment Scheduling Application
PURPOSE: It is used an a managing application for the database that manages business customers and appointments. 

============================================

author: Kun Xie
application version: 1.0


-------------------

Apache NetBeans IDE 14
JavaFX-SDK-18.0.1
Standard Edition & Java Development Kit, Version 18

-------------------
MySQL Connector version: mysql-connector-java-8.0.30

============================================
SCENARIO

You are working for a software company that has been contracted to develop a GUI-based scheduling desktop application. The contract is with a global consulting organization that conducts business in multiple languages and has main offices in Phoenix, Arizona; White Plains, New York; Montreal, Canada; and London, England. The consulting organization has provided a MySQL database from which the application must pull data. The database is used for other systems, so its structure cannot be modified.

The organization outlined specific business requirements that must be met as part of the application. From these requirements, a system analyst at your company created solution statements for you to implement in developing the application. These statements are listed in the requirements section.

Your company acquires Country and First-Level-Division data from a third party that is updated once per year. These tables are prepopulated with read-only data. Please use the attachment “Locale Codes for Region and Language” to review division data. Your company also supplies a list of contacts, which are prepopulated in the Contacts table; however, administrative functions such as adding users are beyond the scope of the application and are done by your company’s IT support staff. Your application should be organized logically using one or more design patterns and generously commented on using Javadoc so other programmers can read and maintain your code.


============================================

OVERVIEW

This project involves concepts such as refining OOP expertise and building database and file server application developments. It includes lambda expressions, collections, input/output, advanced error handling, and uses the newest features of Java 11 to develop software that meets specific business requirements. The program will also apply the localization API and date/time API in application development to support end-users in various geographical regions. 


============================================


IMPLEMENTATION

The project will create a GUI-based application for the company. The main application will connect the local data files using MySQL Workbench to perform ETL.
The NetBeans IDE is used here while incorporating the JavaFX library in order to facilitate GUI building in SceneBuilder.

============================================

FUNCTIONALITIES


•  accepts username and password and provides an appropriate error message

•  determines the user’s location (i.e., ZoneId) and displays it in a label on the log-in form

•  displays the log-in form in English or French based on the user’s computer language setting to translate all the text, labels, buttons, and errors on the form

•  automatically translates error control messages into English or French based on the user’s computer language setting

•  Customer records and appointments can be added, updated, and deleted.

-  When deleting a customer record, all of the customer’s appointments must be deleted first, due to foreign key constraints.

•  When adding and updating a customer, text fields are used to collect the following data: customer name, address, postal code, and phone number.

-  Customer IDs are auto-generated, and first-level division (i.e., states, provinces) and country data are collected using separate combo boxes.

-  When updating a customer, the customer data auto-populates in the form.

•  Country and first-level division data are prepopulated in separate combo boxes or lists in the user interface for the user to choose. The first-level list should be filtered by the user’s selection of a country (e.g., when choosing U.S., filter so it only shows states).

•  All of the original customer information is displayed on the update form.

-  Customer_ID must be disabled.

•  All of the fields can be updated except for Customer_ID.

•  Customer data is displayed using a TableView, including first-level division data. A list of all the customers and their information may be viewed in a TableView, and the data can be updated in text fields on the form.

•  When a customer record is deleted, a custom message should display in the user interface.

A contact name is assigned to an appointment using a drop-down menu or combo box.

•  A custom message is displayed in the user interface with the Appointment_ID and type of appointment cancelled.

•  The Appointment_ID is auto-generated and disabled throughout the application.

•  When adding and updating an appointment, record the following data: Appointment_ID, title, description, location, contact, type, start date and time, end date and time, Customer_ID, and User_ID.

•  All of the original appointment information is displayed on the update form in the local time zone.

•  All of the appointment fields can be updated except Appointment_ID, which must be disabled.

•  The program enables the user to adjust appointment times. While the appointment times should be stored in Coordinated Universal Time (UTC), they should be automatically and consistently updated according to the local time zone set on the user’s computer wherever appointments are displayed in the application.

Note: There are up to three time zones in effect. Coordinated Universal Time (UTC) is used for storing the time in the database, the user’s local time is used for display purposes, and Eastern Standard Time (EST) is used for the company’s office hours. Local time will be checked against EST business hours before they are stored in the database as UTC.

•  The program implements input validation and logical error checks to prevent each of the following changes when adding or updating information; display a custom message specific for each error check in the user interface:

•  scheduling an appointment outside of business hours defined as 8:00 a.m. to 10:00 p.m. EST, including weekends

•  scheduling overlapping appointments for customers

•  entering an incorrect username and password

•  The program provides an alert when there is an appointment within 15 minutes of the user’s log-in. A custom message should be displayed in the user interface, including the appointment ID, date, and time. If the user does not have any appointments within 15 minutes of logging in, display a custom message in the user interface indicating there are no upcoming appointments.

•  This program will track and autolog login activities for later auditing. 

