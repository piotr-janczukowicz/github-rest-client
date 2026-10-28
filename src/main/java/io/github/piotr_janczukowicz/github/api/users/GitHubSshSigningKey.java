package io.github.piotr_janczukowicz.github.api.users;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.time.Instant;

/**
 * SSH Signing Key. A public SSH key used to sign Git commits.
 *
 * @param key Key
 * @param id Id
 * @param title Title
 * @param createdAt Created at
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("components/schemas/ssh-signing-key")
@RecordBuilder
public record GitHubSshSigningKey(String key, long id, String title, Instant createdAt) {

    public GitHubSshSigningKey {
        requireNonNull(key, "key");
        requireNonNull(title, "title");
        requireNonNull(createdAt, "createdAt");
    }
}
