package io.github.piotr_janczukowicz.github.api.secretscanning;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.time.Instant;
import org.jspecify.annotations.Nullable;

/**
 * Custom pattern backfill scans.
 *
 * @param type The type of scan
 * @param status The state of the scan. Either "completed", "running", "pending", "failed", or "unknown". "failed": this
 *     scan type has hit its retry limit and has been marked permanently failed. This usually resolves on its own at the
 *     next scheduled scan attempt. If "failed" persists, contact Support. "unknown": the scan is in an unrecognized or
 *     currently unhandled state.
 * @param completedAt The time that the scan was completed. Empty if the scan is running
 * @param startedAt The time that the scan was started. Empty if the scan is pending
 * @param patternName Name of the custom pattern for custom pattern scans
 * @param patternScope Level at which the custom pattern is defined, one of "repository", "organization", or
 *     "enterprise"
 */
@CatalogSchema("components/schemas/secret-scanning-scan-history/properties/custom_pattern_backfill_scans/items")
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
public record GitHubSecretScanningCustomPatternBackfillScan(
        @Nullable String type,
        @Nullable String status,
        @Nullable Instant completedAt,
        @Nullable Instant startedAt,
        @Nullable String patternName,
        @Nullable String patternScope) {}
