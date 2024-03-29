# Week #2 Status Report

### Snapshot
Below is an image of this week's task board:

![image](images/week2cards.png)

### Accomplishments
Week 2 was primarily spent creating the H2 database and the Cybersource integration for the payment method. The program was created and tested on an individual branch, where it was confirmed that the form was able to accept information, process it on Cybersource, and store the card information on the H2 database. 

**Cards:**

1) [Backoffice Help Desk: Linking Up H2 SQL From Lab 6 To Payment Method](https://github.com/nguyensjsu/sp21-172-team-a/commit/56d3821a4ef124782f7946d397d11dbbfc6557b7)

This commit composed of merging my week's work of work on my branch with the main branch. All of the files that I created and edited were pulled into main. 

### Challenges
One of the primary challenges this week was trying to get the paymentmethod.html page to run on the localhost. The primary issue was that the program was able to access the .html, but was not able to parse and load the page. What was attempted to fix it was:

- surrounding the form boxes with a form tag and naming the form tag "command"
- removing the action tag from the .html and the PaymentCommand class

These changes resolved the issue on my individual branch and the .html was about to load, however upon porting the code to the main branch, the .html was unable to load again. This will be resolved next week.