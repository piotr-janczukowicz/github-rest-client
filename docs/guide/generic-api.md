# Generic API

This page is part of the [user guide](README.md). The [README](../../README.md) has the short version.

`GitHubGenericApi` sends any REST call through the same client as the typed APIs. It is an escape hatch: if a typed
method has a bug, or GitHub has an endpoint the library does not model yet, you can keep working without waiting for a
release. **Use the typed APIs wherever they exist.** A generic call has no models, no validation and no check of the
GitHub flavor or Enterprise Server version; its correctness and compatibility are yours.

```java
GitHubGenericApi generic = github.generic();

GitHubGenericRequest request = GitHubGenericRequest.get("/repos/{owner}/{repo}/issues")
        .withPathVariables(List.of("octocat", "Hello-World"))
        .withQuery(Map.of("state", List.of("open")));

List<GitHubIssue> open = generic.fetch(request, JsonTypes.listOf(GitHubIssue.class));
```

`new GitHubGenericApi(caller)` works too, with the same `GitHubCaller` as the other resources.

## What you get

A generic call goes through the same stack as every typed call: the conditional cache, retry and rate-limit waiting,
mutation pacing, the concurrency limit, error mapping and credential renewal. HTTP errors become
`GitHubApiException` subclasses and transport failures are `IOException`, as described in
[Exception handling](https://github.com/piotr-janczukowicz/github-client/blob/main/docs/guide/exception-handling.md).

## Describing a call

`GitHubGenericRequest` is an immutable record. Start from `get`, `post`, `put`, `patch` or `delete` with a path
template and add what you need with `withPathVariables`, `withQuery`, `withHeaders`, `withJsonBody` or `withBody`.

- **Path.** Relative to the API base of the client, starting with `/`, with `{placeholders}` filled in order by the
  path variables. Each value is percent-encoded as one segment, so a value such as `a/b` or `..` cannot change the
  path. A path with a query, a fragment, a backslash, an empty or dot segment or an encoded separator is refused with
  `IllegalArgumentException`, and so is anything that is not a path, such as a full URL. The call cannot leave the
  API base or reach another host.
- **Body.** `withJsonBody(object)` encodes the object as JSON with the library's codec. `withBody(body)` sends a raw
  body; set its `Content-Type` yourself with `withHeaders`. You cannot set both, and you cannot give a `Content-Type`
  header together with a JSON body, because the JSON body sets it.
- **Headers.** Extra headers such as `Accept` or `If-None-Match` are sent as given. The API version stays
  `X-GitHub-Api-Version: 2026-03-10` and the credentials stay those of the client, so `X-GitHub-Api-Version`,
  `Authorization` and `Host` are refused, in any letter case.

## Operations

| Method | Returns |
|---|---|
| `fetch(request, type)` | A successful response body decoded as `type` (a `JsonType`, for example `JsonTypes.listOf(...)`) |
| `send(request)` | The raw `HttpResponse`: status, headers and body, for calls without a body or with a body that is not JSON |
| `fetchText(request, mediaType)` | The body as text with `Accept: mediaType`, such as a diff; empty on a 404 |
| `list(request, page, type)` | The first `GitHubPage` of a list endpoint; the walk follows `Link` headers as in [Pagination](pagination.md) |
| `search(request, page, type, envelope)` | The first page of a search endpoint, with a function that maps the decoded body to a `GitHubSearchEnvelope` |

`list` and `search` accept only a GET without a body. `fetch` on a successful response without a body, such as an HTTP
204, throws `HttpEmptyResponseException`; use `send` for those calls.

## Limits

- The type you pass to `fetch` or `list` must match what GitHub sends. The library's own models are fine to reuse.
  A response value it does not know decodes to `UNRECOGNIZED` only in the library's own enums.
- `@Flavors` markers and the fail-fast check do not apply. A call that github.com accepts may fail on Enterprise Server.
- The management console of Enterprise Server is not reachable through `github.generic()`. Pass the caller of
  `GitHubCaller.management()` only if you mean to; those calls are not authenticated by the client.
