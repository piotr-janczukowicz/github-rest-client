package io.github.piotr_janczukowicz.github.api.codesecurity;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Code security configuration associated with a repository and attachment status.
 *
 * @param status The attachment status of the code security configuration on the repository.
 * @param configuration Configuration
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("components/schemas/code-security-configuration-for-repository")
@RecordBuilder
public record GitHubCodeSecurityConfigurationForRepository(
        @Nullable GitHubCodeSecurityAttachmentStatus status,
        @Nullable GitHubCodeSecurityConfiguration configuration) {}
