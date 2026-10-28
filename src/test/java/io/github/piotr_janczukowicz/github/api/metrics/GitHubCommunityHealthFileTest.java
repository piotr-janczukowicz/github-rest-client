package io.github.piotr_janczukowicz.github.api.metrics;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCommunityHealthFileTest {

    @Test
    void constructor_shouldStoreUrls_whenCalled() {
        final GitHubCommunityHealthFile file =
                new GitHubCommunityHealthFile("https://api.github.com/readme", "https://github.com/readme");

        assertSoftly(softly -> {
            softly.assertThat(file.url()).isEqualTo("https://api.github.com/readme");
            softly.assertThat(file.htmlUrl()).isEqualTo("https://github.com/readme");
        });
    }

    @Test
    void constructor_shouldThrow_whenUrlNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubCommunityHealthFile(null, "https://github.com/readme"));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("url");
    }

    @Test
    void constructor_shouldThrow_whenHtmlUrlNull() {
        final Throwable thrown =
                catchThrowable(() -> new GitHubCommunityHealthFile("https://api.github.com/readme", null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("htmlUrl");
    }
}
