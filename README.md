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
### MongoDB
#### Start MongoDB:

```
mongod --port 27017
```

Create the admin user for MongoDB with following command line:

```
mongo languagenote

db.createUser(
{ user: "languagenoteadmin",
    pwd: "password",
	roles:
       [
         { role: "readWrite", db: "config" },
         "dbAdmin", "dbOwner","userAdmin"
       ]
});
```

#### Full-text Search Index
```
db.expressions.createIndex( { text: "text" } )
```

#### Backup

```
mongodump --host localhost --port 27117 --username languagenoteadmin --password password --db languagenote --archive=languagenotearchive
```

#### Restore

```
mongorestore --host localhost:27017 --objcheck --username languagenoteadmin --password password --db languagenote --archive=languagenotearchive
```

### Neo4j
Neo4j server start:
```
```

### Application
Build:
```
mvn clean install -DskipTests
```

Deploy:
```
cp target/language-topic.war {TOMCAT_HOME}/webapp/
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

#### Relationships order
The order of relationships are not fixed, they will be changed when we update/change items in the relationships list.
http://stackoverflow.com/questions/33263822/cypher-store-order-of-node-relationships-of-the-same-label-when-i-create

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
- relations / related entities: the entities which are related to another entity. E.g. Topic is related to Categories, Expressions, SenseGroups, Senses, Examples, Questions, AnswerResult

## LessonLearn
### UI/UX: 
Only saving when clicking the save button. 
Don't save when loosing focus because it will make the code is very complicated and need so much manual handling methods. 
For example: saving when losing focus on expressions, on senseGroups, on senses, on examples...

### Difficulty with Neo4j:
1) Don't remove old data when remove that item from the list.

2) In a composite entity, every subordinated entities will be denormalized into many children entities. It has some disadvantages:
a. Composite: means that you can update, save, delete a child entity. When delete the super entity, must remove children entities. When updating the super, we also may need to update the children.
Note: in case of needing to query (high performance) on a child criteria, Neo4j is still a good choice, should reconsider very carefully if you want to use MongoDB.
 
b. Disadvantages:
+ It's extremely difficult to handle the order of children. For example:
At first, parent.children = [1, 2, 3]. Then you update parent.children = [3, 1, 2]. However, when you select data, it still shown parent.chidlren is unchanged [1, 2, 3]
+ It's very complicated to query when there is a cycling reference (problem with Spring OGM). For example:
Word[1].synonyms = [2 ,3] -> query all detail of word[1], it will cause stack overflow:
Word[1] -> load synonyms [2, 3] -> load word[2] -> synonyms[1, 3] -> load word[1] -> synonyms[2, 3]
+ The aggregation query is slower compare to the query of MongoDB.
+ You have to handle cascading CRUD.
 
### Difficulty with MongoDB
+ Indexing and Sort
+ Question: what do we need to sort?
 
## References
https://quizlet.com/11272763/barrons-essential-words-for-the-ielts-logging-flash-cards/

# Backup Database:
## MongoDB

