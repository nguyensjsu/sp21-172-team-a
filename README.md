# CMPE 172 - Team Project

## Overall Architecture Diagram of your Cloud Deployment
- S

## Individual Features
- A section for each of the following discussion the features implemented

### Cashier's App
- What features were implemented?

### Backoffice Help Desk App
- Change Rewards
	- They have the option to change the reward points by inserting the customer ID and the new reward number and clicking update rewards.

### Online Store
- User Registration
	- Users can sign up for an account with the customer store
- Spring Security for User Authentication
	- Users can log into the customer store if their login credentials match any credentials in the database
- Billing Information Section
	- Users can create and add their billing information to their account, which includes
		- Address
		- City
		- State
		- Zip
		- Phone
		- Email
- Payment Method Section
	- Users can create and add their credit card information to their account, which includes
		- Credit Card Number
		- Credit Card Expiration Month
		- Credit Card Expiration Year
		- Credit Card CVV
- Starbucks Card Section
	- allows users to view the balance and rewards points of each card
	- allows users to use their credit card to increase the balance of their Starbucks card


### REST API
- Final design with sample request/response

### Integrations
- CyberSource

### Cloud Deployments
- Design Notes on GitHub an Architecture Diagram of the overall Deployment.
- How does your Team's System Scale?  Can it handle > 1 Million Mobile Devices?

## Technical Requirements
- Discussion with screenshot evidence of how each technical requirement is meet.

### Must use Spring Framework (Spring MVC, Spring JPA, Etc...)
- Cashier's App
- Backoffice Help Desk App

### For customer facing Online Store, team can chose front end Tech Stack
- For Example:  Node.js + Javascript/React

### Development Tools
- Builds must be done with Gradle 5.6
- Version of Java should be JDK 11

### Database & Middleware Requirements
- MySQL Database 8.0
- RabbitMQ


### Cashier's App Screenshot 
![Cashier's App](./images/.png)

### Backoffice Help Desk App
The backoffice help desk allows the barista to insert user ID and change the number of reward points for any specific user.
![Backoffice Help Desk](./images/.png)

### Online Store
![Online Store](./images/.png)

### REST API 
![REST API](./images/.png)

### Integrations
![Integrations](./images/.png)

### Cloud Deployments
![Cashier's App](./images/.png)
