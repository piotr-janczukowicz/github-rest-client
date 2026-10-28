package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * GitHubManageConfigNodesNode
 *
 * @param hostname hostname
 * @param uuid uuid
 * @param replica replica
 * @param clusterRoles cluster roles
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/ghes-config-nodes/properties/nodes/items")
@Flavors(GitHubFlavor.GHES)
public record GitHubManageConfigNodesNode(
        @Nullable String hostname,
        @Nullable String uuid,
        @Nullable Boolean replica,
        @Nullable List<GitHubManageClusterRole> clusterRoles) {}
