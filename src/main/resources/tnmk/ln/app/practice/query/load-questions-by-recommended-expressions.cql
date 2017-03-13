MATCH (q:%s)
MATCH (q)-[:FROM_EXPRESSION]-(exp:Expression)-[:HAS_EXPRESSION]-(n:Note) WHERE id(n) IN {p1}
OPTIONAL MATCH (exp)-[:RESULT_OF_EXPRESSION]-(epr:ExpressionPracticeResult)--(eprowner:User) WHERE id(eprowner)={p0}
OPTIONAL MATCH (exp)-[:RESULT_OF_QUESTION]-(qpr:QuestionPracticeResult)--(qprowner:User) WHERE id(qprowner)={p0}
OPTIONAL MATCH qpath=(q)-[:RESULT_OF_EXPRESSION | :RESULT_OF_QUESTION | :FROM_EXPRESSION | :FROM_SENSE | :FROM_EXAMPLE*0..2]-()

WITH qpath
    , exp
    ,CASE epr.latestAnswerPoints WHEN null THEN 0 END AS epr_answer_points
    ,CASE qpr.latestAnswerPoints WHEN null THEN 0 END AS qpr_answer_points
ORDER BY epr_answer_points, exp.id, qpr_answer_points ASC
RETURN qpath

/*
MATCH (q:%s)
MATCH (q)-[:FROM_EXPRESSION]-(exp:Expression)-[:HAS_EXPRESSION]-(n:Note) WHERE id(n) IN {p1} 
OPTIONAL MATCH (exp)-[:RESULT_OF_EXPRESSION]-(epr:ExpressionPracticeResult)
OPTIONAL MATCH (epr)--(o:User) WHERE id(o)={p0}

OPTIONAL MATCH qpath=(q)-[:FROM_EXPRESSION]-(exp:Expression)-[:RELATE_TO | :HAS_VIDEOS | :HAS_LEXICAL_ENTRIES | :HAS_SENSES | :HAS_SENSE_GROUPS | :RELATE_TO_TOPIC | :NOTE_IN_LOCALE | :HAS_EXAMPLE | :OWN_EXPRESSION | :EXPRESSION_IN_LOCALE | :HAS_MAIN_IMAGE | :HAS_AUDIOS | :HAS_MAIN_AUDIO | :LIKE | :IS_SYNONYMOUS_WITH | :IS_ANTONYMOUS_WITH | :HAS_IMAGES | :HAS_EXPRESSION | :OWN_TOPIC | :HAS_PHOTOS | :OWN_NOTE*0..4]-(x)
WITH qpath, exp, CASE epr.latestAnswerPoints WHEN null THEN 0 END AS epr_answer_points
ORDER BY epr_answer_points, exp.id ASC
RETURN qpath
*/
/*
MATCH (q:QuestionExpressionRecall)
MATCH (q)-[:FROM_EXPRESSION]-(exp:Expression)-[:HAS_EXPRESSION]-(n:Note) WHERE id(n) IN [1070]
OPTIONAL MATCH (exp)-[:RESULT_OF_EXPRESSION]-(epr:ExpressionPracticeResult)
OPTIONAL MATCH (epr)--(u:User) WHERE id(u)=1070

OPTIONAL MATCH qpath=(q)-[:FROM_EXPRESSION]-(exp:Expression)-[:RELATE_TO | :HAS_VIDEOS | :HAS_LEXICAL_ENTRIES | :HAS_SENSES | :HAS_SENSE_GROUPS | :RELATE_TO_TOPIC | :NOTE_IN_LOCALE | :HAS_EXAMPLE | :OWN_EXPRESSION | :EXPRESSION_IN_LOCALE | :HAS_MAIN_IMAGE | :HAS_AUDIOS | :HAS_MAIN_AUDIO | :LIKE | :IS_SYNONYMOUS_WITH | :IS_ANTONYMOUS_WITH | :HAS_IMAGES | :HAS_EXPRESSION | :OWN_TOPIC | :HAS_PHOTOS | :OWN_NOTE*0..4]-(x)
WITH qpath, CASE epr.latestAnswerPoints WHEN null THEN 0 END AS epr_answer_points
ORDER BY epr_answer_points ASC
RETURN qpath
*/
