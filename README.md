# TopSales
Market place demo application using ready made REST API and Primefaces framework

## Installation instructions:

### Quick setup from ready made build

There is ready made war file in this repository. You will find it from src/TopSales/dist folder.
I hope you have already running Glassfish server environment. If not, download and install it some computer.
Open admin page in your favorite web browser Default admin port is 4848. 
Deploy this war file and set context path regarding your environment and set application name TopSales if missing.
Now you should be able to open TopSales in your browser.

### Development environment:

Download JDK1.8 and install it.

Download Netbeans IDE from here https://netbeans.org/downloads/. Select package containing J2EE support.
You should consider what kind of computer you are using as development environment. 
There are different packages for Linux and windows. 

Here you can find detailed instructions for installing Netbeans and Glassfish: https://netbeans.org/community/releases/80/install.html

### Java package dependencies

This is not an Maven project, so you need download and install dependency packages.

 Dependencies
- PrimeFaces 5.2
- Jersey 2.22 
    
Download PrimeFaces from here: http://www.primefaces.org/downloads

Select community edition primefaces-5.2.jar to download
    
Download Jersey from here: https://jersey.java.net/download.html

I have been using Jersey JAX-RS 2.0 RI bundle which contains all needed JAR:s

Add these as libraries in Netbeans. I have been using following library names:

Jersey 2.22

PrimeFaces-5.2

After these steps check out code and it should open cleanly in IDE. There is no need for any configuration. 
Just point project name and select Run. If everything went ok, web browser window should open containing running application.

