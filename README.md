# Purchases Tracker

## *A java program that helps retailers with tracking and analysing customer purchases*

<p> To run: git clone https://github.com/zohairomar1/BusinessTrakingApp
<p>         Run main.PurchaseGUI 
        

<p> My application allows a retail business to enter customer purchases and information based on receipts. I plan to add features that help retail businesses view and analyse their business operations. My application will offer features such as the ability to view the total money spent, track the amount of goods purchased, set a revenue goal and view progress etc.

## User Stories

* As a business, we want to be able to add a customer purchase and corresponding information to a list of purchases.
* As a business, we want to be able to view the purchases we have recorded in the list of purchases. 
* As a business, we want to be able to delete a selected customer purchase because it was refunded/declined.
* As a business, we want to be able to filter and view the amount of money spent / amount of goods bought in a specific time period we choose.
* As a business, we want to set a revenue goal and see data regarding progress.
* As a business, we want to be able to load a previous list of purchases from file.
* As a business, we want to be able to save the current list of purchases to file.

* # Phase 4: Task 3 (POSSIBLE IMPROVEMENTS)

I think I could improve my design in the GUI portion of my program by refactoring in the showMainframe() method.
Instead of instantiating the buttons one at a time and having way too many helper functions to set their respective action performed, I could create a helper function
that takes button text and actionPerformed as parameters and returns a JButton. This would make my code look much more
organized and would also allow me to add new buttons very easily since I can just call the helper function with the new button
requirements (text and actionPerformed).
