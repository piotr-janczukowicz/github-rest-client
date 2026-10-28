package io.github.piotr_janczukowicz.github.api.codesecurity;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Default code security configuration and the repository types it applies to.
 *
 * @param defaultForNewRepos The visibility of newly created repositories for which the code security configuration will
 *     be applied to by default
 * @param configuration Configuration
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("components/schemas/code-security-default-configurations/items")
@RecordBuilder
public record GitHubCodeSecurityDefaultConfiguration(
        @Nullable GitHubDefaultConfigurationForNewRepos defaultForNewRepos,
        @Nullable GitHubCodeSecurityConfiguration configuration) {}
