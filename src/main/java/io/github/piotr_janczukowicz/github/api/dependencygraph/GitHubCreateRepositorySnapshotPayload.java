package io.github.piotr_janczukowicz.github.api.dependencygraph;

import static io.github.piotr_janczukowicz.github.api.GitHubSchemaPatterns.GIT_REF;
import static io.github.piotr_janczukowicz.util.ValidationUtils.requireMatches;
import static io.github.piotr_janczukowicz.util.ValidationUtils.requireMaxLength;
import static io.github.piotr_janczukowicz.util.ValidationUtils.requireMinLength;
import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;
import static io.github.piotr_janczukowicz.util.ValidationUtils.requireSizeAtMost;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.openapi.model.CatalogSimpleXor;
import java.time.Instant;
import java.util.Map;
import org.jspecify.annotations.Nullable;

/**
 * Create a new snapshot of a repository's dependencies.
 *
 * @param version The version of the repository snapshot submission.
 * @param job The CI job that produced the snapshot.
 * @param sha The commit SHA associated with this dependency snapshot. Maximum length: 64 characters.
 * @param ref The repository branch that triggered this snapshot.
 * @param detector A description of the detector used.
 * @param metadata User-defined metadata to store domain-specific information limited to 8 keys with scalar values.
 * @param manifests A collection of package manifests, which are a collection of related dependencies declared in a file
 *     or representing a logical group of dependencies.
 * @param scanned The time at which the snapshot was scanned.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema({"components/schemas/snapshot", "operations/dependency-graph/create-repository-snapshot/requestBody"})
@RecordBuilder
public record GitHubCreateRepositorySnapshotPayload(
        int version,
        GitHubSnapshotJob job,
        String sha,
        String ref,
        GitHubSnapshotDetector detector,
        @Nullable @CatalogSimpleXor("string|number|boolean") Map<String, String> metadata,
        @Nullable Map<String, GitHubManifest> manifests,
        Instant scanned) {

    public GitHubCreateRepositorySnapshotPayload {
        requireNonNull(job, "job");
        sha = requireMaxLength(requireMinLength(sha, 40, "sha"), 64, "sha");
        ref = requireMatches(ref, GIT_REF, "ref");
        requireNonNull(detector, "detector");
        requireNonNull(scanned, "scanned");
        if (metadata != null) {
            metadata = Map.copyOf(requireSizeAtMost(metadata, 8, "metadata"));
        }
        if (manifests != null) {
            manifests = Map.copyOf(manifests);
        }
    }
}
