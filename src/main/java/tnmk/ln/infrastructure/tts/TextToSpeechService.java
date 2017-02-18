package tnmk.ln.infrastructure.tts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tnmk.ln.infrastructure.tts.cache.TtsItem;
import tnmk.ln.infrastructure.tts.cache.TtsItemService;
import tnmk.ln.infrastructure.tts.voicerss.VoiceRssService;

/**
 * http://www.voicerss.org/
 * http://stackoverflow.com/questions/30881128/how-to-play-a-sound-in-angularjs
 *
 * @author khoi.tran on 2/1/17.
 */
@Service
public class TextToSpeechService {
    @Autowired
    private TtsItemService ttsItemCacheService;

    @Autowired
    private VoiceRssService voiceRssService;

    public TtsItem toSpeech(String locale, String originalText) {
        TtsItem ttsItem = ttsItemCacheService.findText(locale, originalText);
        if (ttsItem != null) return ttsItem;
        byte[] mp3Data = toSpeechBytes(locale, originalText);
        ttsItem = ttsItemCacheService.putText(locale, originalText, mp3Data);
        return ttsItem;
    }

    private byte[] toSpeechBytes(String locale, String originalText) {
        return voiceRssService.toSpeech(locale, originalText);
    }
}
