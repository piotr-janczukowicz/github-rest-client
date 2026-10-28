package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * GitHubManageReplicationStatusNodeService
 *
 * @param status status
 * @param name name
 * @param details details
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/ghes-replication-status/properties/nodes/items/properties/services/items")
@Flavors(GitHubFlavor.GHES)
public record GitHubManageReplicationStatusNodeService(
        @Nullable GitHubManageReplicationStatusIndicator status,
        @Nullable String name,
        @Nullable String details) {}
