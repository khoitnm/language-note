package org.tnmk.ln.migration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.tnmk.common.utils.collections.SetUtils;
import org.tnmk.common.utils.data.action.ActionLoopByPage;
import org.tnmk.ln.app.dictionary.ExpressionMapper;
import org.tnmk.ln.app.dictionary.ExpressionRepository;
import org.tnmk.ln.app.dictionary.ExpressionService;
import org.tnmk.ln.app.dictionary.entity.Expression;
import org.tnmk.ln.app.dictionary.entity.LexicalType;
import org.tnmk.ln.app.dictionary.entity.Pronunciation;
import org.tnmk.ln.app.dictionary.entity.SenseGroup;
import org.tnmk.ln.infrastructure.dictionary.oxford.OxfordAudio;
import org.tnmk.ln.infrastructure.dictionary.oxford.OxfordAudioRepositories;
import org.tnmk.ln.infrastructure.dictionary.oxford.OxfordAudioService;
import org.tnmk.ln.infrastructure.dictionary.oxford.OxfordService;
import org.tnmk.ln.infrastructure.dictionary.oxford.OxfordWordRepositories;
import org.tnmk.ln.infrastructure.dictionary.oxford.entity.LexicalEntry;
import org.tnmk.ln.infrastructure.dictionary.oxford.entity.OxfordWord;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author khoi.tran on 4/28/17.
 * @deprecated Already migrated!!!
 */
//@Service
@Deprecated
public class MigrateOxfordPronunciationTextToExpressionPronunciation {
    public static final Logger LOGGER = LoggerFactory.getLogger(MigrateOxfordPronunciationTextToExpressionPronunciation.class);

    @Autowired
    private OxfordWordRepositories oxfordWordRepositories;

    @Autowired
    private OxfordService oxfordService;

    @Autowired
    private ExpressionRepository expressionRepository;

    @PostConstruct
    public void migrate() {
        migratePronunciationTextFromOxfordToExpression();
    }

    private void migratePronunciationTextFromOxfordToExpression() {
        LOGGER.debug("Start download");
        ActionLoopByPage<OxfordWord> audioActionLoopByPage = new ActionLoopByPage<OxfordWord>() {
            @Override
            protected List<OxfordWord> executeEachPageData(Pageable pageRequest) {
                List<OxfordWord> oxfordWords = oxfordWordRepositories.findAll(pageRequest).getContent();
                List<Expression> expressions = new ArrayList<>();
                for (OxfordWord oxfordWord : oxfordWords) {
                    Expression expression = migratePronunciationToExpression(oxfordWord);
                    if (expression != null) {
                        expressions.add(expression);
                    }
                }
                expressionRepository.save(expressions);
                LOGGER.debug("Migrate pronunciation for expressions: {}", expressions);
                return oxfordWords;
            }
        };
        List<OxfordWord> oxfordWords = audioActionLoopByPage.executeSinceLastItem(100);
        LOGGER.debug("End download {}", oxfordWords.size());
    }

    private Expression migratePronunciationToExpression(OxfordWord oxfordWord) {
        Expression expression = expressionRepository.findOneByText(oxfordWord.getWord());
        if (expression == null) {
            return null;
        }
        List<LexicalEntry> oxfordLexicalEntries = oxfordWord.getLexicalEntries();
        for (LexicalEntry oxfordLexicalEntry : oxfordLexicalEntries) {
            if (oxfordLexicalEntry.getPronunciations() == null){
                continue;
            }
            List<Pronunciation> newExpressionPronunciations = ExpressionMapper.toPronunciations(oxfordLexicalEntry.getPronunciations());
            SenseGroup senseGroup = findSuitableSenseGroup(expression, oxfordLexicalEntry);
            if (senseGroup != null) {
                List<Pronunciation> expressionPronunciations = new ArrayList<>();
                if (senseGroup.getPronunciations() != null) {
                    expressionPronunciations.addAll(senseGroup.getPronunciations());
                }
                expressionPronunciations.addAll(newExpressionPronunciations);
                expressionPronunciations = SetUtils.distinctListByProperty(expressionPronunciations, Pronunciation::getPhoneticNotation).collect(Collectors.toList());
                senseGroup.setPronunciations(expressionPronunciations);
            }
        }
        return expression;
    }

    /**
     * @param expression
     * @param oxfordLexicalEntry
     * @return find suitable senseGroup (based on lexical type)
     */
    private SenseGroup findSuitableSenseGroup(Expression expression, LexicalEntry oxfordLexicalEntry) {
        String oxfordLexicalType = oxfordLexicalEntry.getLexicalCategory();
        LexicalType lexicalType = ExpressionMapper.toLexicalType(oxfordLexicalType);
        List<SenseGroup> senseGroups = expression.getSenseGroups().stream().filter(senseGroup -> senseGroup.getLexicalType() == lexicalType).collect(Collectors.toList());
        if (senseGroups.isEmpty()) {
            return null;
        } else {
            return senseGroups.get(0);
        }
    }

}
