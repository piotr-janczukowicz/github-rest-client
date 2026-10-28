package io.github.piotr_janczukowicz.github.api.billing;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;

/**
 * Repository with active GitHub Advanced Security committers.
 *
 * @param name The full name of the repository
 * @param advancedSecurityCommitters The number of active GitHub Advanced Security committers in the repository
 * @param advancedSecurityCommittersBreakdown The active committers of the repository
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/advanced-security-active-committers-repository")
@Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
public record GitHubAdvancedSecurityActiveCommittersRepository(
        String name,
        int advancedSecurityCommitters,
        List<GitHubAdvancedSecurityActiveCommittersUser> advancedSecurityCommittersBreakdown) {

    public GitHubAdvancedSecurityActiveCommittersRepository {
        requireNonNull(name, "name");
        requireNonNull(advancedSecurityCommittersBreakdown, "advancedSecurityCommittersBreakdown");
    }
}
