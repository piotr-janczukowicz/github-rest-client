package io.github.piotr_janczukowicz.github.api.users;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Create Gpg Key.
 *
 * @param name A descriptive name for the new key.
 * @param armoredPublicKey A GPG key in ASCII-armored format.
 */
@CatalogSchema("operations/users/create-gpg-key-for-authenticated-user/requestBody")
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
public record GitHubCreateGpgKeyPayload(@Nullable String name, String armoredPublicKey) {

    public GitHubCreateGpgKeyPayload {
        requireNonNull(armoredPublicKey, "armoredPublicKey");
    }
}
