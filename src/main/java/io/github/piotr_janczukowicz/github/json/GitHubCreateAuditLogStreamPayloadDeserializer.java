package io.github.piotr_janczukowicz.github.json;

import io.github.piotr_janczukowicz.github.api.enterpriseadmin.GitHubCreateAuditLogStreamPayload;
import io.github.piotr_janczukowicz.json.jackson.BufferedJson;
import tools.jackson.core.JsonParser;
import tools.jackson.databind.DeserializationContext;
import tools.jackson.databind.deser.std.StdDeserializer;

/**
 * Decodes the {@code create-audit-log-stream} request body. The sibling {@code stream_type} decides which record
 * {@code vendor_specific} is bound to.
 */
final class GitHubCreateAuditLogStreamPayloadDeserializer extends StdDeserializer<GitHubCreateAuditLogStreamPayload> {

    GitHubCreateAuditLogStreamPayloadDeserializer() {
        super(GitHubCreateAuditLogStreamPayload.class);
    }

    @Override
    public GitHubCreateAuditLogStreamPayload deserialize(
            final JsonParser parser, final DeserializationContext context) {
        try (BufferedJson json = BufferedJson.capture(parser, context)) {
            return GitHubAuditLogStreamPayloads.readCreate(json);
        }
    }
}
