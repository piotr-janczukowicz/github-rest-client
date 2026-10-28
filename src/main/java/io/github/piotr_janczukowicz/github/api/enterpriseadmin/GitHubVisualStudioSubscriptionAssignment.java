package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Visual Studio subscription and its manual match to an enterprise user.
 *
 * @param visualStudioSubscriptionEmail Email of the Visual Studio subscription
 * @param subscriptionId Identifier of the Visual Studio subscription
 * @param username Username the subscription is matched to
 * @param manualMatch Whether the subscription was matched manually
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/visual-studio-subscription-assignment")
@Flavors(GitHubFlavor.GHEC)
public record GitHubVisualStudioSubscriptionAssignment(
        @Nullable String visualStudioSubscriptionEmail,
        @Nullable String subscriptionId,
        @Nullable String username,
        @Nullable Boolean manualMatch) {}
