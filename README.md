
# spark scala sample project

This project is a template for spark scala projects  
It contains all necessary template configurations in order to generate the project's jar files, 
deploy it on HDFS cluster and submit the Oozie workflow/coordinator.

## Getting Started

These instructions will get you a copy of the project up and 
running on your local machine for development and testing purposes. 

See deployment for notes on how to deploy the project on a live system.

### Prerequisites

```
JDK 8
configured Maven (.m2/settings.xml) 
IntelliJ
```

### Checkout Okulab DLT project

Before start the project, clone or donwload  the datalake tools  using the link:

https://github.com/okulab/dlt

```
cd your_workspace/

git clone https://github.com/okulab/dlt.git
```

Once you have the project on your local workspace, go inside of the project.
```
cd your_workspace/dlt
```
And run the command;
```
mvn clean install
```
Please verify if the build was succesfuly finished, if this is the case you can continue .


## Clone Spark-scala-project-sample
```
cd your_workspace/

git clone https://github.com/okulab/dlt.git

cd spark-scala-project-sample

mvn clean install
```


### src/main/assembly

In this directory you will find one file ;

```
descriptor-deploy-run-prd.xml
```

These files are responsible for generating your zip file. You DO NOT NEED to touch and change these files. 
In case you will need to add some files inside of your zip, you 
SHOULD NOT delete any of dependency which was already there. 

+pom (provided)

PS: If you need your changements to appear in all environements, please make sure that you have
 all applied in all descriptor file (dev/test/prd).

### src/main/filters

In this directory you will find the main configurations about your hadoop environement, 
certifications and mail options and stuff like applicatino conf. 

Since all environement configuration is different from the other, you have to 
change the specified file for each environement.

sample
```
sample.queue= your application's queue name.
hdfs.path= your project's main hdfs path.
input.path= the path which will be used to put the project's .jar.
username= the username which will run the oozie job.
es.trust.keystore= the path on hdfs where you store your Elasticsearch trustStore. 
es.keystore= the path on hdfs where you store your Elasticsearch certification key.
oozie.mail.to= the mail addresse which will receive the alert once the oozie job fails.
oozie.mail.cc= the mail addresse which will receive the alert once the oozie job fails (on CC).
oozie.mail.subject= the subject of the mail.
oozie.mail.body= the body of the mail.
```

### src/main/groovy

deploy.groovy :

This default code deploys oozie's coordinator.xml, workflow.xml and the project's jar 
on hdfs and run the oozie coordinator.
You can comment or decomment all the functions which are available on the file specific to your needs. 

PS: Pay attention to the path that you give in runOozieJobs function; 
if you give the the path of coordinator.xml the function will run a coordinator, if you specify
workflow.xml it will run a workflow. 

### src/main/resources

**local-conf/logback.xml:**
NOT MODIFY
This file is used in order to configure logging levels of the application. On the template,
 all groovy logs are printed on the console (DEBUG level) and the other logs are appended in a file 
which is named debug.log (DEBUG level).

**oozie/coordinator.sample/coordinator.xml:**

This file is an oozie coordinator template. This template will allow you to run an oozie coordinator 
job once the deploy.groovy is launched (  inside of the runOozieJobs, coordinator.sample/job.properties 
must be defined.) For all modifications, you can see the [tutorial](https://www.tutorialspoint.com/apache_oozie/apache_oozie_coordinator.htm)

```
frequency="*/20 * * * *"
```
This variable allows you to launch your coordinator with a CRON. Please refer to this "[link](https://www.cloudera.com/documentation/enterprise/5-8-x/topics/admin_oozie_cron.html)" to see details. 

```
start="${start_date}" end="${end_date}"
```
These variables defines the start and end dates for your oozie coordinator job. The job will start and end on the specified dates. In order to change these variables, you should go to 
run/coordinator.sample/coordinator.xml. 

```
${wf_application_path}
```
This variable allows you to specify the path of the oozie workflow, that will be launched by the oozie coordinator, comes from run/coordinator.sample/coordinator.xml. 



**oozie/workflow.sample/workflow.xml:**

This file is an oozie workflow template. This template will allow you to run an oozie workflow job once the deploy.groovy is launched (  inside of the runOozieJobs, workflow.sample/job.properties 
must be defined.). The aim of the template is to launch the project with given options and mail to the user in case of error. 
For all modifications, you can see the [tutorial](https://www.tutorialspoint.com/apache_oozie/apache_oozie_workflow.htm)

```
The variables inside of  @@ and are either coming from src/main/filters/(environement)/conf.properties 

${} => job.properties
```


**run/coordinator.sample/job-properties.xml:**

This is the property file that is used in order to run a oozie coordinator job.


**run/workflow.sample/job-properties.xml:**

This is the property file that is used in order to run a oozie workflow job.


## sample-job

This part of the sample retains the directories where you will put your code and integration tests.

### src/main

User will use this directory to put the project's source code. The structure of this directory depends on the project, responsibles can make any changements inside of this directory.
The code that you find inside of the sample is just a template, you can delete it or make changements on it. 

### src/it

This directory is used in order to put integration tests of the project. 
User should put resource files inside of **it/resources** and tests in **it/scala**. 
