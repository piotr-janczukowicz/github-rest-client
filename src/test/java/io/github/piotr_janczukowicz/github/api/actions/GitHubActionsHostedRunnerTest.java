package io.github.piotr_janczukowicz.github.api.actions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.time.Instant;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubActionsHostedRunnerTest {

    @Test
    void constructor_shouldThrowNullPointerException_whenNameNull() {
        final int id = 1;
        final Long runnerGroupId = 1L;
        final GitHubActionsHostedRunnerPoolImage imageDetails = ActionsCatalogFixtures.actionsHostedRunnerPoolImage();
        final GitHubActionsHostedRunnerMachineSpec machineSizeDetails =
                ActionsCatalogFixtures.actionsHostedRunnerMachineSpec();
        final GitHubHostedRunnerStatus status = GitHubHostedRunnerStatus.READY;
        final String platform = "platform-sample";
        final Integer maximumRunners = 1;
        final boolean publicIpEnabled = true;
        final List<GitHubPublicIp> publicIps = List.of(ActionsCatalogFixtures.publicIp());
        final Instant lastActiveOn = Instant.parse("2024-01-02T03:04:05Z");
        final Boolean imageGen = true;

        final Throwable thrown = catchThrowable(() -> new GitHubActionsHostedRunner(
                id,
                null,
                runnerGroupId,
                imageDetails,
                machineSizeDetails,
                status,
                platform,
                maximumRunners,
                publicIpEnabled,
                publicIps,
                lastActiveOn,
                imageGen));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("name");
    }

    @Test
    void constructor_shouldThrowNullPointerException_whenMachineSizeDetailsNull() {
        final int id = 1;
        final String name = "name-sample";
        final Long runnerGroupId = 1L;
        final GitHubActionsHostedRunnerPoolImage imageDetails = ActionsCatalogFixtures.actionsHostedRunnerPoolImage();
        final GitHubHostedRunnerStatus status = GitHubHostedRunnerStatus.READY;
        final String platform = "platform-sample";
        final Integer maximumRunners = 1;
        final boolean publicIpEnabled = true;
        final List<GitHubPublicIp> publicIps = List.of(ActionsCatalogFixtures.publicIp());
        final Instant lastActiveOn = Instant.parse("2024-01-02T03:04:05Z");
        final Boolean imageGen = true;

        final Throwable thrown = catchThrowable(() -> new GitHubActionsHostedRunner(
                id,
                name,
                runnerGroupId,
                imageDetails,
                null,
                status,
                platform,
                maximumRunners,
                publicIpEnabled,
                publicIps,
                lastActiveOn,
                imageGen));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("machineSizeDetails");
    }

    @Test
    void constructor_shouldThrowNullPointerException_whenStatusNull() {
        final int id = 1;
        final String name = "name-sample";
        final Long runnerGroupId = 1L;
        final GitHubActionsHostedRunnerPoolImage imageDetails = ActionsCatalogFixtures.actionsHostedRunnerPoolImage();
        final GitHubActionsHostedRunnerMachineSpec machineSizeDetails =
                ActionsCatalogFixtures.actionsHostedRunnerMachineSpec();
        final String platform = "platform-sample";
        final Integer maximumRunners = 1;
        final boolean publicIpEnabled = true;
        final List<GitHubPublicIp> publicIps = List.of(ActionsCatalogFixtures.publicIp());
        final Instant lastActiveOn = Instant.parse("2024-01-02T03:04:05Z");
        final Boolean imageGen = true;

        final Throwable thrown = catchThrowable(() -> new GitHubActionsHostedRunner(
                id,
                name,
                runnerGroupId,
                imageDetails,
                machineSizeDetails,
                null,
                platform,
                maximumRunners,
                publicIpEnabled,
                publicIps,
                lastActiveOn,
                imageGen));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("status");
    }

    @Test
    void constructor_shouldThrowNullPointerException_whenPlatformNull() {
        final int id = 1;
        final String name = "name-sample";
        final Long runnerGroupId = 1L;
        final GitHubActionsHostedRunnerPoolImage imageDetails = ActionsCatalogFixtures.actionsHostedRunnerPoolImage();
        final GitHubActionsHostedRunnerMachineSpec machineSizeDetails =
                ActionsCatalogFixtures.actionsHostedRunnerMachineSpec();
        final GitHubHostedRunnerStatus status = GitHubHostedRunnerStatus.READY;
        final Integer maximumRunners = 1;
        final boolean publicIpEnabled = true;
        final List<GitHubPublicIp> publicIps = List.of(ActionsCatalogFixtures.publicIp());
        final Instant lastActiveOn = Instant.parse("2024-01-02T03:04:05Z");
        final Boolean imageGen = true;

        final Throwable thrown = catchThrowable(() -> new GitHubActionsHostedRunner(
                id,
                name,
                runnerGroupId,
                imageDetails,
                machineSizeDetails,
                status,
                null,
                maximumRunners,
                publicIpEnabled,
                publicIps,
                lastActiveOn,
                imageGen));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("platform");
    }

    @Test
    void constructor_shouldAcceptNullPublicIps_whenPublicIpsNull() {
        final GitHubActionsHostedRunner hostedRunner = GitHubActionsHostedRunnerBuilder.builder(
                        ActionsCatalogFixtures.actionsHostedRunner())
                .publicIps(null)
                .build();

        assertThat(hostedRunner.publicIps()).isNull();
    }
}
