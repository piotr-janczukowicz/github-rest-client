package io.github.piotr_janczukowicz.github.api.metrics;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCommunityProfileTest {

    @Test
    void constructor_shouldThrow_whenFilesNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubCommunityProfile(100, null, null, null, null, false));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("files");
    }

    @Test
    void constructor_shouldAcceptNullableFields_whenFilesPresent() {
        final GitHubCommunityProfileFiles files = MetricsCatalogFixtures.communityProfileFiles();

        final GitHubCommunityProfile profile = new GitHubCommunityProfile(100, null, null, files, null, false);

        assertThat(profile.files()).isSameAs(files);
    }
}
