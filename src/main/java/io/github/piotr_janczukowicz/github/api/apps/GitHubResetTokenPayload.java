package io.github.piotr_janczukowicz.github.api.apps;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * Request body for resetting an authorization token.
 *
 * @param accessToken The OAuth access token used to authenticate to the GitHub API
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/apps/reset-token/requestBody")
public record GitHubResetTokenPayload(String accessToken) {

    public GitHubResetTokenPayload {
        requireNonNull(accessToken, "accessToken");
    }

    /**
     * Describes this record without its secrets.
     *
     * @return text with {@code accessToken} redacted
     */
    @Override
    public String toString() {
        return "GitHubResetTokenPayload[accessToken=<redacted>]";
    }
}
