package io.github.piotr_janczukowicz.github.api.deployments;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.time.Instant;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubEnvironmentTest {

    @Test
    void constructor_shouldCopyProtectionRules_whenPresent() {
        final GitHubEnvironmentProtectionRule rule = DeploymentsCatalogFixtures.environmentProtectionRule();
        final GitHubEnvironment environment = new GitHubEnvironment(
                1L,
                "n",
                "staging",
                "u",
                "h",
                Instant.parse("2020-11-23T22:00:40Z"),
                Instant.parse("2020-11-23T22:00:40Z"),
                List.of(rule),
                null);

        assertThat(environment.protectionRules()).containsExactly(rule);
    }

    @Test
    void constructor_shouldAllowNullProtectionRules_whenAbsent() {
        final GitHubEnvironment environment = new GitHubEnvironment(
                1L,
                "n",
                "staging",
                "u",
                "h",
                Instant.parse("2020-11-23T22:00:40Z"),
                Instant.parse("2020-11-23T22:00:40Z"),
                null,
                null);

        assertThat(environment.protectionRules()).isNull();
    }

    @Test
    void constructor_shouldThrow_whenNameNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubEnvironment(
                1L,
                "n",
                null,
                "u",
                "h",
                Instant.parse("2020-11-23T22:00:40Z"),
                Instant.parse("2020-11-23T22:00:40Z"),
                null,
                null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("name");
    }
}
