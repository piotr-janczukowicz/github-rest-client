package io.github.piotr_janczukowicz.github.api.actions;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * Static public IP address limits for GitHub-hosted hosted runners.
 *
 * @param maximum The maximum number of static public IP addresses that can be used for hosted runners
 * @param currentUsage The current number of static public IP addresses in use by hosted runners
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/actions-hosted-runner-limits/properties/public_ips")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubActionsHostedRunnerLimitsPublicIps(int maximum, int currentUsage) {}
