package io.github.piotr_janczukowicz.github.api.organizations;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Enable or disable a security feature for an organization.
 *
 * @param querySuite CodeQL query suite to be used
 * @deprecated Closing down notice: the ability to enable or disable a security feature for all eligible repositories in
 *     an organization is closing down. Prefer code security configurations. Deprecation date {@code 2024-07-22};
 *     removal date {@code 2025-07-22}.
 */
@Deprecated(since = "2024-07-22")
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/orgs/enable-or-disable-security-product-on-all-org-repos/requestBody")
public record GitHubEnableOrganizationSecurityProductPayload(
        @Nullable GitHubOrganizationSecurityProductQuerySuite querySuite) {}
