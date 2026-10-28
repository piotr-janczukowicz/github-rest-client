package io.github.piotr_janczukowicz.github.json;

import io.github.piotr_janczukowicz.github.api.organizations.GitHubOrgRulesetConditions;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubRepositoryRulesetConditionsRefName;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubRepositoryRulesetConditionsRepositoryIdTargetRepositoryId;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubRepositoryRulesetConditionsRepositoryNameTargetRepositoryName;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubRepositoryRulesetConditionsRepositoryPropertyTargetRepositoryProperty;
import io.github.piotr_janczukowicz.json.jackson.BufferedJson;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import org.jspecify.annotations.Nullable;
import tools.jackson.core.JsonParser;
import tools.jackson.databind.DeserializationContext;
import tools.jackson.databind.deser.std.StdDeserializer;

/**
 * Decodes organization ruleset {@code conditions} from one {@code oneOf} branch. Conditions with no branch, several
 * branches or other properties decode as {@link GitHubOrgRulesetConditions.Unknown}.
 */
final class GitHubOrgRulesetConditionsDeserializer extends StdDeserializer<GitHubOrgRulesetConditions> {

    private static final String PROPERTY_REPOSITORY_NAME = "repository_name";
    private static final String PROPERTY_REPOSITORY_ID = "repository_id";
    private static final String PROPERTY_REPOSITORY_PROPERTY = "repository_property";
    private static final String PROPERTY_REF_NAME = "ref_name";
    private static final int EXACTLY_ONE_BRANCH = 1;
    private static final Set<String> KNOWN_PROPERTIES =
            Set.of(PROPERTY_REPOSITORY_NAME, PROPERTY_REPOSITORY_ID, PROPERTY_REPOSITORY_PROPERTY, PROPERTY_REF_NAME);

    GitHubOrgRulesetConditionsDeserializer() {
        super(GitHubOrgRulesetConditions.class);
    }

    @Override
    public @Nullable GitHubOrgRulesetConditions getNullValue(final DeserializationContext context) {
        return null;
    }

    @Override
    public GitHubOrgRulesetConditions deserialize(final JsonParser parser, final DeserializationContext context) {
        try (BufferedJson json = BufferedJson.capture(parser, context)) {
            return read(json);
        }
    }

    private static GitHubOrgRulesetConditions read(final BufferedJson json) {
        final boolean name = json.hasNonNull(PROPERTY_REPOSITORY_NAME);
        final boolean id = json.hasNonNull(PROPERTY_REPOSITORY_ID);
        final boolean property = json.hasNonNull(PROPERTY_REPOSITORY_PROPERTY);
        final int branches = (name ? 1 : 0) + (id ? 1 : 0) + (property ? 1 : 0);
        if (branches != EXACTLY_ONE_BRANCH || hasUnknownProperty(json)) {
            return new GitHubOrgRulesetConditions.Unknown(nonNullProperties(json));
        }
        final GitHubRepositoryRulesetConditionsRefName refName = readRefName(json);
        if (name) {
            return GitHubOrgRulesetConditions.byRepositoryName(
                    refName,
                    json.readRequiredField(
                            PROPERTY_REPOSITORY_NAME,
                            GitHubRepositoryRulesetConditionsRepositoryNameTargetRepositoryName.class));
        }
        if (id) {
            return GitHubOrgRulesetConditions.byRepositoryId(
                    refName,
                    json.readRequiredField(
                            PROPERTY_REPOSITORY_ID,
                            GitHubRepositoryRulesetConditionsRepositoryIdTargetRepositoryId.class));
        }
        return GitHubOrgRulesetConditions.byRepositoryProperty(
                refName,
                json.readRequiredField(
                        PROPERTY_REPOSITORY_PROPERTY,
                        GitHubRepositoryRulesetConditionsRepositoryPropertyTargetRepositoryProperty.class));
    }

    private static Map<String, Object> nonNullProperties(final BufferedJson json) {
        final Map<String, Object> present = new LinkedHashMap<>();
        properties(json).forEach((name, value) -> {
            if (value != null) {
                present.put(name, value);
            }
        });
        return present;
    }

    private static boolean hasUnknownProperty(final BufferedJson json) {
        return !KNOWN_PROPERTIES.containsAll(properties(json).keySet());
    }

    @SuppressWarnings("unchecked")
    private static Map<String, Object> properties(final BufferedJson json) {
        return json.read(Map.class);
    }

    private static @Nullable GitHubRepositoryRulesetConditionsRefName readRefName(final BufferedJson json) {
        if (!json.hasNonNull(PROPERTY_REF_NAME)) {
            return null;
        }
        return json.readField(PROPERTY_REF_NAME, GitHubRepositoryRulesetConditionsRefName.class);
    }
}
