language-note
==============
## I. Run project
### MongoDB
#### Start MongoDB:

```
mongod --port 27017
```
Note: in Windows, you may have to create the data folder for MongoDB manually in following path:
```
C:\data\db
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
db.Expressions.createIndex( { text: "text" } )
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
Download Neo4j
Create a database with any arbitrary name.
Login with default account: neo4j / neo4j
Change password to: password
```

### NodeJS
You should install NodeJs first, then also install bower
```
npm install -g bower
```

### Application
Build:
```
mvn clean install -DskipTests
```

Deploy (please set 'dev' to the project profile ):
```
cp target/language-topic.war {TOMCAT_HOME}/webapp/ 
```

Run with embbed Tomcat:
```
mvn spring-boot:run -Drun.profiles=dev
```

After deploying application, open the web on Browser with URL:
```
localhost:8080/language-note/
```
The default account is:
```
superuser / superuser
```

## II. Security
The current main target is features implementation, the security will be improve later.
Now it's using Username - password authentication. The API only uses session to track authenticated user.
In the future, it should use OAuth2.

## III. Database

## IV. Common code which can be reused for other projects
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


## V. References
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