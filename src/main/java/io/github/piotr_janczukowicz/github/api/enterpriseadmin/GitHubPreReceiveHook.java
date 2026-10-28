package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * GitHubPreReceiveHook
 *
 * @param id id
 * @param name name
 * @param enforcement enforcement
 * @param script script
 * @param scriptRepository script repository
 * @param environment environment
 * @param allowDownstreamConfiguration allow downstream configuration
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/pre-receive-hook")
@Flavors(GitHubFlavor.GHES)
public record GitHubPreReceiveHook(
        @Nullable Integer id,
        @Nullable String name,
        @Nullable String enforcement,
        @Nullable String script,
        @Nullable GitHubPreReceiveHookScriptRepository scriptRepository,
        @Nullable GitHubPreReceiveHookEnvironment environment,
        @Nullable Boolean allowDownstreamConfiguration) {}
