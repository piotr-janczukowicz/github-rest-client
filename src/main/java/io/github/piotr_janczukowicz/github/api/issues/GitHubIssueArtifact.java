package io.github.piotr_janczukowicz.github.api.issues;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.time.Instant;
import org.jspecify.annotations.Nullable;

/**
 * Issue artifacts are named, typed pieces of content attached to an issue. They give people and agents a durable place
 * to preserve context and supporting material alongside the work. Each artifact has a number unique within the issue.
 *
 * @param id The globally unique identifier of the artifact.
 * @param htmlUrl The canonical browser URL for the artifact in its issue context.
 * @param number The artifact number within the issue.
 * @param type The artifact type. The types supported for external creation and filtering are:
 *     <ul>
 *       <li>{@code generic} is a general-purpose Markdown document.
 *       <li>{@code link} represents a named link.
 *       <li>{@code plan} is a Markdown plan.
 *       <li>{@code embedded_html} is a self-contained HTML document. Its {@code body} contains the HTML source.
 *     </ul>
 *
 * @param name The human-readable name of the artifact.
 * @param body The artifact content. For {@code embedded_html} artifacts, this is the HTML source.
 * @param bodyHtml The artifact content rendered as HTML, or {@code null} if rendering times out or is skipped to
 *     protect the response's time budget. The raw content remains available in {@code body}.
 * @param description A short, generated summary of the artifact.
 * @param creator The user or integration that created the artifact.
 * @param updatedByActor The user or integration that most recently updated the artifact.
 * @param createdAt The time the artifact was created.
 * @param updatedAt The time the artifact was last updated.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/issue-artifact")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
// The implemented sealed interface carries the deduction; this type itself decodes as a plain record.
@JsonTypeInfo(use = JsonTypeInfo.Id.NONE)
public record GitHubIssueArtifact(
        long id,
        @Nullable String htmlUrl,
        int number,
        String type,
        String name,
        String body,
        @Nullable String bodyHtml,
        String description,
        @Nullable GitHubIssueArtifactCreator creator,
        @Nullable GitHubIssueArtifactUpdatedByActor updatedByActor,
        @Nullable Instant createdAt,
        @Nullable Instant updatedAt)
        implements GitHubGetIssueArtifactResponse {
    public GitHubIssueArtifact {
        requireNonNull(type, "type");
        requireNonNull(name, "name");
        requireNonNull(body, "body");
        requireNonNull(description, "description");
    }
}
