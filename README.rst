Aakash Battery Check
=====================

About
-----

Aakash Battery Check application is an Android Application which is used to test the battery 
charging of Aakash Tablet. It maintains the start and end time of charging, and calculates the
charging time. The data is stored in a database. The database file can be exported as a .csv 
file to the internal memory of Aakash Tablet.
This .csv file can be then transferred to a PC or laptop through USB cable.
Historical analysis of battery charging cycles can be done from this data.


Need for Battery Check Application
------------------------------------

The total number of Aakash tablets at IIT Bombay and Aakash Remote Centres is 1,00,000.
We face and will face the Battery Problem. Sometimes it takes too long to charge the battery than the
normal time, and sometimes the battery of Aakash Tablet discharges too fast than normal time.
This may result in a user demanding for change in battery of Aakash Tablet, but how will a user know 
there’s a need to change the battery of Aakash Tablet? For this the user needs an app!



Functionality
--------------

**Battery Information**

The application provides the following battery information:

- Battery Charge Percentage: current battery charge percentage.
- Status: whether battery is currently charging or not.
- Temperature: battery temperature in Celsius. 
- Voltage: battery voltage in mAh. 
- Health status: condition of Battery, whether is in good condition or bad. 
- Technology: technology for the battery used.


**Logs**

Log shows all the data stored in database in tabular format. The data can be used for historical 
analysis. Log consists of the following fields

- Sr. No : Shows the number of charging cycles. 
- Start Time: Shows the time when charger was being connected. 
- End Time : Shows the time when charger was being disconnected.
- Start % : Shows the battery percentage when charger was being connected. 
- End % : Shows the battery percentage when charger was being disconnected. 
- Time Diff : Shows the time difference between Start Time and End Time.
- % Charged : Shows the percentage difference between Start % and End %.


**Graph**

Graph shows the logs data in graphical view.

- On x-axis there are charging cycles. 
- y axis consists of two parameters: Start % and End % 
- Red line represents Start % of the battery.
- Green line represents End % of the battery.



License
-------

Distributed under `GNU GPL Version 3 
<http://www.gnu.org/licenses/gpl-3.0.txt>`_.








