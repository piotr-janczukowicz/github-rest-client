package io.github.piotr_janczukowicz.github.api.organizations;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubRepositoryRulesetConditionsRefName;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubRepositoryRulesetConditionsRepositoryIdTargetRepositoryId;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubRepositoryRulesetConditionsRepositoryNameTargetRepositoryName;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubRepositoryRulesetConditionsRepositoryPropertyTargetRepositoryProperty;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.Map;
import org.jspecify.annotations.Nullable;

/** Organization ruleset conditions ({@code repository_name}, {@code repository_id}, or {@code repository_property}). */
@CatalogSchema("components/schemas/org-ruleset-conditions")
public sealed interface GitHubOrgRulesetConditions
        permits GitHubOrgRulesetConditions.RepositoryNameAndRefName,
                GitHubOrgRulesetConditions.RepositoryIdAndRefName,
                GitHubOrgRulesetConditions.RepositoryPropertyAndRefName,
                GitHubOrgRulesetConditions.Unknown {

    /**
     * Conditions targeting repositories by name.
     *
     * @param refName ref name include/exclude, or {@code null} for push and repository-policy rulesets
     * @param repositoryName repository name target
     * @return name conditions
     */
    static RepositoryNameAndRefName byRepositoryName(
            final @Nullable GitHubRepositoryRulesetConditionsRefName refName,
            final GitHubRepositoryRulesetConditionsRepositoryNameTargetRepositoryName repositoryName) {
        return new RepositoryNameAndRefName(refName, repositoryName);
    }

    /**
     * Conditions targeting repositories by id.
     *
     * @param refName ref name include/exclude, or {@code null} for push and repository-policy rulesets
     * @param repositoryId repository id target
     * @return id conditions
     */
    static RepositoryIdAndRefName byRepositoryId(
            final @Nullable GitHubRepositoryRulesetConditionsRefName refName,
            final GitHubRepositoryRulesetConditionsRepositoryIdTargetRepositoryId repositoryId) {
        return new RepositoryIdAndRefName(refName, repositoryId);
    }

    /**
     * Conditions targeting repositories by property.
     *
     * @param refName ref name include/exclude, or {@code null} for push and repository-policy rulesets
     * @param repositoryProperty repository property target
     * @return property conditions
     */
    static RepositoryPropertyAndRefName byRepositoryProperty(
            final @Nullable GitHubRepositoryRulesetConditionsRefName refName,
            final GitHubRepositoryRulesetConditionsRepositoryPropertyTargetRepositoryProperty repositoryProperty) {
        return new RepositoryPropertyAndRefName(refName, repositoryProperty);
    }

    /**
     * Conditions to target repositories by name and refs by name.
     *
     * @param refName Ref name
     * @param repositoryName Repository name
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    record RepositoryNameAndRefName(
            @Nullable GitHubRepositoryRulesetConditionsRefName refName,
            GitHubRepositoryRulesetConditionsRepositoryNameTargetRepositoryName repositoryName)
            implements GitHubOrgRulesetConditions {

        public RepositoryNameAndRefName {
            requireNonNull(repositoryName, "repositoryName");
        }
    }

    /**
     * Conditions to target repositories by id and refs by name.
     *
     * @param refName Ref name
     * @param repositoryId Repository id
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    record RepositoryIdAndRefName(
            @Nullable GitHubRepositoryRulesetConditionsRefName refName,
            GitHubRepositoryRulesetConditionsRepositoryIdTargetRepositoryId repositoryId)
            implements GitHubOrgRulesetConditions {

        public RepositoryIdAndRefName {
            requireNonNull(repositoryId, "repositoryId");
        }
    }

    /**
     * Conditions to target repositories by property and refs by name.
     *
     * @param refName Ref name
     * @param repositoryProperty Repository property
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    record RepositoryPropertyAndRefName(
            @Nullable GitHubRepositoryRulesetConditionsRefName refName,
            GitHubRepositoryRulesetConditionsRepositoryPropertyTargetRepositoryProperty repositoryProperty)
            implements GitHubOrgRulesetConditions {

        public RepositoryPropertyAndRefName {
            requireNonNull(repositoryProperty, "repositoryProperty");
        }
    }

    /**
     * Conditions of a shape this library version does not know (none of the three targets, more than one, or other
     * properties), kept as GitHub sent them so that one new shape does not fail the whole ruleset or the list it is in.
     *
     * <p>The map is a shallow, unmodifiable copy without order and without top-level properties whose value is
     * {@code null}; nested objects and arrays are the plain maps and lists that were decoded. It is not part of the
     * OpenAPI schema and is never sent: create and update payloads reject it.
     *
     * @param properties the properties of the conditions object, by name
     */
    record Unknown(Map<String, Object> properties) implements GitHubOrgRulesetConditions {

        public Unknown {
            requireNonNull(properties, "properties");
            properties = Map.copyOf(properties);
        }
    }
}
