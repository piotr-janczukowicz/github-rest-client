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
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/secret-scanning/bulk-create-enterprise-custom-patterns/201")
@Flavors(GitHubFlavor.GHEC)
public record GitHubBulkCreateEnterpriseCustomPatternsResult(
        @Nullable List<GitHubSecretScanningCustomPattern> createdPatterns) {}
