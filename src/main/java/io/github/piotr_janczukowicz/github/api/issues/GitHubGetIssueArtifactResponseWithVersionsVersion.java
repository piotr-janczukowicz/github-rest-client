package io.github.piotr_janczukowicz.github.api.issues;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.time.Instant;
import org.jspecify.annotations.Nullable;

/**
 * One version of an issue artifact.
 *
 * @param version The sequential version number.
 * @param name The artifact name at this version.
 * @param body The artifact content at this version.
 * @param bodyHtml The artifact content at this version rendered as HTML, or {@code null} if rendering times out or is
 *     skipped to protect the response's time budget. The raw content remains available in {@code body}.
 * @param createdAt The time this version was created.
 * @param actor The user or integration that created this version.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/issues/get-issue-artifact/200/oneOf/1/properties/versions/items")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubGetIssueArtifactResponseWithVersionsVersion(
        int version,
        String name,
        String body,
        @Nullable String bodyHtml,
        @Nullable Instant createdAt,
        @Nullable GitHubGetIssueArtifactResponseWithVersionsVersionActor actor) {
    public GitHubGetIssueArtifactResponseWithVersionsVersion {
        requireNonNull(name, "name");
        requireNonNull(body, "body");
    }
}
