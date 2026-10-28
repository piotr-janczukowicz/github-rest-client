package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * GitHubManageMaintenanceStatusConnectionService
 *
 * @param name name
 * @param number number
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/ghes-get-maintenance/items/properties/connection_services/items")
@Flavors(GitHubFlavor.GHES)
public record GitHubManageMaintenanceStatusConnectionService(
        @Nullable String name, @Nullable Integer number) {}
