package io.github.piotr_janczukowicz.github.api.branches;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * Protected branch required status check.
 *
 * @param url Url
 * @param enforcementLevel Enforcement level
 * @param contexts Contexts
 * @param checks Checks
 * @param contextsUrl Contexts url
 * @param strict Strict
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/protected-branch-required-status-check")
public record GitHubProtectedBranchRequiredStatusCheck(
        @Nullable String url,
        @Nullable String enforcementLevel,
        List<String> contexts,
        List<GitHubStatusCheckPolicyCheck> checks,
        @Nullable String contextsUrl,
        @Nullable Boolean strict) {

    public GitHubProtectedBranchRequiredStatusCheck {
        requireNonNull(contexts, "contexts");
        requireNonNull(checks, "checks");
    }
}
