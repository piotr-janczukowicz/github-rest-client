package io.github.piotr_janczukowicz.github.api.branches;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * Branch links.
 *
 * @param html Html
 * @param selfUrl Self
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/branch-with-protection/properties/_links")
public record GitHubBranchWithProtectionLinks(
        String html, @JsonProperty("self") String selfUrl) {

    public GitHubBranchWithProtectionLinks {
        requireNonNull(html, "html");
        requireNonNull(selfUrl, "selfUrl");
    }
}
