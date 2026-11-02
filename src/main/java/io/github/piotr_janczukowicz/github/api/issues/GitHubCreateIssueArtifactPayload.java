package io.github.piotr_janczukowicz.github.api.issues;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireMinLength;
import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Request body for creating an issue artifact.
 *
 * @param type The artifact type.
 * @param name The human-readable name of the artifact.
 * @param body The artifact content. For {@code embedded_html} artifacts, provide the HTML source.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/issues/create-issue-artifact/requestBody")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubCreateIssueArtifactPayload(@Nullable GitHubCreateIssueArtifactType type, String name, String body) {
    public GitHubCreateIssueArtifactPayload {
        requireNonNull(name, "name");
        requireMinLength(name, 1, "name");
        requireNonNull(body, "body");
        requireMinLength(body, 1, "body");
    }
}
