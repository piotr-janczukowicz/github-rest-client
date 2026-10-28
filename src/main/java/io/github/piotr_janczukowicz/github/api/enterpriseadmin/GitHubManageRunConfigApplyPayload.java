package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * GitHubManageRunConfigApplyPayload
 *
 * @param runId The run ID to execute `ghe-config-apply` with. If not provided, a run ID will be generated randomly.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/enterprise-admin/post-manage-run-config-apply/requestBody")
@Flavors(GitHubFlavor.GHES)
public record GitHubManageRunConfigApplyPayload(@Nullable String runId) {}
