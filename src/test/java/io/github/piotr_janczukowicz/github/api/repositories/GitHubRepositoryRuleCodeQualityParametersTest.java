package io.github.piotr_janczukowicz.github.api.repositories;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubRepositoryRuleCodeQualityParametersTest {

    @Test
    void constructor_shouldThrow_whenSeverityNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubRepositoryRuleCodeQualityParameters(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("severity");
    }
}
