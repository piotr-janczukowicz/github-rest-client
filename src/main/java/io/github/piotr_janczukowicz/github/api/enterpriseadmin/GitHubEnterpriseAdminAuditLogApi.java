package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.api.organizations.GitHubAuditLogEvent;
import io.github.piotr_janczukowicz.github.client.GitHubCaller;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.github.client.GitHubResourceApi;
import io.github.piotr_janczukowicz.github.client.exception.GitHubApiException;
import io.github.piotr_janczukowicz.github.client.pagination.GitHubListSpec;
import io.github.piotr_janczukowicz.github.pagination.GitHubPage;
import io.github.piotr_janczukowicz.github.pagination.GitHubPageRequest;
import io.github.piotr_janczukowicz.github.pagination.GitHubPageRequestKindValidators;
import io.github.piotr_janczukowicz.json.JsonType;
import io.github.piotr_janczukowicz.json.JsonTypes;
import io.github.piotr_janczukowicz.uri.PathTemplate;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

/** GitHub Enterprise Cloud audit log REST resource for API version {@code 2026-03-10}. */
public final class GitHubEnterpriseAdminAuditLogApi extends GitHubResourceApi {

    private static final PathTemplate AUDIT_LOG = PathTemplate.of("/enterprises/{enterprise}/audit-log");
    private static final PathTemplate STREAM_KEY = PathTemplate.of("/enterprises/{enterprise}/audit-log/stream-key");
    private static final PathTemplate STREAMS = PathTemplate.of("/enterprises/{enterprise}/audit-log/streams");
    private static final PathTemplate STREAM =
            PathTemplate.of("/enterprises/{enterprise}/audit-log/streams/{stream_id}");

    private static final GitHubListSpec<GitHubAuditLogEvent> AUDIT_LOG_SPEC = GitHubListSpec.array(
            GitHubPageRequestKindValidators.PAGE_OR_CURSOR, JsonTypes.listOf(GitHubAuditLogEvent.class));
    private static final JsonType<List<GitHubGetAuditLogStreamConfigsItem>> STREAMS_TYPE =
            JsonTypes.listOf(GitHubGetAuditLogStreamConfigsItem.class);

    /**
     * Audit log resource over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubEnterpriseAdminAuditLogApi(final GitHubCaller caller) {
        super(caller);
    }

    /**
     * Get the audit log of an enterprise ({@code GET /enterprises/{enterprise}/audit-log}) with no filter.
     *
     * <p>Uses {@link GitHubPageRequest#byPage()} and an empty filter. See {@link #getAuditLog(String,
     * GitHubGetAuditLogFilter, GitHubPageRequest)}.
     *
     * @param enterprise the slug version of the enterprise name
     * @return first HTTP page of audit log events
     * @throws IllegalArgumentException if {@code enterprise} is empty
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC} or
     *     {@code GHES}
     */
    @Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
    public GitHubPage<GitHubAuditLogEvent> getAuditLog(final String enterprise) throws IOException {
        return getAuditLog(enterprise, GitHubGetAuditLogFilter.empty(), GitHubPageRequest.byPage());
    }

    /**
     * Get the audit log of an enterprise ({@code GET /enterprises/{enterprise}/audit-log}).
     *
     * <p>Accepts {@link io.github.piotr_janczukowicz.github.pagination.GitHubPageNumberRequest} and
     * {@link io.github.piotr_janczukowicz.github.pagination.GitHubCursorRequest} only.
     *
     * @param enterprise the slug version of the enterprise name
     * @param filter query filters
     * @param request page or cursor pagination token
     * @return page of audit log events
     * @throws IllegalArgumentException if {@code enterprise} is empty, or {@code request} is a since variant
     * @throws NullPointerException if {@code filter} or {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC} or
     *     {@code GHES}
     */
    @Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
    public GitHubPage<GitHubAuditLogEvent> getAuditLog(
            final String enterprise, final GitHubGetAuditLogFilter filter, final GitHubPageRequest request)
            throws IOException {
        requireFlavor(GitHubFlavor.GHEC, GitHubFlavor.GHES);
        requireNonNull(filter, PARAM_FILTER);
        requireNonNull(request, PARAM_REQUEST);

        return list(AUDIT_LOG, filter.toQueryParameters(), request, AUDIT_LOG_SPEC, enterprise);
    }

    /**
     * Get the public key that signs audit log stream payloads ({@code GET
     * /enterprises/{enterprise}/audit-log/stream-key}).
     *
     * @param enterprise the slug version of the enterprise name
     * @return audit log stream key
     * @throws IllegalArgumentException if {@code enterprise} is empty
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC} or
     *     {@code GHES}
     */
    @Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
    public GitHubAuditLogStreamKey getAuditLogStreamKey(final String enterprise) throws IOException {
        requireFlavor(GitHubFlavor.GHEC, GitHubFlavor.GHES);
        return caller.fetch(get(STREAM_KEY, enterprise), GitHubAuditLogStreamKey.class);
    }

    /**
     * List the audit log streaming configurations of an enterprise ({@code GET
     * /enterprises/{enterprise}/audit-log/streams}).
     *
     * @param enterprise the slug version of the enterprise name
     * @return audit log streaming configurations
     * @throws IllegalArgumentException if {@code enterprise} is empty
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC} or
     *     {@code GHES}
     */
    @Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
    public List<GitHubGetAuditLogStreamConfigsItem> getAuditLogStreams(final String enterprise) throws IOException {
        requireFlavor(GitHubFlavor.GHEC, GitHubFlavor.GHES);
        return caller.fetch(get(STREAMS, enterprise), STREAMS_TYPE);
    }

    /**
     * Create an audit log streaming configuration for an enterprise ({@code POST
     * /enterprises/{enterprise}/audit-log/streams}).
     *
     * @param enterprise the slug version of the enterprise name
     * @param payload the stream to create
     * @return the created streaming configuration
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IllegalArgumentException if {@code enterprise} is empty
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC} or
     *     {@code GHES}
     */
    @Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
    public GitHubGetAuditLogStreamConfig createAuditLogStream(
            final String enterprise, final GitHubCreateAuditLogStreamPayload payload) throws IOException {
        requireFlavor(GitHubFlavor.GHEC, GitHubFlavor.GHES);
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(caller.json(post(STREAMS, enterprise), payload), GitHubGetAuditLogStreamConfig.class);
    }

    /**
     * Get an audit log streaming configuration of an enterprise ({@code GET
     * /enterprises/{enterprise}/audit-log/streams/{stream_id}}).
     *
     * @param enterprise the slug version of the enterprise name
     * @param streamId the unique identifier of the stream
     * @return streaming configuration, or empty on HTTP 404
     * @throws IllegalArgumentException if {@code enterprise} is empty
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful (HTTP 404 yields empty)
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC} or
     *     {@code GHES}
     */
    @Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
    public Optional<GitHubGetAuditLogStreamConfig> getAuditLogStream(final String enterprise, final long streamId)
            throws IOException {
        requireFlavor(GitHubFlavor.GHEC, GitHubFlavor.GHES);
        return caller.fetchOrEmpty(get(STREAM, enterprise, streamId), GitHubGetAuditLogStreamConfig.class);
    }

    /**
     * Update an audit log streaming configuration of an enterprise ({@code PUT
     * /enterprises/{enterprise}/audit-log/streams/{stream_id}}).
     *
     * @param enterprise the slug version of the enterprise name
     * @param streamId the unique identifier of the stream
     * @param payload the new stream settings
     * @return the updated streaming configuration
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IllegalArgumentException if {@code enterprise} is empty
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC} or
     *     {@code GHES}
     */
    @Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
    public GitHubGetAuditLogStreamConfig updateAuditLogStream(
            final String enterprise, final long streamId, final GitHubUpdateAuditLogStreamPayload payload)
            throws IOException {
        requireFlavor(GitHubFlavor.GHEC, GitHubFlavor.GHES);
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(
                caller.json(put(STREAM, enterprise, streamId), payload), GitHubGetAuditLogStreamConfig.class);
    }

    /**
     * Delete an audit log streaming configuration of an enterprise ({@code DELETE
     * /enterprises/{enterprise}/audit-log/streams/{stream_id}}).
     *
     * @param enterprise the slug version of the enterprise name
     * @param streamId the unique identifier of the stream
     * @throws IllegalArgumentException if {@code enterprise} is empty
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC} or
     *     {@code GHES}
     */
    @Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
    public void deleteAuditLogStream(final String enterprise, final long streamId) throws IOException {
        requireFlavor(GitHubFlavor.GHEC, GitHubFlavor.GHES);
        caller.send(delete(STREAM, enterprise, streamId));
    }
}
