package tnmk.ln.migration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import tnmk.common.action.ActionLoopByPage;
import tnmk.ln.infrastructure.dictionary.oxford.OxfordAudio;
import tnmk.ln.infrastructure.dictionary.oxford.OxfordAudioRepositories;
import tnmk.ln.infrastructure.tts.cache.TtsItemService;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * @author khoi.tran on 4/28/17.
 */
@Service
public class MigrateWordPronunciationToText {
    public static final Logger LOGGER = LoggerFactory.getLogger(MigrateWordPronunciationToText.class);

    @Autowired
    private OxfordAudioRepositories oxfordAudioRepositories;

    @Autowired
    private TtsItemService ttsItemService;

    @PostConstruct
    public void migrateAudio() {
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
