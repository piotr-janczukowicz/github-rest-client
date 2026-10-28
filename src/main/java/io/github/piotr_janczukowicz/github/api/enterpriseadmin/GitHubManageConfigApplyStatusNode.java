package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * GitHubManageConfigApplyStatusNode
 *
 * @param runId The unique Run ID of the ghe-config-apply run on the host
 * @param hostname The hostname of the node
 * @param running Whether the ghe-config-apply run is still running on the host
 * @param successful Whether the ghe-config-apply run was successful on the host
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/enterprise-admin/get-manage-config-apply-status/200/properties/nodes/items")
@Flavors(GitHubFlavor.GHES)
public record GitHubManageConfigApplyStatusNode(
        @Nullable String runId,
        @Nullable String hostname,
        @Nullable Boolean running,
        @Nullable Boolean successful) {}
