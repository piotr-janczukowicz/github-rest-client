package io.github.piotr_janczukowicz.github.json;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.github.api.enterpriseadmin.GitHubSplunkConfig;
import io.github.piotr_janczukowicz.github.api.enterpriseadmin.GitHubUpdateAuditLogStreamEmptyVendorSpecific;
import io.github.piotr_janczukowicz.github.api.enterpriseadmin.GitHubUpdateAuditLogStreamPayload;
import io.github.piotr_janczukowicz.github.api.enterpriseadmin.GitHubUpdateAuditLogStreamType;
import io.github.piotr_janczukowicz.json.JsonDecodeException;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.nio.charset.StandardCharsets;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.provider.Arguments;
import tools.jackson.databind.DatabindException;

@UnitTest
class GitHubUpdateAuditLogStreamPayloadDeserializerTest
        extends AuditLogStreamPayloadDeserializerContract<GitHubUpdateAuditLogStreamPayload> {

    @Override
    protected Class<GitHubUpdateAuditLogStreamPayload> payloadType() {
        return GitHubUpdateAuditLogStreamPayload.class;
    }

    @Override
    protected Object vendorSpecific(final GitHubUpdateAuditLogStreamPayload payload) {
        return payload.vendorSpecific();
    }

    @Override
    protected Stream<Arguments> extraVariants() {
        return Stream.of(Arguments.of("Microsoft Agent 365", "{}"));
    }

    @Test
    void deserialize_shouldBindEmptyRecord_whenMicrosoftAgent365AndEmptyObject() {
        final GitHubUpdateAuditLogStreamPayload payload = decode(body("Microsoft Agent 365", "{}"));

        assertThat(payload.vendorSpecific()).isInstanceOf(GitHubUpdateAuditLogStreamEmptyVendorSpecific.class);
    }

    @Test
    void deserialize_shouldThrowDecodeException_whenMicrosoftAgent365VendorNotEmpty() {
        final Class<GitHubUpdateAuditLogStreamPayload> type = payloadType();
        final byte[] bytes = body("Microsoft Agent 365", SPLUNK).getBytes(StandardCharsets.UTF_8);

        final Throwable thrown = catchThrowable(() -> CODEC.decode(bytes, type));

        assertThat(thrown).isInstanceOf(JsonDecodeException.class).cause().isInstanceOf(DatabindException.class);
    }

    @Test
    void encode_shouldWriteEmptyVendorSpecific_whenMicrosoftAgent365() {
        final GitHubUpdateAuditLogStreamPayload payload = new GitHubUpdateAuditLogStreamPayload(
                false,
                GitHubUpdateAuditLogStreamType.MICROSOFT_AGENT_365,
                new GitHubUpdateAuditLogStreamEmptyVendorSpecific());

        final String encoded = new String(CODEC.encode(payload), StandardCharsets.UTF_8);

        assertThat(encoded).contains("\"vendor_specific\":{}");
        assertThat(decode(encoded)).isEqualTo(payload);
    }

    @Override
    protected GitHubUpdateAuditLogStreamPayload splunkPayload() {
        return new GitHubUpdateAuditLogStreamPayload(
                true,
                GitHubUpdateAuditLogStreamType.SPLUNK,
                new GitHubSplunkConfig("splunk.example.com", 8088, "key-1", "encrypted-token", true));
    }
}
