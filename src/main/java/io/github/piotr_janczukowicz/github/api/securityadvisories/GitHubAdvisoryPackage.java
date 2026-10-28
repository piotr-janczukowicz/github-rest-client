package io.github.piotr_janczukowicz.github.api.securityadvisories;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * The name of the package affected by the vulnerability.
 *
 * @param ecosystem The package's language or package management ecosystem.
 * @param name The unique package name within its ecosystem.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema({
    "components/schemas/repository-advisory-vulnerability/properties/package",
    "components/schemas/vulnerability/properties/package"
})
@RecordBuilder
public record GitHubAdvisoryPackage(
        GitHubSecurityAdvisoryEcosystem ecosystem, @Nullable String name) {

    public GitHubAdvisoryPackage {
        requireNonNull(ecosystem, "ecosystem");
    }
}
