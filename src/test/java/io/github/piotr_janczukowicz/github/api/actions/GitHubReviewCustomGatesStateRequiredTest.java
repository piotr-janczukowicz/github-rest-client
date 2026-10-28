package io.github.piotr_janczukowicz.github.api.actions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubReviewCustomGatesStateRequiredTest {

    @Test
    void constructor_shouldRetainValues_whenAllPresent() {
        final String environmentName = "environmentName-sample";
        final GitHubReviewCustomGatesState state = GitHubReviewCustomGatesState.APPROVED;
        final String comment = "comment-sample";

        final GitHubReviewCustomGatesStateRequired sut = ActionsCatalogFixtures.reviewCustomGatesStateRequired();

        assertSoftly(softly -> {
            softly.assertThat(sut.environmentName()).isEqualTo(environmentName);
            softly.assertThat(sut.state()).isEqualTo(state);
            softly.assertThat(sut.comment()).isEqualTo(comment);
        });
    }

    @Test
    void constructor_shouldThrowNullPointerException_whenEnvironmentNameNull() {
        final GitHubReviewCustomGatesState state = GitHubReviewCustomGatesState.APPROVED;
        final String comment = "comment-sample";

        final Throwable thrown = catchThrowable(() -> new GitHubReviewCustomGatesStateRequired(null, state, comment));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("environmentName");
    }

    @Test
    void constructor_shouldThrowNullPointerException_whenStateNull() {
        final String environmentName = "environmentName-sample";
        final String comment = "comment-sample";

        final Throwable thrown =
                catchThrowable(() -> new GitHubReviewCustomGatesStateRequired(environmentName, null, comment));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("state");
    }
}
