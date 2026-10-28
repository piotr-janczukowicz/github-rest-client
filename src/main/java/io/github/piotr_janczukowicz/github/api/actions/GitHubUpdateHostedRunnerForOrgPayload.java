package io.github.piotr_janczukowicz.github.api.actions;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Update a GitHub-hosted runner of an organization.
 *
 * @param name Name of the runner
 * @param runnerGroupId The existing runner group to add this runner to
 * @param maximumRunners The maximum amount of runners to scale up to
 * @param enableStaticIp Whether this runner should be updated with a static public IP
 * @param size The machine size of the runner
 * @param imageSource The source type of the runner image to use
 * @param imageId The unique identifier of the runner image
 * @param imageVersion The version of the runner image to deploy
 * @param imageGen Whether to enable image generation for this runner pool
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/actions/update-hosted-runner-for-org/requestBody")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubUpdateHostedRunnerForOrgPayload(
        @Nullable String name,
        @Nullable Long runnerGroupId,
        @Nullable Integer maximumRunners,
        @Nullable Boolean enableStaticIp,
        @Nullable String size,
        @Nullable GitHubHostedRunnerImageSource imageSource,
        @Nullable String imageId,
        @Nullable String imageVersion,
        @Nullable Boolean imageGen) {}
