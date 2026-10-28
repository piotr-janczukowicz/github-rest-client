package io.github.piotr_janczukowicz.github.api.deployments;

import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.CODEC;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubDeploymentPayloadValueTest {

    @Test
    void ofText_shouldStoreValue_whenCalled() {
        final GitHubDeploymentPayloadValue payload = GitHubDeploymentPayloadValue.ofText("{ \"deploy\": \"migrate\" }");

        assertThat(payload.textValue()).isEqualTo("{ \"deploy\": \"migrate\" }");
    }

    @Test
    void ofText_shouldThrow_whenTextNull() {
        final Throwable thrown = catchThrowable(() -> GitHubDeploymentPayloadValue.ofText(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("text");
    }

    @Test
    void ofObject_shouldCopyMap_whenCalled() {
        final GitHubDeploymentPayloadValue payload = GitHubDeploymentPayloadValue.ofObject(Map.of("deploy", "migrate"));

        assertThat(payload.objectValue()).containsEntry("deploy", "migrate");
    }

    @Test
    void ofObject_shouldKeepNullValue_whenMapHasNullValue() {
        final Map<String, Object> object = new HashMap<>();
        object.put("deploy", null);

        final GitHubDeploymentPayloadValue payload = GitHubDeploymentPayloadValue.ofObject(object);

        assertThat(payload.objectValue()).containsEntry("deploy", null);
    }

    @Test
    void decode_shouldKeepNullValue_whenPayloadObjectHasNullValue() {
        final byte[] json = "{\"deploy\":null,\"step\":1}".getBytes(StandardCharsets.UTF_8);

        final GitHubDeploymentPayloadValue payload = CODEC.decode(json, GitHubDeploymentPayloadValue.class);

        assertThat(payload.objectValue()).containsEntry("deploy", null).containsEntry("step", 1);
    }

    @Test
    void ofObject_shouldThrow_whenObjectNull() {
        final Throwable thrown = catchThrowable(() -> GitHubDeploymentPayloadValue.ofObject(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("object");
    }

    @Test
    void constructor_shouldThrow_whenNoBranchSet() {
        final Throwable thrown = catchThrowable(() -> new GitHubDeploymentPayloadValue(null, null));

        assertThat(thrown)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("exactly one of textValue or objectValue is required");
    }

    @Test
    void constructor_shouldThrow_whenBothBranchesSet() {
        final Throwable thrown =
                catchThrowable(() -> new GitHubDeploymentPayloadValue("x", Map.of("deploy", "migrate")));

        assertThat(thrown)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("exactly one of textValue or objectValue is required");
    }
}
