package io.github.piotr_janczukowicz.github.api.actions;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * A self-hosted runner.
 *
 * @param id The ID of the runner
 * @param runnerGroupId The ID of the runner group
 * @param name The name of the runner
 * @param os The operating system of the runner
 * @param status The status of the runner
 * @param busy Whether the runner is busy
 * @param labels The labels of the runner
 * @param ephemeral Whether the runner is ephemeral
 * @param version The version of the GitHub Actions runner software
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/runner")
public record GitHubRunner(
        long id,
        @Nullable Long runnerGroupId,
        String name,
        String os,
        String status,
        boolean busy,
        List<GitHubRunnerLabel> labels,
        @Nullable Boolean ephemeral,
        @Nullable String version) {

    public GitHubRunner {
        requireNonNull(name, "name");
        requireNonNull(os, "os");
        requireNonNull(status, "status");
        requireNonNull(labels, "labels");
    }
}
