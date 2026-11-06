package io.github.piotr_janczukowicz.github.api.generic;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import io.github.piotr_janczukowicz.github.client.GitHubCaller;
import io.github.piotr_janczukowicz.github.client.GitHubClient;
import io.github.piotr_janczukowicz.github.client.GitHubResourceApi;
import io.github.piotr_janczukowicz.github.client.exception.GitHubApiException;
import io.github.piotr_janczukowicz.github.client.pagination.GitHubListSpec;
import io.github.piotr_janczukowicz.github.pagination.GitHubPage;
import io.github.piotr_janczukowicz.github.pagination.GitHubPageRequest;
import io.github.piotr_janczukowicz.github.pagination.GitHubSearchEnvelope;
import io.github.piotr_janczukowicz.http.HttpResponse;
import io.github.piotr_janczukowicz.http.client.HttpCall;
import io.github.piotr_janczukowicz.http.client.HttpEmptyResponseException;
import io.github.piotr_janczukowicz.json.JsonType;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

/**
 * Sends calls to the GitHub REST API that the typed APIs do not cover, or cover incorrectly. <strong>A last
 * resort:</strong> use the typed APIs where they exist. A generic call has no models, no validation and no check of the
 * GitHub flavor or Enterprise Server version; the caller owns its correctness and compatibility.
 *
 * <p>A call goes through the same client as the typed APIs, so it gets the conditional cache, retry and rate-limit
 * handling, mutation pacing, error mapping and credential renewal. The API version stays fixed to
 * {@value GitHubClient#API_VERSION}: {@link GitHubGenericRequest} refuses the headers that would change it or the
 * credentials, and refuses paths that could leave the API base.
 *
 * <p>Unsuccessful statuses become {@link GitHubApiException} or a subtype; transport failures are {@link IOException}.
 */
public final class GitHubGenericApi extends GitHubResourceApi {

    private static final String PARAM_MEDIA_TYPE = "mediaType";
    private static final String PARAM_TYPE = "type";

    /**
     * Generic resource over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubGenericApi(final GitHubCaller caller) {
        super(caller);
    }

    /**
     * Sends {@code request} and decodes a successful response body.
     *
     * @param request call to send
     * @param type type of the response body, including type arguments
     * @param <T> response body type
     * @return decoded body
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws HttpEmptyResponseException if a successful response has no body, for example HTTP 204; use {@link #send}
     *     for such calls
     * @throws NullPointerException if an argument is {@code null}
     */
    public <T> T fetch(final GitHubGenericRequest request, final JsonType<T> type) throws IOException {
        requireNonNull(type, PARAM_TYPE);
        return caller.fetch(toCall(request), type);
    }

    /**
     * Sends {@code request} and returns the raw response, for the status, the headers or a body that is not JSON.
     *
     * @param request call to send
     * @return response of a successful call
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws NullPointerException if {@code request} is {@code null}
     */
    public HttpResponse send(final GitHubGenericRequest request) throws IOException {
        return caller.send(toCall(request));
    }

    /**
     * Sends {@code request} with {@code Accept: mediaType} and returns the body as text.
     *
     * @param request call to send; it must be a GET
     * @param mediaType media type for the {@code Accept} header
     * @return body text, {@code ""} when a successful response has no body, or an empty {@link Optional} on HTTP 404
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful for a reason other than 404
     * @throws NullPointerException if an argument is {@code null}
     */
    public Optional<String> fetchText(final GitHubGenericRequest request, final String mediaType) throws IOException {
        requireNonNull(mediaType, PARAM_MEDIA_TYPE);
        return fetchTextOrEmpty(toCall(request), mediaType);
    }

    /**
     * Sends a GET list {@code request} and returns the first page; the page walks the {@code Link} headers of the
     * response. {@code page} binds the {@code page} and {@code per_page} query parameters.
     *
     * @param request GET call without body
     * @param page page token, for example {@link GitHubPageRequest#byPage()}
     * @param type type of the list in the response body
     * @param <T> item type
     * @return first page
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws NullPointerException if an argument is {@code null}
     * @throws IllegalArgumentException if {@code request} is not a GET or has a body
     */
    public <T> GitHubPage<T> list(
            final GitHubGenericRequest request, final GitHubPageRequest page, final JsonType<List<T>> type)
            throws IOException {
        requireNonNull(type, PARAM_TYPE);
        return caller.list(toCall(request), page, GitHubListSpec.pages(type));
    }

    /**
     * Sends a GET search {@code request} whose response body is decoded as {@code type} and mapped to a page envelope.
     *
     * @param request GET call without body
     * @param page page token, for example {@link GitHubPageRequest#byPage()}
     * @param type type of the response body, including type arguments
     * @param envelope maps the decoded body to the page envelope
     * @param <R> response body type
     * @param <T> item type
     * @return first page
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws NullPointerException if an argument is {@code null}
     * @throws IllegalArgumentException if {@code request} is not a GET or has a body
     */
    public <R, T> GitHubPage<T> search(
            final GitHubGenericRequest request,
            final GitHubPageRequest page,
            final JsonType<R> type,
            final Function<R, GitHubSearchEnvelope<T>> envelope)
            throws IOException {
        return caller.search(toCall(request), page, type, envelope);
    }

    private HttpCall toCall(final GitHubGenericRequest request) {
        return requireNonNull(request, PARAM_REQUEST).toCall(caller);
    }
}
