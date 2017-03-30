MATCH (q:Question)
MATCH path=(q)-[:FROM_EXPRESSION]->(exp:Expression)<-[:HAS_EXPRESSION]-(n:Topic) WHERE id(q) IN {p1}
RETURN path