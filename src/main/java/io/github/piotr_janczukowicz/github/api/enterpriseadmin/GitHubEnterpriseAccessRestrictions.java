package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * Information about the enterprise access restrictions proxy header.
 *
 * @param message The message returned for the request.
 * @param headerName The name of the proxy header.
 * @param headerValue The value of the proxy header.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/enterprise-access-restrictions")
@Flavors(GitHubFlavor.GHEC)
public record GitHubEnterpriseAccessRestrictions(String message, String headerName, String headerValue) {

    public GitHubEnterpriseAccessRestrictions {
        requireNonNull(message, "message");
        requireNonNull(headerName, "headerName");
        requireNonNull(headerValue, "headerValue");
    }

    /**
     * Describes this record without its proxy header value, which acts as a shared access secret.
     *
     * @return text with {@code headerValue} redacted
     */
    @Override
    public String toString() {
        return "GitHubEnterpriseAccessRestrictions[message=" + message + ", headerName=" + headerName
                + ", headerValue=<redacted>]";
    }
}
