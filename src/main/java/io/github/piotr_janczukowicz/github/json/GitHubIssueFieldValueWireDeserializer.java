package io.github.piotr_janczukowicz.github.json;

import io.github.piotr_janczukowicz.github.api.issues.GitHubIssueFieldValueWire;
import io.github.piotr_janczukowicz.json.jackson.ScalarUnionReaders;
import tools.jackson.core.JsonParser;
import tools.jackson.databind.DeserializationContext;
import tools.jackson.databind.deser.std.StdDeserializer;

/** Decodes issue field value wire shapes from JSON string, number, or string array. */
final class GitHubIssueFieldValueWireDeserializer extends StdDeserializer<GitHubIssueFieldValueWire> {

    private static final ScalarUnionReaders.Factories<GitHubIssueFieldValueWire> FACTORIES =
            new ScalarUnionReaders.Factories<>(
                    GitHubIssueFieldValueWire::text,
                    GitHubIssueFieldValueWire::number,
                    null,
                    GitHubIssueFieldValueWire::texts,
                    false,
                    "value must be a string, number, or string array");

    GitHubIssueFieldValueWireDeserializer() {
        super(GitHubIssueFieldValueWire.class);
    }

    @Override
    public GitHubIssueFieldValueWire deserialize(final JsonParser parser, final DeserializationContext context) {
        return ScalarUnionReaders.readRequired(parser, context, FACTORIES);
    }
}
