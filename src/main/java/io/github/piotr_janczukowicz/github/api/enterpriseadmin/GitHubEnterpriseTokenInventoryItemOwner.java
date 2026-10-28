package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * The owner of a credential.
 *
 * @param id The unique identifier of the owner
 * @param login The login of the owner
 * @param name The name of the owner
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/enterprise-token-inventory-item/properties/owner")
@Flavors(GitHubFlavor.GHEC)
public record GitHubEnterpriseTokenInventoryItemOwner(
        @Nullable Long id, @Nullable String login, @Nullable String name) {}
