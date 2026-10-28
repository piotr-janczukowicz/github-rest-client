package io.github.piotr_janczukowicz.github.json;

import io.github.piotr_janczukowicz.github.api.projects.GitHubAddFieldForUserPayload;
import io.github.piotr_janczukowicz.json.jackson.BufferedJson;
import tools.jackson.core.JsonParser;
import tools.jackson.databind.DeserializationContext;
import tools.jackson.databind.deser.std.StdDeserializer;

/** Decodes add-field-for-user request bodies by unique variant properties. */
final class GitHubAddFieldForUserPayloadDeserializer extends StdDeserializer<GitHubAddFieldForUserPayload> {

    GitHubAddFieldForUserPayloadDeserializer() {
        super(GitHubAddFieldForUserPayload.class);
    }

    @Override
    public GitHubAddFieldForUserPayload deserialize(final JsonParser parser, final DeserializationContext context) {
        try (BufferedJson json = BufferedJson.capture(parser, context)) {
            return GitHubProjectFieldPayloads.readUser(json);
        }
    }
}
