package io.github.piotr_janczukowicz.github.json;

import org.jspecify.annotations.Nullable;
import tools.jackson.core.JsonParser;
import tools.jackson.core.JsonToken;
import tools.jackson.databind.DatabindException;
import tools.jackson.databind.DeserializationContext;
import tools.jackson.databind.deser.std.StdDeserializer;

/**
 * Decodes import {@code use_lfs} from a JSON boolean or the documented strings {@code opt_in}, {@code opt_out}, and
 * {@code undecided}.
 */
final class GitHubImportUseLfsFlagDeserializer extends StdDeserializer<Boolean> {

    GitHubImportUseLfsFlagDeserializer() {
        super(Boolean.class);
    }

    @Override
    public @Nullable Boolean deserialize(final JsonParser parser, final DeserializationContext context) {
        final JsonToken token = parser.currentToken();
        if (token == JsonToken.VALUE_TRUE) {
            return true;
        }
        if (token == JsonToken.VALUE_FALSE) {
            return false;
        }
        if (token == JsonToken.VALUE_STRING) {
            return fromWire(parser, parser.getString());
        }
        throw DatabindException.from(parser, "use_lfs must be a boolean, string, or null");
    }

    private static @Nullable Boolean fromWire(final JsonParser parser, final String value) {
        return switch (value) {
            case "opt_in" -> true;
            case "opt_out" -> false;
            case "undecided" -> null;
            default -> throw DatabindException.from(parser, "Unknown GitHub import use_lfs: " + value);
        };
    }
}
