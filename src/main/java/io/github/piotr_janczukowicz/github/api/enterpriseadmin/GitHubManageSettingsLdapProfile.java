package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * GitHubManageSettingsLdapProfile
 *
 * @param uid uid
 * @param name name
 * @param mail mail
 * @param key key
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/ghes-get-settings/properties/ldap/properties/profile")
@Flavors(GitHubFlavor.GHES)
public record GitHubManageSettingsLdapProfile(
        @Nullable String uid,
        @Nullable String name,
        @Nullable String mail,
        @Nullable String key) {}
