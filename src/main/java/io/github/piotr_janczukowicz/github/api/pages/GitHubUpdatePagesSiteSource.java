package io.github.piotr_janczukowicz.github.api.pages;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Update GitHub Pages site source ({@code anyOf} preset string vs branch/path object).
 *
 * @param preset Preset source branch
 * @param branchAndPath Explicit branch and path
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("operations/repos/update-information-about-pages-site/requestBody/properties/source")
public record GitHubUpdatePagesSiteSource(
        @Nullable GitHubPagesSourcePreset preset, @Nullable BranchAndPath branchAndPath) {

    public GitHubUpdatePagesSiteSource {
        final boolean hasPreset = preset != null;
        final boolean hasBranchAndPath = branchAndPath != null;

        if (hasPreset == hasBranchAndPath) {
            throw new IllegalArgumentException("exactly one of preset or branchAndPath is required");
        }
    }

    /**
     * Source from a preset branch name.
     *
     * @param preset preset branch
     * @return update source
     */
    public static GitHubUpdatePagesSiteSource preset(final GitHubPagesSourcePreset preset) {
        return new GitHubUpdatePagesSiteSource(requireNonNull(preset, "preset"), null);
    }

    /**
     * Source from an explicit branch and path.
     *
     * @param branch branch
     * @param path path
     * @return update source
     */
    public static GitHubUpdatePagesSiteSource branchAndPath(final String branch, final GitHubPagesSourcePath path) {
        return new GitHubUpdatePagesSiteSource(null, new BranchAndPath(branch, path));
    }

    /**
     * Value GitHub sends and expects in JSON.
     *
     * @return preset wire string or branch/path object
     */
    @JsonValue
    public Object jsonValue() {
        if (preset != null) {
            return preset.wireValue();
        }
        return requireNonNull(branchAndPath, "branchAndPath");
    }

    /**
     * Source from a preset wire string.
     *
     * @param value JSON string
     * @return update source
     */
    @JsonCreator
    public static GitHubUpdatePagesSiteSource fromJson(final String value) {
        return preset(GitHubPagesSourcePreset.fromWire(value));
    }

    /**
     * Source from a branch/path object.
     *
     * @param value JSON object
     * @return update source
     */
    @JsonCreator
    public static GitHubUpdatePagesSiteSource fromJson(final BranchAndPath value) {
        return branchAndPath(value.branch(), value.path());
    }

    /**
     * Update the source for the repository. Must include the branch name and path.
     *
     * @param branch The repository branch used to publish your site's source files.
     * @param path The repository directory that includes the source files for the Pages site. Allowed paths are
     *     {@code /} or {@code /docs}.
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @RecordBuilder
    @CatalogSchema("operations/repos/update-information-about-pages-site/requestBody/properties/source/anyOf/1")
    public record BranchAndPath(String branch, GitHubPagesSourcePath path) {

        public BranchAndPath {
            requireNonNull(branch, "branch");
            requireNonNull(path, "path");
        }
    }
}
