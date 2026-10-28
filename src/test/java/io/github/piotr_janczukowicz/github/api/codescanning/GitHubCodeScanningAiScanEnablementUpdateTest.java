package io.github.piotr_janczukowicz.github.api.codescanning;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCodeScanningAiScanEnablementUpdateTest {

    @Test
    void constructor_shouldThrowNullPointerException_whenPrScanNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubCodeScanningAiScanEnablementUpdate(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("prScan");
    }
}
