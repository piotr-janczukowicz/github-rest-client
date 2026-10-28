package io.github.piotr_janczukowicz.github.api.users;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Email.
 *
 * @param email Email
 * @param primary Primary
 * @param verified Verified
 * @param visibility Visibility
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("components/schemas/email")
@RecordBuilder
public record GitHubEmail(
        String email,
        boolean primary,
        boolean verified,
        @Nullable String visibility) {

    public GitHubEmail {
        requireNonNull(email, "email");
    }
}
