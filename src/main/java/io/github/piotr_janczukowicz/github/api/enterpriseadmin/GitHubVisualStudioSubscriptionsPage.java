package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;

/**
 * List of Visual Studio subscriptions of an enterprise.
 *
 * @param totalCount Total number of Visual Studio subscriptions
 * @param visualStudioSubscriptions Visual Studio subscriptions
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/enterprise-admin/list-vss/200")
@Flavors(GitHubFlavor.GHEC)
public record GitHubVisualStudioSubscriptionsPage(
        int totalCount, List<GitHubVisualStudioSubscriptionAssignment> visualStudioSubscriptions) {

    public GitHubVisualStudioSubscriptionsPage {
        requireNonNull(visualStudioSubscriptions, "visualStudioSubscriptions");
    }
}
