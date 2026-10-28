package io.github.piotr_janczukowicz.github.api.organizations;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * Update organization membership for the authenticated user request body.
 *
 * @param state The state that the membership should be in
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/orgs/update-membership-for-authenticated-user/requestBody")
public record GitHubUpdateAuthenticatedOrganizationMembershipPayload(
        GitHubOrganizationAuthenticatedMembershipState state) {

    public GitHubUpdateAuthenticatedOrganizationMembershipPayload {
        requireNonNull(state, "state");
    }
}
