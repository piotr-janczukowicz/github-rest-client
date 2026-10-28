package io.github.piotr_janczukowicz.github.api.organizations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.github.api.users.UsersCatalogFixtures;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.time.Instant;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubOrganizationCustomRepositoryRoleTest {

    @Test
    void accessors_shouldExposeEveryComponent() {
        final GitHubOrganizationCustomRepositoryRole value =
                OrganizationsCatalogFixtures.organizationCustomRepositoryRole();
        final GitHubOrganizationCustomRepositoryRole expected =
                OrganizationsCatalogFixtures.organizationCustomRepositoryRole();

        assertThat(value.id()).isEqualTo(expected.id());
        assertThat(value.name()).isEqualTo(expected.name());
        assertThat(value.description()).isEqualTo(expected.description());
        assertThat(value.baseRole()).isEqualTo(expected.baseRole());
        assertThat(value.permissions()).isEqualTo(expected.permissions());
        assertThat(value.organization()).isEqualTo(expected.organization());
        assertThat(value.createdAt()).isEqualTo(expected.createdAt());
        assertThat(value.updatedAt()).isEqualTo(expected.updatedAt());
    }

    @Test
    void constructor_shouldThrow_whenNameNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubOrganizationCustomRepositoryRole(
                1L,
                null,
                null,
                GitHubOrganizationCustomRepositoryRoleBaseRole.READ,
                List.of("pull"),
                UsersCatalogFixtures.octocat(),
                Instant.EPOCH,
                Instant.EPOCH));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("name");
    }

    @Test
    void constructor_shouldThrow_whenPermissionsNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubOrganizationCustomRepositoryRole(
                1L,
                "Reviewer",
                null,
                GitHubOrganizationCustomRepositoryRoleBaseRole.READ,
                null,
                UsersCatalogFixtures.octocat(),
                Instant.EPOCH,
                Instant.EPOCH));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("permissions");
    }
}
