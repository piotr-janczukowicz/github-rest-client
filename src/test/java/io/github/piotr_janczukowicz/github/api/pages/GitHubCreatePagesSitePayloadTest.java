package io.github.piotr_janczukowicz.github.api.pages;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCreatePagesSitePayloadTest {

    @Test
    void constructor_shouldThrow_whenSourceAndBuildTypeNull() {
        final GitHubCreatePagesSitePayloadBuilder builder = GitHubCreatePagesSitePayloadBuilder.builder();

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("source or buildType");
    }

    @Test
    void constructor_shouldAcceptBuildType_whenSourceNull() {
        final GitHubCreatePagesSitePayload payload = GitHubCreatePagesSitePayloadBuilder.builder()
                .buildType(GitHubPagesBuildType.WORKFLOW)
                .build();

        assertThat(payload.buildType()).isEqualTo(GitHubPagesBuildType.WORKFLOW);
    }
}
