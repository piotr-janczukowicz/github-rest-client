package io.github.piotr_janczukowicz.github.api.apps;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubDeleteTokenPayloadTest {

    @Test
    void constructor_shouldThrow_whenAccessTokenNull() {
        final GitHubDeleteTokenPayloadBuilder builder = GitHubDeleteTokenPayloadBuilder.builder(
                        AppsCatalogFixtures.deleteTokenPayload())
                .accessToken(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("accessToken");
    }

    @Test
    void toString_shouldHideSecrets() {
        final GitHubDeleteTokenPayload value = AppsCatalogFixtures.deleteTokenPayload();

        final String text = value.toString();

        assertSoftly(softly -> {
            softly.assertThat(text).contains("accessToken=<redacted>");
            softly.assertThat(text).doesNotContain(value.accessToken());
        });
    }
}
