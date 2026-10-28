package io.github.piotr_janczukowicz.github.api.codespaces;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.time.Instant;
import org.jspecify.annotations.Nullable;

/**
 * An export of a codespace. Latest export details for a codespace can be fetched with id {@code latest}.
 *
 * @param state State of the latest export.
 * @param completedAt Completion time of the last export operation.
 * @param branch Name of the exported branch.
 * @param sha Git commit SHA of the exported branch.
 * @param id Id for the export details.
 * @param exportUrl Url for fetching export details.
 * @param htmlUrl Web url for the exported branch.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/codespace-export-details")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubCodespaceExportDetails(
        @Nullable String state,
        @Nullable Instant completedAt,
        @Nullable String branch,
        @Nullable String sha,
        @Nullable String id,
        @Nullable String exportUrl,
        @Nullable String htmlUrl) {}
