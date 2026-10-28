package io.github.piotr_janczukowicz.github.json;

import io.github.piotr_janczukowicz.github.api.users.GitHubSimpleUser;
import io.github.piotr_janczukowicz.json.jackson.BufferedJson;
import org.jspecify.annotations.Nullable;
import tools.jackson.core.JsonParser;
import tools.jackson.core.JsonToken;
import tools.jackson.databind.DeserializationContext;
import tools.jackson.databind.deser.std.StdDeserializer;

/**
 * Maps JSON {@code {}} and {@code null} to {@code null}; otherwise decodes {@code GitHubSimpleUser}.
 *
 * <p>Use only on properties (mixins). Type-level registration on {@link GitHubSimpleUser} would recurse when the
 * non-empty object path binds {@code GitHubSimpleUser} again.
 */
final class GitHubSimpleUserEmptyObjectDeserializer extends StdDeserializer<GitHubSimpleUser> {

    GitHubSimpleUserEmptyObjectDeserializer() {
        super(GitHubSimpleUser.class);
    }

    @Override
    public @Nullable GitHubSimpleUser deserialize(final JsonParser parser, final DeserializationContext context) {
        if (parser.currentToken() == JsonToken.VALUE_NULL) {
            return null;
        }
        if (parser.currentToken() != JsonToken.START_OBJECT) {
            return context.readValue(parser, GitHubSimpleUser.class);
        }
        try (BufferedJson json = BufferedJson.capture(parser, context)) {
            return read(json);
        }
    }

    @Override
    public @Nullable GitHubSimpleUser getNullValue(final DeserializationContext context) {
        return null;
    }

    private static @Nullable GitHubSimpleUser read(final BufferedJson json) {
        if (json.isEmptyObject()) {
            return null;
        }
        return json.read(GitHubSimpleUser.class);
    }
}
