package io.github.piotr_janczukowicz.github.api.actions;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;

/**
 * Artifacts of a repository or a workflow run.
 *
 * @param totalCount Total number of artifacts
 * @param artifacts The artifacts
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema({"operations/actions/list-artifacts-for-repo/200", "operations/actions/list-workflow-run-artifacts/200"})
public record GitHubArtifacts(int totalCount, List<GitHubArtifact> artifacts) {

    public GitHubArtifacts {
        requireNonNull(artifacts, "artifacts");
    }
}
