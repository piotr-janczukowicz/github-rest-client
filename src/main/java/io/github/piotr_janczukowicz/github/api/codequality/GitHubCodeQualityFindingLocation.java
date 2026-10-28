package io.github.piotr_janczukowicz.github.api.codequality;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Code quality file location
 *
 * @param path The file path where the finding was detected.
 * @param startLine The line number where the finding starts.
 * @param startColumn The column number where the finding starts.
 * @param endLine The line number where the finding ends.
 * @param endColumn The column number where the finding ends.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/code-quality-finding-location")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubCodeQualityFindingLocation(
        String path,
        @Nullable Integer startLine,
        @Nullable Integer startColumn,
        @Nullable Integer endLine,
        @Nullable Integer endColumn) {

    public GitHubCodeQualityFindingLocation {
        requireNonNull(path, "path");
    }
}
