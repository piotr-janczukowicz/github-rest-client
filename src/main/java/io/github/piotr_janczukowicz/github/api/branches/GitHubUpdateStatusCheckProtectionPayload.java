package io.github.piotr_janczukowicz.github.api.branches;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * Update status check protection request body.
 *
 * @param strict Require branches to be up to date before merging
 * @param contexts Deprecated: closing down notice; use {@code checks} instead of {@code contexts} for more fine-grained
 *     control.
 * @param checks Required status checks
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/repos/update-status-check-protection/requestBody")
public record GitHubUpdateStatusCheckProtectionPayload(
        @Nullable Boolean strict,
        @Deprecated @Nullable List<String> contexts,
        @Nullable List<GitHubUpdateBranchProtectionRequiredStatusCheck> checks) {

    public GitHubUpdateStatusCheckProtectionPayload {
        if (contexts != null) {
            contexts = List.copyOf(contexts);
        }
        if (checks != null) {
            checks = List.copyOf(checks);
        }
    }
}
