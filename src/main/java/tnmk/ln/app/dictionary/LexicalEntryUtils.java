package tnmk.ln.app.dictionary;

import org.springframework.util.CollectionUtils;
import tnmk.common.util.StringUtil;
import tnmk.ln.app.dictionary.entity.LexicalEntry;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author khoi.tran on 2/28/17.
 */
public class LexicalEntryUtils {
    public static String toText(List<LexicalEntry> lexicalEntries) {
        String text = null;
        if (!CollectionUtils.isEmpty(lexicalEntries)) {
            String[] lexicalEntriesArray = lexicalEntries.stream().map(lexicalEntry -> lexicalEntry.getText()).collect(Collectors.toList()).toArray(new String[0]);
            text = StringUtil.joinNotBlankStrings(" ", lexicalEntriesArray);
        }
        return text;
    }
}