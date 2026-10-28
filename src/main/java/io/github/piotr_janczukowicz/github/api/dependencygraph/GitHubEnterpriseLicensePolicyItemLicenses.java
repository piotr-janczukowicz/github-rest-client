package io.github.piotr_janczukowicz.github.api.dependencygraph;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;

/**
 * The licenses an enterprise allows.
 *
 * @param allowed The licenses that are allowed
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/enterprise-license-policy-item/properties/licenses")
@Flavors(GitHubFlavor.GHEC)
public record GitHubEnterpriseLicensePolicyItemLicenses(List<GitHubEnterpriseLicensePolicyItemLicense> allowed) {

    public GitHubEnterpriseLicensePolicyItemLicenses {
        requireNonNull(allowed, "allowed");
        allowed = List.copyOf(allowed);
    }
}
