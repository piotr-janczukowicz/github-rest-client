package io.github.piotr_janczukowicz.github.api.repositories;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCreateDispatchEventPayloadTest {

    @Test
    void constructor_shouldThrow_whenEventTypeNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubCreateDispatchEventPayload(null, null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("eventType");
    }

    @Test
    void constructor_shouldThrow_whenEventTypeEmpty() {
        final Throwable thrown = catchThrowable(() -> new GitHubCreateDispatchEventPayload("", null));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("eventType");
    }

    @Test
    void constructor_shouldThrow_whenEventTypeLongerThan100() {
        final String eventType = "a".repeat(101);

        final Throwable thrown = catchThrowable(() -> new GitHubCreateDispatchEventPayload(eventType, null));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("eventType");
    }

    @Test
    void constructor_shouldKeepNullValue_whenClientPayloadHasNullValue() {
        final Map<String, Object> clientPayload = new HashMap<>();
        clientPayload.put("unit", null);

        final GitHubCreateDispatchEventPayload payload = new GitHubCreateDispatchEventPayload("build", clientPayload);

        assertThat(payload.clientPayload()).containsEntry("unit", null);
    }

    @Test
    void constructor_shouldThrow_whenClientPayloadHasMoreThan10Properties() {
        final Map<String, Object> clientPayload = Map.ofEntries(
                Map.entry("a", 1),
                Map.entry("b", 2),
                Map.entry("c", 3),
                Map.entry("d", 4),
                Map.entry("e", 5),
                Map.entry("f", 6),
                Map.entry("g", 7),
                Map.entry("h", 8),
                Map.entry("i", 9),
                Map.entry("j", 10),
                Map.entry("k", 11));

        final Throwable thrown = catchThrowable(() -> new GitHubCreateDispatchEventPayload("deploy", clientPayload));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("clientPayload");
    }

    @Test
    void constructor_shouldLeaveClientPayloadNull_whenClientPayloadNull() {
        final GitHubCreateDispatchEventPayload payload = new GitHubCreateDispatchEventPayload("deploy", null);

        assertThat(payload.clientPayload()).isNull();
    }

    @Test
    void constructor_shouldCopyClientPayload_whenClientPayloadProvided() {
        final GitHubCreateDispatchEventPayload payload =
                new GitHubCreateDispatchEventPayload("deploy", Map.of("ref", "main"));

        assertThat(payload.clientPayload()).containsEntry("ref", "main");
    }
}
