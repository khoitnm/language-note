package org.tnmk.ln.infrastructure.dataimport.phrasalverb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tnmk.common.exception.UnexpectedException;
import org.tnmk.common.utils.io.IOUtils;
import org.tnmk.ln.app.vocabulary.entity.ExpressionItem;
import org.tnmk.ln.app.vocabulary.entity.FillingQuestion;
import org.tnmk.ln.app.vocabulary.entity.Lesson;
import org.tnmk.ln.app.vocabulary.entity.Meaning;
import org.tnmk.ln.app.vocabulary.entity.PhrasalVerb;
import org.tnmk.ln.app.vocabulary.entity.Topic;
import org.tnmk.ln.app.vocabulary.entity.Word;
import org.tnmk.ln.app.vocabulary.service.LessonService;
import org.tnmk.ln.app.vocabulary.util.WordUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;
import java.util.stream.Collectors;

/**
 * @author khoi.tran on 2/4/17.
 */
@Service
public class EnglishClubImport {

    @Autowired
    private LessonService lessonService;

    public Lesson loadWebsiteHtml() throws ParserConfigurationException {
        String htmlContent = IOUtils.loadTextFileInClassPath("/dataimport/englishclub.html");
        Lesson lesson = analyseDocument(htmlContent);
        return lessonService.saveLesson(lesson);
    }

    private Lesson analyseDocument(String htmlContent) {
        Lesson lesson = new Lesson();
        lesson.setName("Phrasal Verb");
        Topic category = new Topic();
        category.setName("phrasal verb");
        lesson.setTopics(Arrays.asList(category).stream().collect(Collectors.toSet()));

        Document document = convertStringToDocument(htmlContent);
        Element html = (Element) document.getChildNodes().item(0);
        Element table = (Element) html.getElementsByTagName("table").item(0);
        Element tbody = (Element) table.getElementsByTagName("tbody").item(0);
        List<ExpressionItem> expressionItems = analyseRows(tbody);
        lesson.setExpressionItems(expressionItems);
        return lesson;
    }

    private List<ExpressionItem> analyseRows(Element tbody) {
        NodeList rows = tbody.getElementsByTagName("tr");
        List<ExpressionItem> result = new ArrayList<>();
        for (int i = 0; i < rows.getLength(); i++) {
            Element irow = (Element) rows.item(i);
            ExpressionItem expressionItem = analyseRow(irow);
            ExpressionItem previousExpression = findExpressionItemByExpression(result, expressionItem.getExpression());
            if (!mergeToPreviousExpressionIfNecessary(previousExpression, expressionItem)) {
                result.add(expressionItem);
            }
        }
        return result;
    }

    private ExpressionItem findExpressionItemByExpression(List<ExpressionItem> expressionItems, String expression) {
        for (ExpressionItem expressionItem : expressionItems) {
            if (expressionItem.getExpression().trim().equalsIgnoreCase(expression.trim())) {
                return expressionItem;
            }
        }
        return null;
    }

    private boolean mergeToPreviousExpressionIfNecessary(ExpressionItem previousExpressionItem, ExpressionItem expressionItem) {
        if (previousExpressionItem == null) return false;
        boolean isMerge = expressionItem.getExpression().trim().equalsIgnoreCase(previousExpressionItem.getExpression().trim());
        if (isMerge) {
            mergeToPreviousExpression(previousExpressionItem, expressionItem);
        }
        return isMerge;
    }

    private void mergeToPreviousExpression(ExpressionItem previousExpressionItem, ExpressionItem expressionItem) {
        //merge meanings
        //TODO same meaning? No!
        List<Meaning> meanings = previousExpressionItem.getMeanings();
        if (meanings == null) {
            meanings = new ArrayList<>();
            previousExpressionItem.setMeanings(meanings);
        }
        if (expressionItem.getMeanings() != null) {
            meanings.addAll(expressionItem.getMeanings());
        }

        //merge filling questions
//        List<FillingQuestion> fillingQuestions = previousExpressionItem.getFillingQuestions();
//        if (previousExpressionItem.getFillingQuestions() == null) {
//            fillingQuestions = new ArrayList<>();
//            previousExpressionItem.setFillingQuestions(fillingQuestions);
//        }
//        if (expressionItem.getFillingQuestions() != null) {
//            fillingQuestions.addAll(expressionItem.getFillingQuestions());
//        }
    }

    private ExpressionItem analyseRow(Element row) {
        ExpressionItem expressionItem = new ExpressionItem();
        NodeList columns = row.getElementsByTagName("td");
        Element expressionColumn = (Element) columns.item(0);
        Element explanationColumn = (Element) columns.item(1);
        Element exampleColumn = (Element) columns.item(2);

        PhrasalVerb phrasalVerb = analyseExpression(expressionColumn);
        expressionItem.setPhrasalVerbDetail(phrasalVerb);
        expressionItem.setExpression(phrasalVerb.toExpression());

        String explanation = explanationColumn.getTextContent();
        List<Meaning> meanings = new ArrayList<>();
        Meaning meaning = new Meaning();
        meanings.add(meaning);
        expressionItem.setMeanings(meanings);

        meaning.setExplanation(explanation);

        //TODO
        List<FillingQuestion> fillingQuestions = new ArrayList<>();
        FillingQuestion fillingQuestion = new FillingQuestion();
        fillingQuestions.add(fillingQuestion);

        List<Word> questionByWords = analyseExampleToQuestions(exampleColumn);
        fillingQuestion.setWords(questionByWords);
        meaning.setFillingQuestions(fillingQuestions);

        String example = WordUtils.toString(questionByWords);
        List<String> examples = new ArrayList<>();
        examples.add(example);
        meaning.setExamples(examples);
        return expressionItem;
    }

    private List<Word> analyseExampleToQuestions(Element exampleColumn) {
        List<Word> words = new ArrayList<>();
        NodeList children = exampleColumn.getChildNodes();
        int phrasalVerbPlaceHolderCount = 0;
        for (int i = 0; i < children.getLength(); i++) {
            Node node = children.item(i);
            Word word = new Word();
            String wordValue;
            String wordType;
            if ("strong".equals(node.getNodeName())) {
                wordValue = node.getNodeValue();
                wordType = "phrasal-verb-placeholder";
                phrasalVerbPlaceHolderCount++;
            } else {
                wordValue = node.getNodeValue();
                wordType = "text";
            }
            word.setType(wordType);
            word.setValue(wordValue);
            words.add(word);
        }
        PhrasalVerb phrasalVerb = analyseExpression(exampleColumn);
        Word verbWord = phrasalVerb.getVerb();
        Word prepositionWord = phrasalVerb.getPreposition();

        int indexPhrasalVerbPlaceHolderInExample = 0;
        ListIterator<Word> exampleIterator = words.listIterator();
        while (exampleIterator.hasNext()) {
            Word word = exampleIterator.next();
            if ("phrasal-verb-placeholder".equals(word.getType())) {
                if (phrasalVerbPlaceHolderCount == 1) {
                    exampleIterator.set(verbWord);
                    exampleIterator.add(prepositionWord);
                } else {
                    if (indexPhrasalVerbPlaceHolderInExample == 0) {
                        exampleIterator.set(verbWord);
                    } else {
                        exampleIterator.set(prepositionWord);
                    }
                }
                indexPhrasalVerbPlaceHolderInExample++;
            }
        }

        int i = 0;
        for (Word word : words) {
            word.setIndex(i);
            i++;
        }
        return words;
    }

    private List<Word> analyseWordsAsPhrasalVerb(Element element) {
        String verbValue;
        String prepositionValue;
        String nounValue;

        Word verb;
        Word preposition;
        Word noun;
        List<Word> words = new ArrayList<>();
        NodeList phrasalMainWords = element.getElementsByTagName("strong");
        NodeList pronounWords = element.getElementsByTagName("i");

        Element mainNode = (Element) phrasalMainWords.item(0);
        String mainNodeText = mainNode.getTextContent();

        Element nounNode = (Element) pronounWords.item(0);
        nounValue = nounNode != null ? nounNode.getTextContent() : null;
        int index = 0;
        if (phrasalMainWords.getLength() == 1) {//verb and preposition is continuous.
            String[] analyse = mainNodeText.split(" ", 2);
            verbValue = analyse[0];
            prepositionValue = analyse[1];

            verb = new Word(index++, "verb", verbValue);
            preposition = new Word(index++, "preposition", prepositionValue);

            words.add(verb);
            words.add(preposition);

            if (nounValue != null) {
                noun = new Word(index++, "noun", nounValue);
                words.add(noun);
            }
        } else {
            verbValue = mainNodeText;
            Element prepositionNode = (Element) phrasalMainWords.item(1);
            prepositionValue = prepositionNode.getTextContent();
            verb = new Word(index++, "verb", verbValue);
            words.add(verb);

            if (nounValue != null) {
                noun = new Word(index++, "noun", nounValue);
                words.add(noun);
            }
            preposition = new Word(index++, "preposition", prepositionValue);
            words.add(preposition);
        }
        return words;
    }

    private PhrasalVerb analyseExpression(Element expressionColumn) {
        PhrasalVerb phrasalVerb = new PhrasalVerb();
        phrasalVerb.setWords(analyseWordsAsPhrasalVerb(expressionColumn));
        return phrasalVerb;
    }

    private static String convertDocumentToString(Document doc) {
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer;
        try {
            transformer = tf.newTransformer();
            // below code to remove XML declaration
            // transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            StringWriter writer = new StringWriter();
            transformer.transform(new DOMSource(doc), new StreamResult(writer));
            String output = writer.getBuffer().toString();
            return output;
        } catch (TransformerException e) {
            e.printStackTrace();
        }

        return null;
    }

    private static Document convertStringToDocument(String xmlStr) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        try {
            builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new InputSource(new StringReader(xmlStr)));
            return doc;
        } catch (Exception e) {
            throw new UnexpectedException(e.getMessage(), e);
        }
    }

}
