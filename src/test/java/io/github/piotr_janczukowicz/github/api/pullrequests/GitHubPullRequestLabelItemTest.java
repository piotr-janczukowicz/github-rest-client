package io.github.piotr_janczukowicz.github.api.pullrequests;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.json.OptionalNull;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubPullRequestLabelItemTest {

    @Test
    void constructor_shouldThrow_whenDescriptionWrapperNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubPullRequestLabelItem(
                208_045_946,
                "MDU6TGFiZWwyMDgwNDU5NDY=",
                "https://api.github.com/repos/octocat/Hello-World/labels/bug",
                "bug",
                null,
                "f29513",
                true,
                null,
                null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("description");
    }

    @Test
    void constructor_shouldRetainDescription_whenPresent() {
        final GitHubPullRequestLabelItem label = new GitHubPullRequestLabelItem(
                208_045_946,
                "MDU6TGFiZWwyMDgwNDU5NDY=",
                "https://api.github.com/repos/octocat/Hello-World/labels/bug",
                "bug",
                OptionalNull.of("Something isn't working"),
                "f29513",
                true,
                null,
                null);

        assertThat(label.description()).isEqualTo(OptionalNull.of("Something isn't working"));
    }
}
