package io.github.piotr_janczukowicz.github.json;

import io.github.piotr_janczukowicz.github.api.enterpriseadmin.GitHubUpdateAuditLogStreamPayload;
import io.github.piotr_janczukowicz.json.jackson.BufferedJson;
import tools.jackson.core.JsonParser;
import tools.jackson.databind.DeserializationContext;
import tools.jackson.databind.deser.std.StdDeserializer;

/**
 * Decodes the {@code update-audit-log-stream} request body. The sibling {@code stream_type} decides which record
 * {@code vendor_specific} is bound to.
 */
final class GitHubUpdateAuditLogStreamPayloadDeserializer extends StdDeserializer<GitHubUpdateAuditLogStreamPayload> {

    GitHubUpdateAuditLogStreamPayloadDeserializer() {
        super(GitHubUpdateAuditLogStreamPayload.class);
    }

    @Override
    public GitHubUpdateAuditLogStreamPayload deserialize(
            final JsonParser parser, final DeserializationContext context) {
        try (BufferedJson json = BufferedJson.capture(parser, context)) {
            return GitHubAuditLogStreamPayloads.readUpdate(json);
        }
    }
}
