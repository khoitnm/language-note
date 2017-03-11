package tnmk.ln.infrastructure.nlp;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import tnmk.ln.infrastructure.nlp.standfordnlp.StandfordNLPService;

import java.util.ArrayList;
import java.util.List;

/**
 * http://text-processing.com/docs/stem.html
 * Solution:
 * We can use either PorterStemmer https://tartarus.org/martin/PorterStemmer/java.txt
 * or Lucene Analyzers https://mvnrepository.com/artifact/org.apache.lucene/lucene-analyzers
 *
 * @author khoi.tran on 2/9/17.
 */
@Service
public class LemmaFindingService {
    public static final Logger LOGGER = LoggerFactory.getLogger(LemmaFindingService.class);

    private StandfordNLPService standfordNLPService = new StandfordNLPService();

    public List<LemmaSpan> findByLemma(String language, String findingExpression, String text) {
        List<LemmaSpan> result = new ArrayList<>();
        //TODO an expression can be a word, or a phrasal verb (verb + preposition)...
        String findingLemma = standfordNLPService.lemma(language, findingExpression);
        if (StringUtils.isBlank(findingLemma)) {
            return result;
        }
        LOGGER.debug("Finding lemma: " + findingLemma);

        List<LemmaSpan> lemmaSpans = standfordNLPService.analyzeLemmas(language, text);
        LOGGER.debug("Analyze lemmas: " + lemmaSpans.toString());
        for (LemmaSpan lemmaSpan : lemmaSpans) {
            if (findingLemma.equals(lemmaSpan.getLemma())) {
                result.add(lemmaSpan);
            }
        }
        return result;
    }

}
