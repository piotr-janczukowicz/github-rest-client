package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * GitHubLiveMigrationCreatePayload
 *
 * @param sourceOrganizationLogin Source organization login on the GHES appliance.
 * @param sourceRepositoryName Source repository name.
 * @param targetOrganizationLogin Target organization login on the destination GitHub environment.
 * @param targetRepositoryName Target repository name.
 * @param targetApiEndpoint Migration target API endpoint URL. Must be a valid HTTPS URL.
 * @param patName Name of the organization-scoped migration credential set to use. The raw token is never submitted
 *     inline and is never returned in the response.
 * @param targetVisibility Target repository visibility. Defaults to `internal` when omitted. A source repository with
 *     public visibility must set this explicitly.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/enterprise-admin/live-migration-create/requestBody")
@Flavors(GitHubFlavor.GHES)
public record GitHubLiveMigrationCreatePayload(
        String sourceOrganizationLogin,
        String sourceRepositoryName,
        String targetOrganizationLogin,
        String targetRepositoryName,
        String targetApiEndpoint,
        String patName,
        @Nullable GitHubLiveMigrationCreatePayloadTargetVisibility targetVisibility) {
    public GitHubLiveMigrationCreatePayload {
        requireNonNull(sourceOrganizationLogin, "sourceOrganizationLogin");
        requireNonNull(sourceRepositoryName, "sourceRepositoryName");
        requireNonNull(targetOrganizationLogin, "targetOrganizationLogin");
        requireNonNull(targetRepositoryName, "targetRepositoryName");
        requireNonNull(targetApiEndpoint, "targetApiEndpoint");
        requireNonNull(patName, "patName");
    }
}
