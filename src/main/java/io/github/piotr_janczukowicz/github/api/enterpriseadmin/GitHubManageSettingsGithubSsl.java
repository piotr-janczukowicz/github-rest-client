package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * GitHubManageSettingsGithubSsl
 *
 * @param enabled enabled
 * @param cert cert
 * @param key key
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/ghes-get-settings/properties/github_ssl")
@Flavors(GitHubFlavor.GHES)
public record GitHubManageSettingsGithubSsl(
        @Nullable Boolean enabled,
        @Nullable String cert,
        @Nullable String key) {}
