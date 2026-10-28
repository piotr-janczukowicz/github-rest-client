package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * An organization that authorized a credential.
 *
 * @param id The unique identifier of the organization
 * @param login The login of the organization
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/enterprise-token-inventory-item/properties/authorized_organizations/items")
@Flavors(GitHubFlavor.GHEC)
public record GitHubEnterpriseTokenInventoryItemAuthorizedOrganization(
        @Nullable Long id, @Nullable String login) {}
