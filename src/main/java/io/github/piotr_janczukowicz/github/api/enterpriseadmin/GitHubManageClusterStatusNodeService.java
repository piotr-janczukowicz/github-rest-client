package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * GitHubManageClusterStatusNodeService
 *
 * @param status status
 * @param name name
 * @param details details
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/ghes-cluster-status/properties/nodes/items/properties/services/items")
@Flavors(GitHubFlavor.GHES)
public record GitHubManageClusterStatusNodeService(
        @Nullable GitHubManageClusterStatusIndicator status,
        @Nullable String name,
        @Nullable String details) {}
