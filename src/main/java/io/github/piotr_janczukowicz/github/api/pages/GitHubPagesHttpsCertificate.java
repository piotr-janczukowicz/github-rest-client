package io.github.piotr_janczukowicz.github.api.pages;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.time.LocalDate;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * Pages HTTPS certificate.
 *
 * @param state State
 * @param description Description
 * @param domains Array of the domain set and its alternate name (if it is configured)
 * @param expiresAt Expires at
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/pages-https-certificate")
public record GitHubPagesHttpsCertificate(
        GitHubPagesHttpsCertificateState state,
        String description,
        List<String> domains,
        @Nullable LocalDate expiresAt) {

    public GitHubPagesHttpsCertificate {
        requireNonNull(state, "state");
        requireNonNull(description, "description");
        requireNonNull(domains, "domains");
    }
}
