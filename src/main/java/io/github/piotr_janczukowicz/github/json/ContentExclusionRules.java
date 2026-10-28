package io.github.piotr_janczukowicz.github.json;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import tools.jackson.databind.DatabindException;
import tools.jackson.databind.DeserializationContext;
import tools.jackson.databind.JsonNode;

/** Decoding shared by the Copilot content exclusion rule deserializers. */
final class ContentExclusionRules {

    private static final String IF_ANY_MATCH = "ifAnyMatch";
    private static final String IF_NONE_MATCH = "ifNoneMatch";

    private ContentExclusionRules() {
        throw new UnsupportedOperationException("Utility class");
    }

    static <R> R decode(
            final JsonNode node,
            final DeserializationContext context,
            final Function<String, R> ofPath,
            final Function<List<String>, R> ofIfAnyMatch,
            final Function<List<String>, R> ofIfNoneMatch) {
        if (node.isString()) {
            return ofPath.apply(node.asString());
        }
        if (node.get(IF_ANY_MATCH) != null && node.get(IF_NONE_MATCH) != null) {
            throw DatabindException.from(context, "value must hold only one of ifAnyMatch or ifNoneMatch");
        }
        if (node.get(IF_ANY_MATCH) != null) {
            return ofIfAnyMatch.apply(strings(node.get(IF_ANY_MATCH), context));
        }
        if (node.get(IF_NONE_MATCH) != null) {
            return ofIfNoneMatch.apply(strings(node.get(IF_NONE_MATCH), context));
        }
        throw DatabindException.from(context, "value must be a string, or an object with ifAnyMatch or ifNoneMatch");
    }

    private static List<String> strings(final JsonNode array, final DeserializationContext context) {
        if (!array.isArray()) {
            throw DatabindException.from(context, "pattern list must be a string array");
        }
        final List<String> values = new ArrayList<>();
        for (final JsonNode item : array) {
            if (!item.isString()) {
                throw DatabindException.from(context, "pattern list must hold strings only");
            }
            values.add(item.asString());
        }
        return values;
    }
}
