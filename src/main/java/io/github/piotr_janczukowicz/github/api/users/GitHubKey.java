package io.github.piotr_janczukowicz.github.api.users;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.time.Instant;
import org.jspecify.annotations.Nullable;

/**
 * Key.
 *
 * @param key Key
 * @param id Id
 * @param url Url
 * @param title Title
 * @param createdAt Created at
 * @param verified Verified
 * @param readOnly Read only
 * @param lastUsed Last used
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("components/schemas/key")
@RecordBuilder
public record GitHubKey(
        String key,
        long id,
        String url,
        String title,
        Instant createdAt,
        boolean verified,
        boolean readOnly,
        @Nullable Instant lastUsed) {

    public GitHubKey {
        requireNonNull(key, "key");
        requireNonNull(url, "url");
        requireNonNull(title, "title");
        requireNonNull(createdAt, "createdAt");
    }
}
