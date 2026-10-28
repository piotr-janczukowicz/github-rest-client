package io.github.piotr_janczukowicz.github.api.codesecurity;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Default code security configuration change for an organization.
 *
 * @param defaultForNewRepos Specifies which types of repository this security configuration is applied to by default.
 * @param configuration Configuration
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("operations/code-security/set-configuration-as-default/200")
@RecordBuilder
public record GitHubSetConfigurationAsDefaultResponse(
        @Nullable GitHubDefaultForNewRepos defaultForNewRepos,
        @Nullable GitHubCodeSecurityConfiguration configuration) {}
