package io.github.piotr_janczukowicz.github.api.branches;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * Required status checks for branch protection updates.
 *
 * @param strict Require branches to be up to date before merging
 * @param contexts Deprecated: closing down notice; use {@code checks} instead of {@code contexts} for more fine-grained
 *     control.
 * @param checks Required status checks
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/repos/update-branch-protection/requestBody/properties/required_status_checks")
public record GitHubUpdateBranchProtectionRequiredStatusChecks(
        boolean strict,
        @Deprecated List<String> contexts,
        @Nullable List<GitHubUpdateBranchProtectionRequiredStatusCheck> checks) {

    public GitHubUpdateBranchProtectionRequiredStatusChecks {
        contexts = List.copyOf(requireNonNull(contexts, "contexts"));
        if (checks != null) {
            checks = List.copyOf(checks);
        }
    }
}
