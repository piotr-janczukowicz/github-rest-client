package io.github.piotr_janczukowicz.github.api.branches;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * Protected Branch Admin Enforced
 *
 * @param url Url
 * @param enabled Enabled
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/protected-branch-admin-enforced")
public record GitHubProtectedBranchAdminEnforced(String url, boolean enabled) {

    public GitHubProtectedBranchAdminEnforced {
        requireNonNull(url, "url");
    }
}
