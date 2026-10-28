package io.github.piotr_janczukowicz.github.api.branches;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * Protected branch required signatures.
 *
 * @param url Url
 * @param enabled Enabled
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema({
    "components/schemas/branch-protection/properties/required_signatures",
    "components/schemas/protected-branch/properties/required_signatures"
})
public record GitHubProtectedBranchRequiredSignatures(String url, boolean enabled) {

    public GitHubProtectedBranchRequiredSignatures {
        requireNonNull(url, "url");
    }
}
