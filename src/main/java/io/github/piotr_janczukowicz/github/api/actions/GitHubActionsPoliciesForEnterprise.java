package io.github.piotr_janczukowicz.github.api.actions;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;

/**
 * Actions policies of an enterprise.
 *
 * @param totalCount The total number of Actions policies
 * @param policies An array of Actions policies
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/actions/get-enterprise-actions-policies/200")
@Flavors(GitHubFlavor.GHEC)
public record GitHubActionsPoliciesForEnterprise(int totalCount, List<GitHubActionsPolicy> policies) {

    public GitHubActionsPoliciesForEnterprise {
        requireNonNull(policies, "policies");
    }
}
