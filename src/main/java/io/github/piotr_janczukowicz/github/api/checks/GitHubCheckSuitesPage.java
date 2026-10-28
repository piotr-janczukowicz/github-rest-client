package io.github.piotr_janczukowicz.github.api.checks;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;

/**
 * List check suites response.
 *
 * @param totalCount Total count
 * @param checkSuites Check suites
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/checks/list-suites-for-ref/200")
public record GitHubCheckSuitesPage(int totalCount, List<GitHubCheckSuite> checkSuites) {

    public GitHubCheckSuitesPage {
        requireNonNull(checkSuites, "checkSuites");
    }
}
