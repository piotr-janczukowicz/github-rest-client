package io.github.piotr_janczukowicz.github.api.users;

import static io.github.piotr_janczukowicz.github.api.GitHubSchemaPatterns.SSH_SIGNING_KEY;
import static io.github.piotr_janczukowicz.util.ValidationUtils.requireMatches;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Create Ssh Signing Key.
 *
 * @param title A descriptive name for the new key.
 * @param key The public SSH key to add to your GitHub account. For more information, see "[Checking for existing SSH
 *     keys](https://docs.github.com/authentication/connecting-to-github-with-ssh/checking-for-existing-ssh-keys)."
 */
@CatalogSchema("operations/users/create-ssh-signing-key-for-authenticated-user/requestBody")
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
public record GitHubCreateSshSigningKeyPayload(@Nullable String title, String key) {

    public GitHubCreateSshSigningKeyPayload {
        requireMatches(key, SSH_SIGNING_KEY, "key");
    }
}
