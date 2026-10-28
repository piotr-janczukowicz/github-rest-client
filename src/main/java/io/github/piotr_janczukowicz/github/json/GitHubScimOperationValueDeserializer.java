package io.github.piotr_janczukowicz.github.json;

import io.github.piotr_janczukowicz.github.api.scim.GitHubScimOperationValue;
import java.util.List;
import java.util.Map;
import tools.jackson.core.JsonParser;
import tools.jackson.core.JsonToken;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.DatabindException;
import tools.jackson.databind.DeserializationContext;
import tools.jackson.databind.deser.std.StdDeserializer;

/** Decodes a SCIM user operation value from a JSON string, object, or array of objects. */
final class GitHubScimOperationValueDeserializer extends StdDeserializer<GitHubScimOperationValue> {

    private static final TypeReference<Map<String, Object>> OBJECT_TYPE = new TypeReference<>() {};
    private static final TypeReference<List<Map<String, Object>>> ARRAY_TYPE = new TypeReference<>() {};

    GitHubScimOperationValueDeserializer() {
        super(GitHubScimOperationValue.class);
    }

    @Override
    public GitHubScimOperationValue deserialize(final JsonParser parser, final DeserializationContext context) {
        final JsonToken token = parser.currentToken();
        return switch (token) {
            case VALUE_STRING -> GitHubScimOperationValue.text(parser.getString());
            case START_OBJECT -> GitHubScimOperationValue.object(context.readValue(parser, OBJECT_TYPE));
            case START_ARRAY -> {
                final List<Map<String, Object>> objects = context.readValue(parser, ARRAY_TYPE);
                if (objects.contains(null)) {
                    throw DatabindException.from(parser, "array must hold objects only");
                }
                yield GitHubScimOperationValue.array(objects);
            }
            default ->
                throw DatabindException.from(parser, "value must be a string, an object, or an array of objects");
        };
    }
}
