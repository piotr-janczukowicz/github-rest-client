package io.github.piotr_janczukowicz.github.api.teams;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.github.api.repositories.RepositoriesCatalogFixtures;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubTeamTest {

    @Test
    void constructor_shouldThrow_whenNameNull() {
        final GitHubTeam base = RepositoriesCatalogFixtures.requestedTeam();

        final Throwable thrown = catchThrowable(() -> new GitHubTeam(
                base.id(),
                base.nodeId(),
                null,
                base.slug(),
                base.description(),
                base.privacy(),
                base.notificationSetting(),
                base.permission(),
                base.permissions(),
                base.url(),
                base.htmlUrl(),
                base.membersUrl(),
                base.repositoriesUrl(),
                base.type(),
                base.accessSource(),
                base.organizationId(),
                base.enterpriseId(),
                base.parent()));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("name");
    }

    @Test
    void constructor_shouldThrow_whenTypeNull() {
        final GitHubTeam base = RepositoriesCatalogFixtures.requestedTeam();

        final Throwable thrown = catchThrowable(() -> new GitHubTeam(
                base.id(),
                base.nodeId(),
                base.name(),
                base.slug(),
                base.description(),
                base.privacy(),
                base.notificationSetting(),
                base.permission(),
                base.permissions(),
                base.url(),
                base.htmlUrl(),
                base.membersUrl(),
                base.repositoriesUrl(),
                null,
                base.accessSource(),
                base.organizationId(),
                base.enterpriseId(),
                base.parent()));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("type");
    }
}
