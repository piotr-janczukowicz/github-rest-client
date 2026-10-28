package io.github.piotr_janczukowicz.github.api.projects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubAddProjectFieldPayloadTest {

    @Test
    void basic_shouldCreatePayload_whenDataTypeIsNumber() {
        final GitHubAddProjectFieldPayload.Basic payload =
                GitHubAddProjectFieldPayload.basic("Estimate", GitHubProjectFieldDataType.NUMBER);

        assertThat(payload.dataType()).isEqualTo(GitHubProjectFieldDataType.NUMBER);
    }

    @Test
    void basic_shouldCreatePayload_whenDataTypeIsDate() {
        final GitHubAddProjectFieldPayload.Basic payload =
                GitHubAddProjectFieldPayload.basic("Due", GitHubProjectFieldDataType.DATE);

        assertThat(payload.dataType()).isEqualTo(GitHubProjectFieldDataType.DATE);
    }

    @Test
    void basic_shouldThrow_whenDataTypeIsSingleSelect() {
        final Throwable thrown = catchThrowable(
                () -> GitHubAddProjectFieldPayload.basic("Status", GitHubProjectFieldDataType.SINGLE_SELECT));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("dataType");
    }

    @Test
    void singleSelect_shouldThrow_whenOptionsNull() {
        final Throwable thrown = catchThrowable(() -> GitHubAddProjectFieldPayload.singleSelect("Status", null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("singleSelectOptions");
    }

    @Test
    void singleSelect_shouldCopyOptions_whenEmpty() {
        final GitHubAddProjectFieldPayload.SingleSelect payload =
                GitHubAddProjectFieldPayload.singleSelect("Status", List.of());

        assertSoftly(softly -> {
            softly.assertThat(payload.singleSelectOptions()).isEmpty();
            softly.assertThat(payload.dataType()).isEqualTo(GitHubProjectFieldDataType.SINGLE_SELECT);
        });
    }

    @Test
    void iteration_shouldThrow_whenConfigurationNull() {
        final Throwable thrown = catchThrowable(() -> GitHubAddProjectFieldPayload.iteration("Sprint", null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("iterationConfiguration");
    }

    @Test
    void iteration_shouldCreatePayload_whenConfigurationPresent() {
        final GitHubProjectFieldIterationConfiguration configuration =
                new GitHubProjectFieldIterationConfiguration("2022-04-28", 14, null);

        final GitHubAddProjectFieldPayload.Iteration payload =
                GitHubAddProjectFieldPayload.iteration("Sprint", configuration);

        assertSoftly(softly -> {
            softly.assertThat(payload.iterationConfiguration()).isEqualTo(configuration);
            softly.assertThat(payload.dataType()).isEqualTo(GitHubProjectFieldDataType.ITERATION);
        });
    }

    @Test
    void issueField_shouldCreatePayload_whenCalled() {
        final GitHubAddProjectFieldPayload.IssueField payload = GitHubAddProjectFieldPayload.issueField(99);

        assertSoftly(softly -> {
            softly.assertThat(payload).isInstanceOf(GitHubAddProjectFieldPayload.IssueField.class);
            softly.assertThat(payload.issueFieldId()).isEqualTo(99);
        });
    }
}
