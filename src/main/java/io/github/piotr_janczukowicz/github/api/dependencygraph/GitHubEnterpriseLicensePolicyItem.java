package io.github.piotr_janczukowicz.github.api.dependencygraph;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;

/**
 * The enterprise license policy: the allowed licenses and the package rules.
 *
 * @param licenses The licenses the enterprise allows
 * @param packages The package rules of the enterprise
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/enterprise-license-policy-item")
@Flavors(GitHubFlavor.GHEC)
public record GitHubEnterpriseLicensePolicyItem(
        GitHubEnterpriseLicensePolicyItemLicenses licenses, List<GitHubEnterpriseLicensePolicyItemPackage> packages) {

    public GitHubEnterpriseLicensePolicyItem {
        requireNonNull(licenses, "licenses");
        requireNonNull(packages, "packages");
        packages = List.copyOf(packages);
    }
}
