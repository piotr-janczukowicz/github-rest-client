package io.github.piotr_janczukowicz.github.api.actions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubRunnerGroupsOrgTest {

    @Test
    void constructor_shouldThrowNullPointerException_whenNameNull() {
        final double id = 1.0;
        final String visibility = "visibility-sample";
        final boolean isDefault = true;
        final String selectedRepositoriesUrl = "https://api.github.com/example";
        final String runnersUrl = "https://api.github.com/example";
        final String hostedRunnersUrl = "https://api.github.com/example";
        final String networkConfigurationId = "networkConfigurationId-sample";
        final boolean inherited = true;
        final Boolean inheritedAllowsPublicRepositories = true;
        final boolean allowsPublicRepositories = true;
        final Boolean workflowRestrictionsReadOnly = true;
        final Boolean restrictedToWorkflows = true;
        final List<String> selectedWorkflows = List.of("selectedWorkflows-sample");

        final Throwable thrown = catchThrowable(() -> new GitHubRunnerGroupsOrg(
                id,
                null,
                visibility,
                isDefault,
                selectedRepositoriesUrl,
                runnersUrl,
                hostedRunnersUrl,
                networkConfigurationId,
                inherited,
                inheritedAllowsPublicRepositories,
                allowsPublicRepositories,
                workflowRestrictionsReadOnly,
                restrictedToWorkflows,
                selectedWorkflows));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("name");
    }

    @Test
    void constructor_shouldThrowNullPointerException_whenVisibilityNull() {
        final double id = 1.0;
        final String name = "name-sample";
        final boolean isDefault = true;
        final String selectedRepositoriesUrl = "https://api.github.com/example";
        final String runnersUrl = "https://api.github.com/example";
        final String hostedRunnersUrl = "https://api.github.com/example";
        final String networkConfigurationId = "networkConfigurationId-sample";
        final boolean inherited = true;
        final Boolean inheritedAllowsPublicRepositories = true;
        final boolean allowsPublicRepositories = true;
        final Boolean workflowRestrictionsReadOnly = true;
        final Boolean restrictedToWorkflows = true;
        final List<String> selectedWorkflows = List.of("selectedWorkflows-sample");

        final Throwable thrown = catchThrowable(() -> new GitHubRunnerGroupsOrg(
                id,
                name,
                null,
                isDefault,
                selectedRepositoriesUrl,
                runnersUrl,
                hostedRunnersUrl,
                networkConfigurationId,
                inherited,
                inheritedAllowsPublicRepositories,
                allowsPublicRepositories,
                workflowRestrictionsReadOnly,
                restrictedToWorkflows,
                selectedWorkflows));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("visibility");
    }

    @Test
    void constructor_shouldThrowNullPointerException_whenRunnersUrlNull() {
        final double id = 1.0;
        final String name = "name-sample";
        final String visibility = "visibility-sample";
        final boolean isDefault = true;
        final String selectedRepositoriesUrl = "https://api.github.com/example";
        final String hostedRunnersUrl = "https://api.github.com/example";
        final String networkConfigurationId = "networkConfigurationId-sample";
        final boolean inherited = true;
        final Boolean inheritedAllowsPublicRepositories = true;
        final boolean allowsPublicRepositories = true;
        final Boolean workflowRestrictionsReadOnly = true;
        final Boolean restrictedToWorkflows = true;
        final List<String> selectedWorkflows = List.of("selectedWorkflows-sample");

        final Throwable thrown = catchThrowable(() -> new GitHubRunnerGroupsOrg(
                id,
                name,
                visibility,
                isDefault,
                selectedRepositoriesUrl,
                null,
                hostedRunnersUrl,
                networkConfigurationId,
                inherited,
                inheritedAllowsPublicRepositories,
                allowsPublicRepositories,
                workflowRestrictionsReadOnly,
                restrictedToWorkflows,
                selectedWorkflows));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("runnersUrl");
    }

    @Test
    void constructor_shouldAcceptNullSelectedWorkflows_whenSelectedWorkflowsNull() {
        final GitHubRunnerGroupsOrg runnerGroup = GitHubRunnerGroupsOrgBuilder.builder(
                        ActionsCatalogFixtures.runnerGroupsOrg())
                .selectedWorkflows(null)
                .build();

        assertThat(runnerGroup.selectedWorkflows()).isNull();
    }
}
