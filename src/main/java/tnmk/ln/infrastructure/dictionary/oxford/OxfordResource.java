package tnmk.ln.infrastructure.dictionary.oxford;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import tnmk.ln.app.common.entity.UriPrefixConstants;
import tnmk.ln.app.vocabulary.entity.Meaning;
import tnmk.ln.infrastructure.dictionary.oxford.entity.OxfordWord;

import javax.validation.Valid;
import java.util.List;

/**
 * @author khoi.tran on 2/24/17.
 */
@RestController
public class OxfordResource {
    @Autowired
    private OxfordService oxfordService;

    @RequestMapping(value = UriPrefixConstants.API_PREFIX + "/dictionary/words", method = RequestMethod.POST)
    public OxfordWord lookUp(@RequestBody ExpressionLookUpRequest expressionLookUpRequest) {
        return oxfordService.lookUpDefinition(expressionLookUpRequest.getSrcLanguage(), expressionLookUpRequest.getSourceText());
    }

    @RequestMapping(value = UriPrefixConstants.API_PREFIX + "/dictionary/words/meanings", method = RequestMethod.POST)
    public List<Meaning> lookUpMeanings(@RequestBody @Valid ExpressionLookUpRequest expressionLookUpRequest) {
        OxfordWord oxfordWord = lookUp(expressionLookUpRequest);
        List<Meaning> meanings = OxfordMapper.toMeanings(oxfordWord);
        return meanings;
    }

    public static class ExpressionLookUpRequest {
        @NotBlank
        private String sourceText;
        @NotBlank
        private String srcLanguage = "en";
        @NotBlank
        private String destLanguage = "en";

        public String getSourceText() {
            return sourceText;
        }

        public void setSourceText(String sourceText) {
            this.sourceText = sourceText;
        }

        public String getSrcLanguage() {
            return srcLanguage;
        }

        public void setSrcLanguage(String srcLanguage) {
            this.srcLanguage = srcLanguage;
        }

        public String getDestLanguage() {
            return destLanguage;
        }
    }
}
