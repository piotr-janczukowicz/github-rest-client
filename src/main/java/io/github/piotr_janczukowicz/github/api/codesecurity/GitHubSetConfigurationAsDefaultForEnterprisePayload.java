package io.github.piotr_janczukowicz.github.api.codesecurity;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Set a code security configuration as a default for an enterprise.
 *
 * @param defaultForNewRepos Specify which types of repository this security configuration should be applied to by
 *     default.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("operations/code-security/set-configuration-as-default-for-enterprise/requestBody")
@RecordBuilder
public record GitHubSetConfigurationAsDefaultForEnterprisePayload(
        @Nullable GitHubDefaultForNewRepos defaultForNewRepos) {}
