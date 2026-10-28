package io.github.piotr_janczukowicz.github.json;

import io.github.piotr_janczukowicz.github.api.scim.GitHubUpdateAttributeForUserValue;
import io.github.piotr_janczukowicz.github.api.scim.GitHubUpdateAttributeForUserValueEmail;
import io.github.piotr_janczukowicz.github.api.scim.GitHubUpdateAttributeForUserValueObject;
import java.util.List;
import tools.jackson.core.JsonParser;
import tools.jackson.core.JsonToken;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.DatabindException;
import tools.jackson.databind.DeserializationContext;
import tools.jackson.databind.deser.std.StdDeserializer;

/** Decodes an update-attribute value from a JSON string, named-attribute object, or array of email objects. */
final class GitHubUpdateAttributeForUserValueDeserializer extends StdDeserializer<GitHubUpdateAttributeForUserValue> {

    private static final TypeReference<List<GitHubUpdateAttributeForUserValueEmail>> ARRAY_TYPE =
            new TypeReference<>() {};

    GitHubUpdateAttributeForUserValueDeserializer() {
        super(GitHubUpdateAttributeForUserValue.class);
    }

    @Override
    public GitHubUpdateAttributeForUserValue deserialize(
            final JsonParser parser, final DeserializationContext context) {
        final JsonToken token = parser.currentToken();
        return switch (token) {
            case VALUE_STRING -> GitHubUpdateAttributeForUserValue.text(parser.getString());
            case START_OBJECT ->
                GitHubUpdateAttributeForUserValue.object(
                        context.readValue(parser, GitHubUpdateAttributeForUserValueObject.class));
            case START_ARRAY -> {
                final List<GitHubUpdateAttributeForUserValueEmail> emails = context.readValue(parser, ARRAY_TYPE);
                if (emails.contains(null)) {
                    throw DatabindException.from(parser, "array must hold objects only");
                }
                yield GitHubUpdateAttributeForUserValue.array(emails);
            }
            default ->
                throw DatabindException.from(parser, "value must be a string, an object, or an array of objects");
        };
    }
}
