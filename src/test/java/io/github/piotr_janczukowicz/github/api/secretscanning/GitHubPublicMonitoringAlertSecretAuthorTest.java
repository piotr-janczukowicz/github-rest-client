package io.github.piotr_janczukowicz.github.api.secretscanning;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.github.json.GitHubJsonCodecs;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubPublicMonitoringAlertSecretAuthorTest {

    @Test
    void decode_shouldReturnDeliveredDetails_whenOnlyFieldsBothShapesHave() {
        final byte[] json = "{\"login\":\"octocat\"}".getBytes(StandardCharsets.UTF_8);

        final GitHubPublicMonitoringAlertSecretAuthor author =
                GitHubJsonCodecs.defaults().decode(json, GitHubPublicMonitoringAlertSecretAuthor.class);

        assertThat(author).isInstanceOf(GitHubPublicMonitoringSecretAuthor.class);
    }

    @Test
    void decode_shouldReturnDeliveredDetails_whenUnknownPropertyPresent() {
        final byte[] json = "{\"login\":\"octocat\",\"brand_new\":1}".getBytes(StandardCharsets.UTF_8);

        final GitHubPublicMonitoringAlertSecretAuthor author =
                GitHubJsonCodecs.defaults().decode(json, GitHubPublicMonitoringAlertSecretAuthor.class);

        assertThat(author).isInstanceOf(GitHubPublicMonitoringSecretAuthor.class);
    }

    @Test
    void decode_shouldThrow_whenSimpleUserFieldPresentButRequiredFieldsMissing() {
        final byte[] json = "{\"login\":\"octocat\",\"node_id\":\"MDQ6VXNlcjE=\"}".getBytes(StandardCharsets.UTF_8);

        final Throwable thrown = catchThrowable(
                () -> GitHubJsonCodecs.defaults().decode(json, GitHubPublicMonitoringAlertSecretAuthor.class));

        assertThat(thrown).hasRootCauseInstanceOf(NullPointerException.class);
    }
}
