package tnmk.ln.app.note;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import tnmk.ln.app.common.entity.UriPrefixConstants;
import tnmk.ln.app.note.entity.Note;

/**
 * @author khoi.tran on 3/4/17.
 */
@RestController
public class NoteResource {
    @RequestMapping(value = UriPrefixConstants.API_PREFIX + "/topics/construct", method = RequestMethod.GET)
    public Note construct() {
        return NoteFactory.constructSchema();
    }
}
