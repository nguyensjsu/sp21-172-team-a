# Week #4 Status Report

### Snapshot
Below is an image of this week's task board:

![image](https://user-images.githubusercontent.com/43689410/118232951-21a36980-b446-11eb-993d-ed9602b5a759.png)

### Accomplishments
Our team had a bit of miscommunication and confusion over how the cashier's application should tie in with other applications, so changes had to be made several times to reflect what teammates needed.

1) Push working order input into repository from cashier's app. This made the order repository work with very simple configurations for others to integrate into their code. [Commit](https://github.com/nguyensjsu/sp21-172-team-a/commit/ded742d77162611444480c539831e688ec4aef54)
2) Fixed CSS errors that arose and problems that arose from someone else's push. This change also integrated a lot of updates to the order functionality and mapping (one such error was that the @Slf4j was not annotated and the page was not able to update locally) [Commit](https://github.com/nguyensjsu/sp21-172-team-a/commit/d8b63cec372b75ea0694192f2cc7271c2b104ee9)
3) Customer-to-orders mapping that used JPA One to Many mapping for relational databases. The changes were inspired by [this](https://attacomsian.com/blog/spring-data-jpa-one-to-many-mapping) document [Commit](https://github.com/nguyensjsu/sp21-172-team-a/commit/ca329d0ab572493912349eb4f14b89e9bab25a39) 

### Challenges
This week our team made a few Git errors that caused us to accidently remove or edit other people's work that should not have been edited (commented out/deleted working code to test their own code), and this affected others when pushed to main. This can be seen in my 2nd listed commit and I had made errors as well with the customer repository. 

Our team also realized that we had a few misunderstandings about the project description (how the applications should be connected yet separated). This was resolved with several discussions as well as going to office hours.
