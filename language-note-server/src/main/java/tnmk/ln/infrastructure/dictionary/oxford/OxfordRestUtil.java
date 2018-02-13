package tnmk.ln.infrastructure.dictionary.oxford;

import tnmk.common.exception.UnexpectedException;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * @author khoi.tran on 4/29/17.
 */
public class OxfordRestUtil {

    public static URI createUri(String uriString) {
        try {
            return new URI(uriString);
        } catch (URISyntaxException e) {
            throw new UnexpectedException(e.getMessage(), e);
        }
    }
}
