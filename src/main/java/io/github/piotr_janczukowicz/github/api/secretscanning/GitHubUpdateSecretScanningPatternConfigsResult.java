package io.github.piotr_janczukowicz.github.api.secretscanning;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Update organization pattern configurations response.
 *
 * @param patternConfigVersion The updated pattern configuration version.
 */
@CatalogSchema("operations/secret-scanning/update-org-pattern-configs/200")
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
public record GitHubUpdateSecretScanningPatternConfigsResult(
        @Nullable String patternConfigVersion) {}
