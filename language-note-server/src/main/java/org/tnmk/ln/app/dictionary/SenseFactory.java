package org.tnmk.ln.app.dictionary;

import org.tnmk.ln.app.dictionary.entity.Sense;
import org.tnmk.ln.app.digitalasset.entity.DigitalAssetFactory;

import java.util.Arrays;

/**
 * @author khoi.tran on 3/4/17.
 */
public class SenseFactory {
    public static Sense constructSchema() {
        Sense sense = new Sense();
        sense.setExamples(Arrays.asList(ExampleFactory.constructSchema()));
        sense.setAudios(Arrays.asList(DigitalAssetFactory.constructSchema()));
        sense.setPhotos(Arrays.asList(DigitalAssetFactory.constructSchema()));
        sense.setVideos(Arrays.asList(DigitalAssetFactory.constructSchema()));
        return sense;
    }
}
