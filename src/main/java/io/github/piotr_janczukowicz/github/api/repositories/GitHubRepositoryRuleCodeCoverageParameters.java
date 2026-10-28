package io.github.piotr_janczukowicz.github.api.repositories;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireBetweenIfPresent;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Parameters of the code coverage rule: line coverage thresholds for pull requests.
 *
 * @param maxCoverageDrop The maximum percentage points that line coverage may drop relative to the default branch. Pull
 *     requests that reduce line coverage by more than this amount will be blocked
 * @param minimumCoverage The absolute minimum line coverage percentage required. Pull requests with line coverage below
 *     this threshold will be blocked
 */
@CatalogSchema("components/schemas/repository-rule-code-coverage/properties/parameters")
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubRepositoryRuleCodeCoverageParameters(
        @Nullable Double maxCoverageDrop, @Nullable Double minimumCoverage) {

    private static final double MAX_PERCENT = 100.0;

    public GitHubRepositoryRuleCodeCoverageParameters {
        requireBetweenIfPresent(maxCoverageDrop, 0.0, MAX_PERCENT, "maxCoverageDrop");
        requireBetweenIfPresent(minimumCoverage, 0.0, MAX_PERCENT, "minimumCoverage");
    }
}
