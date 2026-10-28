package io.github.piotr_janczukowicz.github.api.metrics;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;

/**
 * Commit activity for one week.
 *
 * @param days Days
 * @param total Total
 * @param week Week
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/commit-activity")
public record GitHubCommitActivity(List<Integer> days, long total, long week) {

    public GitHubCommitActivity {
        requireNonNull(days, "days");
    }
}
