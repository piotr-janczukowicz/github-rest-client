package io.github.piotr_janczukowicz.github.api.actions;

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
 * A GitHub-hosted runner.
 *
 * @param id The unique identifier of the hosted runner
 * @param name The name of the hosted runner
 * @param runnerGroupId The unique identifier of the group that the hosted runner belongs to
 * @param imageDetails The image details of the hosted runner pool
 * @param machineSizeDetails The machine size details of the hosted runner
 * @param status The status of the runner
 * @param platform The operating system of the image
 * @param maximumRunners The maximum amount of hosted runners
 * @param publicIpEnabled Whether public IP is enabled for the hosted runners
 * @param publicIps The public IP ranges when public IP is enabled for the hosted runners
 * @param lastActiveOn The time at which the runner was last used, in ISO 8601 format
 * @param imageGen Whether custom image generation is enabled for the hosted runners
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/actions-hosted-runner")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubActionsHostedRunner(
        long id,
        String name,
        @Nullable Long runnerGroupId,
        @Nullable GitHubActionsHostedRunnerPoolImage imageDetails,
        GitHubActionsHostedRunnerMachineSpec machineSizeDetails,
        GitHubHostedRunnerStatus status,
        String platform,
        @Nullable Integer maximumRunners,
        boolean publicIpEnabled,
        @Nullable List<GitHubPublicIp> publicIps,
        @Nullable Instant lastActiveOn,
        @Nullable Boolean imageGen) {

    public GitHubActionsHostedRunner {
        requireNonNull(name, "name");
        requireNonNull(machineSizeDetails, "machineSizeDetails");
        requireNonNull(status, "status");
        requireNonNull(platform, "platform");
    }
}
