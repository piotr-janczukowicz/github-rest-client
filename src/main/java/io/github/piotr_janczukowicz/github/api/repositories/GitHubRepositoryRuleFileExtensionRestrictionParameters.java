package io.github.piotr_janczukowicz.github.api.repositories;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;

/** Parameters of the file extension restriction rule: the file extensions that cannot be pushed. */
@CatalogSchema("components/schemas/repository-rule-file-extension-restriction/properties/parameters")
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
public record GitHubRepositoryRuleFileExtensionRestrictionParameters(List<String> restrictedFileExtensions) {
    public GitHubRepositoryRuleFileExtensionRestrictionParameters {
        restrictedFileExtensions = List.copyOf(requireNonNull(restrictedFileExtensions, "restrictedFileExtensions"));
    }
}
