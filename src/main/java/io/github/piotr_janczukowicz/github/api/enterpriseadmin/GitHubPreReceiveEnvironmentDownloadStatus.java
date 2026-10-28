package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * GitHubPreReceiveEnvironmentDownloadStatus
 *
 * @param url url
 * @param state state
 * @param downloadedAt downloaded at
 * @param message message
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/pre-receive-environment-download-status")
@Flavors(GitHubFlavor.GHES)
public record GitHubPreReceiveEnvironmentDownloadStatus(
        @Nullable String url,
        @Nullable String state,
        @Nullable String downloadedAt,
        @Nullable String message) {}
