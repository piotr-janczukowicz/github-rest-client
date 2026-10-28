package io.github.piotr_janczukowicz.github.api.actions;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Create a GitHub-hosted runner for an organization.
 *
 * @param name Name of the runner
 * @param image The image of the runner
 * @param size The machine size of the runner
 * @param runnerGroupId The existing runner group to add this runner to
 * @param maximumRunners The maximum amount of runners to scale up to
 * @param enableStaticIp Whether this runner should be created with a static public IP
 * @param imageGen Whether this runner should be used to generate custom images
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/actions/create-hosted-runner-for-org/requestBody")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubCreateHostedRunnerForOrgPayload(
        String name,
        GitHubCreateHostedRunnerImage image,
        String size,
        long runnerGroupId,
        @Nullable Integer maximumRunners,
        @Nullable Boolean enableStaticIp,
        @Nullable Boolean imageGen) {

    public GitHubCreateHostedRunnerForOrgPayload {
        requireNonNull(name, "name");
        requireNonNull(image, "image");
        requireNonNull(size, "size");
    }
}
