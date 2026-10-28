package io.github.piotr_janczukowicz.github.api.actions;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * Actions OIDC subject customization for a repository.
 *
 * @param useDefault Whether to use the default template or not
 * @param includeClaimKeys Array of unique strings, each claim key can only contain alphanumeric characters and
 *     underscores
 * @param useImmutableSubject Whether to opt in to the immutable OIDC subject claim format for this repository
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/actions/set-custom-oidc-sub-claim-for-repo/requestBody")
public record GitHubSetCustomOidcSubClaimForRepoPayload(
        boolean useDefault,
        @Nullable List<String> includeClaimKeys,
        @Nullable Boolean useImmutableSubject) {

    public GitHubSetCustomOidcSubClaimForRepoPayload {
        if (includeClaimKeys != null) {
            includeClaimKeys = List.copyOf(includeClaimKeys);
        }
    }
}
