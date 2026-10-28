package io.github.piotr_janczukowicz.github.api.organizations;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Set organization membership for a user request body.
 *
 * @param role Role to give the user in the organization
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/orgs/set-membership-for-user/requestBody")
public record GitHubSetOrganizationMembershipPayload(
        @Nullable GitHubOrganizationMembershipRole role) {}
