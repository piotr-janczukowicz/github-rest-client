package io.github.piotr_janczukowicz.github.api.issues;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireAtLeastOne;
import static io.github.piotr_janczukowicz.util.ValidationUtils.requireMinLengthIfPresent;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Request body for updating an issue artifact; at least one of {@code name} and {@code body} is required.
 *
 * @param name The new human-readable name of the artifact.
 * @param body The new artifact content.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/issues/update-issue-artifact/requestBody")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubUpdateIssueArtifactPayload(
        @Nullable String name, @Nullable String body) {
    public GitHubUpdateIssueArtifactPayload {
        requireAtLeastOne(name, body, "name or body");
        requireMinLengthIfPresent(name, 1, "name");
        requireMinLengthIfPresent(body, 1, "body");
    }
}
