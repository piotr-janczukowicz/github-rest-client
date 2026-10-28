package io.github.piotr_janczukowicz.github.api.checks;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubSimpleCommitPersonTest {

    @Test
    void constructor_shouldThrow_whenNameNull() {
        final Throwable thrown =
                catchThrowable(() -> GitHubSimpleCommitPersonBuilder.builder(ChecksCatalogFixtures.simpleCommitPerson())
                        .name(null)
                        .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("name");
    }

    @Test
    void constructor_shouldThrow_whenEmailNull() {
        final Throwable thrown =
                catchThrowable(() -> GitHubSimpleCommitPersonBuilder.builder(ChecksCatalogFixtures.simpleCommitPerson())
                        .email(null)
                        .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("email");
    }
}
