MATCH (qpr:QuestionPracticeResult)--(u:User) WHERE id(u)={p0}
MATCH (qpr)--(q:Question) WHERE id(q) = {p1}
RETURN qpr