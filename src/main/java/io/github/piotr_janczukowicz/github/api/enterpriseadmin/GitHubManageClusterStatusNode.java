package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * GitHubManageClusterStatusNode
 *
 * @param hostname hostname
 * @param status status
 * @param services services
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/ghes-cluster-status/properties/nodes/items")
@Flavors(GitHubFlavor.GHES)
public record GitHubManageClusterStatusNode(
        @Nullable String hostname,
        @Nullable GitHubManageClusterStatusIndicator status,
        @Nullable List<GitHubManageClusterStatusNodeService> services) {}
