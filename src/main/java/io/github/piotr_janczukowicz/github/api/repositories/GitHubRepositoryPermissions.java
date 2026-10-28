package io.github.piotr_janczukowicz.github.api.repositories;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * Permissions.
 *
 * @param admin Admin
 * @param maintain Maintain
 * @param push Push
 * @param triage Triage
 * @param pull Pull
 */
@CatalogSchema("components/schemas/team/properties/permissions")
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"pull", "triage", "push", "maintain", "admin"})
@RecordBuilder
public record GitHubRepositoryPermissions(
        boolean admin, boolean maintain, boolean push, boolean triage, boolean pull) {}
