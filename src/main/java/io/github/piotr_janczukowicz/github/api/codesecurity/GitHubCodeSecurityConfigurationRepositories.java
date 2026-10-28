package io.github.piotr_janczukowicz.github.api.codesecurity;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubSimpleRepository;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Repositories associated with a code security configuration and attachment status.
 *
 * @param status The attachment status of the code security configuration on the repository.
 * @param repository Repository
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("components/schemas/code-security-configuration-repositories")
@RecordBuilder
public record GitHubCodeSecurityConfigurationRepositories(
        @Nullable GitHubCodeSecurityAttachmentStatus status,
        @Nullable GitHubSimpleRepository repository) {}
