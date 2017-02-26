# language-note
An application for learning languages.

With this project, a user can note his own vocabularies, phrases, idioms in any lessons, books.
After that, he can test his vocabularies. The test will show some explanations and then the user has to recall the correct vocabularies, phrases.
The user will get a score for each words, the words with the most failure answers will be more likely to appear in later tests.

For the first version, we are focusing on English and vocabularies.

# Requirement 
There are some questions we have to answer:
+ I would like to _collect_ some **Expressions** _in the global_ **Dictionary** or _in other users'_ **Dictionaries** _into my own_ **Note**.
+ I can _practice_ on my own **Note**.
+ I will _answer_ **Questions** inside a **Note**, and I will _have_ an **Answer Result** for each question. The **Practice Result** _of a_ **Note** will be aggregate from the **Practice Result** of each **Question** in the **Practice Session**.
+ I can _practice_ with the new **Expressions** first.
+ The next time I _practice_, it must show either **Expressions** I have never tried or **Expressions** I didn't get max points in **last 2 Practice Results**.

# Run project
Build:
````
mvn clean install -DskipTests
````

Deploy:
````
cp target/language-note.war {TOMCAT_HOME}/webapp/
````

Neo4j server start:
````

````

# Security
The current main target is features implementation, the security will be improve later.
Now it's using Username - password authentication. The API only uses session to track authenticated user.
In the future, it should use OAuth2.

# Database

# References
Some good resource related to language:
http://blog.mashape.com/list-of-25-natural-language-processing-apis/
