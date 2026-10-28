package io.github.piotr_janczukowicz.github.api.projects;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.time.Instant;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * Field inside a projects v2 project.
 *
 * @param id Id
 * @param issueFieldId Issue field id
 * @param nodeId Node id
 * @param projectUrl Project url
 * @param name Name
 * @param dataType Data type
 * @param options Single select options
 * @param configuration Iteration configuration
 * @param createdAt Created at
 * @param updatedAt Updated at
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/projects-v2-field")
public record GitHubProjectField(
        long id,
        @Nullable Long issueFieldId,
        @Nullable String nodeId,
        String projectUrl,
        String name,
        GitHubProjectFieldDataType dataType,
        @Nullable List<GitHubProjectSingleSelectOption> options,
        @Nullable GitHubProjectFieldConfiguration configuration,
        Instant createdAt,
        Instant updatedAt) {

    public GitHubProjectField {
        requireNonNull(projectUrl, "projectUrl");
        requireNonNull(name, "name");
        requireNonNull(dataType, "dataType");
        requireNonNull(createdAt, "createdAt");
        requireNonNull(updatedAt, "updatedAt");
    }
}
