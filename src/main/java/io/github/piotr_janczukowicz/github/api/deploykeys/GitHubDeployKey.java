package io.github.piotr_janczukowicz.github.api.deploykeys;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.time.Instant;
import org.jspecify.annotations.Nullable;

/**
 * An SSH key granting access to a single repository.
 *
 * @param id Id
 * @param key Key
 * @param url Url
 * @param title Title
 * @param verified Verified
 * @param createdAt Created at
 * @param readOnly Read only
 * @param addedBy Added by
 * @param lastUsed Last used
 * @param enabled Enabled
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("components/schemas/deploy-key")
@RecordBuilder
public record GitHubDeployKey(
        long id,
        String key,
        String url,
        String title,
        boolean verified,
        String createdAt,
        boolean readOnly,
        @Nullable String addedBy,
        @Nullable Instant lastUsed,
        @Nullable Boolean enabled) {

    public GitHubDeployKey {
        requireNonNull(key, "key");
        requireNonNull(url, "url");
        requireNonNull(title, "title");
        requireNonNull(createdAt, "createdAt");
    }
}
