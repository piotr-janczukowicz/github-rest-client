package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * GitHubManageSettingsSaml
 *
 * @param ssoUrl sso url
 * @param certificate certificate
 * @param certificatePath certificate path
 * @param issuer issuer
 * @param idpInitiatedSso idp initiated sso
 * @param disableAdminDemote disable admin demote
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/ghes-get-settings/properties/saml")
@Flavors(GitHubFlavor.GHES)
public record GitHubManageSettingsSaml(
        @Nullable String ssoUrl,
        @Nullable String certificate,
        @Nullable String certificatePath,
        @Nullable String issuer,
        @Nullable Boolean idpInitiatedSso,
        @Nullable Boolean disableAdminDemote) {}
