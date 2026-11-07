package io.github.piotr_janczukowicz.github.api.billing;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * The identifier for the resource: {@code login} for a user, {@code name} for an organization, {@code owner} and
 * {@code name} for a repository, or {@code slug} for an enterprise team. The branches overlap on {@code name}, so one
 * record carries the union of their properties.
 *
 * @param login The login name for a user.
 * @param name The name for an organization, or the name of the repository.
 * @param owner The owner of the repository.
 * @param slug The slug for an enterprise team.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/billing-cost-center-membership/properties/resource/properties/identifier")
@Flavors(GitHubFlavor.GHEC)
public record GitHubBillingCostCenterMembershipResourceIdentifier(
        @Nullable String login,
        @Nullable String name,
        @Nullable String owner,
        @Nullable String slug) {}
