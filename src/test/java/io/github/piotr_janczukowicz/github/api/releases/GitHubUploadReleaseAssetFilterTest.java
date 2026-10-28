package io.github.piotr_janczukowicz.github.api.releases;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.Map;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubUploadReleaseAssetFilterTest {

    @Test
    void constructor_shouldThrow_whenNameNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubUploadReleaseAssetFilter(null, "label"));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("name");
    }

    @Test
    void of_shouldEmitName_whenCalled() {
        final GitHubUploadReleaseAssetFilter filter = GitHubUploadReleaseAssetFilter.of("example.zip");

        assertThat(filter.toQueryParameters()).containsEntry("name", "example.zip");
    }

    @Test
    void toQueryParameters_shouldOmitNulls_whenLabelUnset() {
        final Map<String, String> query =
                GitHubUploadReleaseAssetFilter.of("example.zip").toQueryParameters();

        assertThat(query).containsExactly(Map.entry("name", "example.zip"));
    }

    @Test
    void toQueryParameters_shouldIncludeLabel_whenSet() {
        final GitHubUploadReleaseAssetFilter filter = GitHubUploadReleaseAssetFilterBuilder.builder()
                .name("example.zip")
                .label("short description")
                .build();

        assertThat(filter.toQueryParameters())
                .containsEntry("name", "example.zip")
                .containsEntry("label", "short description");
    }
}
