package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * GitHubManageSettingsSnmp
 *
 * @param enabled enabled
 * @param community community
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/ghes-get-settings/properties/snmp")
@Flavors(GitHubFlavor.GHES)
public record GitHubManageSettingsSnmp(
        @Nullable Boolean enabled, @Nullable String community) {}
