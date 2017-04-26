MATCH (q:%s) WHERE q.fromExpressionId IN {p1}
OPTIONAL MATCH (epr:ExpressionPracticeResult)<-[:PRACTICE]-(eprowner:User) WHERE id(eprowner)={p0} AND epr.expressionId = q.fromExpressionId
OPTIONAL MATCH (q)<-[:RESULT_OF_QUESTION]-(qpr:QuestionPracticeResult)<-[:PRACTICE]-(eprowner:User) WHERE id(eprowner)={p0}
WITH q
    ,CASE epr.sumLatestAnswerPoint WHEN null THEN 0 ELSE epr.sumLatestAnswerPoint END AS epr_answer_points
    ,CASE qpr.sumLatestAnswerPoint WHEN null THEN 0 ELSE qpr.sumLatestAnswerPoint END AS qpr_answer_points
ORDER BY epr_answer_points, q.fromExpressionId, qpr_answer_points ASC
RETURN id(q)