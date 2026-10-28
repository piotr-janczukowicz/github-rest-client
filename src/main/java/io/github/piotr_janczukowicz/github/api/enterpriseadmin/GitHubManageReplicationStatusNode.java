package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * GitHubManageReplicationStatusNode
 *
 * @param hostname hostname
 * @param status status
 * @param services services
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/ghes-replication-status/properties/nodes/items")
@Flavors(GitHubFlavor.GHES)
public record GitHubManageReplicationStatusNode(
        @Nullable String hostname,
        @Nullable GitHubManageReplicationStatusIndicator status,
        @Nullable List<GitHubManageReplicationStatusNodeService> services) {}
