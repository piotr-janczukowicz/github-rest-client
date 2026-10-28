package io.github.piotr_janczukowicz.github.api.checks;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubMinimalRepository;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * Check suite configuration preferences for a repository.
 *
 * @param preferences Preferences
 * @param repository Repository
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/check-suite-preference")
public record GitHubCheckSuitePreference(GitHubCheckSuitePreferences preferences, GitHubMinimalRepository repository) {

    public GitHubCheckSuitePreference {
        requireNonNull(preferences, "preferences");
        requireNonNull(repository, "repository");
    }
}
