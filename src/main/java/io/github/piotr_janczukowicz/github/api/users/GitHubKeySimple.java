package io.github.piotr_janczukowicz.github.api.users;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.time.Instant;
import org.jspecify.annotations.Nullable;

/**
 * Key Simple.
 *
 * @param id Id
 * @param key Key
 * @param createdAt Created at
 * @param lastUsed Last used
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("components/schemas/key-simple")
@RecordBuilder
public record GitHubKeySimple(
        long id,
        String key,
        @Nullable Instant createdAt,
        @Nullable Instant lastUsed) {

    public GitHubKeySimple {
        requireNonNull(key, "key");
    }
}
