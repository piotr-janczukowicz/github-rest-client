package io.github.piotr_janczukowicz.github.api.actions;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.time.Instant;
import org.jspecify.annotations.Nullable;

/**
 * A step of a job.
 *
 * @param status The phase of the lifecycle that the job step is currently in
 * @param conclusion The outcome of the job step
 * @param name The name of the job step
 * @param number The position of the step in the job
 * @param startedAt The time that the step started
 * @param completedAt The time that the step finished
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/job/properties/steps/items")
public record GitHubJobStep(
        GitHubJobStepStatus status,
        @Nullable String conclusion,
        String name,
        int number,
        @Nullable Instant startedAt,
        @Nullable Instant completedAt) {

    public GitHubJobStep {
        requireNonNull(status, "status");
        requireNonNull(name, "name");
    }
}
