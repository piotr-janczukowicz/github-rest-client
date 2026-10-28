package io.github.piotr_janczukowicz.github.api.dependencygraph;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.time.Instant;
import org.jspecify.annotations.Nullable;

/**
 * A license the enterprise allows.
 *
 * @param spdxId The SPDX identifier of the license
 * @param addedAt The date and time the license was added to the policy
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/enterprise-license-policy-item/properties/licenses/properties/allowed/items")
@Flavors(GitHubFlavor.GHEC)
public record GitHubEnterpriseLicensePolicyItemLicense(
        String spdxId, @Nullable Instant addedAt) {

    public GitHubEnterpriseLicensePolicyItemLicense {
        requireNonNull(spdxId, "spdxId");
    }
}
