package tnmk.ln.infrastructure.tts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tnmk.common.util.IterableUtil;
import tnmk.common.util.StringUtil;
import tnmk.ln.infrastructure.dictionary.oxford.OxfordAudio;
import tnmk.ln.infrastructure.dictionary.oxford.OxfordAudioRepositories;
import tnmk.ln.infrastructure.tts.cache.TtsItem;
import tnmk.ln.infrastructure.tts.cache.TtsItemService;
import tnmk.ln.infrastructure.tts.voicerss.VoiceRssService;

import java.util.List;

/**
 * http://www.voicerss.org/
 * http://stackoverflow.com/questions/30881128/how-to-play-a-sound-in-angularjs
 *
 * @author khoi.tran on 2/1/17.
 */
@Service
public class TextToSpeechService {
    @Autowired
    private TtsItemService ttsItemService;

    @Autowired
    private VoiceRssService voiceRssService;

    @Autowired
    private OxfordAudioRepositories oxfordAudioRepositories;

    public TtsItem toSpeech(String locale, String originalText) {
        String cleanText = cleanupText(originalText);
        TtsItem ttsItem = ttsItemService.findText(locale, cleanText);
        if (ttsItem != null) return ttsItem;
        byte[] mp3Data = toSpeechBytes(locale, cleanText);
        ttsItem = ttsItemService.putText(locale, cleanText, mp3Data);
        return ttsItem;
    }

    public String cleanupText(String originalText) {
        return originalText.trim().toLowerCase();
    }

    //TODO, if originalText is only one word, lookup in the Oxford Audio (from Oxford Dictionary)
    private byte[] toSpeechBytes(String locale, String cleanText) {
        String[] words = StringUtil.toWords(cleanText);
        byte[] result = null;
        if (words.length == 1) {
            String[] localParts = locale.split("-");
            String language = localParts[0].toLowerCase();
            List<OxfordAudio> oxfordAudios = oxfordAudioRepositories.findByLanguageAndWord(language, cleanText);
            OxfordAudio oxfordAudio = IterableUtil.getFirst(oxfordAudios);
            if (oxfordAudio != null) {
                result = oxfordAudio.getFileItem().getBytesContent();
            }
        }
        if (result == null) {
            result = voiceRssService.toSpeech(locale, cleanText);
        }
        return result;
    }
}
