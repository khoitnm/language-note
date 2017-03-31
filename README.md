language-topic!
==============
An application for learning languages.

> With this project, a user can topic his own vocabularies, phrases, idioms in any lessons, books.
> After that, he can test his vocabularies. The test will show some explanations and then the user has to recall the correct vocabularies, phrases.
> The user will get a score for each words, the words with the most failure answers will be more likely to appear in later tests.
> 
> For the first version, we are focusing on English and vocabularies.

## I. Requirement 
There are some questions we have to answer:

- I would like to _collect_ some **Expressions** _in the global_ **Dictionary** or _in other users'_ **Dictionaries** _into my own_ **Topic**.
- I can _practice_ on my own **Topic**.
- I will _answer_ **Questions** inside a **Topic**, and I will _have_ an **Answer Result** for each question. The **Practice Result** _of a_ **Topic** will be aggregate from the **Practice Result** of each **Question** in the **Practice Session**. 
- I can _practice_ with the new **Expressions** first.
- The next time I _practice_, it must show either **Expressions** I have never tried or **Expressions** I didn't get max points in **last 2 Practice Results**.


- Add a Chrome plugin to look-up new words. The looked up words will be added in to a topic (the name is the title of web page). When you look up a word, the app will consider it as an failed answer for an expression-recall question. 

## II. Run project
Build:
```
mvn clean install -DskipTests
```

Deploy:
```
cp target/language-topic.war {TOMCAT_HOME}/webapp/
```

Neo4j server start:
```
```

## III. Security
The current main target is features implementation, the security will be improve later.
Now it's using Username - password authentication. The API only uses session to track authenticated user.
In the future, it should use OAuth2.

## IV. Database

## V. Common code which can be reused for other projects
### 1. Neo4j
#### Relationship replacement
Spring Data Neo4j doesn't support any built-in way for updating (replacing) relationships.
So in this project, we build a common method in `OgmRepository` for doing that with 1 depth query.
In the future, the annotation `@CascadeRelationship` will be there for supporting multi-depth updating (not implemented yet).

#### Query
By default, Spring Data Neo4j support querying by depth. But there's some relationships we don't want to load.
So we will use ``@EagerRelationship`` for eager loading children nodes. (not implemented yet)

### 2. Bean Validator


## VI. References
Some good resource related to language:
http://blog.mashape.com/list-of-25-natural-language-processing-apis/

## Glossary
- construct:
- create: construct and saved into DB
- initiate: starting a service, including constructing some objects and creating other objects.

- compositions: the entities which form the parent entity. The compositions cannot exist without the parent. E.g. Expression is composed of SenseGroups, Senses, Examples. 
- related entities: the entities which are related to another entity. E.g. Topic is related to Categories, Expressions, SenseGroups, Senses, Examples, Questions, AnswerResult

