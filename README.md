**Transaction Processor** This is maven project.
###### Steps
- Checkout the project and exceute ```mvn clean compile assembly:single``` the command
- This command creates executable jar file in ```target``` folder
- Execute the jar using ``` java -jar target/csv-processor-jar-with-dependencies.jar``` with inputs options.

###### Syntax :

```java -jar target/csv-processor-jar-with-dependencies.jar -fp "/path/to/data.csv" -dl "," -fd "20/08/2018 12:00:00" -td "20/08/2018 13:00:00" -mn "Kwik-E-Mart"```

Here:
 - -fp is the file path
 - -dl delimeter
 - -fd from date
 - -td to date
 - -mn merchant name
