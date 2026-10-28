package io.github.piotr_janczukowicz.github.api.pages;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

@UnitTest
class GitHubPagesDeploymentStatusTest {

    @ParameterizedTest
    @CsvSource({
        "deployment_in_progress,DEPLOYMENT_IN_PROGRESS",
        "syncing_files,SYNCING_FILES",
        "finished_file_sync,FINISHED_FILE_SYNC",
        "updating_pages,UPDATING_PAGES",
        "purging_cdn,PURGING_CDN",
        "deployment_cancelled,DEPLOYMENT_CANCELLED",
        "deployment_failed,DEPLOYMENT_FAILED",
        "deployment_content_failed,DEPLOYMENT_CONTENT_FAILED",
        "deployment_attempt_error,DEPLOYMENT_ATTEMPT_ERROR",
        "deployment_lost,DEPLOYMENT_LOST",
        "succeed,SUCCEED"
    })
    void fromWire_shouldReturnType_whenKnownValue(final String wire, final GitHubPagesDeploymentStatus value) {
        final GitHubPagesDeploymentStatus decoded = GitHubPagesDeploymentStatus.fromWire(wire);

        assertSoftly(softly -> {
            softly.assertThat(decoded).isEqualTo(value);
            softly.assertThat(decoded.wireValue()).isEqualTo(wire);
        });
    }

    @Test
    void fromWire_shouldThrowNullPointerException_whenValueNull() {
        final Throwable thrown = catchThrowable(() -> GitHubPagesDeploymentStatus.fromWire(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("value");
    }

    @Test
    void fromWire_shouldReturnUnknown_whenValueNotKnown() {
        final GitHubPagesDeploymentStatus decoded = GitHubPagesDeploymentStatus.fromWire("brand_new_value");

        assertSoftly(softly -> {
            softly.assertThat(decoded).isEqualTo(GitHubPagesDeploymentStatus.UNKNOWN);
            softly.assertThat(decoded.wireValue()).isEqualTo("unknown");
        });
    }
}
