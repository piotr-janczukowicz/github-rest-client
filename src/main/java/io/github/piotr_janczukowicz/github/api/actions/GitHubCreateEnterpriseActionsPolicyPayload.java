package io.github.piotr_janczukowicz.github.api.actions;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubRepositoryRuleEnforcement;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * Request body for {@code POST /enterprises/{enterprise}/actions/policies}.
 *
 * @param name The name of the policy.
 * @param enforcement The enforcement level of the policy
 * @param conditions Conditions the policy applies to
 * @param rules An array of rules within the policy.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("operations/actions/create-enterprise-actions-policy/requestBody")
@RecordBuilder
@Flavors(GitHubFlavor.GHEC)
public record GitHubCreateEnterpriseActionsPolicyPayload(
        String name,
        GitHubRepositoryRuleEnforcement enforcement,
        @Nullable GitHubActionsPolicyEnterpriseConditions conditions,
        @Nullable List<GitHubActionsRule> rules) {

    public GitHubCreateEnterpriseActionsPolicyPayload {
        requireNonNull(name, "name");
        requireNonNull(enforcement, "enforcement");
        if (rules != null) {
            rules = List.copyOf(rules);
        }
    }
}
