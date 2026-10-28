package io.github.piotr_janczukowicz.github.api.organizations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.github.api.users.UsersCatalogFixtures;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.time.Instant;
import java.time.OffsetDateTime;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubOrganizationPatGrantTest {

    private static final Instant GRANTED_AT =
            OffsetDateTime.parse("2023-05-16T08:47:09.000-07:00").toInstant();

    @Test
    void constructor_shouldThrow_whenOwnerNull() {
        final Throwable thrown =
                catchThrowable(() -> validGrantBuilder().owner(null).build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("owner");
    }

    @Test
    void constructor_shouldThrow_whenRepositorySelectionNull() {
        final Throwable thrown = catchThrowable(
                () -> validGrantBuilder().repositorySelection(null).build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("repositorySelection");
    }

    @Test
    void constructor_shouldThrow_whenRepositoriesUrlNull() {
        final Throwable thrown =
                catchThrowable(() -> validGrantBuilder().repositoriesUrl(null).build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("repositoriesUrl");
    }

    @Test
    void constructor_shouldThrow_whenPermissionsNull() {
        final Throwable thrown =
                catchThrowable(() -> validGrantBuilder().permissions(null).build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("permissions");
    }

    @Test
    void constructor_shouldThrow_whenAccessGrantedAtNull() {
        final Throwable thrown =
                catchThrowable(() -> validGrantBuilder().accessGrantedAt(null).build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("accessGrantedAt");
    }

    @Test
    void constructor_shouldThrow_whenTokenNameNull() {
        final Throwable thrown =
                catchThrowable(() -> validGrantBuilder().tokenName(null).build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("tokenName");
    }

    private static GitHubOrganizationPatGrantBuilder validGrantBuilder() {
        return GitHubOrganizationPatGrantBuilder.builder()
                .id(1L)
                .owner(UsersCatalogFixtures.octocat())
                .repositorySelection(GitHubOrganizationPatRepositorySelection.ALL)
                .repositoriesUrl("https://api.github.com/organizations/1/personal-access-tokens/1/repositories")
                .permissions(OrganizationsCatalogFixtures.organizationPatPermissions())
                .accessGrantedAt(GRANTED_AT)
                .tokenId(1L)
                .tokenName("token")
                .tokenExpired(false);
    }
}
