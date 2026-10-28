package io.github.piotr_janczukowicz.github.api.actions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubRunnerGroupsEnterpriseTest {

    @Test
    void constructor_shouldThrowNullPointerException_whenNameNull() {
        final GitHubRunnerGroupsEnterprise source = ActionsCatalogFixtures.runnerGroupsEnterprise();

        final Throwable thrown = catchThrowable(() -> new GitHubRunnerGroupsEnterprise(
                source.id(),
                null,
                source.visibility(),
                source.isDefault(),
                source.selectedOrganizationsUrl(),
                source.runnersUrl(),
                source.hostedRunnersUrl(),
                source.networkConfigurationId(),
                source.allowsPublicRepositories(),
                source.workflowRestrictionsReadOnly(),
                source.restrictedToWorkflows(),
                source.selectedWorkflows()));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("name");
    }

    @Test
    void constructor_shouldThrowNullPointerException_whenVisibilityNull() {
        final GitHubRunnerGroupsEnterprise source = ActionsCatalogFixtures.runnerGroupsEnterprise();

        final Throwable thrown = catchThrowable(() -> new GitHubRunnerGroupsEnterprise(
                source.id(),
                source.name(),
                null,
                source.isDefault(),
                source.selectedOrganizationsUrl(),
                source.runnersUrl(),
                source.hostedRunnersUrl(),
                source.networkConfigurationId(),
                source.allowsPublicRepositories(),
                source.workflowRestrictionsReadOnly(),
                source.restrictedToWorkflows(),
                source.selectedWorkflows()));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("visibility");
    }

    @Test
    void constructor_shouldThrowNullPointerException_whenRunnersUrlNull() {
        final GitHubRunnerGroupsEnterprise source = ActionsCatalogFixtures.runnerGroupsEnterprise();

        final Throwable thrown = catchThrowable(() -> new GitHubRunnerGroupsEnterprise(
                source.id(),
                source.name(),
                source.visibility(),
                source.isDefault(),
                source.selectedOrganizationsUrl(),
                null,
                source.hostedRunnersUrl(),
                source.networkConfigurationId(),
                source.allowsPublicRepositories(),
                source.workflowRestrictionsReadOnly(),
                source.restrictedToWorkflows(),
                source.selectedWorkflows()));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("runnersUrl");
    }

    @Test
    void constructor_shouldAcceptNullSelectedWorkflows_whenSelectedWorkflowsNull() {
        final GitHubRunnerGroupsEnterprise runnerGroup = GitHubRunnerGroupsEnterpriseBuilder.builder(
                        ActionsCatalogFixtures.runnerGroupsEnterprise())
                .selectedWorkflows(null)
                .build();

        assertThat(runnerGroup.selectedWorkflows()).isNull();
    }

    @Test
    void accessors_shouldReturnComponents_whenBuilt() {
        final GitHubRunnerGroupsEnterprise value = ActionsCatalogFixtures.runnerGroupsEnterprise();

        assertSoftly(softly -> {
            softly.assertThat(value.id()).isEqualTo(1.0);
            softly.assertThat(value.name()).isEqualTo("name-sample");
            softly.assertThat(value.visibility()).isEqualTo("visibility-sample");
            softly.assertThat(value.isDefault()).isTrue();
            softly.assertThat(value.selectedOrganizationsUrl()).isEqualTo("https://api.github.com/example");
            softly.assertThat(value.runnersUrl()).isEqualTo("https://api.github.com/example");
            softly.assertThat(value.hostedRunnersUrl()).isEqualTo("https://api.github.com/example");
            softly.assertThat(value.networkConfigurationId()).isEqualTo("networkConfigurationId-sample");
            softly.assertThat(value.allowsPublicRepositories()).isTrue();
            softly.assertThat(value.workflowRestrictionsReadOnly()).isTrue();
            softly.assertThat(value.restrictedToWorkflows()).isTrue();
            softly.assertThat(value.selectedWorkflows()).containsExactly("selectedWorkflows-sample");
        });
    }
}
