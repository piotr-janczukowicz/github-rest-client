package io.github.piotr_janczukowicz.github.api.organizations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.time.Instant;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubOrgHookTest {

    private static final GitHubOrgHookConfig VALID_CONFIG = OrganizationsCatalogFixtures.orgHookConfig();
    private static final Instant UPDATED_AT = Instant.parse("2011-09-06T20:39:23Z");
    private static final Instant CREATED_AT = Instant.parse("2011-09-06T17:26:27Z");

    @Test
    void constructor_shouldThrow_whenUrlNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubOrgHook(
                1L,
                null,
                "https://api.github.com/orgs/octocat/hooks/1/pings",
                null,
                "web",
                List.of("push"),
                true,
                VALID_CONFIG,
                UPDATED_AT,
                CREATED_AT,
                "Organization"));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("url");
    }

    @Test
    void constructor_shouldThrow_whenPingUrlNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubOrgHook(
                1L,
                "https://api.github.com/orgs/octocat/hooks/1",
                null,
                null,
                "web",
                List.of("push"),
                true,
                VALID_CONFIG,
                UPDATED_AT,
                CREATED_AT,
                "Organization"));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("pingUrl");
    }

    @Test
    void constructor_shouldThrow_whenNameNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubOrgHook(
                1L,
                "https://api.github.com/orgs/octocat/hooks/1",
                "https://api.github.com/orgs/octocat/hooks/1/pings",
                null,
                null,
                List.of("push"),
                true,
                VALID_CONFIG,
                UPDATED_AT,
                CREATED_AT,
                "Organization"));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("name");
    }

    @Test
    void constructor_shouldThrow_whenEventsNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubOrgHook(
                1L,
                "https://api.github.com/orgs/octocat/hooks/1",
                "https://api.github.com/orgs/octocat/hooks/1/pings",
                null,
                "web",
                null,
                true,
                VALID_CONFIG,
                UPDATED_AT,
                CREATED_AT,
                "Organization"));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("events");
    }

    @Test
    void constructor_shouldThrow_whenConfigNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubOrgHook(
                1L,
                "https://api.github.com/orgs/octocat/hooks/1",
                "https://api.github.com/orgs/octocat/hooks/1/pings",
                null,
                "web",
                List.of("push"),
                true,
                null,
                UPDATED_AT,
                CREATED_AT,
                "Organization"));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("config");
    }

    @Test
    void constructor_shouldThrow_whenUpdatedAtNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubOrgHook(
                1L,
                "https://api.github.com/orgs/octocat/hooks/1",
                "https://api.github.com/orgs/octocat/hooks/1/pings",
                null,
                "web",
                List.of("push"),
                true,
                VALID_CONFIG,
                null,
                CREATED_AT,
                "Organization"));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("updatedAt");
    }

    @Test
    void constructor_shouldThrow_whenCreatedAtNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubOrgHook(
                1L,
                "https://api.github.com/orgs/octocat/hooks/1",
                "https://api.github.com/orgs/octocat/hooks/1/pings",
                null,
                "web",
                List.of("push"),
                true,
                VALID_CONFIG,
                UPDATED_AT,
                null,
                "Organization"));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("createdAt");
    }

    @Test
    void constructor_shouldThrow_whenTypeNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubOrgHook(
                1L,
                "https://api.github.com/orgs/octocat/hooks/1",
                "https://api.github.com/orgs/octocat/hooks/1/pings",
                null,
                "web",
                List.of("push"),
                true,
                VALID_CONFIG,
                UPDATED_AT,
                CREATED_AT,
                null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("type");
    }
}
