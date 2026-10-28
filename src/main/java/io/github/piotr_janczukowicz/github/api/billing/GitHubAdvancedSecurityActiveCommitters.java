package io.github.piotr_janczukowicz.github.api.billing;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * Active GitHub Advanced Security committers of an enterprise or organization.
 *
 * @param totalAdvancedSecurityCommitters The total number of active GitHub Advanced Security committers
 * @param totalCount The total number of repositories with active GitHub Advanced Security committers
 * @param maximumAdvancedSecurityCommitters The total number of GitHub Advanced Security licences required if all
 *     repositories were to enable GitHub Advanced Security
 * @param purchasedAdvancedSecurityCommitters The total number of GitHub Advanced Security licences purchased
 * @param repositories Repositories with active GitHub Advanced Security committers
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/advanced-security-active-committers")
@Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
public record GitHubAdvancedSecurityActiveCommitters(
        @Nullable Integer totalAdvancedSecurityCommitters,
        @Nullable Integer totalCount,
        @Nullable Integer maximumAdvancedSecurityCommitters,
        @Nullable Integer purchasedAdvancedSecurityCommitters,
        List<GitHubAdvancedSecurityActiveCommittersRepository> repositories) {

    public GitHubAdvancedSecurityActiveCommitters {
        requireNonNull(repositories, "repositories");
    }
}
