Aakash Battery Check
=====================

About
-----

Aakash Battery Check application is an Android Application which
will be used to test the battery of Aakash Tablet.
This app will maintain the start and end time of charging
and calculate the charging time.
The data will be stored in the database.
That database file can be then exported in the form of .csv file to
the internal memory of Aakash Tablet.
This .csv file can be then transferred to PCs through USB cable.
Historical analysis of battery charging cycles can be done through this data.




Functionality
--------------

**Battery Information**

The application provides all the battery infomation.
The battery information includes the following.

- Battery Percentage: Shows the current battery percentage.
- Status: Shows whether battery is currently charging or not.
- Temperature: Shows the battery temperature in celsuis.
- Voltage: Shows battery volatge in mAh.
- Health status: Shows the condition of Battery, whether its in good condition or bad.
- Technology: Shows the technology for the battery used.


**Logs**

Logs shows all the data stored in database in tabular format.
The data can be used for historical analysis.
Logs consists of following fields

- Sr. No: Shows the number of charging cycles.
- Start Time: Shows the time when charger was being connected.
- End Time: Shows the time when charger was being disconnected.
- Start %: Shows the battery percentage when charger was being connected.
- End %: Shows the battery percentage when charger was being disconnected.
- Time Diff: Shows the time difference between Start Time and End Time.
- % Charged: Shows the percentage difference between Start % and End %.



License
-------

Distributed under `GNU GPL Version 3 
<http://www.gnu.org/licenses/gpl-3.0.txt>`_.








