package io.github.piotr_janczukowicz.github.json;

import io.github.piotr_janczukowicz.github.api.enterpriseadmin.GitHubCreateAuditLogStreamPayload;
import io.github.piotr_janczukowicz.github.api.enterpriseadmin.GitHubCreateAuditLogStreamType;
import io.github.piotr_janczukowicz.github.api.enterpriseadmin.GitHubSplunkConfig;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.stream.Stream;
import org.junit.jupiter.params.provider.Arguments;

@UnitTest
class GitHubCreateAuditLogStreamPayloadDeserializerTest
        extends AuditLogStreamPayloadDeserializerContract<GitHubCreateAuditLogStreamPayload> {

    @Override
    protected Class<GitHubCreateAuditLogStreamPayload> payloadType() {
        return GitHubCreateAuditLogStreamPayload.class;
    }

    @Override
    protected Object vendorSpecific(final GitHubCreateAuditLogStreamPayload payload) {
        return payload.vendorSpecific();
    }

    @Override
    protected Stream<Arguments> extraInvalidBodies() {
        return Stream.of(
                Arguments.of(body("Microsoft Agent 365", "{}"), "Unknown GitHub create audit log stream type"));
    }

    @Override
    protected GitHubCreateAuditLogStreamPayload splunkPayload() {
        return new GitHubCreateAuditLogStreamPayload(
                true,
                GitHubCreateAuditLogStreamType.SPLUNK,
                new GitHubSplunkConfig("splunk.example.com", 8088, "key-1", "encrypted-token", true));
    }
}
