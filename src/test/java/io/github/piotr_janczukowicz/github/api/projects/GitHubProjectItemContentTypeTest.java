package io.github.piotr_janczukowicz.github.api.projects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.github.api.issues.GitHubIssue;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubPullRequestSimple;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EnumSource;

@UnitTest
class GitHubProjectItemContentTypeTest {

    @ParameterizedTest
    @CsvSource({"Issue,ISSUE", "PullRequest,PULL_REQUEST", "DraftIssue,DRAFT_ISSUE", "unknown,UNKNOWN"})
    void fromWire_shouldReturnType_whenKnownValue(final String wire, final GitHubProjectItemContentType value) {
        final GitHubProjectItemContentType decoded = GitHubProjectItemContentType.fromWire(wire);

        assertSoftly(softly -> {
            softly.assertThat(decoded).isEqualTo(value);
            softly.assertThat(decoded.wireValue()).isEqualTo(wire);
        });
    }

    @ParameterizedTest
    @EnumSource(GitHubProjectItemContentType.class)
    void contentValueType_shouldMatchContent_whenCalled(final GitHubProjectItemContentType value) {
        final Class<?> contentType = value.contentValueType();

        assertThat(contentType)
                .isIn(GitHubIssue.class, GitHubPullRequestSimple.class, GitHubProjectDraftIssue.class, Map.class);
    }

    @Test
    void fromWire_shouldReturnUnknown_whenValueIsNotKnown() {
        final GitHubProjectItemContentType decoded = GitHubProjectItemContentType.fromWire("Widget");

        assertThat(decoded).isEqualTo(GitHubProjectItemContentType.UNKNOWN);
    }

    @Test
    void fromWire_shouldThrow_whenValueIsNull() {
        final Throwable thrown = catchThrowable(() -> GitHubProjectItemContentType.fromWire(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("value");
    }
}
