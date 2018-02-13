package org.tnmk.ln.infrastructure.nlp.standfordnlp;

import edu.stanford.nlp.pipeline.StanfordCoreNLP;

import java.util.Properties;

/**
 * @author khoi.tran on 3/10/17.
 */
public class StandfordNLPLoader {

    /**
     * @param language
     */
    //TODO should cache
    public static StanfordCoreNLP constructStandfordNLP(String language) {
        Properties props = new Properties();
        props.put("annotators", "tokenize, ssplit, pos, lemma");
//        props.put("annotators", "tokenize,ssplit,pos,lemma,parse,natlog");
//        props.put("ssplit.isOneSentence", "true");
//        props.put("parse.model", "edu/stanford/nlp/models/srparser/englishSR.ser.gz");
        props.put("tokenize.language", language);
        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
        return pipeline;
    }

}
