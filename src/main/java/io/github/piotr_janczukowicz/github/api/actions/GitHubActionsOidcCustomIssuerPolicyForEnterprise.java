package io.github.piotr_janczukowicz.github.api.actions;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * OIDC custom issuer policy of an enterprise.
 *
 * @param includeEnterpriseSlug Whether the enterprise customer requested a custom issuer URL
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/actions-oidc-custom-issuer-policy-for-enterprise")
@Flavors(GitHubFlavor.GHEC)
public record GitHubActionsOidcCustomIssuerPolicyForEnterprise(
        @Nullable Boolean includeEnterpriseSlug) {}
