package io.github.piotr_janczukowicz.github.api.users;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Gpg Key Email.
 *
 * @param email Email
 * @param verified Verified
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema({
    "components/schemas/gpg-key/properties/emails/items",
    "components/schemas/gpg-key/properties/subkeys/items/properties/emails/items"
})
@RecordBuilder
public record GitHubGpgKeyEmail(
        @Nullable String email, @Nullable Boolean verified) {}
