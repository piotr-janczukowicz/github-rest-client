package io.github.piotr_janczukowicz.github.json;

import io.github.piotr_janczukowicz.github.api.projects.GitHubAddProjectFieldPayload;
import io.github.piotr_janczukowicz.json.jackson.BufferedJson;
import tools.jackson.core.JsonParser;
import tools.jackson.databind.DeserializationContext;
import tools.jackson.databind.deser.std.StdDeserializer;

/** Decodes add-project-field request bodies by unique variant properties. */
final class GitHubAddProjectFieldPayloadDeserializer extends StdDeserializer<GitHubAddProjectFieldPayload> {

    GitHubAddProjectFieldPayloadDeserializer() {
        super(GitHubAddProjectFieldPayload.class);
    }

    @Override
    public GitHubAddProjectFieldPayload deserialize(final JsonParser parser, final DeserializationContext context) {
        try (BufferedJson json = BufferedJson.capture(parser, context)) {
            return GitHubProjectFieldPayloads.readOrg(json);
        }
    }
}
