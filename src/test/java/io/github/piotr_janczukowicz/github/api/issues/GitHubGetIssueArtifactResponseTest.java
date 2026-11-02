package io.github.piotr_janczukowicz.github.api.issues;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.github.json.GitHubJsonCodecs;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubGetIssueArtifactResponseTest {

    @Test
    void decode_shouldThrow_whenNoPropertyDecidesTheShape() {
        final byte[] json = "{\"brand_new\":1}".getBytes(StandardCharsets.UTF_8);

        final Throwable thrown =
                catchThrowable(() -> GitHubJsonCodecs.defaults().decode(json, GitHubGetIssueArtifactResponse.class));

        assertThat(thrown).hasStackTraceContaining("Cannot deduce unique subtype");
    }

    @Test
    void decode_shouldThrow_whenPropertiesOfBothShapesMix() {
        final byte[] json = "{\"id\":1,\"number\":1,\"artifact\":{},\"versions\":[]}".getBytes(StandardCharsets.UTF_8);

        final Throwable thrown =
                catchThrowable(() -> GitHubJsonCodecs.defaults().decode(json, GitHubGetIssueArtifactResponse.class));

        assertThat(thrown).hasRootCauseInstanceOf(NullPointerException.class);
    }

    @Test
    void decode_shouldThrow_whenOnlyVersionsGiven() {
        final byte[] json = "{\"versions\":[]}".getBytes(StandardCharsets.UTF_8);

        final Throwable thrown =
                catchThrowable(() -> GitHubJsonCodecs.defaults().decode(json, GitHubGetIssueArtifactResponse.class));

        assertThat(thrown).hasRootCauseInstanceOf(NullPointerException.class);
    }
}
