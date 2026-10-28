package io.github.piotr_janczukowicz.github.api.copilot;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import org.jspecify.annotations.Nullable;

/** Content exclusion rule entry ({@code anyOf} path string vs {@code ifAnyMatch} vs {@code ifNoneMatch} object). */
@CatalogSchema(
        "operations/copilot/set-copilot-content-exclusion-for-organization/requestBody/additionalProperties/items")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubCopilotContentExclusionRule(
        @JsonIgnore @Nullable String path,
        @JsonProperty("ifAnyMatch") @Nullable IfAnyMatchDetails ifAnyMatch,
        @JsonProperty("ifNoneMatch") @Nullable IfNoneMatchDetails ifNoneMatch) {

    private static final int EXACTLY_ONE = 1;

    public GitHubCopilotContentExclusionRule {
        final int present = (path == null ? 0 : 1) + (ifAnyMatch == null ? 0 : 1) + (ifNoneMatch == null ? 0 : 1);

        if (present != EXACTLY_ONE) {
            throw new IllegalArgumentException("exactly one of path, ifAnyMatch or ifNoneMatch is required");
        }
    }

    /**
     * Rule by file path.
     *
     * @param path path to the file that will be excluded
     * @return rule
     */
    public static GitHubCopilotContentExclusionRule ofPath(final String path) {
        return new GitHubCopilotContentExclusionRule(requireNonNull(path, "path"), null, null);
    }

    /**
     * Rule excluding files when any of the patterns match.
     *
     * @param details rule details
     * @return rule
     */
    public static GitHubCopilotContentExclusionRule ofIfAnyMatch(final IfAnyMatchDetails details) {
        return new GitHubCopilotContentExclusionRule(null, requireNonNull(details, "details"), null);
    }

    /**
     * Rule excluding files when none of the patterns match.
     *
     * @param details rule details
     * @return rule
     */
    public static GitHubCopilotContentExclusionRule ofIfNoneMatch(final IfNoneMatchDetails details) {
        return new GitHubCopilotContentExclusionRule(null, null, requireNonNull(details, "details"));
    }

    /**
     * Rule details that exclude files when any of the patterns match.
     *
     * @param ifAnyMatch patterns to match
     */
    @RecordBuilder
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @CatalogSchema(
            "operations/copilot/set-copilot-content-exclusion-for-organization/requestBody/additionalProperties/items/anyOf/1")
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public record IfAnyMatchDetails(
            @JsonProperty("ifAnyMatch") List<String> ifAnyMatch) {

        public IfAnyMatchDetails {
            ifAnyMatch = List.copyOf(requireNonNull(ifAnyMatch, "ifAnyMatch"));
        }
    }

    /**
     * Rule details that exclude files when none of the patterns match.
     *
     * @param ifNoneMatch patterns that must not match
     */
    @RecordBuilder
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @CatalogSchema(
            "operations/copilot/set-copilot-content-exclusion-for-organization/requestBody/additionalProperties/items/anyOf/2")
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public record IfNoneMatchDetails(
            @JsonProperty("ifNoneMatch") List<String> ifNoneMatch) {

        public IfNoneMatchDetails {
            ifNoneMatch = List.copyOf(requireNonNull(ifNoneMatch, "ifNoneMatch"));
        }
    }
}
