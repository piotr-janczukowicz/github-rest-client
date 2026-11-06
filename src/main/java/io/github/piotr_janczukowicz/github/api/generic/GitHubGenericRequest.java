package io.github.piotr_janczukowicz.github.api.generic;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import io.github.piotr_janczukowicz.github.client.GitHubCaller;
import io.github.piotr_janczukowicz.github.client.GitHubClient;
import io.github.piotr_janczukowicz.http.HttpBody;
import io.github.piotr_janczukowicz.http.HttpHeaderNames;
import io.github.piotr_janczukowicz.http.HttpHeaders;
import io.github.piotr_janczukowicz.http.HttpMethod;
import io.github.piotr_janczukowicz.http.client.HttpCall;
import io.github.piotr_janczukowicz.uri.PathTemplate;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import org.jspecify.annotations.Nullable;

/**
 * A call to the GitHub REST API described by hand, for {@link GitHubGenericApi}.
 *
 * <p>The path is relative to the API base of the client and may contain placeholders in braces; each path variable
 * fills one placeholder in order and is percent-encoded as a single segment, so a value such as {@code a/b} or
 * {@code ..} cannot change the path. A path that is not absolute ({@code /...}), or that contains a query, fragment,
 * backslash, empty or dot segment, or an encoded separator, is refused.
 *
 * <p>The API version is fixed to {@value GitHubClient#API_VERSION} and the credentials are those of the client, so the
 * headers {@code X-GitHub-Api-Version}, {@code Authorization} and {@code Host} are refused, and {@code Content-Type} is
 * refused together with a {@code jsonBody}, which sets it (the framing headers {@code Content-Length} and
 * {@code Transfer-Encoding} are refused by {@link HttpHeaders} itself). At most one of {@code jsonBody} and
 * {@code body} may be set.
 *
 * @param method HTTP method
 * @param path path template relative to the API base, starting with {@code /}
 * @param pathVariables values for the placeholders of {@code path}, in order
 * @param query query parameters
 * @param headers extra request headers
 * @param jsonBody object encoded as JSON, or {@code null}
 * @param body raw body, or {@code null}
 */
public record GitHubGenericRequest(
        HttpMethod method,
        String path,
        List<String> pathVariables,
        Map<String, List<String>> query,
        HttpHeaders headers,
        @Nullable Object jsonBody,
        @Nullable HttpBody body) {

    private static final Set<String> RESERVED_HEADERS = Set.of(
            "x-github-api-version",
            HttpHeaderNames.AUTHORIZATION.toLowerCase(Locale.ROOT),
            HttpHeaderNames.HOST.toLowerCase(Locale.ROOT));

    public GitHubGenericRequest {
        requireNonNull(method, "method");
        requireNonNull(path, "path");
        requireNonNull(pathVariables, "pathVariables");
        requireNonNull(query, "query");
        requireNonNull(headers, "headers");
        if (!path.startsWith("/") || GitHubCaller.isAmbiguousPath(path)) {
            throw new IllegalArgumentException("path must be absolute below the API base, without query, fragment,"
                    + " backslash, empty or dot segments or encoded separator, but is: " + path);
        }
        headers.asMap().keySet().forEach(GitHubGenericRequest::requireAllowedHeader);
        if (jsonBody != null && body != null) {
            throw new IllegalArgumentException("only one of jsonBody and body may be set");
        }
        pathVariables = List.copyOf(pathVariables);
        if (jsonBody != null && headers.get(HttpHeaderNames.CONTENT_TYPE).isPresent()) {
            throw new IllegalArgumentException("Content-Type is set by jsonBody and cannot be given as a header");
        }
        query = copyOf(query);
    }

    /**
     * GET request.
     *
     * @param path path template
     * @return request without variables, query, headers or body
     * @throws NullPointerException if {@code path} is {@code null}
     * @throws IllegalArgumentException if {@code path} is refused
     */
    public static GitHubGenericRequest get(final String path) {
        return of(HttpMethod.GET, path);
    }

    /**
     * POST request.
     *
     * @param path path template
     * @return request without variables, query, headers or body
     * @throws NullPointerException if {@code path} is {@code null}
     * @throws IllegalArgumentException if {@code path} is refused
     */
    public static GitHubGenericRequest post(final String path) {
        return of(HttpMethod.POST, path);
    }

    /**
     * PUT request.
     *
     * @param path path template
     * @return request without variables, query, headers or body
     * @throws NullPointerException if {@code path} is {@code null}
     * @throws IllegalArgumentException if {@code path} is refused
     */
    public static GitHubGenericRequest put(final String path) {
        return of(HttpMethod.PUT, path);
    }

    /**
     * PATCH request.
     *
     * @param path path template
     * @return request without variables, query, headers or body
     * @throws NullPointerException if {@code path} is {@code null}
     * @throws IllegalArgumentException if {@code path} is refused
     */
    public static GitHubGenericRequest patch(final String path) {
        return of(HttpMethod.PATCH, path);
    }

    /**
     * DELETE request.
     *
     * @param path path template
     * @return request without variables, query, headers or body
     * @throws NullPointerException if {@code path} is {@code null}
     * @throws IllegalArgumentException if {@code path} is refused
     */
    public static GitHubGenericRequest delete(final String path) {
        return of(HttpMethod.DELETE, path);
    }

    /**
     * Copy with {@code pathVariables}.
     *
     * @param pathVariables values for the placeholders, in order
     * @return new request
     * @throws NullPointerException if {@code pathVariables} is {@code null}
     */
    public GitHubGenericRequest withPathVariables(final List<String> pathVariables) {
        return new GitHubGenericRequest(method, path, pathVariables, query, headers, jsonBody, body);
    }

    /**
     * Copy with {@code query}.
     *
     * @param query query parameters
     * @return new request
     * @throws NullPointerException if {@code query} is {@code null}
     */
    public GitHubGenericRequest withQuery(final Map<String, List<String>> query) {
        return new GitHubGenericRequest(method, path, pathVariables, query, headers, jsonBody, body);
    }

    /**
     * Copy with {@code headers}.
     *
     * @param headers extra headers
     * @return new request
     * @throws NullPointerException if {@code headers} is {@code null}
     * @throws IllegalArgumentException if a header is reserved
     */
    public GitHubGenericRequest withHeaders(final HttpHeaders headers) {
        return new GitHubGenericRequest(method, path, pathVariables, query, headers, jsonBody, body);
    }

    /**
     * Copy with a JSON body.
     *
     * @param jsonBody object to encode as JSON
     * @return new request
     * @throws IllegalArgumentException if a raw body is set
     */
    public GitHubGenericRequest withJsonBody(final Object jsonBody) {
        return new GitHubGenericRequest(method, path, pathVariables, query, headers, jsonBody, body);
    }

    /**
     * Copy with a raw body.
     *
     * @param body raw body
     * @return new request
     * @throws IllegalArgumentException if a JSON body is set
     */
    public GitHubGenericRequest withBody(final HttpBody body) {
        return new GitHubGenericRequest(method, path, pathVariables, query, headers, jsonBody, body);
    }

    /**
     * The call this request describes, with the path expanded and the body encoded by {@code caller}.
     *
     * @param caller encodes a JSON body
     * @return call to send
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public HttpCall toCall(final GitHubCaller caller) {
        requireNonNull(caller, "caller");
        final String expanded = PathTemplate.of(path).expand(pathVariables.toArray());
        final HttpCall call =
                HttpCall.of(method, expanded).withQueryValues(query).withHeaders(headers);
        if (jsonBody != null) {
            return caller.json(call, jsonBody);
        }
        return body == null ? call : call.withBody(body);
    }

    private static Map<String, List<String>> copyOf(final Map<String, List<String>> query) {
        final Map<String, List<String>> copy = LinkedHashMap.newLinkedHashMap(query.size());
        query.forEach((name, values) -> copy.put(name, List.copyOf(values)));
        return Collections.unmodifiableMap(copy);
    }

    private static GitHubGenericRequest of(final HttpMethod method, final String path) {
        return new GitHubGenericRequest(method, path, List.of(), Map.of(), HttpHeaders.empty(), null, null);
    }

    private static void requireAllowedHeader(final String name) {
        if (RESERVED_HEADERS.contains(name.toLowerCase(Locale.ROOT))) {
            throw new IllegalArgumentException("header is reserved and cannot be set on a generic request: " + name);
        }
    }
}
