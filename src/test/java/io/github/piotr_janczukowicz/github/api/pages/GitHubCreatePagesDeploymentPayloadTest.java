package io.github.piotr_janczukowicz.github.api.pages;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCreatePagesDeploymentPayloadTest {

    @Test
    void constructor_shouldThrow_whenPagesBuildVersionNull() {
        final Throwable thrown =
                catchThrowable(() -> new GitHubCreatePagesDeploymentPayload(null, null, null, null, "token"));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("pagesBuildVersion");
    }

    @Test
    void constructor_shouldThrow_whenOidcTokenNull() {
        final Throwable thrown =
                catchThrowable(() -> new GitHubCreatePagesDeploymentPayload(null, null, null, "sha", null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("oidcToken");
    }

    @Test
    void toString_shouldRedactSecret_whenPopulated() {
        final GitHubCreatePagesDeploymentPayload subject = GitHubCreatePagesDeploymentPayloadBuilder.builder(
                        PagesCatalogFixtures.createPagesDeploymentPayload())
                .oidcToken("eyJ-oidc-secret-value")
                .build();

        final String text = subject.toString();

        assertSoftly(softly -> {
            softly.assertThat(text).contains("<redacted>");
            softly.assertThat(text).doesNotContain("eyJ-oidc-secret-value");
        });
    }
}
