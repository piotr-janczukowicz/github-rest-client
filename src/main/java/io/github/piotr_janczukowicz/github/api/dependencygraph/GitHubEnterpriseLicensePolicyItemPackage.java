package io.github.piotr_janczukowicz.github.api.dependencygraph;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.time.Instant;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * A package rule of the enterprise license policy.
 *
 * @param packageManager The package manager of the package
 * @param name The name of the package
 * @param action Whether the package is allowed or private
 * @param reason The reason for the package rule
 * @param extraAllowedSpdxIds Additional SPDX identifiers allowed for the package
 * @param addedAt The date and time the package rule was added to the policy
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/enterprise-license-policy-item/properties/packages/items")
@Flavors(GitHubFlavor.GHEC)
public record GitHubEnterpriseLicensePolicyItemPackage(
        GitHubEnterpriseLicensePolicyItemPackageManager packageManager,
        String name,
        GitHubEnterpriseLicensePolicyItemPackageAction action,
        String reason,
        List<String> extraAllowedSpdxIds,
        @Nullable Instant addedAt) {

    public GitHubEnterpriseLicensePolicyItemPackage {
        requireNonNull(packageManager, "packageManager");
        requireNonNull(name, "name");
        requireNonNull(action, "action");
        requireNonNull(reason, "reason");
        requireNonNull(extraAllowedSpdxIds, "extraAllowedSpdxIds");
        extraAllowedSpdxIds = List.copyOf(extraAllowedSpdxIds);
    }
}
