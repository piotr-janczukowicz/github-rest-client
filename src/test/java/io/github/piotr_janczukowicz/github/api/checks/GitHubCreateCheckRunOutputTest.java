package io.github.piotr_janczukowicz.github.api.checks;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import java.util.stream.IntStream;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCreateCheckRunOutputTest {

    @Test
    void constructor_shouldThrow_whenTitleNull() {
        final Throwable thrown = catchThrowable(
                () -> GitHubCreateCheckRunOutputBuilder.builder(ChecksCatalogFixtures.createCheckRunOutput())
                        .title(null)
                        .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("title");
    }

    @Test
    void constructor_shouldThrow_whenSummaryNull() {
        final Throwable thrown = catchThrowable(
                () -> GitHubCreateCheckRunOutputBuilder.builder(ChecksCatalogFixtures.createCheckRunOutput())
                        .summary(null)
                        .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("summary");
    }

    @Test
    void constructor_shouldThrow_whenSummaryTooLong() {
        final Throwable thrown = catchThrowable(
                () -> GitHubCreateCheckRunOutputBuilder.builder(ChecksCatalogFixtures.createCheckRunOutput())
                        .summary("x".repeat(65_536))
                        .build());

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("summary");
    }

    @Test
    void constructor_shouldThrow_whenTextTooLong() {
        final Throwable thrown = catchThrowable(
                () -> GitHubCreateCheckRunOutputBuilder.builder(ChecksCatalogFixtures.createCheckRunOutput())
                        .text("x".repeat(65_536))
                        .build());

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("text");
    }

    @Test
    void constructor_shouldThrow_whenAnnotationsExceedMax() {
        final Throwable thrown = catchThrowable(
                () -> GitHubCreateCheckRunOutputBuilder.builder(ChecksCatalogFixtures.createCheckRunOutput())
                        .annotations(IntStream.range(0, 51)
                                .mapToObj(index -> ChecksCatalogFixtures.createCheckRunAnnotation())
                                .toList())
                        .build());

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("annotations");
    }

    @Test
    void constructor_shouldLeaveAnnotationsNull_whenNull() {
        final GitHubCreateCheckRunOutput output = GitHubCreateCheckRunOutputBuilder.builder(
                        ChecksCatalogFixtures.createCheckRunOutput())
                .annotations(null)
                .build();

        assertThat(output.annotations()).isNull();
    }

    @Test
    void constructor_shouldLeaveImagesNull_whenNull() {
        final GitHubCreateCheckRunOutput output = GitHubCreateCheckRunOutputBuilder.builder(
                        ChecksCatalogFixtures.createCheckRunOutput())
                .images(null)
                .build();

        assertThat(output.images()).isNull();
    }

    @Test
    void constructor_shouldCopyImages_whenProvided() {
        final List<GitHubCreateCheckRunImage> images = List.of(ChecksCatalogFixtures.createCheckRunImage());

        final GitHubCreateCheckRunOutput output = GitHubCreateCheckRunOutputBuilder.builder(
                        ChecksCatalogFixtures.createCheckRunOutput())
                .images(images)
                .build();

        assertThat(output.images()).containsExactlyElementsOf(images);
    }
}
