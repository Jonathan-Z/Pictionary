javac *.java
javac UI/*.java
javac GameLogic/*.java -Xlint:deprecation -Xlint:unchecked
javac Protocol/*.java

java Pictionary
