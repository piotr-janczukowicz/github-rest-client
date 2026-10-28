package io.github.piotr_janczukowicz.github.json;

import static io.github.piotr_janczukowicz.json.jackson.JsonTokens.requireToken;

import io.github.piotr_janczukowicz.github.api.projects.GitHubProjectViewSort;
import io.github.piotr_janczukowicz.github.api.projects.GitHubProjectViewSortDirection;
import tools.jackson.core.JsonParser;
import tools.jackson.core.JsonToken;
import tools.jackson.databind.DeserializationContext;
import tools.jackson.databind.deser.std.StdDeserializer;

/** Decodes project view sort tuples {@code [field_id, direction]}. */
final class GitHubProjectViewSortDeserializer extends StdDeserializer<GitHubProjectViewSort> {

    private static final String TUPLE = "sort_by entry must be a two-element array";

    GitHubProjectViewSortDeserializer() {
        super(GitHubProjectViewSort.class);
    }

    @Override
    public GitHubProjectViewSort deserialize(final JsonParser parser, final DeserializationContext context) {
        requireToken(parser, parser.currentToken(), JsonToken.START_ARRAY, TUPLE);
        requireToken(parser, parser.nextToken(), JsonToken.VALUE_NUMBER_INT, TUPLE);
        final long fieldId = parser.getLongValue();
        requireToken(parser, parser.nextToken(), JsonToken.VALUE_STRING, TUPLE);
        final String direction = parser.getString();
        requireToken(parser, parser.nextToken(), JsonToken.END_ARRAY, TUPLE);
        return new GitHubProjectViewSort(fieldId, GitHubProjectViewSortDirection.fromWire(direction));
    }
}
