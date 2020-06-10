# Java Junior Developer Interview Task 
### _by Mihail Barcovschii_  

## Technical Task
* Create a SQLite in-memory database with a single table that will store information from the scv file.
    * Table consists of the same columns as the document
    * SCV file is attached to the technical task
    * All DDL used in a database creating must be submitted to the repository
* Parse the given document
    * Each record needs to be verified to contain the right number of data elements to match the columns
    * Records that do not match the columns count must be written to the bad-data-<timestamp>.csv file
    * Elements with commas must be double quoted
* At the end of the process write statistics to a log file
    * \# of records received
    * \# of records successful
    * \# of records failed
    
## How to run this code
In order to run this code you need to clone this repo and run main() method from Main.class

## Used Technologies
* Maven
* Git
* SQLite in-memory database
* OpenCSV library
* JUnit framework

## How was the process of creating this project
At the very beginning I created a project using Maven and its QuickStart archetype. After that I initiated the local
git repository by using git init command.  

I created a remote repository on GitHub, made the Initial Commit and pushed it.
To start, I decided to get into the database. After some tutorials about SQLite database I created an "API" (don't
know if I can name it so) **InMemoryDatabase.class** with which I interacted with the database.
  
In several commits I wrote a functional for connecting to a database, creating a table, inserting and selecting data
from it. A small unit test was also written to test this functionality. (But i really need help to understand hot to build
these unit tests properly. Unfortunately, I did not go too deep into this topic. Also, I don't understand why 
JUnit is more used than TestNG, when TestNG is more flexible and functional)

After working with the database, I switched to the file parsing. I created a **SCVHandler.class** for this. In this
class were used such classes from OpenSCV library like CSVReader and CSVWriter. And after testing functionality of my parser
I combined the work of my classes in the main() method of **Main.class**

I also created a **HumanPOJO.class**, which my app need in order to exchange data between the parser and the database. 
The parser can create object of this POJO class, and the database can receive these objects as arguments for methods 
that insert data, as well as my **InMemoryDatabase.class** returns data in the form of a list of such objects.

DDL used in creating database can be found in src/main/resources/database directory. I considered that this table needs
one more column, which will be the primary key, so I made one named ID. 

How have been chosen the correct and incorrect records in the document. All records with more or less than 10 columns
were considered incorrect. All records in which there were empty columns or the dollar sigh was missing in the seventh
column are also considered incorrect.

How is logging going on in my project. At start I wanted to use Log4J library, but then I realized that I practically
don't remember anything about it, and don't want to read anoter tutorial. If it will be needed in the next project, I 
will definitely learn this library. So my choice fell on a simple FileWriter / SCVWriter classes.

In the **CSVHandler.class** records that do not fit the above conditions are logged in logs/scvParsingLevelErrors directory.
Also, if records consists of more than 10 columns, columns following the tenth will be written into logs/jokes.txt. For
sure the incorrect records will not get into the resulting list. 

Besides this check and logging, the same thing happens in **InMemoryDatabase.class**. If, for some reason, not all
POJO object fields are initialized, such an object will not be inserted into the database, and this error will be logged into
losg/dataInsertingLevelErrors directory.

Final statistics are written to a log/statistics.txt. Before writing statistics to this file, this information is stored
in the static fields of the **Main.class**.

Thank you for your attention and cool task!

## During the creation of this project I:
* Refreshed my basic knowledge of in-memory databases and JDBC
* Learned how to work with SQLite database
* Learned how to parse SCV documents with using of OpenSCV library
* Realized that cannot do unit testing properly
* Learned how to format Markdown documents
* Etc. Tomorrow I'll write more with a fresh mind
