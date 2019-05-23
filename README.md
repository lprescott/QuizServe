# Final Project for ISCI-418Y, Software Engineering, Spring 2019

### Presentation [here](https://docs.google.com/presentation/d/1TYiOCDKvI02Zjpk0cX1OBYb9qO-9BI-ckkVlMHD0TyA/edit?usp=sharing)

### Screenshots [here](https://github.com/lprescott/ICSI418-Group-Project/blob/master/project-logistics/screenshots/)

![The Login Page](https://github.com/lprescott/ICSI418-Group-Project/blob/master/project-logistics/screenshots/image19.png)

### Video [here](https://www.screencast.com/t/82TMiD1ZoB)

### ~~See it live [here](http://quiz-app.us-east-1.elasticbeanstalk.com/), at this domain: http://quiz-app.us-east-1.elasticbeanstalk.com/.~~ Terminated
|| Professor Yates  | Professor Cusano | Testing |
|--------| ------------- | ------------- | ------- |
|email| jyates@albany.edu  | cacusano@albany.edu  | admin@gmail.com |
|password| csi2019  | csi2019  | csi2019 |

### Requirements
The final project was a web-based application. More specifically, it was a testing tool that’s function was the managing, administration, and taking of multiple-choice and true-false examinations. The goal of the project was to create a testing system, which includes a user component, where one can take any number of assigned tests, receiving said results, as well as viewing results of previously taken tests. The testing system also includes an administrative component where one can create, read, update and delete all types of users, questions (answers), and tests – as well as view user’s results.

More specific requirements include: users are identified by and login through an email and password; users can be marked inactive by an administrator and are therefore not allowed to login; users are emailed their identification information upon account creation by an administrator; questions are to be categorized and can contain images as well as text; upon taking a test, a user’s progress should be saved actively as they answer questions; the header image, header text, and footer text should be customizable by each administrator; a test with questions and answers can be uploaded to the web-app in the form of a csv file, 
propagating through the database.

To fulfill the project objective and requirements detailed previously, it is important to specify what data is required: To secure the test management solution, users (both administrator and normal) must login with username and password data. Users activity data must also be retrieved upon login; i.e. users marked with inactive data will not be allowed to login. To successfully represent tests in a web-based GUI, a test has certain required data including who created it, its name, the due-date, and what questions the test contains. Questions must have data to determine whether it is TF or MC, as well as its answers if it is a MC question or if the answer is true or false if it is a TF question. Answers must have text, and data determining whether the answer is correct given its question. And chosen answers must be stored before a Test entity is created; i.e. answer data must be stored until test submission and then flushed when the test submitted or the due date passes.

* See [Assignment PDF](https://github.com/lprescott/ICSI418-Group-Project/blob/master/project-logistics/Final%20Project%20for%20CSI%20418%20Spring%202019.pdf)
* See [Live Trello](https://trello.com/b/pfH92DPN/icsi-418-group-project)

### Design
![Wire Frame](https://github.com/lprescott/ICSI418-Group-Project/blob/master/project-logistics/wire-frame.png) 

<p align="center">
  <img src="https://github.com/lprescott/ICSI418-Group-Project/blob/master/project-logistics/state-machine-diagram.png" title="State Machine">
</p>

### Product Backlog
* The priority of the stories goes top -> bottom, and the number at the beginning of each card title is the story point, however, they are also available as tags for each trello item. Thank you.
* See [Live Trello](https://trello.com/b/pfH92DPN/icsi-418-group-project)
* See [Product Backlog PDF](https://github.com/lprescott/ICSI418-Group-Project/blob/master/project-logistics/Product-Backlog.pdf)

### Project Plan with Sprints
* The priority of the tasks goes top -> bottom; the labels determine which sprint a task is part of; the product backlog (column 1) determines the due date of these sprints by said tags. The five agile columns we have in our project plan are: Product Backlog, Not Started, In Development, To Verify, and Done.
* Live Public Link:  https://trello.com/b/pfH92DPN/icsi-418-group-project
* 4.11.2019 Trello Backup:  https://trello.com/b/6Yb23Pnh/4112019
* 4.6.2019 Trello Backup:  https://trello.com/b/J43ZnM3y/462019
* 3.28.2019 Trello Backup:  https://trello.com/b/ImeyHv1X/3282019
* 3.21.2019 Trello Backup:  https://trello.com/b/ZcDJdnAc/3212019
* 3.14.2019 Trello Backup:  https://trello.com/b/xDXtTe7T/3142019

<p align="center">
  <img src="https://github.com/lprescott/ICSI418-Group-Project/blob/master/project-logistics/cumulative-flow.png" title="Cumulative Flow">
</p>

### Testing Plan and Results
* Download [Testing Plan and Results Document](https://github.com/lprescott/ICSI418-Group-Project/blob/master/project-logistics/Testing-Plan-and-Results.docx)
  * See [PDF](https://github.com/lprescott/ICSI418-Group-Project/blob/master/project-logistics/Testing-Plan-and-Results.pdf)

### Deployment Plan Including Install Scripts
* See [Live Trello](https://trello.com/b/pfH92DPN/icsi-418-group-project), more specifically: Sprint 7.
* See [SQL Scripts](https://github.com/lprescott/ICSI418-Group-Project/blob/master/sql/)
* Download [Deployment Plan Document](https://github.com/lprescott/ICSI418-Group-Project/blob/master/project-logistics/Deployment-Plan.docx)
  * See [PDF](https://github.com/lprescott/ICSI418-Group-Project/blob/master/project-logistics/Deployment-Plan.pdf)

### Maintenance Plan 
* Download [Maintenance Plan Document](https://github.com/lprescott/ICSI418-Group-Project/blob/master/project-logistics/Maintenance-Plan.docx)
  * See [PDF](https://github.com/lprescott/ICSI418-Group-Project/blob/master/project-logistics/Maintenance-Plan.pdf)

## Other Info
### Dependencies
* javax.servlet-api 4.0.1
* javax.servlet.jsp-api 2.3.3
* jstl 1.2
* mysql-connector-java 8.0.15
* javax.mail 1.6.2
* commons-io 1.3.2
* commons-fileupload 1.4
* opencsv 4.5


### [Group Chat Invite Link](https://join.slack.com/t/icsi-418team/shared_invite/enQtNTU4NjUxODQ4NTQ2LTM2MDMwY2ExM2U0YjU0ZjMzNzkzY2JlNGFiMTQ4YWJlMjBkM2JmNTMyZThlMWRkZmYxZjhhZTcxYWQ5M2E5Y2I)
* [Slack](https://www.slack.com) Team Code: __icsi-418team__.slack.com

### [Trello Invite Link](https://trello.com/invite/b/pfH92DPN/355ce0c1f77e07fc7a083b350d3e0692/icsi-418-group-project)
* A repository/task manager for hosting our product backlog and sprints

### [Assignment PDF](https://github.com/lprescott/ICSI418-Group-Project/blob/master/project-logistics/Final%20Project%20for%20CSI%20418%20Spring%202019.pdf)
  
### Team Members:
1. Luke R. Prescott
2. Sean Loucks
3. Jack Holden
4. Max Moore
5. Chin Wa Cheung
6. Will Dahl
7. Gary Passarelli
