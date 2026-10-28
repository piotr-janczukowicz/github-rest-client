package io.github.piotr_janczukowicz.github.api.actions;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * Actions OIDC subject customization.
 *
 * @param includeClaimKeys Unique claim keys included in the OIDC subject
 * @param useImmutableSubject Whether to opt in to the immutable OIDC subject claim format
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/oidc-custom-sub")
public record GitHubOidcCustomSub(
        List<String> includeClaimKeys, @Nullable Boolean useImmutableSubject) {

    public GitHubOidcCustomSub {
        requireNonNull(includeClaimKeys, "includeClaimKeys");
    }
}
