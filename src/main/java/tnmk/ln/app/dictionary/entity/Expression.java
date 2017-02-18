package tnmk.ln.app.dictionary.entity;

import tnmk.ln.infrastructure.filestorage.entity.FileItem;

import java.util.List;

/**
 * @author khoi.tran on 2/18/17.
 */
public class Expression {
    private String value;
    private ExpressionType expressionType;
    private List<LexicalEntry> lexicalEntries;
    private List<Sense> senses;
    private List<Expression> synonyms;
    private List<Expression> antonyms;
    private List<Expression> family;
    private FileItem audio;
    private List<FileItem> images;
}
