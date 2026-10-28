package io.github.piotr_janczukowicz.github.api.apps;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubResetTokenPayloadTest {

    @Test
    void constructor_shouldThrow_whenAccessTokenNull() {
        final GitHubResetTokenPayloadBuilder builder = GitHubResetTokenPayloadBuilder.builder(
                        AppsCatalogFixtures.resetTokenPayload())
                .accessToken(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("accessToken");
    }

    @Test
    void toString_shouldHideSecrets() {
        final GitHubResetTokenPayload value = AppsCatalogFixtures.resetTokenPayload();

        final String text = value.toString();

        assertSoftly(softly -> {
            softly.assertThat(text).contains("accessToken=<redacted>");
            softly.assertThat(text).doesNotContain(value.accessToken());
        });
    }
}
