package io.github.piotr_janczukowicz.github.api.credentials;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;
import static io.github.piotr_janczukowicz.util.ValidationUtils.requireSizeBetween;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;

/**
 * Revoke a list of credentials.
 *
 * @param credentials A list of credentials to be revoked, up to 1000 per request.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/credentials/revoke/requestBody")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubRevokePayload(List<String> credentials) {

    private static final int MIN_CREDENTIALS = 1;
    private static final int MAX_CREDENTIALS = 1000;

    public GitHubRevokePayload {
        requireNonNull(credentials, "credentials");
        credentials = List.copyOf(requireSizeBetween(credentials, MIN_CREDENTIALS, MAX_CREDENTIALS, "credentials"));
    }

    /**
     * Describes this GitHubRevokePayload without credential material.
     *
     * @return text with the credentials redacted
     */
    @Override
    public String toString() {
        return "GitHubRevokePayload[credentials=<redacted:" + credentials.size() + ">]";
    }
}
