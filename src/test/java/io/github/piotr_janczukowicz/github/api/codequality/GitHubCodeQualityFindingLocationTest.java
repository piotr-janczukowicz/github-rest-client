package io.github.piotr_janczukowicz.github.api.codequality;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCodeQualityFindingLocationTest {

    @Test
    void constructor_shouldThrow_whenPathNull() {
        final Throwable thrown = catchThrowable(() -> GitHubCodeQualityFindingLocationBuilder.builder(
                        CodeQualityCatalogFixtures.codeQualityFindingLocation())
                .path(null)
                .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("path");
    }
}
