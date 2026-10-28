package io.github.piotr_janczukowicz.github.api.repositories;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubRepositoryRuleCodeCoverageDetailedTest {

    @Test
    void constructor_shouldThrow_whenTypeNull() {
        final Throwable thrown =
                catchThrowable(() -> new GitHubRepositoryRuleCodeCoverageDetailed(null, null, null, null, null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("type");
    }
}
