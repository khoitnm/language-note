package org.tnmk.ln.migration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.tnmk.common.utils.data.action.ActionLoopByPage;
import org.tnmk.ln.infrastructure.dictionary.oxford.OxfordAudioRepositories;
import org.tnmk.ln.infrastructure.dictionary.oxford.OxfordAudioService;
import org.tnmk.ln.infrastructure.dictionary.oxford.OxfordWordRepositories;
import org.tnmk.ln.infrastructure.dictionary.oxford.OxfordAudio;
import org.tnmk.ln.infrastructure.dictionary.oxford.OxfordService;
import org.tnmk.ln.infrastructure.dictionary.oxford.entity.OxfordWord;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author khoi.tran on 4/28/17.
 * @deprecated Already migrated successfully, don't need to migrate anymore. Just keep it here as a backup file.
 */
@Service
@Deprecated
public class MigrateWordPronunciationToText {
    public static final Logger LOGGER = LoggerFactory.getLogger(MigrateWordPronunciationToText.class);

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

    private void downloadOxfordAudios() {
        LOGGER.debug("Start download");
        ActionLoopByPage<OxfordWord> audioActionLoopByPage = new ActionLoopByPage<OxfordWord>() {
            @Override
            protected List<OxfordWord> executeEachPageData(Pageable pageRequest) {
                List<OxfordWord> oxfordWords = oxfordWordRepositories.findAll(pageRequest).getContent();
                for (OxfordWord oxfordWord : oxfordWords) {
                    oxfordAudioService.downloadAndSaveAudios(oxfordWord);
                }
                List<String> audioWords = oxfordWords.stream().map(oxfordWord -> oxfordWord.getWord()).collect(Collectors.toList());
                LOGGER.debug("Download audio for oxfordWords: {}", audioWords);
                return oxfordWords;
            }
        };
        List<OxfordWord> oxfordWords = audioActionLoopByPage.executeSinceLastItem(357);
        LOGGER.debug("End download {}", oxfordWords.size());
    }

    private void migrateAudioToTTS() {
        LOGGER.debug("Start migration");
        ActionLoopByPage<OxfordAudio> audioActionLoopByPage = new ActionLoopByPage<OxfordAudio>() {
            @Override
            protected List<OxfordAudio> executeEachPageData(Pageable pageRequest) {
                List<OxfordAudio> oxfordAudios = oxfordAudioRepositories.findAll(pageRequest).getContent();
                for (OxfordAudio oxfordAudio : oxfordAudios) {
                    oxfordAudioService.replaceTTSByOxford(oxfordAudio);
                }
                return oxfordAudios;
            }
        };
        List<OxfordAudio> totalOxfordAudios = audioActionLoopByPage.executeSinceLastItem(264);
        LOGGER.debug("End migration {}", totalOxfordAudios.size());
    }

}
