package io.github.piotr_janczukowicz.github.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.piotr_janczukowicz.github.api.issues.GitHubIssueLabel;
import io.github.piotr_janczukowicz.github.api.users.GitHubSimpleUser;
import org.jspecify.annotations.Nullable;
import tools.jackson.core.JsonParser;
import tools.jackson.core.JsonToken;
import tools.jackson.databind.DeserializationContext;
import tools.jackson.databind.deser.std.StdDeserializer;

/** Decodes issue labels from either a label name string or a label object. */
final class GitHubIssueLabelDeserializer extends StdDeserializer<GitHubIssueLabel> {

    GitHubIssueLabelDeserializer() {
        super(GitHubIssueLabel.class);
    }

    @Override
    public GitHubIssueLabel deserialize(final JsonParser parser, final DeserializationContext context) {
        if (parser.currentToken() == JsonToken.VALUE_STRING) {
            return new GitHubIssueLabel(null, null, null, parser.getString(), null, null, null, null);
        }
        return context.readValue(parser, LabelObject.class).toLabel();
    }

    private record LabelObject(
            @Nullable Long id,
            @Nullable String nodeId,
            @Nullable String url,
            @Nullable String name,
            @Nullable String description,
            @Nullable String color,
            @JsonProperty("default") @Nullable Boolean isDefault,
            @Nullable GitHubSimpleUser archivedBy) {

        private GitHubIssueLabel toLabel() {
            return new GitHubIssueLabel(id, nodeId, url, name, description, color, isDefault, archivedBy);
        }
    }
}
