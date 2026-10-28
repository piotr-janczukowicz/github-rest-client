package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * GitHubManageSetMaintenanceResult
 *
 * @param hostname hostname
 * @param uuid uuid
 * @param message message
 * @param error error
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/ghes-set-maintenance-response/items")
@Flavors(GitHubFlavor.GHES)
public record GitHubManageSetMaintenanceResult(
        @Nullable String hostname,
        @Nullable String uuid,
        @Nullable String message,
        @Nullable String error) {}
