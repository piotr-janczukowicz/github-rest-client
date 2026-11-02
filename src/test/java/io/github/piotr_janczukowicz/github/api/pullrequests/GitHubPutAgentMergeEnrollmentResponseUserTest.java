package io.github.piotr_janczukowicz.github.api.pullrequests;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubPutAgentMergeEnrollmentResponseUserTest {

    @Test
    void constructor_shouldThrow_whenIdTooSmall() {
        final GitHubPutAgentMergeEnrollmentResponseUserBuilder builder =
                GitHubPutAgentMergeEnrollmentResponseUserBuilder.builder(
                                PullrequestsCatalogFixtures.putAgentMergeEnrollmentResponseUser())
                        .id(0L);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("id");
    }
}
