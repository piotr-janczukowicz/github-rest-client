package io.github.piotr_janczukowicz.github.api.secretscanning;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.time.Instant;
import org.jspecify.annotations.Nullable;

/**
 * Information on a single scan performed by secret scanning on the repository.
 *
 * @param type The type of scan
 * @param status The state of the scan. Either "completed", "running", "pending", "failed", or "unknown". "failed": this
 *     scan type has hit its retry limit and has been marked permanently failed. This usually resolves on its own at the
 *     next scheduled scan attempt. If "failed" persists, contact Support. "unknown": the scan is in an unrecognized or
 *     currently unhandled state.
 * @param completedAt The time that the scan was completed. Empty if the scan is running
 * @param startedAt The time that the scan was started. Empty if the scan is pending
 */
@CatalogSchema("components/schemas/secret-scanning-scan")
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
public record GitHubSecretScanningScan(
        @Nullable String type,
        @Nullable String status,
        @Nullable Instant completedAt,
        @Nullable Instant startedAt) {}
