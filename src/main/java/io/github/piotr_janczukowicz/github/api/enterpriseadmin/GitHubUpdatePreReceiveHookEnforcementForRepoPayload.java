package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Request to change the enforcement of a pre-receive hook on a repository.
 *
 * @param enforcement The state of enforcement for the hook on this repository
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/enterprise-admin/update-pre-receive-hook-enforcement-for-repo/requestBody")
@Flavors(GitHubFlavor.GHES)
public record GitHubUpdatePreReceiveHookEnforcementForRepoPayload(
        @Nullable GitHubUpdatePreReceiveHookEnforcementForRepoEnforcement enforcement) {}
