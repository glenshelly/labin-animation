# Animation

This application determines movement of particles in a linear field.  

Full requirements are described in Animation section of the 'BackendCodeTest' pdf in this directory.


Building and Running
====================

Assumptions 
-----------
The following software is assumed to be installed and available for the building and running of this application
- Java 8
- Maven, with a junit dependency
 

Building the application with Maven
-----------------------------------
Run the maven package or install phase from the root of the application:
```angular2html

   > mvn clean package
```

This will create the following jar file: ./target/animation.jar


Running the unit tests
----------------------
Run the included unit tests using the standard maven test phase:
```$xslt
> mvn clean test
```


Running the application
-----------------------
To run the application on a single given string, execute the animation jar with two input parameter
```angular2html

   > java  -jar  target/animation.jar  {speed-parameter}  {initial-chamber-parameter}
```

For example, to test with a speed of 2 and an initial chamber configuration of ..LRL.. 
run the application as follows: 
```angular2html

   > java  -jar target/animation.jar 2 ..LRL..
```

Generating and Testing Large Chambers
------------------------

A helper class called ParticleSubmitter (in com.glen.animation.generator) can be used to generate
Very Large Chambers for testing.   Adjust the values at the start of the main() method to 
vary the contents, size and quantity of the auto-generated and submitted chambers.

Using this approach on a basic development laptop provided the following preliminary benchmark: 
a 10,000,000 character chamber with 4,000,000 particles and a speed of 500,000 (1/20 the chamber size)
took between 300ms and 1,300ms to process.


Notes on the Implementation
===========================

Assumptions regarding the requirements
--------------------------------------

1. An bad or missing speed (less than or equal to 0) will result in an IllegalArgumentException
1. A null input String for chamber configuration will result in an IllegalArgumentException
1. The values for left and right particles in the input string are case sensitive.


Likely Improvements in real life
--------------------------------
Depending on the actual usage for this (A one-off script?  A production-grade application?), the
following improvements might be appropriate:

- Add additional tests
- Use a real logging system (e.g., log4j)




