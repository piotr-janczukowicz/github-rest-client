package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * Credential authorizations that were granted. Flattened over the two response branches: a classic personal access
 * token carries no fingerprint, an SSH key carries one.
 *
 * @param credentialId Identifier of the granted credential
 * @param credentialType Type of the granted credential
 * @param fingerprint Fingerprint of the SSH key, present only for SSH key credentials
 * @param organizations Organizations the credential is authorized for
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema({
    "operations/enterprise-admin/grant-credential-authorizations/201",
    "operations/enterprise-admin/grant-credential-authorizations/201/oneOf/0",
    "operations/enterprise-admin/grant-credential-authorizations/201/oneOf/1"
})
@Flavors(GitHubFlavor.GHEC)
public record GitHubGrantCredentialAuthorizationsResponse(
        long credentialId,
        GitHubGrantCredentialAuthorizationsCredentialType credentialType,
        @Nullable String fingerprint,
        List<String> organizations) {

    public GitHubGrantCredentialAuthorizationsResponse {
        requireNonNull(credentialType, "credentialType");
        requireNonNull(organizations, "organizations");
    }
}
