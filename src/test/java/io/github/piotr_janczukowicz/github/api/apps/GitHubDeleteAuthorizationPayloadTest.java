package io.github.piotr_janczukowicz.github.api.apps;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubDeleteAuthorizationPayloadTest {

    @Test
    void constructor_shouldThrow_whenAccessTokenNull() {
        final GitHubDeleteAuthorizationPayloadBuilder builder = GitHubDeleteAuthorizationPayloadBuilder.builder(
                        AppsCatalogFixtures.deleteAuthorizationPayload())
                .accessToken(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("accessToken");
    }

    @Test
    void toString_shouldHideSecrets() {
        final GitHubDeleteAuthorizationPayload value = AppsCatalogFixtures.deleteAuthorizationPayload();

        final String text = value.toString();

        assertSoftly(softly -> {
            softly.assertThat(text).contains("accessToken=<redacted>");
            softly.assertThat(text).doesNotContain(value.accessToken());
        });
    }
}
