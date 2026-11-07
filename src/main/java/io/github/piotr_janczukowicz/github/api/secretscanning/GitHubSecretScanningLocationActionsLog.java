package io.github.piotr_janczukowicz.github.api.secretscanning;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.openapi.model.SuppressSchemaTypeMismatch;

/**
 * Represents an 'actions_log' secret scanning location type. The path identifies the workflow definition, while line
 * and column positions identify the secret in the raw job log, not in the workflow file.
 *
 * @param path The workflow definition path. Dynamically generated workflows can have a synthetic path.
 * @param startLine Line number at which the secret starts in the raw job log.
 * @param endLine Line number at which the secret ends in the raw job log.
 * @param startColumn The one-based column at which the secret starts within the start line of the raw job log.
 * @param endColumn The one-based column at which the secret ends within the end line of the raw job log.
 */
// The implemented sealed interface carries the deduction; this type is decoded on its own through the location type.
@JsonTypeInfo(use = JsonTypeInfo.Id.NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/secret-scanning-location-actions-log")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubSecretScanningLocationActionsLog(
        String path,

        @SuppressSchemaTypeMismatch("type rule: int (start_line)")
        int startLine,

        @SuppressSchemaTypeMismatch("type rule: int (end_line)")
        int endLine,

        @SuppressSchemaTypeMismatch("type rule: int (start_column)")
        int startColumn,

        @SuppressSchemaTypeMismatch("type rule: int (end_column)")
        int endColumn)
        implements GitHubSecretScanningLocationDetails, GitHubSecretScanningFirstDetectedLocation {
    public GitHubSecretScanningLocationActionsLog {
        requireNonNull(path, "path");
    }
}
