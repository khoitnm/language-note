MATCH (q:%s)
WHERE id(q) IN {p1}
OPTIONAL MATCH (exp)<-[:RESULT_OF_EXPRESSION]-(epr:ExpressionPracticeResult)<-[:PRACTICE]-(eprowner:User) WHERE id(eprowner)={p0}
OPTIONAL MATCH (q)<-[:RESULT_OF_QUESTION]-(qpr:QuestionPracticeResult)<-[:PRACTICE]-(eprowner:User) WHERE id(eprowner)={p0}
WITH q
    ,CASE epr.sumLatestAnswerPoint WHEN null THEN 0 END AS epr_answer_points
    ,CASE qpr.sumLatestAnswerPoint WHEN null THEN 0 END AS qpr_answer_points
ORDER BY epr_answer_points, q.fromExpressionId, qpr_answer_points ASC
RETURN id(q)

