package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * A SCIM group that a provisioned enterprise user is a member of.
 *
 * @param value The identifier of the group.
 * @param ref The SCIM reference URL of the group.
 * @param display The display name of the group.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/scim-enterprise-user-response/allOf/1/properties/groups/items")
@Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
public record GitHubScimEnterpriseUserGroup(
        @Nullable String value,
        @JsonProperty("$ref") @Nullable String ref,
        @Nullable String display) {}
