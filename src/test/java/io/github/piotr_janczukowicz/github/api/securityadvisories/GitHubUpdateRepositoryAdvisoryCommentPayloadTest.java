package io.github.piotr_janczukowicz.github.api.securityadvisories;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubUpdateRepositoryAdvisoryCommentPayloadTest {

    @Test
    void constructor_shouldThrow_whenBodyNull() {
        final GitHubUpdateRepositoryAdvisoryCommentPayloadBuilder builder =
                GitHubUpdateRepositoryAdvisoryCommentPayloadBuilder.builder(
                                SecurityAdvisoriesCatalogFixtures.updateRepositoryAdvisoryCommentPayload())
                        .body(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("body");
    }
}
