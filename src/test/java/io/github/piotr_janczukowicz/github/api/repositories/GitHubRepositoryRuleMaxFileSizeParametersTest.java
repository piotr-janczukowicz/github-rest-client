package io.github.piotr_janczukowicz.github.api.repositories;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubRepositoryRuleMaxFileSizeParametersTest {

    @Test
    void constructor_shouldThrow_whenMaxFileSizeBelowMinimum() {
        final Throwable thrown = catchThrowable(() -> new GitHubRepositoryRuleMaxFileSizeParameters(0, null));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("maxFileSize");
    }

    @Test
    void constructor_shouldThrow_whenMaxFileSizeAboveMaximum() {
        final Throwable thrown = catchThrowable(() -> new GitHubRepositoryRuleMaxFileSizeParameters(101, null));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("maxFileSize");
    }

    @Test
    void constructor_shouldAcceptValue_whenAtDumpBounds() {
        final GitHubRepositoryRuleMaxFileSizeParametersBuilder builder =
                GitHubRepositoryRuleMaxFileSizeParametersBuilder.builder(
                        RepositoriesCatalogFixtures.maxFileSizeParameters());

        final GitHubRepositoryRuleMaxFileSizeParameters minimum =
                builder.maxFileSize(1).build();
        final GitHubRepositoryRuleMaxFileSizeParameters maximum =
                builder.maxFileSize(100).build();

        assertSoftly(softly -> {
            softly.assertThat(minimum.maxFileSize()).isEqualTo(1);
            softly.assertThat(maximum.maxFileSize()).isEqualTo(100);
        });
    }

    @Test
    void constructor_shouldCopyIgnoredFilePaths_whenGiven() {
        final List<String> source = new ArrayList<>(List.of("keep.md"));
        final GitHubRepositoryRuleMaxFileSizeParameters parameters =
                new GitHubRepositoryRuleMaxFileSizeParameters(1, source);

        source.add("other.md");

        assertThat(parameters.ignoredFilePaths()).containsExactly("keep.md");
    }

    @Test
    void constructor_shouldLeaveIgnoredFilePathsNull_whenNull() {
        final GitHubRepositoryRuleMaxFileSizeParameters parameters =
                GitHubRepositoryRuleMaxFileSizeParametersBuilder.builder(
                                RepositoriesCatalogFixtures.maxFileSizeParameters())
                        .ignoredFilePaths(null)
                        .build();

        assertThat(parameters.ignoredFilePaths()).isNull();
    }
}
