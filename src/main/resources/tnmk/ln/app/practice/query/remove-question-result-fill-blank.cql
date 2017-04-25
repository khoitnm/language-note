MATCH (n:QuestionPracticeResult)--(q:QuestionFillBlank) detach delete n;
MATCH (n:QuestionPart)--(q:QuestionFillBlank) detach delete n;
MATCH (n:QuestionFillBlank) detach delete n;
//Remove all documents in MongoDB collection 'QuestionParts'