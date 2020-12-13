# camunda-exportProductCatalogue

## Build in Eclipse IDE

After opening of the project in the IDE(eclipse), build path need to be configured.
```
Rightclick on project -> Build path -> Configure build path
```

Then remove existing JRE and add JDK as alternative JRE to build path
```
Add Library -> JRE System Library -> Alternat JRE
```

![alttext](https://github.com/myCamundaProjects/Camunda-exportProductCatalogue/blob/main/images/AddJREsystemLib.PNG?raw=true)

As package created using Talend ESB is used in this application, relevant main package and dependancies are included in talend-lib directory inside the project resource directory.
And `maven-war-plugin` has been used to include them in war package.
As those are also needs to be in build path, add web app libraries to build path
```
Add Library -> Web App Libraries -> Select Project
```
![alttext](https://github.com/myCamundaProjects/Camunda-exportProductCatalogue/blob/main/images/AddWebAppLib.PNG?raw=true)

Then app.properties file inside the project resource directory need to modify with relevant environment configurations

![alttext](https://github.com/myCamundaProjects/Camunda-exportProductCatalogue/blob/main/images/AppProperties.PNG?raw=true)

Then bulild the application.

## Deployment to Tomcat Application Server

As camunda workflow allows to download exported items, context path need to be set to the local export path.
Before deploying to the application server. ContextPath need to be set
eg: if the context path used is `/export/data`. A file named `export#data.xml` need to be added in 
`$CATALINA_HOME/conf/{enginename}/{hostname}/`
If the locat export directory is `D:/data/` (exportDir=D:/data/ in app.properties)

![alttext](https://github.com/myCamundaProjects/Camunda-exportProductCatalogue/blob/main/images/contextXML.PNG?raw=true)

 Copy the *.war file from the `target` directory to the deployment directory of your application server e.g. `$CATALINA_HOME/webapps`

