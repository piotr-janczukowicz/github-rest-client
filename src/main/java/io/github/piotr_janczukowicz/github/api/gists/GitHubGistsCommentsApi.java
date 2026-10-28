package io.github.piotr_janczukowicz.github.api.gists;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import io.github.piotr_janczukowicz.github.client.GitHubCaller;
import io.github.piotr_janczukowicz.github.client.GitHubResourceApi;
import io.github.piotr_janczukowicz.github.client.exception.GitHubApiException;
import io.github.piotr_janczukowicz.github.pagination.GitHubPage;
import io.github.piotr_janczukowicz.github.pagination.GitHubPageRequest;
import io.github.piotr_janczukowicz.json.JsonType;
import io.github.piotr_janczukowicz.json.JsonTypes;
import io.github.piotr_janczukowicz.uri.PathTemplate;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/** GitHub gist comments REST resource for API version {@code 2026-03-10}. */
public final class GitHubGistsCommentsApi extends GitHubResourceApi {

    private static final PathTemplate COMMENTS = PathTemplate.of("/gists/{gist_id}/comments");
    private static final PathTemplate COMMENT = PathTemplate.of("/gists/{gist_id}/comments/{comment_id}");

    private static final JsonType<List<GitHubGistComment>> COMMENTS_TYPE = JsonTypes.listOf(GitHubGistComment.class);

    /**
     * Gist comments resource over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubGistsCommentsApi(final GitHubCaller caller) {
        super(caller);
    }

    /**
     * List gist comments ({@code GET /gists/{gist_id}/comments}).
     *
     * @param gistId gist id
     * @return first HTTP page of gist comments
     * @throws IllegalArgumentException if {@code gistId} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubGistComment> listComments(final String gistId) throws IOException {
        return listComments(gistId, GitHubPageRequest.byPage());
    }

    /**
     * List gist comments ({@code GET /gists/{gist_id}/comments}).
     *
     * @param gistId gist id
     * @param request pagination token
     * @return first HTTP page of gist comments
     * @throws IllegalArgumentException if {@code gistId} is blank, or {@code request} is invalid
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubGistComment> listComments(final String gistId, final GitHubPageRequest request)
            throws IOException {
        requireNonNull(request, PARAM_REQUEST);

        return pageList(COMMENTS, Map.of(), request, COMMENTS_TYPE, gistId);
    }

    /**
     * Create a gist comment ({@code POST /gists/{gist_id}/comments}).
     *
     * @param gistId gist id
     * @param payload comment to create
     * @return created comment
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IllegalArgumentException if {@code gistId} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubGistComment createComment(final String gistId, final GitHubCreateCommentPayload payload)
            throws IOException {
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(caller.json(post(COMMENTS, gistId), payload), GitHubGistComment.class);
    }

    /**
     * Get a gist comment ({@code GET /gists/{gist_id}/comments/{comment_id}}).
     *
     * @param gistId gist id
     * @param commentId comment id
     * @return comment, or empty on HTTP 404
     * @throws IllegalArgumentException if {@code gistId} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful (HTTP 404 yields empty)
     */
    public Optional<GitHubGistComment> getComment(final String gistId, final long commentId) throws IOException {
        return caller.fetchOrEmpty(get(COMMENT, gistId, commentId), GitHubGistComment.class);
    }

    /**
     * Update a gist comment ({@code PATCH /gists/{gist_id}/comments/{comment_id}}).
     *
     * @param gistId gist id
     * @param commentId comment id
     * @param payload updated comment
     * @return updated comment
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IllegalArgumentException if {@code gistId} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubGistComment updateComment(
            final String gistId, final long commentId, final GitHubUpdateCommentPayload payload) throws IOException {
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(caller.json(patch(COMMENT, gistId, commentId), payload), GitHubGistComment.class);
    }

    /**
     * Delete a gist comment ({@code DELETE /gists/{gist_id}/comments/{comment_id}}).
     *
     * @param gistId gist id
     * @param commentId comment id
     * @throws IllegalArgumentException if {@code gistId} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public void deleteComment(final String gistId, final long commentId) throws IOException {
        caller.send(delete(COMMENT, gistId, commentId));
    }
}
