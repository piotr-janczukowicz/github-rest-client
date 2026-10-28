package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * GitHubPreReceiveHookScriptRepository
 *
 * @param id id
 * @param fullName full name
 * @param url url
 * @param htmlUrl html url
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/pre-receive-hook/properties/script_repository")
@Flavors(GitHubFlavor.GHES)
public record GitHubPreReceiveHookScriptRepository(
        @Nullable Integer id,
        @Nullable String fullName,
        @Nullable String url,
        @Nullable String htmlUrl) {}
