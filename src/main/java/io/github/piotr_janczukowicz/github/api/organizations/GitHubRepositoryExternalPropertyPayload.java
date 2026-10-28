package io.github.piotr_janczukowicz.github.api.organizations;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Repository name and associated external custom property value.
 *
 * @param repositoryName The name of the repository
 * @param value The value assigned to the repository. Set to {@code null} to unset the value for this repository
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/repository-external-property-payload")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubRepositoryExternalPropertyPayload(
        String repositoryName,
        @JsonInclude(JsonInclude.Include.ALWAYS) @Nullable String value) {

    public GitHubRepositoryExternalPropertyPayload {
        requireNonNull(repositoryName, "repositoryName");
    }
}
