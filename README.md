[![Build](https://github.com/piotr-janczukowicz/github-rest-client/actions/workflows/main.yml/badge.svg)](https://github.com/piotr-janczukowicz/github-rest-client/actions/workflows/main.yml)
[![Maven Central](https://img.shields.io/maven-central/v/io.github.piotr-janczukowicz/github-rest-client)](https://central.sonatype.com/artifact/io.github.piotr-janczukowicz/github-rest-client)
[![Release](https://img.shields.io/github/v/release/piotr-janczukowicz/github-rest-client)](https://github.com/piotr-janczukowicz/github-rest-client/releases)
[![License: MIT](https://img.shields.io/badge/license-MIT-blue.svg)](LICENSE)
![Java 21](https://img.shields.io/badge/Java-21-orange.svg)

**API version:** `2026-03-10` · **Last updated:** 2026-10-24

## Supported GitHub products

<!-- support-table:start -->
| Product | Client flavor | Base URI | Status |
|---|---|---|---|
| GitHub.com | `GitHubFlavor.CLOUD`, or unset | `https://api.github.com/` (default) | Supported: every operation and model comes from the description; one that only github.com has is marked `@Flavors(GitHubFlavor.CLOUD)` |
| GitHub Enterprise Cloud | `GitHubFlavor.GHEC`, recommended: it shares the base URI with github.com | `https://api.github.com/`, or `https://api.SUBDOMAIN.ghe.com/` with data residency | Supported: an enterprise-scoped operation is marked `@Flavors(GitHubFlavor.GHEC)` |
| GitHub Enterprise Server | `GitHubFlavor.GHES`, required, with the release | `https://HOST/api/v3/` | Supported: versions 3.21 to 3.22; a server-only operation is marked `@Flavors(GitHubFlavor.GHES)`, and one that only some versions carry is marked `@GhesVersions` |
<!-- support-table:end -->

## Overview

A Java client for the [GitHub REST API](https://docs.github.com/en/rest) for github.com, GitHub Enterprise Cloud and
GitHub Enterprise Server 3.21 and 3.22. Each GitHub resource has a typed `GitHub*Api` class, generated from GitHub's
[OpenAPI descriptions](https://github.com/github/rest-api-description/tree/main/descriptions-next), and a `GitHub`
aggregator gives access to all of them. The client waits out rate limits, follows pagination and keeps responses
immutable. Keep the defaults on github.com and Enterprise Cloud; tune an Enterprise Server client to the instance.

**Requirements:** Java 21. The library is a Java module, `io.github.piotr_janczukowicz.githubapi`, and also works on the class
path. It is published on [Maven Central](https://central.sonatype.com/artifact/io.github.piotr-janczukowicz/github-rest-client):

```xml
<dependency>
    <groupId>io.github.piotr-janczukowicz</groupId>
    <artifactId>github-rest-client</artifactId>
    <version>2026.3.10.0</version>
</dependency>
```

The library is in prerelease; see the [Disclaimer](#disclaimer) before relying on it.

In this README: [Usage](#usage) · [Flavors](#flavors) · [Enterprise Cloud and Server](#github-enterprise-cloud-and-server) ·
[Pagination](#pagination) · [Rate limiting](#rate-limiting) · [Authentication](#authentication) ·
[Immutability](#immutability) · [Exception handling](#exception-handling) · [Good to know](#good-to-know) ·
[Limitations](#limitations) · [Performance](#performance). The [user guide](docs/guide/README.md) has the detail: [rate limiting](docs/guide/rate-limiting.md),
[authentication](docs/guide/authentication.md), [performance](docs/guide/performance.md) and [live smoke tests](docs/guide/smoke-tests.md).

## Usage

### Create a client

Create one `GitHubClient` per credential. It owns the transport and the rate-limit state.

```java
GitHubClient client = GitHubClients.create(AuthenticationProviders.bearer(System.getenv("GITHUB_TOKEN")), "my-app/1.0");
```

### Create a caller and resources

Wrap the client in one `GitHubCaller` and share it. The caller holds no resources of its own; it adds fetching and
decoding with the library's JSON codec.

```java
GitHubCaller caller = GitHubCaller.of(client);

GitHub github = new GitHub(caller);                            // every resource, created on first use
Optional<GitHubRateLimitOverview> limits = github.rateLimit().get();

GitHubRateLimitApi rateLimit = new GitHubRateLimitApi(caller); // or a single resource, on the same caller
```

### Close the client

`client.close()` closes its transport. A second credential needs its own client and its own caller.

```java
client.close();
```

### Calling the API

A resource is reached through the `GitHub` aggregator, `github.<area>().<group>().<method>(...)`, for example
`github.issues().issue()`. The method names follow GitHub's operation names, and the Javadoc of each method names the
HTTP request it sends. Request bodies are `...Payload` records and query filters are `...Filter` records; both have a
`...Builder` (`GitHubCreatePayloadBuilder`), and a filter has `empty()`. A lookup of one resource returns an `Optional`
(a 404 is an empty result), and a list returns a `GitHubPage` (see [Pagination](#pagination)).

```java
GitHubIssueApi issues = github.issues().issue();

GitHubIssue created = issues.create("octocat", "Hello-World", GitHubCreatePayloadBuilder.builder()
        .title("Found a bug")
        .assignees(List.of("octocat"))
        .build());

Optional<GitHubIssue> found = issues.get("octocat", "Hello-World", created.number());   // empty on 404

GitHubListForRepoFilter filter = GitHubListForRepoFilterBuilder.builder()
        .state(GitHubListIssuesState.OPEN)
        .labels("bug")
        .build();
List<GitHubIssue> open = issues.listForRepo("octocat", "Hello-World", filter, GitHubPageRequest.byPage()).all();
```

### Configure only what you need

`GitHubClients.create(auth, userAgent)` uses the defaults, and the defaults follow GitHub's own advice for github.com. On
**github.com and GitHub Enterprise Cloud** keep them and set only what your case needs, such as the base URI of a
data-residency tenant. Raising the concurrency or shortening the pause between writes makes GitHub's secondary limits
likelier, so the client does not let you go below the floors in the table.

A **GitHub Enterprise Server** instance is yours: its administrator decides whether and how rate limits apply, and what
the hardware can take. Tune the client to it as you like, within the same safety rules, and set the three things it
always needs: the base URI, the `GHES` flavor and the release (see [GitHub Enterprise Server](#github-enterprise-server)).

| Option | Default | github.com and Enterprise Cloud | Enterprise Server |
|---|---|---|---|
| `maxConcurrentRequests` | 1 | Keep 1; more raises the risk of secondary limits | 1 to 100, as the instance can take |
| `mutationInterval` | 1 s | At least 1 s | Any value from `Duration.ZERO` (no pacing) up |
| `retry` (waits, attempts, backoff) | Waits out rate limits, retries transient failures | Tune the waits and attempts; rate-limit handling cannot be switched off | The same |
| Conditional cache limits | 64 entries, 1 MiB per body, 4 MiB in all | Rarely worth changing | Size to your workload; the cache cannot be switched off |
| Timeouts, proxy and TLS of the transport | OkHttp defaults of the library | Set what your network needs | The same |
| `allowInsecureHttp` | `false` | Not needed | `true` for a plain `http` base URI on a host other than loopback |
| Base URI | `https://api.github.com/` | Only for `https://api.SUBDOMAIN.ghe.com/` | Required: `https://HOST/api/v3/` |
| `flavor` and `ghesVersion` | Unset: every method is allowed | Set `GHEC` for Enterprise Cloud, which shares the base URI with github.com: a method it lacks then fails fast | Required: `GHES` with the release 3.21 or 3.22 |

```java
// github.com: defaults
GitHubClient cloud = GitHubClients.create(AuthenticationProviders.bearer(token), "my-app/1.0");

// Enterprise Server: tuned to the instance
GitHubClient server = GitHubClients.create(
        AuthenticationProviders.bearer(token),
        "my-app/1.0",
        GitHubClientConfiguration.builder()
                .configuration(HttpClientConfiguration.of(URI.create("https://HOST/api/v3/")))
                .flavor(GitHubFlavor.GHES)
                .ghesVersion(new GhesVersion(3, 22))
                .transport(HttpTransportConfiguration.builder().maxConcurrentRequests(8).build())
                .mutationInterval(Duration.ZERO)
                .build());
```

## Flavors

The library follows three OpenAPI descriptions of the REST API, one per *flavor*: github.com (`api.github.com`, called
*cloud* here), GitHub Enterprise Cloud (*GHEC*) and GitHub Enterprise Server (*GHES*, releases 3.21 and 3.22). Most
operations and schemas are in all three. Some exist in two or in one only: the enterprise-scoped ones (enterprise
administration, enterprise teams, SCIM, announcement banners and others) are in GHEC, most of them also in GHES; the
server administration (management console, statistics, pre-receive hooks and others) is in GHES only; a few
organization and user billing operations are in cloud only.

The flavor never changes the base URI: you set it yourself, and it is the same `https://api.github.com/` for cloud and
GHEC. GHEC with data residency has its own host (see [below](#github-enterprise-cloud-with-data-residency)), and a
GHES client needs `https://HOST/api/v3/` (see [below](#github-enterprise-server)).

- **Marked in the code:** a type or method that does not exist in every flavor carries `@Flavors` with the flavors that
  have it, such as `@Flavors(GitHubFlavor.GHES)` or `@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})`; one without the
  annotation exists in all three. A GHES item that only some releases carry also has `@GhesVersions(since = "3.22")`. The
  annotations are generated from the three official OpenAPI descriptions.
- **Shapes that differ:** when a schema differs between the flavors, one type covers all of them: a property that only
  some flavors have is `@Nullable`, and enum constants are the union.
- **Default:** a client does not check the flavor. Calling an operation that your product lacks fails with GitHub's own
  `404`; a getter that returns an `Optional` answers with an empty one instead of throwing.
- **Fail fast:** give the configuration a flavor to reject an operation the flavor does not carry before any request is
  sent. The method throws `UnsupportedOperationException` and names the flavors that have it. `GHES` is required for a
  server and also needs its release, which rejects a method that the release lacks in the same way.

```java
GitHubClientConfiguration clientConfiguration = GitHubClientConfiguration.builder()
        .flavor(GitHubFlavor.GHEC)
        .build();
GitHubClient client = GitHubClients.create(AuthenticationProviders.bearer(token), userAgent, clientConfiguration);

GitHub github = new GitHub(GitHubCaller.of(client));
github.enterpriseAdmin().enterpriseRoles().listEnterpriseRoles("my-enterprise"); // GHEC: allowed on a GHEC client
github.billing().budgets().listBudgets("my-org");                                // cloud only: UnsupportedOperationException
github.enterpriseAdmin().adminStats().getAllStats();                             // GHES only: UnsupportedOperationException
```

## GitHub Enterprise Cloud and Server

### GitHub Enterprise Cloud with data residency

A tenant on `*.ghe.com` has its own API host. Use `https://api.SUBDOMAIN.ghe.com/` as the base URI (no `/api/v3`
prefix) and, if you want operations the tenant does not carry to fail before a request is sent, set the flavor
separately. The uploads host is derived as `uploads.SUBDOMAIN.ghe.com`.

```java
GitHubClientConfiguration clientConfiguration = GitHubClientConfiguration.builder()
        .configuration(HttpClientConfiguration.of(URI.create("https://api.SUBDOMAIN.ghe.com/")))
        .flavor(GitHubFlavor.GHEC)
        .build();
GitHubClient client = GitHubClients.create(AuthenticationProviders.bearer(token), userAgent, clientConfiguration);
```

### GitHub Enterprise Server

Pass the API base URI of the server, which must end in `/api/v3/`, select the `GHES` flavor and name the server's
release. The release is always explicit: there is no default, `build()` rejects a release that the library does not
support (3.21 and 3.22) and lists the supported ones, and a server that is newer than the library needs a library
release first. A method that only some releases carry fails fast with `UnsupportedOperationException` on a client of
another release, and so does a method of another flavor. For installation auth, give the same `clientConfiguration` to
`GitHubInstallationAuthenticationProviders.create(...)`, so the token requests go to the same host. Uploads go to the
host's `/api/uploads/` path.

```java
GitHubClientConfiguration clientConfiguration = GitHubClientConfiguration.builder()
        .configuration(HttpClientConfiguration.of(URI.create("https://HOST/api/v3/")))
        .flavor(GitHubFlavor.GHES)
        .ghesVersion(new GhesVersion(3, 22))
        .build();
GitHubClient client = GitHubClients.create(AuthenticationProviders.bearer(token), userAgent, clientConfiguration);
```

The management console (`github.enterpriseAdmin().manageGhes()`) is on the same host under `/manage/v1`, outside the API
base path, and takes the console password instead of the token: every method takes a `GitHubManagementCredentials` for
that one call, which is sent as HTTP Basic authentication and is never stored or printed. The client sends these calls
through a console view of itself (`client.management()`): the API token is never sent to the console, and an ordinary
call to `/manage/v1` is refused. The base URI of a client that uses it must be `https://HOST/api/v3/`.

```java
List<GitHubManageVersion> versions =
        github.enterpriseAdmin().manageGhes().getVersion(new GitHubManagementCredentials(consolePassword));
```

## Pagination

GitHub returns list endpoints in pages. Each response holds up to `per_page` items (30 by default, 100 at most), and
the `Link` header with `rel="next"` points to the next page. GitHub asks clients to follow that link rather than build
URLs themselves, and to stop when there is no `rel="next"`. Search endpoints return at most 1,000 results per query.

The library follows these rules. Each list method returns a `GitHubPage<T>` for one HTTP page. `next()` fetches the
following page only when the `Link` header has one, and the other methods walk the remaining pages. Page size defaults
to 100. A walk has no page cap unless `maxPages` is set on the client's
`PaginationConfiguration`; a walk that reaches the cap throws `GitHubPaginationLimitExceededException`. Search walks stop
at 1,000 items.

```java
GitHubRepositoriesForksApi forks = new GitHubRepositoriesForksApi(caller);

// One HTTP page
GitHubPage<GitHubMinimalRepository> page = forks.listForks("octocat", "Hello-World");
List<GitHubMinimalRepository> firstPage = page.items();
Optional<GitHubPage<GitHubMinimalRepository>> second = page.next(); // empty when there is no next link

// Every item, in memory
List<GitHubMinimalRepository> all = forks.listForks("octocat", "Hello-World").all();

// Lazy: stops fetching pages once the limit is reached
List<GitHubMinimalRepository> fifty = forks.listForks("octocat", "Hello-World").stream().limit(50).toList();

// Page by page, keeping pages that were read before a later failure
forks.listForks("octocat", "Hello-World").forEachPage(p -> process(p.items()));
```

Use one terminal call per walk: `all()`, `stream()`, `pages()` and `forEachPage(...)` advance the walk, and `next()` on a
page that has already been passed throws `IllegalStateException`. A `GitHubPage` is not thread-safe.

GitHub silently caps `per_page` at 100 and returns fewer items, without an error. This library does not cap it: an
explicit `perPage` above the endpoint maximum throws `IllegalArgumentException` when the request is sent. Without an
explicit value, the page size is the smaller of `PaginationConfiguration.perPage()` and the endpoint maximum.

## Rate limiting

The client follows GitHub's [rate-limit rules](https://docs.github.com/en/rest/using-the-rest-api/rate-limits-for-the-rest-api)
for you, and you cannot switch this off:

- **Primary limit:** when the quota is used up, the client waits for the reset and retries.
- **Secondary limit:** it waits for `retry-after`, or 60 seconds doubling up to 5 minutes, for at most 4 attempts.
- **Long waits fail fast:** a wait longer than `maxRateLimitWait` (5 minutes) throws `GitHubRateLimitException` at once
  with `retryAt()`, and later calls with the same credential fail locally until the block ends (an hour at most).
- **Per credential and bucket:** limits, waits and cached responses belong to one credential and one bucket (`core`,
  `search` and others), and are never shared between credentials.
- **Conditional requests:** repeated `GET`s are sent with `If-None-Match` or `If-Modified-Since`, and a 304 returns the
  kept response (64 entries, 4 MiB by default; the cache cannot be switched off).
- **Gentle by default:** one request at a time and writes at least one second apart; see
  [Configure only what you need](#configure-only-what-you-need).

```java
GitHubClient client = GitHubClients.create(
        AuthenticationProviders.bearer(System.getenv("GITHUB_TOKEN")),
        "my-app/1.0",
        GitHubClientConfiguration.builder()
                .retry(RetryConfiguration.builder().maxRateLimitWait(Duration.ofMinutes(10)).build())
                .build());

try {
    return forks.listForks("octocat", "Hello-World").all();
} catch (GitHubRateLimitException e) {
    e.retryAt().ifPresent(at -> log.warn("GitHub rate limit, retry after {}", at));
    throw e;
}
```

`client.lastRateLimit("core")` and `client.lastRateLimits()` return the latest quota seen, for monitoring and budgeting
only. Primary and secondary limits, buckets, credentials, unauthenticated access, polling and the details of the
conditional cache are in [Rate limiting in depth](docs/guide/rate-limiting.md).

## Authentication

Every client sends one `Authorization` header, chosen by its `AuthenticationProvider`. For another credential, create
another client. GitHub's rules are in its
[authentication documentation](https://docs.github.com/en/rest/authentication/authenticating-to-the-rest-api).

| You have | Use |
|---|---|
| A personal access token (prefer fine-grained) | `AuthenticationProviders.bearer(token)` |
| A token that changes, such as an OAuth user token | `AuthenticationProviders.bearer(() -> currentToken())`, optionally with a refresh function |
| A GitHub App installation | `GitHubInstallationAuthenticationProviders.create(...)`: signs the JWT, fetches and renews the token |
| The app itself, for app-level endpoints | `GitHubAppAuthenticationProviders.app(appId, privateKeyPem)` |
| An OAuth app, for endpoints that take its client credentials | `AuthenticationProviders.basic(clientId, clientSecret)` |
| Nothing, for public data (60 requests per hour per IP address) | `AuthenticationProviders.none()` |

```java
GitHubClient client = GitHubClients.create(AuthenticationProviders.bearer(System.getenv("GITHUB_TOKEN")), "my-app/1.0");

GitHubInstallationAuthenticationProvider auth = GitHubInstallationAuthenticationProviders.create(
        userAgent, clientConfiguration, GitHubInstallationAuthConfiguration.builder()
        .appId("Iv1.0123456789abcdef")          // client ID (recommended) or numeric app ID
        .privateKeyPem(pem)                      // PKCS#1 or PKCS#8, unencrypted
        .installationId(12345678L)
        .build());
GitHubClient installationClient = GitHubClients.create(auth, userAgent, clientConfiguration);
```

You create the installation auth, so you close it as well: `installationClient.close()` does not close it.

- `GitHubUnauthorizedException` (401): the credentials are invalid, expired or revoked.
- `GitHubForbiddenException` (403): the token lacks the permission the request needs; `ssoUrl()` is set when the token
  must be authorized for an organization that uses SAML SSO.
- `GitHubNotFoundException` (404): for private resources GitHub may answer 404 instead of 403.
- The auth types never print their secrets, and a plain `http` base URI is refused on a host other than loopback unless
  you allow it with `allowInsecureHttp(true)`.

Token renewal, back-off, SAML partial results, redirects and the security notes are in
[Authentication in depth](docs/guide/authentication.md).

## Immutability

What you get back cannot be changed by accident, and what you hand in cannot be changed behind the library's back.

### Output

- **Models are records.** Every response model is an immutable record.
- **Collections and maps are unmodifiable.** Every list, set and map in a response throws `UnsupportedOperationException`
  on `add`, `put` or `remove`. Copy it if you need to change it.
- **What GitHub sends is what you get.** A field that GitHub leaves out is `null`, not an empty collection, and a `null`
  element that GitHub sends is kept. A property that can be absent is marked `@Nullable`.
- **Pages, headers, errors and configuration** are immutable too, so you can share them between threads.

```java
GitHubPage<GitHubMinimalRepository> page = forks.listForks("octocat", "Hello-World");
List<GitHubMinimalRepository> items = page.items();
items.add(other); // UnsupportedOperationException
```

### Input

A payload, filter or other object that you build and pass to a method copies every list, set and map you give it. Changing
your own collection afterwards does not change the request. A `null` collection that the type allows stays `null`; a `null`
element or value is rejected with a `NullPointerException`.

```java
List<String> labels = new ArrayList<>(List.of("gpu"));
GitHubAddCustomLabelsToSelfHostedRunnerForRepoPayload payload =
        new GitHubAddCustomLabelsToSelfHostedRunnerForRepoPayload(labels);
labels.add("arm"); // not in payload.labels()
```

## Exception handling

### What reaches your code

| Exception | When | Type |
|---|---|---|
| `GitHubUnauthorizedException` | 401: the token is missing, expired, revoked or malformed | unchecked |
| `GitHubForbiddenException` | 403 that is not a rate limit: missing permission, blocked access, policy, SAML SSO (`ssoUrl()`) | unchecked |
| `GitHubNotFoundException` | 404: the resource is missing, or private and not visible to this credential | unchecked |
| `GitHubValidationException` | 422: the payload was rejected; `errors()` lists the fields | unchecked |
| `GitHubRateLimitException` | a rate limit the client did not wait out (see [Long waits fail fast](docs/guide/rate-limiting.md#long-waits-fail-fast)) | unchecked |
| `GitHubApiException` | any other unsuccessful status: 409, 410, 5xx after the retries, or 304 you did not ask for | unchecked |
| `IOException` | the transport failed: connection, timeout, TLS, a body above the size limit (`ResponseTooLargeException`) | checked |
| `UncheckedIOException` | the same transport failures while you read `stream()` or `pages()`, which cannot throw a checked exception; the cause is the `IOException` (`all()` and `forEachPage(...)` throw it directly) | unchecked |
| `JsonDecodeException` | the response body could not be read as the expected model | unchecked |
| `JsonEncodeException` | a payload you passed could not be written as JSON | unchecked |
| `HttpEmptyResponseException` | a successful response without a body where a value was expected (202, 204) | unchecked |
| `GitHubPaginationException` | the next page cannot be followed, a page repeats, or `maxPages` is reached (`GitHubPaginationLimitExceededException`) | unchecked |
| `GitHubInstallationTokenUnavailableException` | an installation token fetch failed earlier and the provider is in its backoff; `retryAt()` | unchecked |
| `UnsupportedOperationException` | the operation is not in the configured [flavor](#flavors) | unchecked |
| `HttpOriginViolationException` | a request target leaves the API origin, such as an absolute URL on another host; it is an `IllegalArgumentException` | unchecked |
| `IllegalArgumentException`, `NullPointerException` | an argument is blank, null or outside its limits | unchecked |

Every `GitHubApiException` carries `statusCode()`, `documentationUrl()`, `requestId()` (quote it to GitHub support),
`rawBody()`, `headers()`, `method()` and `uri()`.

### What the client handles for you

- **Rate limits** are waited out when the wait is at most `maxRateLimitWait`, with the attempts and the coordination across
  calls described in [Rate limiting](#rate-limiting). You see `GitHubRateLimitException` only for a longer wait or after the
  attempts are used up.
- **Transient failures** are retried: `502`, `503` and `504`, and I/O failures. By default only for `GET`, `HEAD` and
  `OPTIONS`; a failed connection attempt is retried for every method. A `POST`, `PATCH`, `PUT` or `DELETE` that fails
  after the request was sent is not repeated, because the change may have been applied.
- **A 304** is handled by the conditional cache; you do not see it.
- **Token refresh** is done by the authentication you chose; see [Rotating tokens](docs/guide/authentication.md#rotating-tokens).

### What you should handle

- **Not found.** `GitHubNotFoundException` does not prove the resource is missing: GitHub answers 404 for a private resource
  too. For a lookup of one resource by a key, use the `Optional`-returning method, which turns a 404 into an empty result;
  other 404s still throw.
- **Retry yourself only what is safe.** After an `IOException` on a mutation, read the state before repeating the call.
  After `GitHubRateLimitException`, wait until `retryAt()`. After `GitHubValidationException`, fix the payload; repeating
  it fails the same way.
- **Stop on credentials.** On `GitHubUnauthorizedException`, re-authenticate; do not loop with the same token. On a
  `GitHubForbiddenException` with `ssoUrl()`, the user must authorize the token for the organization.
- **Do not catch programming errors.** `IllegalArgumentException`, `NullPointerException` and
  `UnsupportedOperationException` mean the call is wrong, not that GitHub failed.
- **Logging.** Exception messages contain no query string and no token, and `Authorization` is printed as `***`. The cause
  of a `JsonDecodeException` may quote a value from the response body, so do not log it where the body is secret.

```java
try {
    return repositories.get("octocat", "Hello-World"); // Optional: a 404 is an empty result
} catch (GitHubRateLimitException e) {
    schedule(e.retryAt().orElse(Instant.now().plusSeconds(60)));
    throw e;
} catch (GitHubValidationException e) {
    e.errors().forEach(error -> log.warn("{} {}: {}", error.field(), error.code(), error.message()));
    throw e;
} catch (GitHubApiException e) {
    log.error("GitHub answered {} (request {})", e.statusCode(), e.requestId().orElse("unknown"), e);
    throw e;
} catch (IOException e) {
    throw new UncheckedIOException("GitHub is unreachable", e);
}
```

Catch the specific types first: `GitHubApiException` is their parent.

## Good to know

- **Newer GitHub data:** a JSON property the library does not know is ignored. An enum that only appears in responses
  has an `UNKNOWN` constant for a value the library does not know. An enum that is also sent in requests has none,
  because a request must not be able to send it, and reads such a value as `null`.
- **Files:** `github.releases().assets()` uploads a release asset from a `Path` or a stream and downloads one to a
  `Path` or an `OutputStream`.
- **Deprecated operations:** an operation GitHub has deprecated stays as a `@Deprecated` method with GitHub's dates in
  its Javadoc; one GitHub has closed throws `UnsupportedOperationException` without sending a request.
- **Threads:** clients, callers and resources are thread-safe and meant to be shared. A `GitHubPage` is not.
- **Logging:** the library logs through SLF4J, mostly at DEBUG. `Authorization` is printed as `***`, and exception
  messages contain no query string and no token.

## Limitations

- **Authentication.** Authentication covers bearer tokens (including a refresh supplier), GitHub App JWTs, and
  installation tokens that the client fetches and renews, see [Authentication](#authentication). The client does not run
  interactive OAuth flows (web or device) or the GitHub App manifest flow; obtain those credentials yourself and pass
  them to the matching `AuthenticationProviders` / App helpers.
- **Memory.** A response that is not read with `stream(...)` is read into memory in full, up to `maxResponseBytes`
  (64 MiB by default); a larger body fails with `ResponseTooLargeException`. Prefer `stream(...)` or the download
  helpers that write to a `Path` / `OutputStream` (and release-asset upload from a `Path` / stream) for large bodies.
  Details and measurements: [Performance › Memory](docs/guide/performance.md#memory).

## Performance

A call adds about 0.1 ms of client CPU and 90 KB of allocation on top of the network round trip, measured against a local
stub server. Page walks are lazy, so `stream()` and `forEachPage(...)` keep memory flat however many pages there are.
A client sends one request at a time by default; raising `maxConcurrentRequests` raises throughput (see
[Configure only what you need](#configure-only-what-you-need)). Close clients you no longer need. The measurements and
their methodology are in [Performance in depth](docs/guide/performance.md).

## Disclaimer

This library is built on the OpenAPI JSON schemas that GitHub publishes in
[github/rest-api-description](https://github.com/github/rest-api-description/tree/main/descriptions-next). The
caveats of that project apply here too:

- The `descriptions-next` folder holds the 3.1 version of the description and is subject to breaking changes on the
  `main` branch.
- Not all headers are described in the OpenAPI documents. Expect those to be added over time.
- Certain GitHub API resources use multi-segment path parameters, which the OpenAPI specification does not support.
- A lot of operations are accessible through multiple paths.
- Certain tools have poor support for references to components within the artifact.

The schema repository is licensed under the MIT license.

**Only smoke tests have been run on the live GitHub environment, covering particular feature sets. Not every endpoint has
been tested. This library is a prerelease, intended to surface errors in the schemas and the code. Verify the behavior of
any endpoint you depend on before using it in production.**
