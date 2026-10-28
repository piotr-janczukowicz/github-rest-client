package io.github.piotr_janczukowicz.github.api.checks;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.stream.IntStream;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubUpdateCheckRunOutputTest {

    @Test
    void constructor_shouldThrow_whenSummaryNull() {
        final Throwable thrown = catchThrowable(
                () -> GitHubUpdateCheckRunOutputBuilder.builder(ChecksCatalogFixtures.updateCheckRunOutput())
                        .summary(null)
                        .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("summary");
    }

    @Test
    void constructor_shouldThrow_whenSummaryTooLong() {
        final Throwable thrown = catchThrowable(
                () -> GitHubUpdateCheckRunOutputBuilder.builder(ChecksCatalogFixtures.updateCheckRunOutput())
                        .summary("x".repeat(65_536))
                        .build());

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("summary");
    }

    @Test
    void constructor_shouldThrow_whenTextTooLong() {
        final Throwable thrown = catchThrowable(
                () -> GitHubUpdateCheckRunOutputBuilder.builder(ChecksCatalogFixtures.updateCheckRunOutput())
                        .text("x".repeat(65_536))
                        .build());

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("text");
    }

    @Test
    void constructor_shouldThrow_whenAnnotationsExceedMax() {
        final Throwable thrown = catchThrowable(
                () -> GitHubUpdateCheckRunOutputBuilder.builder(ChecksCatalogFixtures.updateCheckRunOutput())
                        .annotations(IntStream.range(0, 51)
                                .mapToObj(index -> ChecksCatalogFixtures.updateCheckRunAnnotation())
                                .toList())
                        .build());

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("annotations");
    }

    @Test
    void constructor_shouldLeaveAnnotationsNull_whenNull() {
        final GitHubUpdateCheckRunOutput output = GitHubUpdateCheckRunOutputBuilder.builder(
                        ChecksCatalogFixtures.updateCheckRunOutput())
                .annotations(null)
                .build();

        assertThat(output.annotations()).isNull();
    }

    @Test
    void constructor_shouldLeaveImagesNull_whenNull() {
        final GitHubUpdateCheckRunOutput output = GitHubUpdateCheckRunOutputBuilder.builder(
                        ChecksCatalogFixtures.updateCheckRunOutput())
                .images(null)
                .build();

        assertThat(output.images()).isNull();
    }
}
