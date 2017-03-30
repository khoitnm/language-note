MATCH (q:%s)
MATCH (q)-[:FROM_EXPRESSION]->(exp:Expression)<-[:HAS_EXPRESSION]-(n:Topic) WHERE id(n) IN {p1}
OPTIONAL MATCH (exp)<-[:RESULT_OF_EXPRESSION]-(epr:ExpressionPracticeResult)<-[:PRACTICE]-(eprowner:User) WHERE id(eprowner)={p0}
OPTIONAL MATCH (q)<-[:RESULT_OF_QUESTION]-(qpr:QuestionPracticeResult)<-[:PRACTICE]-(eprowner:User) WHERE id(eprowner)={p0}
WITH q
    ,CASE epr.latestAnswerPoints WHEN null THEN 0 END AS epr_answer_points
    ,CASE qpr.latestAnswerPoints WHEN null THEN 0 END AS qpr_answer_points
ORDER BY epr_answer_points, exp.id, qpr_answer_points ASC
RETURN q.id