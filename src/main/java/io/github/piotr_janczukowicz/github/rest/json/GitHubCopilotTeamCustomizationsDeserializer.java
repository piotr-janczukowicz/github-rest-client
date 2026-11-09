package io.github.piotr_janczukowicz.github.rest.json;

import io.github.piotr_janczukowicz.json.jackson.BufferedJson;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import org.jspecify.annotations.Nullable;
import tools.jackson.core.JsonParser;
import tools.jackson.databind.DeserializationContext;
import tools.jackson.databind.JavaType;
import tools.jackson.databind.deser.std.StdDeserializer;
import tools.jackson.databind.type.TypeFactory;

/**
 * Decodes a Copilot team customization document: the two named keys plus every other key kept as an additional
 * property.
 *
 * <p>One instance serves one Java type (the get response, the set payload or the set response); they differ only in the
 * nested plugin assignment type and in the constructor.
 *
 * @param <T> the customization document type
 * @param <P> the plugin assignment type nested in {@code pluginAssignments}
 */
final class GitHubCopilotTeamCustomizationsDeserializer<T, P> extends StdDeserializer<T> {

    private static final String PLUGIN_ASSIGNMENTS = "pluginAssignments";
    private static final String EXTRA_KNOWN_MARKETPLACES = "extraKnownMarketplaces";
    private static final Set<String> NAMED = Set.of(PLUGIN_ASSIGNMENTS, EXTRA_KNOWN_MARKETPLACES);

    private static final TypeFactory TYPES = TypeFactory.createDefaultInstance();
    private static final JavaType MARKETPLACES = TYPES.constructMapType(
            Map.class,
            TYPES.constructType(String.class),
            TYPES.constructMapType(Map.class, String.class, Object.class));

    private final JavaType plugins;
    private final Factory<T, P> factory;

    GitHubCopilotTeamCustomizationsDeserializer(
            final Class<T> type, final Class<P> pluginAssignment, final Factory<T, P> factory) {
        super(type);
        this.plugins = TYPES.constructMapType(Map.class, String.class, pluginAssignment);
        this.factory = factory;
    }

    @Override
    @SuppressWarnings({"unchecked", "rawtypes"})
    public T deserialize(final JsonParser parser, final DeserializationContext context) {
        try (BufferedJson json = BufferedJson.capture(parser, context)) {
            final Map<String, P> pluginAssignments =
                    json.hasNonNull(PLUGIN_ASSIGNMENTS) ? json.readField(PLUGIN_ASSIGNMENTS, plugins) : null;
            final Map<String, Map<String, Object>> extraKnownMarketplaces = json.hasNonNull(EXTRA_KNOWN_MARKETPLACES)
                    ? json.readField(EXTRA_KNOWN_MARKETPLACES, MARKETPLACES)
                    : null;
            final Map<String, Object> additional = new LinkedHashMap<>(json.read(Map.class));
            additional.keySet().removeAll(NAMED);
            return factory.create(pluginAssignments, extraKnownMarketplaces, additional);
        }
    }

    /**
     * Builds the customization document from its decoded parts.
     *
     * @param <T> the customization document type
     * @param <P> the plugin assignment type
     */
    @FunctionalInterface
    interface Factory<T, P> {

        T create(
                @Nullable Map<String, P> pluginAssignments,
                @Nullable Map<String, Map<String, Object>> extraKnownMarketplaces,
                Map<String, Object> additionalProperties);
    }
}
