# Purchases Tracker

## *A java program that helps retailers with tracking and analysing customer purchases*

<p> My application allows a retail business to enter customer purchases and information based on receipts. I plan to add features that help retail businesses view and analyse their business operations. My application will offer features such as the ability to view the total money spent, track the amount of goods purchased, set a revenue goal and view progress etc.

<p> This project is of interest to me because I am very interested in general business operations and optimization. Also, I plan to transfer into the Business and Computer Science combined major, so I think this project matches my field of study and interests very well.


## User Stories

* As a business, we want to be able to add a customer purchase and corresponding information to a list of purchases.
* As a business, we want to be able to view the purchases we have recorded in the list of purchases. 
* As a business, we want to be able to delete a selected customer purchase because it was refunded/declined.
* As a business, we want to be able to filter and view the amount of money spent / amount of goods bought in a specific time period we choose.
* As a business, we want to set a revenue goal and see data regarding progress.
* As a business, we want to be able to load a previous list of purchases from file.
* As a business, we want to be able to save the current list of purchases to file.
* # Instructions for Grader

- You can generate the first required action related to the user story "adding multiple purchases to a list of purchases" by adding a purchase.
- You can generate the second required action related to the user story "adding multiple purchases to a list of purchases" by removing a purchase.
- You can locate my visual component by starting up the program.
- You can save the state of my application by using the save button.
- You can reload the state of my application by using the load button.

* # Phase 4: Task 2
Fri Dec 01 16:24:08 PST 2023
A list of purchases was initialized with a revenue goal of 650


Fri Dec 01 16:24:08 PST 2023
The following purchase was added:
Transaction ID: 1, Customer Name: zohair, Day of Purchase: 2, Items Bought: [], Transaction Amount: $10


Fri Dec 01 16:24:08 PST 2023
The following purchase was added:
Transaction ID: 2, Customer Name: gregor, Day of Purchase: 3, Items Bought: [], Transaction Amount: $20


Fri Dec 01 16:24:08 PST 2023
The following purchase was added:
Transaction ID: 33, Customer Name: paul, Day of Purchase: 5, Items Bought: [], Transaction Amount: $30


Fri Dec 01 16:24:08 PST 2023
The following purchase was added:
Transaction ID: 44, Customer Name: drake, Day of Purchase: 7, Items Bought: [], Transaction Amount: $40


Fri Dec 01 16:24:08 PST 2023
The following purchase was added:
Transaction ID: 55, Customer Name: yeat, Day of Purchase: 9, Items Bought: [], Transaction Amount: $20


Fri Dec 01 16:24:08 PST 2023
The following purchase was added:
Transaction ID: 66, Customer Name: travis, Day of Purchase: 10, Items Bought: [], Transaction Amount: $17

Fri Dec 01 16:24:15 PST 2023
The following purchase was removed from the list:
Transaction ID: 66, Customer Name: travis, Day of Purchase: 10, Items Bought: [], Transaction Amount: $17


Fri Dec 01 16:24:21 PST 2023
The revenue was calculated as: $120


Fri Dec 01 16:24:22 PST 2023
The average transaction spending was calculated as: $24.0


Fri Dec 01 16:24:24 PST 2023
The revenue was calculated as: $120


Fri Dec 01 16:24:24 PST 2023
The average transaction spending was calculated as: $24.0


Fri Dec 01 16:24:24 PST 2023
The amount of transactions on average required to reach revenue goal was calculated as: $22.083334


Fri Dec 01 16:24:40 PST 2023
The following purchases were filtered between starting day 5 and ending day 7:
Transaction ID: 33, Customer Name: paul, Day of Purchase: 5, Items Bought: [], Transaction Amount: $30
Transaction ID: 44, Customer Name: drake, Day of Purchase: 7, Items Bought: [], Transaction Amount: $40

Process finished with exit code 0

* # Phase 4: Task 3

I think I could improve my design in the GUI portion of my program by refactoring in the showMainframe() method.
Instead of instantiating the buttons one at a time and having way too many helper functions to set their respective action performed, I could create a helper function
that takes button text and actionPerformed as parameters and returns a JButton. This would make my code look much more
organized and would also allow me to add new buttons very easily since I can just call the helper function with the new button
requirements (text and actionPerformed).
