package io.github.piotr_janczukowicz.github.json;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.github.api.actions.GitHubPendingDeploymentReviewer;
import io.github.piotr_janczukowicz.github.api.actions.GitHubPendingDeploymentReviewerEntry;
import io.github.piotr_janczukowicz.github.api.apps.GitHubIntegrationInstallationRequestAccount;
import io.github.piotr_janczukowicz.github.api.apps.GitHubIntegrationOwner;
import io.github.piotr_janczukowicz.github.api.copilotspaces.GitHubCopilotSpaceOwner;
import io.github.piotr_janczukowicz.github.api.deployments.GitHubEnvironmentReviewer;
import io.github.piotr_janczukowicz.github.api.organizations.GitHubEnterprise;
import io.github.piotr_janczukowicz.github.api.organizations.GitHubInstallationAccount;
import io.github.piotr_janczukowicz.github.api.organizations.GitHubOrganizationSimple;
import io.github.piotr_janczukowicz.github.api.organizations.OrganizationsCatalogFixtures;
import io.github.piotr_janczukowicz.github.api.teams.GitHubTeam;
import io.github.piotr_janczukowicz.github.api.teams.TeamCatalogFixtures;
import io.github.piotr_janczukowicz.github.api.users.GitHubSimpleUser;
import io.github.piotr_janczukowicz.github.api.users.UsersCatalogFixtures;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import io.github.piotr_janczukowicz.util.FileUtils;
import java.lang.reflect.Constructor;
import java.nio.charset.StandardCharsets;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

@UnitTest
class GitHubApiModulesTest {

    @Test
    void constructor_shouldThrow_whenInstantiatedViaReflection() throws Exception {
        final Constructor<GitHubApiModules> constructor = GitHubApiModules.class.getDeclaredConstructor();
        constructor.setAccessible(true);

        final Throwable thrown = catchThrowable(constructor::newInstance);

        assertThat(thrown).hasCauseInstanceOf(UnsupportedOperationException.class);
    }

    @Test
    void unwrapSealed_shouldThrow_whenUnknownShape() {
        final Throwable thrown = catchThrowable(() -> GitHubApiModules.unwrapSealed("owner"));

        assertThat(thrown)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Unknown GitHub sealed owner");
    }

    @ParameterizedTest
    @MethodSource("knownShapes")
    void unwrapSealed_shouldReturnWrappedValue_whenKnownShape(final Object wrapper, final Object inner) {
        final Object unwrapped = GitHubApiModules.unwrapSealed(wrapper);

        assertThat(unwrapped).isSameAs(inner);
    }

    @Test
    void decode_shouldReturnUserReviewer_whenUserShape() {
        final String json = FileUtils.readString(
                GitHubPendingDeploymentReviewerEntry.class, "pending-deployment-reviewer-entry.json");

        final GitHubPendingDeploymentReviewerEntry entry = GitHubJsonCodecs.defaults()
                .decode(json.getBytes(StandardCharsets.UTF_8), GitHubPendingDeploymentReviewerEntry.class);

        assertThat(entry.reviewer()).isInstanceOf(GitHubPendingDeploymentReviewer.SimpleUser.class);
    }

    @Test
    void decode_shouldReturnTeamReviewer_whenTeamShape() {
        final String json =
                "{\"type\":\"Team\",\"reviewer\":" + FileUtils.readString(GitHubTeam.class, "team.json") + "}";

        final GitHubPendingDeploymentReviewerEntry entry = GitHubJsonCodecs.defaults()
                .decode(json.getBytes(StandardCharsets.UTF_8), GitHubPendingDeploymentReviewerEntry.class);

        assertThat(entry.reviewer()).isInstanceOf(GitHubPendingDeploymentReviewer.Team.class);
    }

    static Stream<Arguments> knownShapes() {
        final GitHubSimpleUser user = UsersCatalogFixtures.octocat();
        final GitHubEnterprise enterprise = OrganizationsCatalogFixtures.enterprise();
        final GitHubTeam team = TeamCatalogFixtures.team();
        final GitHubOrganizationSimple organization = OrganizationsCatalogFixtures.organizationSimple();
        return Stream.of(
                Arguments.of(new GitHubIntegrationOwner.SimpleUser(user), user),
                Arguments.of(new GitHubIntegrationOwner.Enterprise(enterprise), enterprise),
                Arguments.of(new GitHubInstallationAccount.SimpleUser(user), user),
                Arguments.of(new GitHubInstallationAccount.Enterprise(enterprise), enterprise),
                Arguments.of(new GitHubIntegrationInstallationRequestAccount.SimpleUser(user), user),
                Arguments.of(new GitHubIntegrationInstallationRequestAccount.Enterprise(enterprise), enterprise),
                Arguments.of(GitHubEnvironmentReviewer.ofUser(user), user),
                Arguments.of(GitHubEnvironmentReviewer.ofTeam(team), team),
                Arguments.of(GitHubCopilotSpaceOwner.ofUser(user), user),
                Arguments.of(new GitHubPendingDeploymentReviewer.SimpleUser(user), user),
                Arguments.of(new GitHubPendingDeploymentReviewer.Team(team), team),
                Arguments.of(GitHubCopilotSpaceOwner.ofOrganization(organization), organization));
    }
}
