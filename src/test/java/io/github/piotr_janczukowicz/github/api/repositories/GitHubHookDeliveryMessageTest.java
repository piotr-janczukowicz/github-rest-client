package io.github.piotr_janczukowicz.github.api.repositories;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubHookDeliveryMessageTest {

    @Test
    void constructor_shouldLeaveHeadersAndPayloadNull_whenHeadersAndPayloadNull() {
        final GitHubHookDeliveryMessage message = new GitHubHookDeliveryMessage(null, null);

        assertSoftly(softly -> {
            softly.assertThat(message.headers()).isNull();
            softly.assertThat(message.payload()).isNull();
        });
    }

    @Test
    void constructor_shouldKeepNullValues_whenHeadersAndPayloadContainNull() {
        final Map<String, Object> headers = new HashMap<>();
        headers.put("X-Null", null);
        final Map<String, Object> payload = new HashMap<>();
        payload.put("action", null);

        final GitHubHookDeliveryMessage message = new GitHubHookDeliveryMessage(headers, payload);

        assertSoftly(softly -> {
            softly.assertThat(message.headers()).containsEntry("X-Null", null);
            softly.assertThat(message.payload()).containsEntry("action", null);
        });
    }
}
