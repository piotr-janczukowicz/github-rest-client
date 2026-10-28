package io.github.piotr_janczukowicz.github.api.dependabot;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubSetRepositoryAccessDefaultLevelPayloadTest {

    @Test
    void constructor_shouldThrow_whenDefaultLevelNull() {
        final GitHubSetRepositoryAccessDefaultLevelPayloadBuilder builder =
                GitHubSetRepositoryAccessDefaultLevelPayloadBuilder.builder(
                                DependabotCatalogFixtures.setRepositoryAccessDefaultLevelPayload())
                        .defaultLevel(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("defaultLevel");
    }
}
