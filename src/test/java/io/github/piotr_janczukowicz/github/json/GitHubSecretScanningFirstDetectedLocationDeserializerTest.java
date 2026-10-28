package io.github.piotr_janczukowicz.github.json;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.github.api.secretscanning.GitHubSecretScanningFirstDetectedLocation;
import io.github.piotr_janczukowicz.github.api.secretscanning.GitHubSecretScanningLocationCommit;
import io.github.piotr_janczukowicz.github.api.secretscanning.GitHubSecretScanningUnknownLocationDetails;
import io.github.piotr_janczukowicz.json.JsonCodec;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import io.github.piotr_janczukowicz.util.FileUtils;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubSecretScanningFirstDetectedLocationDeserializerTest {

    private static final JsonCodec CODEC = GitHubJsonCodecs.defaults();

    @Test
    void decode_shouldReturnUnknown_whenShapeMatchesNoSubtype() {
        final byte[] json = "{\"some_new_url\":\"https://example.test/x\",\"line\":3}".getBytes(StandardCharsets.UTF_8);

        final GitHubSecretScanningFirstDetectedLocation decoded =
                CODEC.decode(json, GitHubSecretScanningFirstDetectedLocation.class);

        assertThat(decoded)
                .isEqualTo(new GitHubSecretScanningUnknownLocationDetails(
                        Map.of("some_new_url", "https://example.test/x", "line", 3)));
    }

    @Test
    void decode_shouldReturnCommit_whenCommitShape() {
        final byte[] json = FileUtils.readBytes(
                GitHubSecretScanningLocationCommit.class, "secret-scanning-location-commit-component.json");

        final GitHubSecretScanningFirstDetectedLocation decoded =
                CODEC.decode(json, GitHubSecretScanningFirstDetectedLocation.class);

        assertThat(decoded).isInstanceOf(GitHubSecretScanningLocationCommit.class);
    }

    @Test
    void decode_shouldReturnUnknown_whenOnlyHtmlUrlPresent() {
        final byte[] json =
                "{\"html_url\":\"https://github.com/octocat/Hello-World\"}".getBytes(StandardCharsets.UTF_8);

        final GitHubSecretScanningFirstDetectedLocation decoded =
                CODEC.decode(json, GitHubSecretScanningFirstDetectedLocation.class);

        assertThat(decoded)
                .isEqualTo(new GitHubSecretScanningUnknownLocationDetails(
                        Map.of("html_url", "https://github.com/octocat/Hello-World")));
    }
}
