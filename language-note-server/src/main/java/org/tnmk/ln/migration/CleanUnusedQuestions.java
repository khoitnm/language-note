package org.tnmk.ln.migration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tnmk.ln.infrastructure.dictionary.oxford.OxfordAudioRepositories;
import org.tnmk.ln.infrastructure.dictionary.oxford.OxfordAudioService;
import org.tnmk.ln.infrastructure.dictionary.oxford.OxfordWordRepositories;
import org.tnmk.ln.infrastructure.dictionary.oxford.OxfordService;

import javax.annotation.PostConstruct;

/**
 * @author khoi.tran on 4/28/17.
 * @deprecated Not finished yet.
 */
//@Service
@Deprecated
public class CleanUnusedQuestions {
    public static final Logger LOGGER = LoggerFactory.getLogger(CleanUnusedQuestions.class);

    @Autowired
    private OxfordAudioRepositories oxfordAudioRepositories;

    @Autowired
    private OxfordWordRepositories oxfordWordRepositories;

    @Autowired
    private OxfordService oxfordService;

    @Autowired
    private OxfordAudioService oxfordAudioService;

    @PostConstruct
    public void migrateAudio() {
//        downloadOxfordAudios();
//        migrateAudioToTTS();
    }

}
