# language-note
Personal Project which supports learning English.

With this project, a user can note his own vocabularies, phrases, idioms in any lessons, books.
After that, he can test his vocabularies. The test will show some explanations and then the user has to recall the correct vocabularies, phrases.
The user will get a score for each words, the words with the most failure answers will be more likely to appear in later tests.

# Requirement 
There are some questions we have to answer:
+ I would like to collect some **Expressions** in the global **Dictionary** or in other users' **Dictionaries** into my own Lesson.
+ I can practice on my own **Lesson**.
+ I will answer **Questions** inside a **Lesson**, and I will have an **Answer Result** for each question. The **Practice Result** of a **Lesson** will be aggregate from the **Practice Result** of each **Question** in the **Practice Session**.
+ I can practice with the new **Expressions** first.
+ The next time I practice, it must show either **Expressions** I have never tried or **Expressions** I didn't get max points in **last 2 Practice Results**.

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

