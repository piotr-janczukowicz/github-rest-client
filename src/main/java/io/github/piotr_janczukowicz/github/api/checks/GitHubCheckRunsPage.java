package io.github.piotr_janczukowicz.github.api.checks;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;

/**
 * List check runs response.
 *
 * @param totalCount Total count
 * @param checkRuns Check runs
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema({"operations/checks/list-for-suite/200", "operations/checks/list-for-ref/200"})
public record GitHubCheckRunsPage(int totalCount, List<GitHubCheckRun> checkRuns) {

    public GitHubCheckRunsPage {
        requireNonNull(checkRuns, "checkRuns");
    }
}
