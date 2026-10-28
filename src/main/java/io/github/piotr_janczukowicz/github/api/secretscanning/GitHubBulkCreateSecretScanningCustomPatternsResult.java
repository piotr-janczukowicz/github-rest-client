package io.github.piotr_janczukowicz.github.api.secretscanning;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * Secret Scanning Custom Pattern. A custom pattern for secret scanning.
 *
 * @param createdPatterns Created patterns
 */
@CatalogSchema({
    "operations/secret-scanning/bulk-create-org-custom-patterns/201",
    "operations/secret-scanning/bulk-create-repo-custom-patterns/201"
})
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubBulkCreateSecretScanningCustomPatternsResult(
        @Nullable List<GitHubSecretScanningCustomPattern> createdPatterns) {}
