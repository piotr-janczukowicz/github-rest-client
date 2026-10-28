package io.github.piotr_janczukowicz.github.api.dependabot;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubDependabotPublicKeyTest {

    @Test
    void constructor_shouldThrow_whenKeyIdNull() {
        final GitHubDependabotPublicKeyBuilder builder = GitHubDependabotPublicKeyBuilder.builder(
                        DependabotCatalogFixtures.dependabotPublicKey())
                .keyId(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("keyId");
    }

    @Test
    void constructor_shouldThrow_whenKeyNull() {
        final GitHubDependabotPublicKeyBuilder builder = GitHubDependabotPublicKeyBuilder.builder(
                        DependabotCatalogFixtures.dependabotPublicKey())
                .key(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("key");
    }
}
