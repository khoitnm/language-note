MATCH (n:QuestionPracticeResult)--(q:QuestionFillBlank) detach delete n;
MATCH (n:QuestionPart)--(q:QuestionFillBlank) detach delete n;
MATCH (n:QuestionFillBlank) detach delete n;
//Remove all documents in MongoDB collection 'QuestionParts'


/*MATCH (q:QuestionExpressionRecall) WHERE q.fromExpressionId IN ['58fce0be146e54a1f8617bc8','58fce072146e54a1f8617b7e',  '58fce145146e54a1f8617bea']
 OPTIONAL MATCH (epr:ExpressionPracticeResult)<-[:PRACTICE]-(eprowner:User) WHERE id(eprowner)=32 AND epr.expressionId = q.fromExpressionId
 WITH q, epr
 ,CASE epr.sumLatestAnswerPoint WHEN null THEN 0 END AS epr_answer_points
 ORDER BY q.fromExpressionId DESC
 RETURN id(q), q.fromExpressionId, epr
 */
//-------------
// Show result with expression
////////////////////////////



/*

MATCH (q:QuestionExpressionRecall) WHERE q.fromExpressionId IN ['58fce072146e54a1f8617b7e', '58fce0be146e54a1f8617bc8', '58fce145146e54a1f8617bea', '58fd9b52146e54a1f8617c10', '58fd9d26146e54a1f8617c6f', '58fd9dd1146e54a1f8617c90', '58fd9e94146e54a1f8617cb2', '58fda417146e54a1f8617cbe', '58fe2522146e542421edc5c1', '58fe2840146e542421edc5ec']
 OPTIONAL MATCH (epr:ExpressionPracticeResult)<-[:PRACTICE]-(eprowner:User) WHERE id(eprowner)=32 AND epr.expressionId = q.fromExpressionId
 OPTIONAL MATCH (q)<-[:RESULT_OF_QUESTION]-(qpr:QuestionPracticeResult)<-[:PRACTICE]-(eprowner:User) WHERE id(eprowner)=32
 WITH q
     ,CASE epr.sumLatestAnswerPoint WHEN null THEN 0 ELSE epr.sumLatestAnswerPoint END AS epr_answer_points
     ,CASE qpr.sumLatestAnswerPoint WHEN null THEN 0 ELSE qpr.sumLatestAnswerPoint END AS qpr_answer_points
 ORDER BY epr_answer_points, q.fromExpressionId, qpr_answer_points ASC
 RETURN id(q), q.fromExpressionId, epr_answer_points, qpr_answer_points
 */
