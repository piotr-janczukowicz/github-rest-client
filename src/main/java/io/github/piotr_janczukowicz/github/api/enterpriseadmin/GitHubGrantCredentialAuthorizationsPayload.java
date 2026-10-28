package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;
import static io.github.piotr_janczukowicz.util.ValidationUtils.requireSizeBetween;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.openapi.model.CatalogSimpleXor;
import java.util.List;

/**
 * Grant credential authorizations request body.
 *
 * @param credentialId Identifier of the credential, sent as a number or a string
 * @param credentialType Type of the credential
 * @param organizations Organizations the credential is authorized for
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/enterprise-admin/grant-credential-authorizations/requestBody")
@Flavors(GitHubFlavor.GHEC)
public record GitHubGrantCredentialAuthorizationsPayload(
        @CatalogSimpleXor("integer|string") String credentialId,
        GitHubGrantCredentialAuthorizationsCredentialType credentialType,
        List<String> organizations) {

    private static final String PARAM_CREDENTIAL_ID = "credentialId";
    private static final String PARAM_ORGANIZATIONS = "organizations";

    public GitHubGrantCredentialAuthorizationsPayload {
        requireNonNull(credentialId, PARAM_CREDENTIAL_ID);
        requireNonNull(credentialType, "credentialType");
        organizations = List.copyOf(
                requireSizeBetween(requireNonNull(organizations, PARAM_ORGANIZATIONS), 1, 50, PARAM_ORGANIZATIONS));
    }
}
