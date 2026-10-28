package io.github.piotr_janczukowicz.github.api.organizations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.time.Instant;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCredentialAuthorizationTest {

    @Test
    void accessors_shouldExposeEveryComponent() {
        final GitHubCredentialAuthorization value = OrganizationsCatalogFixtures.credentialAuthorization();

        assertThat(value.login()).isEqualTo("octocat");
        assertThat(value.credentialId()).isEqualTo(1L);
        assertThat(value.credentialType()).isEqualTo(GitHubCredentialAuthorizationCredentialType.SSH_KEY);
        assertThat(value.scopes()).containsExactly("repo");
        assertThat(value.authorizedCredentialId()).isEqualTo(2L);
        assertThat(value).hasSameHashCodeAs(OrganizationsCatalogFixtures.credentialAuthorization());
    }

    @Test
    void constructor_shouldKeepScopesNull_whenScopesNull() {
        final GitHubCredentialAuthorization value = new GitHubCredentialAuthorization(
                "octocat",
                1L,
                GitHubCredentialAuthorizationCredentialType.SSH_KEY,
                null,
                Instant.parse("2011-01-26T19:06:43Z"),
                null,
                null,
                null,
                null,
                null,
                null,
                null);

        assertThat(value.scopes()).isNull();
    }

    @Test
    void constructor_shouldThrow_whenLoginNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubCredentialAuthorization(
                null,
                1L,
                GitHubCredentialAuthorizationCredentialType.SSH_KEY,
                null,
                Instant.parse("2011-01-26T19:06:43Z"),
                null,
                null,
                null,
                null,
                null,
                null,
                null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("login");
    }

    @Test
    void constructor_shouldThrow_whenCredentialTypeNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubCredentialAuthorization(
                "octocat",
                1L,
                null,
                null,
                Instant.parse("2011-01-26T19:06:43Z"),
                null,
                null,
                null,
                null,
                null,
                null,
                null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("credentialType");
    }

    @Test
    void constructor_shouldThrow_whenCredentialAuthorizedAtNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubCredentialAuthorization(
                "octocat",
                1L,
                GitHubCredentialAuthorizationCredentialType.SSH_KEY,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("credentialAuthorizedAt");
    }

    @Test
    void toString_shouldRedactCredentialText_whenCalled() {
        final String text =
                OrganizationsCatalogFixtures.credentialAuthorization().toString();

        assertThat(text).contains("authorizedCredentialTitle=<redacted>").doesNotContain("Deploy key");
    }
}
