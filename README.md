# Animation

This application determines movement of items in a linear field.  

Full requirements are in the 'BackendCodeTest' pdf in this directory.


Building and Running
====================

Assumptions 
-----------
The following software is assumed to be installed and available for the building and running of this application
- Java 8
- Maven
 

Building the application with Maven
-----------------------------------
Run the maven package or install phase from the root of the application:
```angular2html

   > mvn clean package
```

This will create the following jar file: ./target/missing_letters.jar


Running the unit tests
----------------------
Run the included unit tests using the standard maven test phase:
```$xslt
> mvn test
```


Running the application
-----------------------
To run the application on a single given string, execute the missing_letters jar with a input string parameter
```angular2html

   > java  -jar  target/missing_letters.jar  {input-string-parameter}
```

For example, to test a string such as "alert brown dog," 
run the application as follows (using quotes to surround input with spaces): 
```angular2html

   > java -jar target/missing_letters.jar "alert brown dog"
```

Generating and Testing Large Chambers
------------------------

A helper class called ParticleSubmitter (in com.glen.aniation.generator) can be used to generate
Very Large Chambers for testing.   Adjust the values at the start of the main() method to 
vary the contents, size and quantity of the auto-generated and submitted chambers.

Using this approach on a basic development laptop provided the following preliminary benchmark: 
a 10,000,000 character chamber with 4,000,000 particles took between 300ms and 1,300ms to process.


Notes on the Implementation
===========================

Assumptions regarding the requirements
--------------------------------------

1. An bad speed (less than or equal to 0) will result in an IllegalArgumentException
1. A null input String will result in an IllegalArgumentException
1. The values for left and right particles in the input string are case sensitive.


Likely Improvements in real life
--------------------------------
Depending on the actual usage for this (a one-off script?  a production-grade application?), the
following improvements might be appropriate:

- Add additional tests
- Use a real logging system (e.g., log4j)



