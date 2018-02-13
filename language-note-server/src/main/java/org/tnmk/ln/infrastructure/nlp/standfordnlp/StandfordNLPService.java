package org.tnmk.ln.infrastructure.nlp.standfordnlp;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.process.Morphology;
import edu.stanford.nlp.util.CoreMap;
import org.tnmk.ln.infrastructure.nlp.LemmaSpan;

import java.util.ArrayList;
import java.util.List;

/**
 * @author khoi.tran on 3/10/17.
 */
public class StandfordNLPService {

    public String lemma(String language, String inflectionWord) {
        //TODO need to config the language
        Morphology morphology = new Morphology();
        return morphology.stem(inflectionWord);
    }

    public List<LemmaSpan> analyzeLemmas(String language, String text) {
        List<LemmaSpan> lemmaSpans = new ArrayList<>();
        StanfordCoreNLP pipeline = StandfordNLPLoader.constructStandfordNLP(language);
        Annotation document = new Annotation(text);
        pipeline.annotate(document);
        List<CoreMap> sentences = document.get(CoreAnnotations.SentencesAnnotation.class);

        for (CoreMap sentence : sentences) {
            for (CoreLabel token : sentence.get(CoreAnnotations.TokensAnnotation.class)) {
                LemmaSpan lemmaSpan = new LemmaSpan();
                lemmaSpans.add(lemmaSpan);

                lemmaSpan.setStart(token.beginPosition());
                lemmaSpan.setEnd(token.endPosition());
                lemmaSpan.setOriginalWord(token.originalText());
                lemmaSpan.setLemma(token.lemma());
            }
        }
        return lemmaSpans;
    }
}
