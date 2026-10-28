package io.github.piotr_janczukowicz.github.json;

import io.github.piotr_janczukowicz.github.api.organizations.GitHubOrgRulesetConditions;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubRepositoryRulesetConditionsRefName;
import java.util.LinkedHashMap;
import java.util.Map;
import org.jspecify.annotations.Nullable;
import tools.jackson.core.JsonGenerator;
import tools.jackson.databind.SerializationContext;
import tools.jackson.databind.ser.std.StdSerializer;

/**
 * Encodes organization ruleset {@code conditions} as a single {@code oneOf} object, or the properties of unknown
 * conditions as sent.
 */
final class GitHubOrgRulesetConditionsSerializer extends StdSerializer<GitHubOrgRulesetConditions> {

    GitHubOrgRulesetConditionsSerializer() {
        super(GitHubOrgRulesetConditions.class);
    }

    @Override
    public void serialize(
            final GitHubOrgRulesetConditions value, final JsonGenerator generator, final SerializationContext context) {
        context.writeValue(generator, body(value));
    }

    private static Map<String, Object> body(final GitHubOrgRulesetConditions value) {
        final Map<String, Object> body = new LinkedHashMap<>();
        switch (value) {
            case GitHubOrgRulesetConditions.RepositoryNameAndRefName(final var refName, final var repositoryName) -> {
                putRefName(body, refName);
                body.put("repository_name", repositoryName);
            }
            case GitHubOrgRulesetConditions.RepositoryIdAndRefName(final var refName, final var repositoryId) -> {
                putRefName(body, refName);
                body.put("repository_id", repositoryId);
            }
            case GitHubOrgRulesetConditions.RepositoryPropertyAndRefName(
                    final var refName,
                    final var repositoryProperty) -> {
                putRefName(body, refName);
                body.put("repository_property", repositoryProperty);
            }
            case GitHubOrgRulesetConditions.Unknown(final var properties) -> body.putAll(properties);
        }
        return body;
    }

    private static void putRefName(
            final Map<String, Object> body, final @Nullable GitHubRepositoryRulesetConditionsRefName refName) {
        if (refName != null) {
            body.put("ref_name", refName);
        }
    }
}
