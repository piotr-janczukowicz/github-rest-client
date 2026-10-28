package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * GitHubGlobalHook
 *
 * @param type type
 * @param id id
 * @param name name
 * @param active active
 * @param events events
 * @param config config
 * @param updatedAt updated at
 * @param createdAt created at
 * @param url url
 * @param pingUrl ping url
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/global-hook")
@Flavors(GitHubFlavor.GHES)
public record GitHubGlobalHook(
        @Nullable String type,
        @Nullable Integer id,
        @Nullable String name,
        @Nullable Boolean active,
        @Nullable List<String> events,
        @Nullable GitHubGlobalHookConfig config,
        @Nullable String updatedAt,
        @Nullable String createdAt,
        @Nullable String url,
        @Nullable String pingUrl) {}
