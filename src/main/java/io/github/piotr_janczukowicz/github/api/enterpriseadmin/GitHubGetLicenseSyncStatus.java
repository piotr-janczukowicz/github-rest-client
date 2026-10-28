package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * Information about the status of a license sync job for an enterprise.
 *
 * @param serverInstances Connected server instances
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/get-license-sync-status")
@Flavors(GitHubFlavor.GHEC)
public record GitHubGetLicenseSyncStatus(@Nullable List<GitHubGetLicenseSyncStatusServerInstance> serverInstances) {}
