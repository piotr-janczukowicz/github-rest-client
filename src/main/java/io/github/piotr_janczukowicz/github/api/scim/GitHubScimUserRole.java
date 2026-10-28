package io.github.piotr_janczukowicz.github.api.scim;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * A role assigned to the SCIM user.
 *
 * @param value The identifier of the role
 * @param primary Whether this is the primary role
 * @param type The type of role
 * @param display The display name of the role
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/scim-user/properties/roles/items")
@Flavors(GitHubFlavor.GHEC)
public record GitHubScimUserRole(
        @Nullable String value,
        @Nullable Boolean primary,
        @Nullable String type,
        @Nullable String display) {}
