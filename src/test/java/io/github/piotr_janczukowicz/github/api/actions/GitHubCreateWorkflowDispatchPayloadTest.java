package io.github.piotr_janczukowicz.github.api.actions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCreateWorkflowDispatchPayloadTest {

    @Test
    void constructor_shouldThrowNullPointerException_whenRefNull() {
        final Map<String, Object> inputs = Map.of("key", "value");

        final Throwable thrown = catchThrowable(() -> new GitHubCreateWorkflowDispatchPayload(null, inputs));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("ref");
    }

    @Test
    void constructor_shouldThrow_whenInputsHasMoreThanTwentyFiveEntries() {
        final GitHubCreateWorkflowDispatchPayloadBuilder builder = builder().inputs(inputsWithEntries(26));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("inputs");
    }

    @Test
    void constructor_shouldAccept_whenInputsAtMaxEntries() {
        final GitHubCreateWorkflowDispatchPayload payload =
                builder().inputs(inputsWithEntries(25)).build();

        assertThat(payload.inputs()).hasSize(25);
    }

    @Test
    void constructor_shouldLeaveInputsNull_whenNull() {
        final GitHubCreateWorkflowDispatchPayload payload =
                builder().inputs(null).build();

        assertThat(payload.inputs()).isNull();
    }

    private static Map<String, Object> inputsWithEntries(final int count) {
        final Map<String, Object> inputs = new HashMap<>();
        for (int index = 0; index < count; index++) {
            inputs.put("key" + index, "value");
        }
        return inputs;
    }

    private static GitHubCreateWorkflowDispatchPayloadBuilder builder() {
        return GitHubCreateWorkflowDispatchPayloadBuilder.builder(
                ActionsCatalogFixtures.createWorkflowDispatchPayload());
    }

    @Test
    void constructor_shouldCopyInputs_whenInputsGiven() {
        final Map<String, Object> source = new LinkedHashMap<>(
                ActionsCatalogFixtures.createWorkflowDispatchPayload().inputs());
        final int size = source.size();
        final GitHubCreateWorkflowDispatchPayload payload = GitHubCreateWorkflowDispatchPayloadBuilder.builder(
                        ActionsCatalogFixtures.createWorkflowDispatchPayload())
                .inputs(source)
                .build();

        source.put("extra", "value");
        final Map<String, Object> inputs = payload.inputs();

        final Throwable thrown = catchThrowable(() -> inputs.put("extra", "value"));

        assertSoftly(softly -> {
            softly.assertThat(payload.inputs()).hasSize(size);
            softly.assertThat(thrown).isInstanceOf(UnsupportedOperationException.class);
        });
    }

    @Test
    void constructor_shouldAcceptNullInputs_whenInputsNull() {
        final GitHubCreateWorkflowDispatchPayload payload = GitHubCreateWorkflowDispatchPayloadBuilder.builder(
                        ActionsCatalogFixtures.createWorkflowDispatchPayload())
                .inputs(null)
                .build();

        assertThat(payload.inputs()).isNull();
    }
}
