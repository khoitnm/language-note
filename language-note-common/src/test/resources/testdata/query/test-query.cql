/** This is just a testing query for Neo4j */
MATCH (e:Expression)--(u:User) WHERE id(u)=%s AND e.id=%s RETURN e