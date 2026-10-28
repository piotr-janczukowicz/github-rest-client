package io.github.piotr_janczukowicz.github.api.actions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.github.api.repositories.GitHubRepository;
import io.github.piotr_janczukowicz.github.api.repositories.RepositoriesCatalogFixtures;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubAuthenticationTokenTest {

    @Test
    void constructor_shouldThrowNullPointerException_whenTokenNull() {
        final Instant expiresAt = Instant.parse("2024-01-02T03:04:05Z");
        final Map<String, Object> permissions = Map.of("key", "value");
        final List<GitHubRepository> repositories = List.of(RepositoriesCatalogFixtures.teamEnvironmentRepository());
        final String singleFile = "singleFile-sample";
        final GitHubAuthenticationTokenRepositorySelection repositorySelection =
                GitHubAuthenticationTokenRepositorySelection.ALL;

        final Throwable thrown = catchThrowable(() -> new GitHubAuthenticationToken(
                null, expiresAt, permissions, repositories, singleFile, repositorySelection));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("token");
    }

    @Test
    void constructor_shouldThrowNullPointerException_whenExpiresAtNull() {
        final String token = "token-sample";
        final Map<String, Object> permissions = Map.of("key", "value");
        final List<GitHubRepository> repositories = List.of(RepositoriesCatalogFixtures.teamEnvironmentRepository());
        final String singleFile = "singleFile-sample";
        final GitHubAuthenticationTokenRepositorySelection repositorySelection =
                GitHubAuthenticationTokenRepositorySelection.ALL;

        final Throwable thrown = catchThrowable(() ->
                new GitHubAuthenticationToken(token, null, permissions, repositories, singleFile, repositorySelection));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("expiresAt");
    }

    @Test
    void toString_shouldHideSecrets() {
        final GitHubAuthenticationToken value = ActionsCatalogFixtures.authenticationToken();

        final String text = value.toString();

        assertSoftly(softly -> {
            softly.assertThat(text).contains("token=<redacted>");
            softly.assertThat(text).doesNotContain(value.token());
        });
    }

    @Test
    void constructor_shouldAcceptNullPermissions_whenPermissionsNull() {
        final GitHubAuthenticationToken token = GitHubAuthenticationTokenBuilder.builder(
                        ActionsCatalogFixtures.authenticationToken())
                .permissions(null)
                .build();

        assertThat(token.permissions()).isNull();
    }

    @Test
    void constructor_shouldAcceptNullRepositories_whenRepositoriesNull() {
        final GitHubAuthenticationToken token = GitHubAuthenticationTokenBuilder.builder(
                        ActionsCatalogFixtures.authenticationToken())
                .repositories(null)
                .build();

        assertThat(token.repositories()).isNull();
    }
}
