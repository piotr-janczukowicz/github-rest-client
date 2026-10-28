package io.github.piotr_janczukowicz.github.api.secretscanning;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * Secret scanning scan history.
 *
 * @param incrementalScans Incremental scans
 * @param patternUpdateScans Pattern update scans
 * @param backfillScans Backfill scans
 * @param customPatternBackfillScans Custom pattern backfill scans
 * @param genericSecretsBackfillScans Generic secrets backfill scans
 */
@CatalogSchema("components/schemas/secret-scanning-scan-history")
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
public record GitHubSecretScanningScanHistory(
        @Nullable List<GitHubSecretScanningScan> incrementalScans,
        @Nullable List<GitHubSecretScanningScan> patternUpdateScans,
        @Nullable List<GitHubSecretScanningScan> backfillScans,
        @Nullable List<GitHubSecretScanningCustomPatternBackfillScan> customPatternBackfillScans,
        @Nullable List<GitHubSecretScanningScan> genericSecretsBackfillScans) {}
