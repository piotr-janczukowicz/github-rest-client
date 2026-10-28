package io.github.piotr_janczukowicz.github.json;

import static io.github.piotr_janczukowicz.json.jackson.JsonTokens.requireToken;

import io.github.piotr_janczukowicz.github.api.apps.GitHubIntegrationPermissions;
import java.util.LinkedHashMap;
import java.util.Map;
import tools.jackson.core.JsonParser;
import tools.jackson.core.JsonToken;
import tools.jackson.databind.DeserializationContext;
import tools.jackson.databind.deser.std.StdDeserializer;

/**
 * Decodes integration permissions with named properties and additional string keys.
 *
 * <p>Custom decoding is kept so non-string permission values fail instead of being coerced to {@link String} by the
 * default mapper.
 */
final class GitHubIntegrationPermissionsDeserializer extends StdDeserializer<GitHubIntegrationPermissions> {

    GitHubIntegrationPermissionsDeserializer() {
        super(GitHubIntegrationPermissions.class);
    }

    @Override
    public GitHubIntegrationPermissions deserialize(final JsonParser parser, final DeserializationContext context) {
        requireToken(
                parser,
                parser.currentToken(),
                JsonToken.START_OBJECT,
                "GitHub integration permissions must be an object");
        final Map<String, String> additional = new LinkedHashMap<>();
        String issues = null;
        String checks = null;
        String metadata = null;
        String contents = null;
        String deployments = null;
        while (parser.nextToken() != JsonToken.END_OBJECT) {
            final String field = parser.currentName();
            final JsonToken value = parser.nextToken();
            if (value == JsonToken.VALUE_NULL) {
                continue;
            }
            final String text = requireText(field, value, parser);
            switch (field) {
                case "issues":
                    issues = text;
                    break;
                case "checks":
                    checks = text;
                    break;
                case "metadata":
                    metadata = text;
                    break;
                case "contents":
                    contents = text;
                    break;
                case "deployments":
                    deployments = text;
                    break;
                default:
                    additional.put(field, text);
                    break;
            }
        }
        return new GitHubIntegrationPermissions(issues, checks, metadata, contents, deployments, additional);
    }

    private static String requireText(final String field, final JsonToken value, final JsonParser parser) {
        requireToken(
                parser,
                value,
                JsonToken.VALUE_STRING,
                "GitHub integration permission '" + field + "' must be a string");
        return parser.getString();
    }
}
