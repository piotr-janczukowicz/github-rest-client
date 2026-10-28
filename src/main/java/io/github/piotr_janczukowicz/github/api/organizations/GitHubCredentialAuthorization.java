package io.github.piotr_janczukowicz.github.api.organizations;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.time.Instant;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * Credential authorization of an organization, such as a personal access token or SSH key.
 *
 * @param login User login that owns the underlying credential
 * @param credentialId Unique identifier for the authorization of the credential
 * @param credentialType Human-readable description of the credential type
 * @param tokenLastEight Last eight characters of the credential
 * @param credentialAuthorizedAt Date when the credential was authorized for use
 * @param scopes List of OAuth scopes the token has been granted
 * @param fingerprint Unique string to distinguish the credential
 * @param credentialAccessedAt Date when the credential was last accessed
 * @param authorizedCredentialId Unique identifier of the authorized credential
 * @param authorizedCredentialTitle Title of the authorized credential
 * @param authorizedCredentialNote Note of the authorized credential
 * @param authorizedCredentialExpiresAt Date when the authorized credential expires
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/credential-authorization")
@Flavors(GitHubFlavor.GHEC)
public record GitHubCredentialAuthorization(
        String login,
        long credentialId,
        GitHubCredentialAuthorizationCredentialType credentialType,
        @Nullable String tokenLastEight,
        Instant credentialAuthorizedAt,
        @Nullable List<String> scopes,
        @Nullable String fingerprint,
        @Nullable Instant credentialAccessedAt,
        @Nullable Long authorizedCredentialId,
        @Nullable String authorizedCredentialTitle,
        @Nullable String authorizedCredentialNote,
        @Nullable Instant authorizedCredentialExpiresAt) {

    public GitHubCredentialAuthorization {
        requireNonNull(login, "login");
        requireNonNull(credentialType, "credentialType");
        requireNonNull(credentialAuthorizedAt, "credentialAuthorizedAt");
    }

    /**
     * Text representation that hides the authorized credential title and note.
     *
     * @return text representation without the credential text
     */
    @Override
    public String toString() {
        return "GitHubCredentialAuthorization[login=" + login
                + ", credentialId=" + credentialId
                + ", credentialType=" + credentialType
                + ", tokenLastEight=" + tokenLastEight
                + ", credentialAuthorizedAt=" + credentialAuthorizedAt
                + ", scopes=" + scopes
                + ", fingerprint=" + fingerprint
                + ", credentialAccessedAt=" + credentialAccessedAt
                + ", authorizedCredentialId=" + authorizedCredentialId
                + ", authorizedCredentialTitle=<redacted>"
                + ", authorizedCredentialNote=<redacted>"
                + ", authorizedCredentialExpiresAt=" + authorizedCredentialExpiresAt + "]";
    }
}
