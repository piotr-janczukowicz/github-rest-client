package io.github.piotr_janczukowicz.github.api.teams;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubTeamSimpleTest {

    @Test
    void constructor_shouldThrow_whenNameNull() {
        final GitHubTeamSimple base = TeamSimpleCatalogFixtures.sampleParentTeam();

        final Throwable thrown = catchThrowable(() -> new GitHubTeamSimple(
                base.id(),
                base.nodeId(),
                base.url(),
                base.membersUrl(),
                null,
                base.description(),
                base.permission(),
                base.privacy(),
                base.notificationSetting(),
                base.htmlUrl(),
                base.repositoriesUrl(),
                base.slug(),
                base.ldapDn(),
                base.type(),
                base.organizationId(),
                base.enterpriseId()));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("name");
    }

    @Test
    void constructor_shouldThrow_whenTypeNull() {
        final GitHubTeamSimple base = TeamSimpleCatalogFixtures.sampleParentTeam();

        final Throwable thrown = catchThrowable(() -> new GitHubTeamSimple(
                base.id(),
                base.nodeId(),
                base.url(),
                base.membersUrl(),
                base.name(),
                base.description(),
                base.permission(),
                base.privacy(),
                base.notificationSetting(),
                base.htmlUrl(),
                base.repositoriesUrl(),
                base.slug(),
                base.ldapDn(),
                null,
                base.organizationId(),
                base.enterpriseId()));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("type");
    }
}
