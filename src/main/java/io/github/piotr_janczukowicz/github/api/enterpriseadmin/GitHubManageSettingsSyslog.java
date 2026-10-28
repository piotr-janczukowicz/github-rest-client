package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * GitHubManageSettingsSyslog
 *
 * @param enabled enabled
 * @param server server
 * @param protocolName protocol name
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/ghes-get-settings/properties/syslog")
@Flavors(GitHubFlavor.GHES)
public record GitHubManageSettingsSyslog(
        @Nullable Boolean enabled,
        @Nullable String server,
        @Nullable String protocolName) {}
