package io.github.piotr_janczukowicz.github.api.repositories;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * Parameters of the file path restriction rule: the file paths that cannot be pushed.
 *
 * @param ignoredFilePaths Files matching these patterns are allowed even when they also match a restricted path.
 */
@CatalogSchema("components/schemas/repository-rule-file-path-restriction/properties/parameters")
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
public record GitHubRepositoryRuleFilePathRestrictionParameters(
        List<String> restrictedFilePaths, @Nullable List<String> ignoredFilePaths) {
    public GitHubRepositoryRuleFilePathRestrictionParameters {
        restrictedFilePaths = List.copyOf(requireNonNull(restrictedFilePaths, "restrictedFilePaths"));
        if (ignoredFilePaths != null) {
            ignoredFilePaths = List.copyOf(ignoredFilePaths);
        }
    }
}
