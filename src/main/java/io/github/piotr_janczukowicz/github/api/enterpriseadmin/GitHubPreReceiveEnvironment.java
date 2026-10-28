package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * GitHubPreReceiveEnvironment
 *
 * @param id id
 * @param name name
 * @param imageUrl image url
 * @param url url
 * @param htmlUrl html url
 * @param defaultEnvironment default environment
 * @param createdAt created at
 * @param hooksCount hooks count
 * @param download download
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/pre-receive-environment")
@Flavors(GitHubFlavor.GHES)
public record GitHubPreReceiveEnvironment(
        @Nullable Long id,
        @Nullable String name,
        @Nullable String imageUrl,
        @Nullable String url,
        @Nullable String htmlUrl,
        @Nullable Boolean defaultEnvironment,
        @Nullable String createdAt,
        @Nullable Integer hooksCount,
        @Nullable GitHubPreReceiveEnvironmentDownload download) {}
