package tnmk.ln.app.dictionary;

import tnmk.ln.app.dictionary.entity.Sense;
import tnmk.ln.app.digitalasset.entity.DigitalAssetFactory;

import java.util.Arrays;

/**
 * @author khoi.tran on 3/4/17.
 */
public class SenseFactory {
    public static Sense constructSense() {
        Sense sense = new Sense();
        sense.setExamples(Arrays.asList(ExampleFactory.constructExample()));
        sense.setAudios(Arrays.asList(DigitalAssetFactory.construct()));
        sense.setPhotos(Arrays.asList(DigitalAssetFactory.construct()));
        sense.setVideos(Arrays.asList(DigitalAssetFactory.construct()));
        return sense;
    }
}
