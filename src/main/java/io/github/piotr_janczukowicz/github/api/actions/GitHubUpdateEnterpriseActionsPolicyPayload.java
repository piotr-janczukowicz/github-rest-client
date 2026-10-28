package io.github.piotr_janczukowicz.github.api.actions;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubRepositoryRuleEnforcement;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * Request body for {@code PUT /enterprises/{enterprise}/actions/policies/{policy_id}}.
 *
 * @param name The name of the policy.
 * @param enforcement The enforcement level of the policy
 * @param conditions Conditions the policy applies to
 * @param rules An array of rules within the policy.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("operations/actions/update-enterprise-actions-policy/requestBody")
@RecordBuilder
@Flavors(GitHubFlavor.GHEC)
public record GitHubUpdateEnterpriseActionsPolicyPayload(
        @Nullable String name,
        @Nullable GitHubRepositoryRuleEnforcement enforcement,
        @Nullable GitHubActionsPolicyEnterpriseConditions conditions,
        @Nullable List<GitHubActionsRule> rules) {

    public GitHubUpdateEnterpriseActionsPolicyPayload {
        if (rules != null) {
            rules = List.copyOf(rules);
        }
    }
}
