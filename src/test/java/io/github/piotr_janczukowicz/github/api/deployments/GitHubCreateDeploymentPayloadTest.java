package io.github.piotr_janczukowicz.github.api.deployments;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCreateDeploymentPayloadTest {

    @Test
    void constructor_shouldThrow_whenRefNull() {
        final Throwable thrown = catchThrowable(
                () -> GitHubCreateDeploymentPayloadBuilder.builder().ref(null).build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("ref");
    }

    @Test
    void constructor_shouldCopyRequiredContexts_whenPresent() {
        final GitHubCreateDeploymentPayload payload = GitHubCreateDeploymentPayloadBuilder.builder()
                .ref("main")
                .requiredContexts(List.of("ci"))
                .build();

        assertThat(payload.requiredContexts()).containsExactly("ci");
    }

    @Test
    void constructor_shouldLeaveRequiredContextsNull_whenNull() {
        final GitHubCreateDeploymentPayload payload = GitHubCreateDeploymentPayloadBuilder.builder(
                        DeploymentsCatalogFixtures.createDeploymentPayload())
                .requiredContexts(null)
                .build();

        assertThat(payload.requiredContexts()).isNull();
    }
}
