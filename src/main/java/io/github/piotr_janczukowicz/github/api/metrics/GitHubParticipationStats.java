package io.github.piotr_janczukowicz.github.api.metrics;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;

/**
 * Participation stats.
 *
 * @param all All commits
 * @param owner Owner commits
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/participation-stats")
public record GitHubParticipationStats(List<Integer> all, List<Integer> owner) {

    public GitHubParticipationStats {
        requireNonNull(all, "all");
        requireNonNull(owner, "owner");
    }
}
