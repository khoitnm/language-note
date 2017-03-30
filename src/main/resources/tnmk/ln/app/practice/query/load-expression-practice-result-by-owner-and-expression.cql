MATCH (epr:ExpressionPracticeResult)--(u:User) WHERE id(u)={p0}
MATCH (epr)--(e:Expression) WHERE id(e) = {p1}
RETURN epr