package tnmk.ln.app.vocabulary.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import tnmk.ln.app.common.entity.json.DeserializerEnumByField;
import tnmk.ln.app.common.entity.json.SerializerEnumByField;

/**
 * @author khoi.tran on 1/25/17.
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@JsonSerialize(using = ExpressionType.ExpressionTypeSerializer.class)
@JsonDeserialize(using = ExpressionType.ExpressionTypeDeserializer.class)
public enum ExpressionType {
    WORD("word"), IDIOM("idiom"), PHRASE("phrase"), PHRASAL_VERB("phrasal verb");

    public String getStringValue() {
        return stringValue;
    }

    private final String stringValue;

    ExpressionType(String stringValue) {this.stringValue = stringValue;}

    public static class ExpressionTypeDeserializer extends DeserializerEnumByField<ExpressionType> {
        public ExpressionTypeDeserializer() {
            super(ExpressionType.class);
        }
    }

    public static class ExpressionTypeSerializer extends SerializerEnumByField<ExpressionType> {
        protected ExpressionTypeSerializer() {
            super(ExpressionType.class);
        }
    }
}
