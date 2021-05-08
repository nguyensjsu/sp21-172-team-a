# Week #3 Status Report

### Snapshot
Below is an image of this week's task board:

![image](images/week3cards.png)

### Accomplishments
Week 3 was primarily spent fixing the payment method html page and controller as well as creating the Customer class, repository, and all respective classes.  

**Cards:**

1) [Fixing Payment Method](https://github.com/nguyensjsu/sp21-172-team-a/commit/6f77af5ec69a981e819cddc36c97f4db79515b94)

This commit composed of fixing the payment method code that broke when being transferred to main last week. 
The primary issue was:
- incorrect Thymeleaf routing that prevented the HTML from routing the form input info to the controller correctly. 
- the Thymeleaf routing did not get sent to the payment method controller due to a change in the location of the page on the site (from localhost:8080/ to local host:8080/paymentmethod). 

This problem was resolved by:
- adding Thymeleaf routing to every form input
- routing the entire form to the payment controller
- changing the @RequestMapping of the payment controller to /paymentmethod

2) [Customer Class, Database, and Supporting Components](https://github.com/nguyensjsu/sp21-172-team-a/commit/72045f280b83c911c384926af35ca68888c332b3)

This commit composed of creating all of the elements that are to be stored in the product database. These classes include:
- Customer class, the primary object to be stored
- CustomerRepository
- BillingInfo, which stores the billing address information (Address, State, ZIP, Email, etc.)
- CreditCard, for credit card information
- StarbucksCard, for Starbucks cards
- Order, for all things Starbucks order related
All content related to configuring the elements to be correctly stored to the database as well as the creation of the Order classes were made with the help of Justine

### Challenges
One of the primary challenges Justine and I faced while trying to configure CustomerRepository to correctly store information from the BillingInfo, Order, StarbucksCard, and CreditCard. We were able to do this by:
- adding the @Embeddable tag to all class objects to be stored in Customer
- adding @CollectionTable and @ElementCollection tags to the elements in the Customer class
- making the Customer class store an ArrayList of these elements, and not jsut a single instance

Another problem Justine and I came across this week was trying to properly test the functionality of the database. Because our system for creating Customer objects was not properly implemented yet, we were not sure how to test the functionality of the database aside from its creation. We managed to get aroudn this by:
- making SpringCashierApplication implement CommandLineRunner
- overriding the run() method that comes from this class to create a Customer object
Once this was accomplished, we were able to temporarily test the database by creating various objects and storing them in the database.
