package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * GitHubManageConfigApplyStatus
 *
 * @param running Whether the ghe-config-apply run is still running in the environment
 * @param successful Whether the ghe-config-apply run was successful in the environment
 * @param nodes nodes
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/enterprise-admin/get-manage-config-apply-status/200")
@Flavors(GitHubFlavor.GHES)
public record GitHubManageConfigApplyStatus(
        @Nullable Boolean running,
        @Nullable Boolean successful,
        @Nullable List<GitHubManageConfigApplyStatusNode> nodes) {}
