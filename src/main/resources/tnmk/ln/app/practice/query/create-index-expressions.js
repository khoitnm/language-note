//The field Expression.text will be full-text indexed: https://docs.mongodb.com/manual/core/index-text/
db.Expression.createIndex({text: "text"});

//Search Expression by full text search:
db.Expression.find({$text: {$search: "some keyword"}, "locale.language": "en"});