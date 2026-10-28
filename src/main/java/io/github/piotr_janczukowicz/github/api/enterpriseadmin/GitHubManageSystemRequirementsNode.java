package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * GitHubManageSystemRequirementsNode
 *
 * @param hostname hostname
 * @param status status
 * @param rolesStatus roles status
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/ghes-checks-system-requirements/properties/nodes/items")
@Flavors(GitHubFlavor.GHES)
public record GitHubManageSystemRequirementsNode(
        @Nullable String hostname,
        @Nullable GitHubManageChecksResultIndicator status,
        @Nullable List<GitHubManageSystemRequirementsNodeRolesStatu> rolesStatus) {}
