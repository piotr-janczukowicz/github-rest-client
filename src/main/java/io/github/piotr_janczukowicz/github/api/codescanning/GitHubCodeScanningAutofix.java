package io.github.piotr_janczukowicz.github.api.codescanning;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.time.Instant;
import org.jspecify.annotations.Nullable;

/**
 * Code scanning autofix.
 *
 * @param status The status of an autofix.
 * @param description The description of an autofix.
 * @param startedAt The start time of an autofix in ISO 8601 format: `YYYY-MM-DDTHH:MM:SSZ`.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/code-scanning-autofix")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubCodeScanningAutofix(
        GitHubCodeScanningAutofixStatus status, @Nullable String description, Instant startedAt) {
    public GitHubCodeScanningAutofix {
        requireNonNull(status, "status");
        requireNonNull(startedAt, "startedAt");
    }
}
