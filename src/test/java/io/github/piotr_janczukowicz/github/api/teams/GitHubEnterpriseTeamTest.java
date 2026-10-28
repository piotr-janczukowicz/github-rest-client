package io.github.piotr_janczukowicz.github.api.teams;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubEnterpriseTeamTest {

    @Test
    void constructor_shouldThrow_whenNameNull() {
        final Throwable thrown =
                catchThrowable(() -> GitHubEnterpriseTeamBuilder.builder(TeamCatalogFixtures.enterpriseTeam())
                        .name(null)
                        .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("name");
    }

    @Test
    void constructor_shouldThrow_whenSlugNull() {
        final Throwable thrown =
                catchThrowable(() -> GitHubEnterpriseTeamBuilder.builder(TeamCatalogFixtures.enterpriseTeam())
                        .slug(null)
                        .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("slug");
    }

    @Test
    void constructor_shouldThrow_whenUrlNull() {
        final Throwable thrown =
                catchThrowable(() -> GitHubEnterpriseTeamBuilder.builder(TeamCatalogFixtures.enterpriseTeam())
                        .url(null)
                        .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("url");
    }

    @Test
    void constructor_shouldThrow_whenHtmlUrlNull() {
        final Throwable thrown =
                catchThrowable(() -> GitHubEnterpriseTeamBuilder.builder(TeamCatalogFixtures.enterpriseTeam())
                        .htmlUrl(null)
                        .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("htmlUrl");
    }

    @Test
    void constructor_shouldThrow_whenMembersUrlNull() {
        final Throwable thrown =
                catchThrowable(() -> GitHubEnterpriseTeamBuilder.builder(TeamCatalogFixtures.enterpriseTeam())
                        .membersUrl(null)
                        .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("membersUrl");
    }

    @Test
    void constructor_shouldThrow_whenCreatedAtNull() {
        final Throwable thrown =
                catchThrowable(() -> GitHubEnterpriseTeamBuilder.builder(TeamCatalogFixtures.enterpriseTeam())
                        .createdAt(null)
                        .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("createdAt");
    }

    @Test
    void constructor_shouldThrow_whenUpdatedAtNull() {
        final Throwable thrown =
                catchThrowable(() -> GitHubEnterpriseTeamBuilder.builder(TeamCatalogFixtures.enterpriseTeam())
                        .updatedAt(null)
                        .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("updatedAt");
    }

    @Test
    void constructor_shouldLeaveGroupIdNull_whenNull() {
        final GitHubEnterpriseTeam value = GitHubEnterpriseTeamBuilder.builder(TeamCatalogFixtures.enterpriseTeam())
                .groupId(null)
                .build();

        assertThat(value.groupId()).isNull();
    }
}
