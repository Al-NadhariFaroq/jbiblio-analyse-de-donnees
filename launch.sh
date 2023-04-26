#!/bin/bash

mkdir classes
mkdir lib
mv devops-jbiblio-analyse-de-donnees-1.0-SNAPSHOT.jar lib/
javac -d classes -cp classes:lib/devops-jbiblio-analyse-de-donnees-1.0-SNAPSHOT.jar Demo.java
java -cp classes:lib/devops-jbiblio-analyse-de-donnees-1.0-SNAPSHOT.jar Demo

