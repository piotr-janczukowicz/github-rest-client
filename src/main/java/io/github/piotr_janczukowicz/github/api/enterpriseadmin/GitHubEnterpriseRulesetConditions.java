package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.api.actions.GitHubEnterpriseRulesetConditionsOrganizationIdTargetOrganizationId;
import io.github.piotr_janczukowicz.github.api.actions.GitHubEnterpriseRulesetConditionsOrganizationNameTargetOrganizationName;
import io.github.piotr_janczukowicz.github.api.actions.GitHubEnterpriseRulesetConditionsOrganizationPropertyTargetOrganizationProperty;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubRepositoryRulesetConditionsRefName;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubRepositoryRulesetConditionsRepositoryNameTargetRepositoryName;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubRepositoryRulesetConditionsRepositoryPropertyTargetRepositoryProperty;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Conditions for an enterprise ruleset. The conditions object supports one organization target
 * ({@code organization_name}, {@code organization_id}, or {@code organization_property}) combined with one repository
 * target ({@code repository_name} or {@code repository_property}), and may also contain {@code ref_name}. The six spec
 * branches are flattened into this record because they overlap on {@code ref_name}.
 *
 * @param organizationName Organization name patterns the ruleset applies to
 * @param organizationId Organization ids the ruleset applies to
 * @param organizationProperty Organization properties the ruleset applies to
 * @param repositoryName Repository name patterns the ruleset applies to
 * @param repositoryProperty Repository properties the ruleset applies to
 * @param refName Ref name include and exclude patterns
 */
@CatalogSchema("components/schemas/enterprise-ruleset-conditions")
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@Flavors(GitHubFlavor.GHEC)
public record GitHubEnterpriseRulesetConditions(
        @Nullable GitHubEnterpriseRulesetConditionsOrganizationNameTargetOrganizationName organizationName,
        @Nullable GitHubEnterpriseRulesetConditionsOrganizationIdTargetOrganizationId organizationId,
        @Nullable GitHubEnterpriseRulesetConditionsOrganizationPropertyTargetOrganizationProperty organizationProperty,
        @Nullable GitHubRepositoryRulesetConditionsRepositoryNameTargetRepositoryName repositoryName,
        @Nullable GitHubRepositoryRulesetConditionsRepositoryPropertyTargetRepositoryProperty repositoryProperty,
        @Nullable GitHubRepositoryRulesetConditionsRefName refName) {

    private static final int REQUIRED_TARGET_COUNT = 1;

    public GitHubEnterpriseRulesetConditions {
        final int organizationTargets = (organizationName == null ? 0 : 1)
                + (organizationId == null ? 0 : 1)
                + (organizationProperty == null ? 0 : 1);
        if (organizationTargets != REQUIRED_TARGET_COUNT) {
            throw new IllegalArgumentException(
                    "exactly one of organizationName, organizationId or organizationProperty is required");
        }
        final int repositoryTargets = (repositoryName == null ? 0 : 1) + (repositoryProperty == null ? 0 : 1);
        if (repositoryTargets != REQUIRED_TARGET_COUNT) {
            throw new IllegalArgumentException("exactly one of repositoryName or repositoryProperty is required");
        }
    }
}
