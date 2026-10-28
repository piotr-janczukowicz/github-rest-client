package io.github.piotr_janczukowicz.github.api.secretscanning;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;
import static io.github.piotr_janczukowicz.util.ValidationUtils.requireSizeAtMost;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;

/**
 * Bulk create secret scanning custom patterns.
 *
 * @param patterns The list of custom patterns to create (maximum 100).
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema({
    "operations/secret-scanning/bulk-create-org-custom-patterns/requestBody",
    "operations/secret-scanning/bulk-create-repo-custom-patterns/requestBody"
})
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubBulkCreateSecretScanningCustomPatternsPayload(
        List<GitHubSecretScanningCustomPatternToCreate> patterns) {

    public GitHubBulkCreateSecretScanningCustomPatternsPayload {
        requireNonNull(patterns, "patterns");
        patterns = List.copyOf(requireSizeAtMost(patterns, 100, "patterns"));
    }
}
