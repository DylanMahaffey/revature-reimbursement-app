# Expense Reimbursements Application

## Project Description

A Web Application built using Java Servelets, Apache Tomcat, and UIKit which allows for the handling of expense reimbursements.

## Technologies Used

- Apache Tomcat 8.5.60
- UIKit
- Jackson Core (Object Mapper)
- Hibernate 4.3.11.Final
- Java Servlet API 3.1.0
- Log4j API 2.13.0
- PostgreSQL 42.2.18
- JUnit
- Mockito 1.8.4


## Features

List of features ready and TODOs for future development
- After registering or logging in, users can submit tickets specifying what type of expense this was from a set catagory and a list of expenses.
- Financial Managers can approve/deny a reimbursement request. To avoid fraudulant behavior Financial Managers are unable to approve/deny their own reimburstment requests. 

To-do list:
* Complete implementation of receipt (PDF/jpg/png) upload.
* Addition of email confirmation upon new user registration.

## Getting Started
   
In order to see this project in action, you will need a few things:

1) Be sure to have Apache Tomcat 8.5.60 installed.
2) Be sure to have the Java 8 runtime environment installed.

If both of the pre-requisites above are met, go ahead and clone this repo by using the below command:

        git clone https://github.com/DylanMahaffey/revature-reimbursement-app.git

Once cloned, copy the .war file located within the /target directory and paste it into your tomcat webapps folder.

Once the .war is run, by default you will be able to view the application at http://localhost:8080/P1/view/login

## Usage

Welcome to RevReimburse!

![home](http://weather-api-angular-test.s3-website-us-west-1.amazonaws.com/readme-images/Capture.PNG)

To start, register a new user by clicking the "Register" button at the top right of the nav bar.
You can log in as an Employee using the following credentials or create an account of your own:
Email: temp@mail.com
Password: pass

![employee-dashboard](http://weather-api-angular-test.s3-website-us-west-1.amazonaws.com/readme-images/Employee%20Login%20Screen.PNG)

After registering we are logged in and redirected to the employee login page.

![new-ticket](http://weather-api-angular-test.s3-website-us-west-1.amazonaws.com/readme-images/New%20Ticket.PNG)

As an employee, I can create new tickets to request reimbursement from a finance manager.

![ticket-data](http://weather-api-angular-test.s3-website-us-west-1.amazonaws.com/readme-images/new%20ticket%20demo%20data.PNG)

This is what the data in a ticket request will look like.

![my-tickets](http://weather-api-angular-test.s3-website-us-west-1.amazonaws.com/readme-images/ticket%20posted.PNG)

The ticket is now posted and status is pending.

At this point the ticket must be interacted with by a finance manager to be approved or denied. 
Lets log in as a manger to test out the rest of this application.

I have created a manager profile you can log in as:
Email: gitDemo@rev.com
Password: pass

![manager-view](http://weather-api-angular-test.s3-website-us-west-1.amazonaws.com/readme-images/manager%20view.PNG)

When we log in as a manager we are met with all past requests. We can choose to sort these requests by 
date (default), approval status, or price.

![manager-my-tickets](http://weather-api-angular-test.s3-website-us-west-1.amazonaws.com/readme-images/manager%20my%20tickets.PNG)

As a finance manager, to protect against fraud, we can view our own requests but can not approve or deny them. 

![ticket-details](http://weather-api-angular-test.s3-website-us-west-1.amazonaws.com/readme-images/ticket%20details.PNG)

Here, we can click on the view button and are met with the same modal as a regular employee that lists details about the request.

![manager-ticket-details](http://weather-api-angular-test.s3-website-us-west-1.amazonaws.com/readme-images/manager%20ticket%20details.PNG)

This is different from what we see as a manager when we click on someone else's request. When doing this we are met with 
buttons that allow us to approve or deny requests.

![approved-ticket](http://weather-api-angular-test.s3-website-us-west-1.amazonaws.com/readme-images/approved%20ticket.PNG)

After we click approved the ticket is now shown with a status of Approved!

## License

This project uses the following license: MIT

