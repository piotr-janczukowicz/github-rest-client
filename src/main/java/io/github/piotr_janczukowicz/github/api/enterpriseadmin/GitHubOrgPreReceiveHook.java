package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * GitHubOrgPreReceiveHook
 *
 * @param id id
 * @param name name
 * @param enforcement enforcement
 * @param configurationUrl configuration url
 * @param allowDownstreamConfiguration allow downstream configuration
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/org-pre-receive-hook")
@Flavors(GitHubFlavor.GHES)
public record GitHubOrgPreReceiveHook(
        @Nullable Integer id,
        @Nullable String name,
        @Nullable String enforcement,
        @Nullable String configurationUrl,
        @Nullable Boolean allowDownstreamConfiguration) {}
