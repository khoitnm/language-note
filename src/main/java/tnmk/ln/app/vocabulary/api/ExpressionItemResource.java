//package tnmk.ln.app.vocabulary.api;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//import tnmk.common.util.EnumUtil;
//import tnmk.ln.app.common.entity.UriPrefixConstants;
//import tnmk.ln.app.vocabulary.entity.ExpressionItem;
//import tnmk.ln.app.vocabulary.entity.ExpressionType;
//import tnmk.ln.app.vocabulary.entity.FillingQuestion;
//import tnmk.ln.app.vocabulary.entity.Meaning;
//import tnmk.ln.app.vocabulary.entity.PhrasalVerb;
//import tnmk.ln.app.vocabulary.entity.Word;
//import tnmk.ln.app.vocabulary.model.ExpressionFilter;
//import tnmk.ln.app.vocabulary.model.ExpressionItemAnswer;
//import tnmk.ln.app.vocabulary.repository.ExpressionItemRepository;
//import tnmk.ln.app.vocabulary.service.ExpressionItemFilterService;
//import tnmk.ln.app.vocabulary.service.ExpressionUserPointService;
//import tnmk.ln.infrastructure.security.helper.SecurityContextHelper;
//import tnmk.ln.infrastructure.security.neo4j.entity.User;
//
//import java.util.Arrays;
//import java.util.List;
//
//@RestController
//public class ExpressionItemResource {
//    @Autowired
//    private ExpressionItemFilterService expressionItemFilterService;
//    @Autowired
//    private ExpressionUserPointService expressionUserPointService;
//
//    @Autowired
//    private ExpressionItemRepository expressionItemRepository;
//
//    @RequestMapping(value = UriPrefixConstants.API_PREFIX + "/expression-items/initiation", method = RequestMethod.GET)
//    public ExpressionItem initExpressionItem(@RequestParam(value = "type", defaultValue = "word") String expressionTypeString) {
//        ExpressionItem expressionItem = new ExpressionItem();
//        ExpressionType expressionType = EnumUtil.validateExistEnum(ExpressionType.class, "stringValue", expressionTypeString);
//        expressionItem.setType(expressionType);
//        expressionItem.setMeanings(Arrays.asList(initMeaning()));
//        if (expressionType.equals(ExpressionType.PHRASAL_VERB)) {
//            PhrasalVerb phrasalVerb = new PhrasalVerb();
//            List<Word> words = Arrays.asList(new Word(0, "verb", ""), new Word(1, "preposition", ""), new Word(2, "noun", ""));
//            phrasalVerb.setWords(words);
//            expressionItem.setPhrasalVerbDetail(phrasalVerb);
//        }
//        return expressionItem;
//    }
//
//    @RequestMapping(value = UriPrefixConstants.API_PREFIX + "/expression-items/meanings/initiation", method = RequestMethod.GET)
//    public Meaning initMeaning() {
//        Meaning meaning = new Meaning();
//        FillingQuestion fillingQuestion = new FillingQuestion();
//        fillingQuestion.setWords(Arrays.asList(new Word(0, "text", ""), new Word(1, "verb", ""), new Word(2, "noun", ""), new Word(3, "preposition", ""), new Word(4, "text", "")));
//        List<FillingQuestion> fillingQuestions = Arrays.asList(fillingQuestion);
//        meaning.setFillingQuestions(fillingQuestions);
//        return meaning;
//    }
//
//    @RequestMapping(value = UriPrefixConstants.API_PREFIX + "/expression-items", method = RequestMethod.GET)
//    public List<ExpressionItem> loadExpressionItems() {
//        return expressionItemRepository.findAll();
//    }
//
//    @RequestMapping(value = UriPrefixConstants.API_PREFIX + "/expression-items/answers", method = RequestMethod.POST)
//    public List<? extends ExpressionItem> updateAnswers(@RequestBody List<ExpressionItemAnswer> expressionItemAnswers) {
//        User user = SecurityContextHelper.validateExistAuthenticatedUser();
//        return expressionUserPointService.updateAnswers(user.getId(), expressionItemAnswers);
//    }
//
//    @RequestMapping(value = UriPrefixConstants.API_PREFIX + "/expression-items/favourite", method = RequestMethod.POST)
//    public int updateFavourite(@RequestBody ExpressionUserPointService.ExpressionFavouriteRequest expressionFavouriteRequest) {
//        User user = SecurityContextHelper.validateExistAuthenticatedUser();
//        return expressionUserPointService.updateFavourite(user.getId(), expressionFavouriteRequest);
//    }
//
//    @RequestMapping(value = UriPrefixConstants.API_PREFIX + "/expression-items/filter", method = RequestMethod.POST)
//    public List<ExpressionItem> filterExpressionItems(@RequestBody ExpressionFilter expressionFilter) {
//        User user = SecurityContextHelper.validateExistAuthenticatedUser();
//        return expressionItemFilterService.filter(user.getId(), expressionFilter);
//    }
//
//}