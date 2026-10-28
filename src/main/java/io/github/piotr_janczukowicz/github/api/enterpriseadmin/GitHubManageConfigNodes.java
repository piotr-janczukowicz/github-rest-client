package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * GitHubManageConfigNodes
 *
 * @param topology topology
 * @param nodes nodes
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/ghes-config-nodes")
@Flavors(GitHubFlavor.GHES)
public record GitHubManageConfigNodes(
        @Nullable GitHubManageClusterTopology topology,
        @Nullable List<GitHubManageConfigNodesNode> nodes) {}
