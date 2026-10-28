package io.github.piotr_janczukowicz.github.json;

import io.github.piotr_janczukowicz.github.api.issues.GitHubIssueSuggestionTargetValue;
import io.github.piotr_janczukowicz.json.jackson.ScalarUnionReaders;
import org.jspecify.annotations.Nullable;
import tools.jackson.core.JsonParser;
import tools.jackson.databind.DeserializationContext;
import tools.jackson.databind.deser.std.StdDeserializer;

/** Decodes issue suggestion target values from JSON string, number, boolean, or string array. */
final class GitHubIssueSuggestionTargetValueDeserializer extends StdDeserializer<GitHubIssueSuggestionTargetValue> {

    private static final ScalarUnionReaders.Factories<GitHubIssueSuggestionTargetValue> FACTORIES =
            new ScalarUnionReaders.Factories<>(
                    GitHubIssueSuggestionTargetValue::text,
                    GitHubIssueSuggestionTargetValue::number,
                    GitHubIssueSuggestionTargetValue::flag,
                    GitHubIssueSuggestionTargetValue::texts,
                    true,
                    "target_value must be a string, number, boolean, string array, or null");

    GitHubIssueSuggestionTargetValueDeserializer() {
        super(GitHubIssueSuggestionTargetValue.class);
    }

    @Override
    public @Nullable GitHubIssueSuggestionTargetValue deserialize(
            final JsonParser parser, final DeserializationContext context) {
        return ScalarUnionReaders.read(parser, context, FACTORIES);
    }
}
