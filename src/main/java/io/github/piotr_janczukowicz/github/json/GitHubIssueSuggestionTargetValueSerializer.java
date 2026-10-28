package io.github.piotr_janczukowicz.github.json;

import io.github.piotr_janczukowicz.github.api.issues.GitHubIssueSuggestionTargetValue;
import java.math.BigDecimal;
import tools.jackson.core.JsonGenerator;
import tools.jackson.databind.SerializationContext;
import tools.jackson.databind.ser.std.StdSerializer;

/** Encodes issue suggestion target values as JSON string, number, boolean, or string array. */
final class GitHubIssueSuggestionTargetValueSerializer extends StdSerializer<GitHubIssueSuggestionTargetValue> {

    GitHubIssueSuggestionTargetValueSerializer() {
        super(GitHubIssueSuggestionTargetValue.class);
    }

    @Override
    public void serialize(
            final GitHubIssueSuggestionTargetValue value,
            final JsonGenerator generator,
            final SerializationContext context) {
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
        final Boolean booleanValue = value.booleanValue();
        if (booleanValue != null) {
            generator.writeBoolean(booleanValue);
            return;
        }
        context.writeValue(generator, value.listValue());
    }
}
