package io.github.piotr_janczukowicz.github.api.dependencygraph;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * A dependency change between two commits.
 *
 * @param changeType Change type
 * @param manifest Manifest
 * @param ecosystem Ecosystem
 * @param name Name
 * @param version Version
 * @param packageUrl Package url
 * @param license License
 * @param sourceRepositoryUrl Source repository url
 * @param vulnerabilities Vulnerabilities
 * @param scope Where the dependency is utilized. {@code development} means that the dependency is only utilized in the
 *     development environment. {@code runtime} means that the dependency is utilized at runtime and in the development
 *     environment.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("components/schemas/dependency-graph-diff/items")
@RecordBuilder
public record GitHubDependencyGraphDiffEntry(
        GitHubDependencyGraphDiffChangeType changeType,
        String manifest,
        String ecosystem,
        String name,
        String version,
        @Nullable String packageUrl,
        @Nullable String license,
        @Nullable String sourceRepositoryUrl,
        List<GitHubDependencyGraphDiffVulnerability> vulnerabilities,
        GitHubDependencyGraphDiffScope scope) {

    public GitHubDependencyGraphDiffEntry {
        requireNonNull(changeType, "changeType");
        requireNonNull(manifest, "manifest");
        requireNonNull(ecosystem, "ecosystem");
        requireNonNull(name, "name");
        requireNonNull(version, "version");
        requireNonNull(vulnerabilities, "vulnerabilities");
        requireNonNull(scope, "scope");
    }
}
