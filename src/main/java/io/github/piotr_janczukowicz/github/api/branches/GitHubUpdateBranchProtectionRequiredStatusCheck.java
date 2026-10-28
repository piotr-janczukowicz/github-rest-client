package io.github.piotr_janczukowicz.github.api.branches;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Required status check item for branch protection updates.
 *
 * @param context The name of the required check
 * @param appId The ID of the GitHub App that must provide this check
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema({
    "operations/repos/update-branch-protection/requestBody/properties/required_status_checks/properties/checks/items",
    "operations/repos/update-status-check-protection/requestBody/properties/checks/items"
})
public record GitHubUpdateBranchProtectionRequiredStatusCheck(
        String context, @Nullable Long appId) {

    private static final String PARAM_CONTEXT = "context";

    public GitHubUpdateBranchProtectionRequiredStatusCheck {
        requireNonNull(context, PARAM_CONTEXT);
    }
}
