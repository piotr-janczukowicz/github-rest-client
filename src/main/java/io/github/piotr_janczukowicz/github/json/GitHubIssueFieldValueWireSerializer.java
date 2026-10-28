package io.github.piotr_janczukowicz.github.json;

import io.github.piotr_janczukowicz.github.api.issues.GitHubIssueFieldValueWire;
import java.math.BigDecimal;
import tools.jackson.core.JsonGenerator;
import tools.jackson.databind.SerializationContext;
import tools.jackson.databind.ser.std.StdSerializer;

/** Encodes issue field value wire shapes as JSON string, number, or string array. */
final class GitHubIssueFieldValueWireSerializer extends StdSerializer<GitHubIssueFieldValueWire> {

    GitHubIssueFieldValueWireSerializer() {
        super(GitHubIssueFieldValueWire.class);
    }

    @Override
    public void serialize(
            final GitHubIssueFieldValueWire value, final JsonGenerator generator, final SerializationContext context) {
        final String textValue = value.textValue();
        if (textValue != null) {
            generator.writeString(textValue);
            return;
        }
        final BigDecimal numberValue = value.numberValue();
        if (numberValue != null) {
            generator.writeNumber(numberValue);
            return;
        }
        context.writeValue(generator, value.listValue());
    }
}
