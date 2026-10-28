package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * GitHubManageSystemRequirementsNodeRolesStatu
 *
 * @param status status
 * @param role role
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema(
        "components/schemas/ghes-checks-system-requirements/properties/nodes/items/properties/roles_status/items")
@Flavors(GitHubFlavor.GHES)
public record GitHubManageSystemRequirementsNodeRolesStatu(
        @Nullable GitHubManageChecksResultIndicator status,
        @Nullable String role) {}
