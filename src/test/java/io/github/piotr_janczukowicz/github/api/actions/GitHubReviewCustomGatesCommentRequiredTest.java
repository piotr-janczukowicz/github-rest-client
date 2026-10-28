package io.github.piotr_janczukowicz.github.api.actions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubReviewCustomGatesCommentRequiredTest {

    @Test
    void constructor_shouldRetainValues_whenAllPresent() {
        final String environmentName = "environmentName-sample";
        final String comment = "comment-sample";

        final GitHubReviewCustomGatesCommentRequired sut = ActionsCatalogFixtures.reviewCustomGatesCommentRequired();

        assertSoftly(softly -> {
            softly.assertThat(sut.environmentName()).isEqualTo(environmentName);
            softly.assertThat(sut.comment()).isEqualTo(comment);
        });
    }

    @Test
    void constructor_shouldThrowNullPointerException_whenEnvironmentNameNull() {
        final String comment = "comment-sample";

        final Throwable thrown = catchThrowable(() -> new GitHubReviewCustomGatesCommentRequired(null, comment));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("environmentName");
    }

    @Test
    void constructor_shouldThrowNullPointerException_whenCommentNull() {
        final String environmentName = "environmentName-sample";

        final Throwable thrown =
                catchThrowable(() -> new GitHubReviewCustomGatesCommentRequired(environmentName, null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("comment");
    }
}
