package io.github.piotr_janczukowicz.github.api.dependencygraph;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * The CI job that produced a dependency snapshot.
 *
 * @param id The external ID of the job.
 * @param correlator Correlator provides a key that is used to group snapshots submitted over time. Only the "latest"
 *     submitted snapshot for a given combination of {@code job.correlator} and {@code detector.name} will be considered
 *     when calculating a repository's current dependencies. Correlator should be as unique as it takes to distinguish
 *     all detection runs for a given "wave" of CI workflow you run. If you're using GitHub Actions, a good default
 *     value for this could be the environment variables GITHUB_WORKFLOW and GITHUB_JOB concatenated together. If you're
 *     using a build matrix, then you'll also need to add additional key(s) to distinguish between each submission
 *     inside a matrix variation.
 * @param htmlUrl The url for the job.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("components/schemas/snapshot/properties/job")
@RecordBuilder
public record GitHubSnapshotJob(
        String id, String correlator, @Nullable String htmlUrl) {

    public GitHubSnapshotJob {
        requireNonNull(id, "id");
        requireNonNull(correlator, "correlator");
    }
}
