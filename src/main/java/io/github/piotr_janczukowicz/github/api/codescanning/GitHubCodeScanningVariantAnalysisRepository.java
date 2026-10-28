package io.github.piotr_janczukowicz.github.api.codescanning;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.time.Instant;
import org.jspecify.annotations.Nullable;

/**
 * Repository Identifier
 *
 * @param id A unique identifier of the repository.
 * @param name The name of the repository.
 * @param fullName The full, globally unique, name of the repository.
 * @param isPrivate Whether the repository is private.
 * @param stargazersCount Stargazers count.
 * @param updatedAt Updated at.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/code-scanning-variant-analysis-repository")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubCodeScanningVariantAnalysisRepository(
        long id,
        String name,
        String fullName,
        @JsonProperty("private") boolean isPrivate,
        int stargazersCount,
        @Nullable Instant updatedAt) {
    public GitHubCodeScanningVariantAnalysisRepository {
        requireNonNull(name, "name");
        requireNonNull(fullName, "fullName");
    }
}
