package io.github.piotr_janczukowicz.github.api.dependencygraph;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.time.Instant;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * An enterprise license policy as GitHub returns it, with its creation date.
 *
 * @param createdAt The date and time the license policy was created
 * @param licenses The licenses the enterprise allows
 * @param packages The package rules of the enterprise
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/enterprise-license-policy-response")
@Flavors(GitHubFlavor.GHEC)
public record GitHubEnterpriseLicensePolicyResponse(
        @Nullable Instant createdAt,
        @Nullable GitHubEnterpriseLicensePolicyItemLicenses licenses,
        @Nullable List<GitHubEnterpriseLicensePolicyItemPackage> packages) {}
