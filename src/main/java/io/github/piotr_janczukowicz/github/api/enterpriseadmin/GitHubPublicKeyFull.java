package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.time.Instant;
import org.jspecify.annotations.Nullable;

/**
 * GitHubPublicKeyFull
 *
 * @param id id
 * @param key key
 * @param userId user id
 * @param repositoryId repository id
 * @param url url
 * @param title title
 * @param readOnly read only
 * @param verified verified
 * @param createdAt created at
 * @param addedBy added by
 * @param lastUsed last used
 * @param enabled enabled
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/public-key-full")
@Flavors(GitHubFlavor.GHES)
public record GitHubPublicKeyFull(
        long id,
        String key,
        @Nullable Integer userId,
        @Nullable Long repositoryId,
        String url,
        String title,
        boolean readOnly,
        boolean verified,
        Instant createdAt,
        @Nullable String addedBy,
        @Nullable Instant lastUsed,
        @Nullable Boolean enabled) {
    public GitHubPublicKeyFull {
        requireNonNull(key, "key");
        requireNonNull(url, "url");
        requireNonNull(title, "title");
        requireNonNull(createdAt, "createdAt");
    }
}
