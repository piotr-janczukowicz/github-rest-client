package io.github.piotr_janczukowicz.github.api.users;

import static io.github.piotr_janczukowicz.github.api.GitHubSchemaPatterns.SSH_PUBLIC_KEY;
import static io.github.piotr_janczukowicz.util.ValidationUtils.requireMatches;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Create Public Ssh Key.
 *
 * @param title A descriptive name for the new key.
 * @param key The public SSH key to add to your GitHub account.
 */
@CatalogSchema("operations/users/create-public-ssh-key-for-authenticated-user/requestBody")
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
public record GitHubCreatePublicSshKeyPayload(@Nullable String title, String key) {

    public GitHubCreatePublicSshKeyPayload {
        requireMatches(key, SSH_PUBLIC_KEY, "key");
    }
}
