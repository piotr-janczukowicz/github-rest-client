# github-rest-client

Java 21 client for the [GitHub REST API](https://docs.github.com/en/rest): typed models and operations for github.com, GitHub Enterprise Cloud and GitHub Enterprise Server, with pagination and rate-limit handling built in.

[![Codacy Badge](https://app.codacy.com/project/badge/Grade/b1f7def0919f47b88374ae4bd4fdc531)](https://app.codacy.com/gh/piotr-janczukowicz/github-rest-client/dashboard?utm_source=gh&utm_medium=referral&utm_content=&utm_campaign=Badge_grade)
[![Codacy Coverage](https://app.codacy.com/project/badge/Coverage/b1f7def0919f47b88374ae4bd4fdc531)](https://app.codacy.com/gh/piotr-janczukowicz/github-rest-client/dashboard?utm_source=gh&utm_medium=referral&utm_content=&utm_campaign=Badge_coverage)
[![Build](https://github.com/piotr-janczukowicz/github-rest-client/actions/workflows/main.yml/badge.svg)](https://github.com/piotr-janczukowicz/github-rest-client/actions/workflows/main.yml)
[![Maven Central](https://img.shields.io/maven-central/v/io.github.piotr-janczukowicz/github-rest-client)](https://central.sonatype.com/artifact/io.github.piotr-janczukowicz/github-rest-client)
[![License: MIT](https://img.shields.io/badge/license-MIT-blue.svg)](LICENSE)
![Java 21](https://img.shields.io/badge/Java-21-orange.svg)

**API version:** `2026-03-10` · **Last updated:** 2026-10-31

## Why this library

- **Generated from GitHub's own descriptions.** Every resource has a typed `GitHub*Api` class, built from the official [OpenAPI descriptions](https://github.com/github/rest-api-description/tree/main/descriptions-next), and a `GitHub` aggregator gives access to all of them.
- **One client for three products.** github.com, GitHub Enterprise Cloud and GitHub Enterprise Server 3.21 and 3.22. A call a product lacks is marked in the code and can be rejected before any request is sent.
- **Safe when GitHub adds values.** A response value the library does not know decodes to `UNRECOGNIZED` instead of throwing, so a new enum value on GitHub's side does not break your code.
- **The boring parts are handled.** Rate limits are waited out, pagination is followed and responses are immutable.
- **Small on purpose.** Synchronous REST only: no GraphQL and no async API.

## Install

**Requirements:** Java 21. The library is a Java module, `io.github.piotr_janczukowicz.githubrestclient`, and also works on the class path. It is published on [Maven Central](https://central.sonatype.com/artifact/io.github.piotr-janczukowicz/github-rest-client):

```xml
<dependency>
    <groupId>io.github.piotr-janczukowicz</groupId>
    <artifactId>github-rest-client</artifactId>
    <version>LATEST_VERSION</version>
</dependency>
```

Replace `LATEST_VERSION` with the version in the Maven Central badge at the top of this page, or with the newest entry on the [releases page](https://github.com/piotr-janczukowicz/github-rest-client/releases).

The library is in prerelease; see the [Disclaimer](#disclaimer) before relying on it.

## Quick start

```java
try (GitHubClient client = GitHubClients.create(AuthenticationProviders.bearer(System.getenv("GITHUB_TOKEN")), "my-app/1.0")) {
    GitHub github = new GitHub(GitHubCaller.of(client));

    GitHubIssueApi issues = github.issues().issue();
    for (GitHubIssue issue : issues.listForRepo("octocat", "Hello-World").all()) {
        System.out.println(issue.number() + " " + issue.title());
    }
}
```

A lookup of one resource returns an `Optional` (a 404 is an empty result), and a list returns a `GitHubPage`. See [Usage](#usage) for creating, filtering and configuring.

## Supported GitHub products

<!-- support-table:start -->
| Product | Client flavor | Base URI | Status |
|---|---|---|---|
| GitHub.com | `GitHubFlavor.CLOUD`, or unset | `https://api.github.com/` (default) | Supported: every operation and model comes from the description; one that only github.com has is marked `@Flavors(GitHubFlavor.CLOUD)` |
| GitHub Enterprise Cloud | `GitHubFlavor.GHEC`, recommended: it shares the base URI with github.com | `https://api.github.com/`, or `https://api.SUBDOMAIN.ghe.com/` with data residency | Supported: an enterprise-scoped operation is marked `@Flavors(GitHubFlavor.GHEC)` |
| GitHub Enterprise Server | `GitHubFlavor.GHES`, required, with the release | `https://HOST/api/v3/` | Supported: versions 3.21 to 3.22; a server-only operation is marked `@Flavors(GitHubFlavor.GHES)`, and one that only some versions carry is marked `@GhesVersions` |
<!-- support-table:end -->

Keep the defaults on github.com and Enterprise Cloud; tune an Enterprise Server client to the instance.

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
github.com and GitHub Enterprise Cloud keep them. A GitHub Enterprise Server instance is yours to tune, within the same
safety rules, and always needs the base URI, the `GHES` flavor and the release. The options, their defaults and limits,
and the examples are in [Configuration](docs/guide/configuration.md).

## Flavors

The library follows three OpenAPI descriptions, one per *flavor*: github.com (*cloud*), GitHub Enterprise Cloud (*GHEC*)
and GitHub Enterprise Server (*GHES*, releases 3.21 and 3.22). Most operations are in all three. A type or method that
is not carries `@Flavors` with the flavors that have it, and a client that is given a flavor rejects a call its product
lacks with `UnsupportedOperationException` before any request is sent. Without a flavor, such a call fails with GitHub's
own `404`. The flavor never changes the base URI. The details are in
[Flavors and GitHub Enterprise](docs/guide/flavors.md).

## GitHub Enterprise Cloud and Server

### GitHub Enterprise Cloud with data residency

A tenant on `*.ghe.com` has its own API host: use `https://api.SUBDOMAIN.ghe.com/` as the base URI and set the `GHEC`
flavor. See [the guide](docs/guide/flavors.md#github-enterprise-cloud-with-data-residency).

### GitHub Enterprise Server

Pass the API base URI of the server (it ends in `/api/v3/`), select the `GHES` flavor and name the server's release. The
release is always explicit. See [the guide](docs/guide/flavors.md#github-enterprise-server) for versions, installation
auth, uploads and the management console.

```java
GitHubClientConfiguration clientConfiguration = GitHubClientConfiguration.builder()
        .configuration(HttpClientConfiguration.of(URI.create("https://HOST/api/v3/")))
        .flavor(GitHubFlavor.GHES)
        .ghesVersion(new GhesVersion(3, 22))
        .build();
GitHubClient client = GitHubClients.create(AuthenticationProviders.bearer(token), userAgent, clientConfiguration);
```

## Pagination

Each list method returns a `GitHubPage<T>` for one HTTP page. `next()` fetches the following page only when GitHub sent a
`rel="next"` link, and `all()`, `stream()`, `pages()` and `forEachPage(...)` walk the rest. The page size defaults to 100
and a walk has no page cap unless you set `maxPages`. The rules, the examples and the limits are in
[Pagination](docs/guide/pagination.md).

```java
List<GitHubMinimalRepository> all = forks.listForks("octocat", "Hello-World").all();
List<GitHubMinimalRepository> fifty = forks.listForks("octocat", "Hello-World").stream().limit(50).toList();
```

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

Models are immutable records, and every list, set and map in a response is unmodifiable. A field that GitHub leaves out
is `null`, not an empty collection. Collections you pass in a payload or filter are copied. See
[Immutability](docs/guide/immutability.md).

## Exception handling

HTTP errors are unchecked and share the parent `GitHubApiException`: `GitHubUnauthorizedException` (401),
`GitHubForbiddenException` (403), `GitHubNotFoundException` (404), `GitHubValidationException` (422) and
`GitHubRateLimitException`. Transport failures are `IOException`. The client waits out rate limits and retries transient
failures itself; a 404 on a lookup of one resource is an empty `Optional`. Catch the specific types first, and see
[Exception handling](docs/guide/exception-handling.md) for the full table and what to handle yourself.

## Good to know

- **Newer GitHub data:** a JSON property the library does not know is ignored. An enum that only appears in responses
  has an `UNRECOGNIZED` constant for a value the library does not know (`UNKNOWN` is only a value GitHub itself lists). An enum that is also sent in requests has none,
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
