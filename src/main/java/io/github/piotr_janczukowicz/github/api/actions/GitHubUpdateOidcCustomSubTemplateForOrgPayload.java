package io.github.piotr_janczukowicz.github.api.actions;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * Actions OIDC subject customization for an organization.
 *
 * @param includeClaimKeys Array of unique strings. Each claim key can only contain alphanumeric characters and
 *     underscores
 * @param useImmutableSubject Whether to opt in to the immutable OIDC subject claim format for the organization
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/oidc/update-oidc-custom-sub-template-for-org/requestBody")
public record GitHubUpdateOidcCustomSubTemplateForOrgPayload(
        @Nullable List<String> includeClaimKeys, @Nullable Boolean useImmutableSubject) {

    public GitHubUpdateOidcCustomSubTemplateForOrgPayload {
        if (includeClaimKeys != null) {
            includeClaimKeys = List.copyOf(includeClaimKeys);
        }
    }
}
