package io.github.piotr_janczukowicz.github.api.organizations;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * Update an organization programmatic access grant.
 *
 * @param action Action to apply to the token
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/orgs/update-pat-access/requestBody")
public record GitHubUpdatePatAccessPayload(GitHubOrganizationPatAccessUpdateAction action) {

    public GitHubUpdatePatAccessPayload {
        requireNonNull(action, "action");
    }
}
