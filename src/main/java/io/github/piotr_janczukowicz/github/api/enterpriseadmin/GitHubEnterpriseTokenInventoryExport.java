package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * The status of an asynchronous enterprise token inventory CSV export.
 *
 * @param exportId Opaque id for the export, used to poll its status.
 * @param status The state of the export job.
 * @param asOf When the export was requested.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/enterprise-token-inventory-export")
@Flavors(GitHubFlavor.GHEC)
public record GitHubEnterpriseTokenInventoryExport(
        String exportId,
        GitHubEnterpriseTokenInventoryExportStatus status,
        @Nullable String asOf) {

    public GitHubEnterpriseTokenInventoryExport {
        requireNonNull(exportId, "exportId");
        requireNonNull(status, "status");
    }
}
