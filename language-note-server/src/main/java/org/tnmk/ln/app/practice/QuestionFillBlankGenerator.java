package org.tnmk.ln.app.practice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tnmk.ln.app.practice.entity.question.QuestionPart;
import org.tnmk.ln.app.practice.entity.question.QuestionPartType;
import org.tnmk.ln.infrastructure.nlp.LemmaFindingService;
import org.tnmk.ln.infrastructure.nlp.LemmaSpan;

import java.util.ArrayList;
import java.util.List;

/**
 * @author khoi.tran on 3/11/17.
 */
@Service
public class QuestionFillBlankGenerator {

    @Autowired
    private LemmaFindingService lemmaFindingService;

    public List<QuestionPart> analyzeToQuestionParts(String language, String findingExpression, String text) {
        List<QuestionPart> rs = new ArrayList<>();
        //TODO if finding expression is a phrasal verb, we only need to find the verb and the corresponding preposition.
        List<LemmaSpan> lemmaSpans = lemmaFindingService.findByLemma(language, findingExpression, text);
        int i = 0;
        int previousEndingIndex = 0;
        int lemmaSpansCount = lemmaSpans.size();
        for (LemmaSpan lemmaSpan : lemmaSpans) {
            if (lemmaSpan.getStart() > 0) {
                QuestionPart textPart = new QuestionPart();
                rs.add(textPart);
                textPart.setText(text.substring(previousEndingIndex, lemmaSpan.getStart()));
                textPart.setQuestionPartType(QuestionPartType.TEXT);
            }
            QuestionPart fillBlank = new QuestionPart();
            rs.add(fillBlank);
            fillBlank.setLemma(lemmaSpan.getLemma());
            fillBlank.setText(lemmaSpan.getOriginalWord());
            fillBlank.setQuestionPartType(QuestionPartType.BLANK);
            previousEndingIndex = lemmaSpan.getEnd();
            if (i >= lemmaSpansCount - 1 && lemmaSpan.getEnd() < text.length()) {
                QuestionPart textPart = new QuestionPart();
                rs.add(textPart);
                textPart.setText(text.substring(lemmaSpan.getEnd()));
                textPart.setQuestionPartType(QuestionPartType.TEXT);
            }
            i++;
        }
        return rs;
    }
}
