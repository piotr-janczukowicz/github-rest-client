package io.github.piotr_janczukowicz.github.api.repositories;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.time.Instant;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubHookTest {

    private static final Instant UPDATED_AT = Instant.parse("2011-09-06T20:39:23Z");
    private static final Instant CREATED_AT = Instant.parse("2011-09-06T17:26:27Z");
    private static final GitHubWebhookConfig VALID_CONFIG = RepositoriesCatalogFixtures.webhookConfig();
    private static final GitHubHookResponse VALID_LAST_RESPONSE = new GitHubHookResponse(200, "OK", "OK");

    @Test
    void constructor_shouldThrow_whenUpdatedAtNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubHook(
                "Repository",
                42L,
                "web",
                true,
                List.of("push"),
                VALID_CONFIG,
                null,
                CREATED_AT,
                "https://api.github.com/repos/octocat/Hello-World/hooks/1",
                "https://api.github.com/repos/octocat/Hello-World/hooks/1/test",
                "https://api.github.com/repos/octocat/Hello-World/hooks/1/pings",
                null,
                VALID_LAST_RESPONSE));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("updatedAt");
    }

    @Test
    void constructor_shouldThrow_whenCreatedAtNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubHook(
                "Repository",
                42L,
                "web",
                true,
                List.of("push"),
                VALID_CONFIG,
                UPDATED_AT,
                null,
                "https://api.github.com/repos/octocat/Hello-World/hooks/1",
                "https://api.github.com/repos/octocat/Hello-World/hooks/1/test",
                "https://api.github.com/repos/octocat/Hello-World/hooks/1/pings",
                null,
                VALID_LAST_RESPONSE));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("createdAt");
    }
}
