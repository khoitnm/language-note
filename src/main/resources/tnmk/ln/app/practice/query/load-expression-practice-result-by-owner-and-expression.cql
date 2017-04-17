MATCH (epr:ExpressionPracticeResult)--(u:User) WHERE id(u)={p0} AND epr.expressionId = {p1}
RETURN epr