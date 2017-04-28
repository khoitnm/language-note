package tnmk.ln.migration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import tnmk.common.action.ActionLoopByPage;
import tnmk.ln.infrastructure.dictionary.oxford.OxfordAudio;
import tnmk.ln.infrastructure.dictionary.oxford.OxfordAudioRepositories;
import tnmk.ln.infrastructure.dictionary.oxford.OxfordService;
import tnmk.ln.infrastructure.dictionary.oxford.OxfordWordRepositories;
import tnmk.ln.infrastructure.dictionary.oxford.entity.OxfordWord;
import tnmk.ln.infrastructure.tts.cache.TtsItemService;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author khoi.tran on 4/28/17.
 * @deprecated Already migrated successfully, don't need to migrate anymore. Just keep it here as a backup file.
 */
//@Service
@Deprecated
public class MigrateWordPronunciationToText {
    public static final Logger LOGGER = LoggerFactory.getLogger(MigrateWordPronunciationToText.class);

    @Autowired
    private OxfordAudioRepositories oxfordAudioRepositories;

    @Autowired
    private OxfordWordRepositories oxfordWordRepositories;

    @Autowired
    private TtsItemService ttsItemService;

    @Autowired
    private OxfordService oxfordService;

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
                    oxfordService.downloadAndSaveAudios(oxfordWord);
                }
                List<String> audioWords = oxfordWords.stream().map(oxfordWord -> oxfordWord.getWord()).collect(Collectors.toList());
                LOGGER.debug("Download audio for oxfordWords: {}", audioWords);
                return oxfordWords;
            }
        };
        List<OxfordWord> oxfordWords = audioActionLoopByPage.executeAllPages(20);
        LOGGER.debug("End download {}", oxfordWords.size());
    }

    private void migrateAudioToTTS() {
        LOGGER.debug("Start migration");
        ActionLoopByPage<OxfordAudio> audioActionLoopByPage = new ActionLoopByPage<OxfordAudio>() {
            @Override
            protected List<OxfordAudio> executeEachPageData(Pageable pageRequest) {
                List<OxfordAudio> oxfordAudios = oxfordAudioRepositories.findAll(pageRequest).getContent();
                for (OxfordAudio oxfordAudio : oxfordAudios) {
                    String ttsLocale;
                    String oxfordWordLanguage = oxfordAudio.getLanguage();
                    if (oxfordWordLanguage.equalsIgnoreCase("en")) {
                        ttsLocale = "en-us";
                    } else {
                        continue;
                    }
                    LOGGER.debug("Replace tts \nLanguage: {}, Locale: {}, text: {}", oxfordWordLanguage, ttsLocale, oxfordAudio.getWord());
                    ttsItemService.putText(ttsLocale, oxfordAudio.getWord(), oxfordAudio.getFileItem().getBytesContent());
                }
                return oxfordAudios;
            }
        };
        List<OxfordAudio> totalOxfordAudios = audioActionLoopByPage.executeAllPages(50);
        LOGGER.debug("End migration {}", totalOxfordAudios.size());
    }
}
