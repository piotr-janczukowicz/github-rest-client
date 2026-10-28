package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * GitHubManageReplicationStatus
 *
 * @param status status
 * @param nodes nodes
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/ghes-replication-status")
@Flavors(GitHubFlavor.GHES)
public record GitHubManageReplicationStatus(
        @Nullable GitHubManageReplicationStatusIndicator status,
        @Nullable List<GitHubManageReplicationStatusNode> nodes) {}
