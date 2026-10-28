package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * GitHubManageSettingsLdapReconciliation
 *
 * @param user user
 * @param org org
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/ghes-get-settings/properties/ldap/properties/reconciliation")
@Flavors(GitHubFlavor.GHES)
public record GitHubManageSettingsLdapReconciliation(
        @Nullable String user, @Nullable String org) {}
