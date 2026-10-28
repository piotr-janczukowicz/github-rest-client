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
 * @param includeClaimKeys Unique claim keys included in the OIDC subject
 * @param useImmutableSubject Whether the repository has opted in to the immutable OIDC subject claim format
 * @param subClaimPrefix The current sub claim prefix for this repository
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/oidc-custom-sub-repo")
public record GitHubOidcCustomSubRepo(
        boolean useDefault,
        @Nullable List<String> includeClaimKeys,
        @Nullable Boolean useImmutableSubject,
        @Nullable String subClaimPrefix) {}
