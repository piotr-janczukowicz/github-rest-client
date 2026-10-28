package io.github.piotr_janczukowicz.github.api.securityadvisories;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * The name of the package affected by the vulnerability.
 *
 * @param ecosystem The package's language or package management ecosystem.
 * @param name The unique package name within its ecosystem.
 */
@CatalogSchema({
    "components/schemas/repository-advisory-create/properties/vulnerabilities/items/properties/package",
    "components/schemas/repository-advisory-update/properties/vulnerabilities/items/properties/package",
    "components/schemas/private-vulnerability-report-create/properties/vulnerabilities/items/properties/package"
})
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubRepositoryAdvisoryPackagePayload(
        GitHubSecurityAdvisoryEcosystem ecosystem, @Nullable String name) {

    public GitHubRepositoryAdvisoryPackagePayload {
        requireNonNull(ecosystem, "ecosystem");
    }
}
