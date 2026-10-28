package io.github.piotr_janczukowicz.github.api.secretscanning;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;
import static io.github.piotr_janczukowicz.util.ValidationUtils.requireSizeAtMost;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * Bulk delete secret scanning custom patterns.
 *
 * @param patterns The list of custom patterns to delete.
 * @param postDeleteAction What to do with alerts associated with the deleted patterns. `delete_alerts` permanently
 *     removes the alerts. `resolve_alerts` resolves the alerts as "pattern deleted". Defaults to `delete_alerts` when
 *     not specified.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/secret-scanning/bulk-delete-enterprise-custom-patterns/requestBody")
@Flavors(GitHubFlavor.GHEC)
public record GitHubBulkDeleteEnterpriseCustomPatternsPayload(
        List<GitHubSecretScanningCustomPatternToDelete> patterns,
        @Nullable GitHubBulkDeleteEnterpriseCustomPatternsPostDeleteAction postDeleteAction) {

    public GitHubBulkDeleteEnterpriseCustomPatternsPayload {
        requireNonNull(patterns, "patterns");
        requireSizeAtMost(patterns, 500, "patterns");
        patterns = List.copyOf(patterns);
    }
}
