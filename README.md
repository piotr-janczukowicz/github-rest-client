# github-rest-client

Java 21 client for the [GitHub REST API](https://docs.github.com/en/rest): typed models and operations for github.com,
GitHub Enterprise Cloud and GitHub Enterprise Server, with pagination and rate-limit handling built in.

[![Codacy Coverage](https://app.codacy.com/project/badge/Coverage/b1f7def0919f47b88374ae4bd4fdc531)](https://app.codacy.com/gh/piotr-janczukowicz/github-rest-client/dashboard?utm_source=gh&utm_medium=referral&utm_content=&utm_campaign=Badge_coverage)
[![Build](https://github.com/piotr-janczukowicz/github-rest-client/actions/workflows/main.yml/badge.svg)](https://github.com/piotr-janczukowicz/github-rest-client/actions/workflows/main.yml)
[![Maven Central](https://img.shields.io/maven-central/v/io.github.piotr-janczukowicz/github-rest-client)](https://central.sonatype.com/artifact/io.github.piotr-janczukowicz/github-rest-client)
[![License: MIT](https://img.shields.io/badge/license-MIT-blue.svg)](LICENSE)

**API version:** `2026-03-10` · **Last updated:** 2026-10-31

## Why this library

- **Generated from GitHub's own descriptions.** Every resource has a typed `GitHub*Api` class, built from the official
  [OpenAPI descriptions](https://github.com/github/rest-api-description/tree/main/descriptions-next).
- **One client for three products.** github.com, GitHub Enterprise Cloud and GitHub Enterprise Server 3.21 and 3.22, with
  an optional check that rejects a call a product lacks before it is sent.
- **Safe when GitHub adds values.** A response value the library does not know decodes to `UNRECOGNIZED` instead of
  throwing, so a new enum value on GitHub's side does not break your code.
- **The boring parts are handled.** Rate limits are waited out, pagination is followed and responses are immutable.
- **Small on purpose.** Synchronous REST only: no GraphQL and no async API.

## Install

**Requirements:** Java 21. The library is a Java module, `io.github.piotr_janczukowicz.githubrestclient`, and also works
on the class path. It is published on
[Maven Central](https://central.sonatype.com/artifact/io.github.piotr-janczukowicz/github-rest-client):

```xml
<dependency>
    <groupId>io.github.piotr-janczukowicz</groupId>
    <artifactId>github-rest-client</artifactId>
    <version>LATEST_VERSION</version>
</dependency>
```

Replace `LATEST_VERSION` with the version in the Maven Central badge at the top of this page, or with the newest entry on
the [releases page](https://github.com/piotr-janczukowicz/github-rest-client/releases). The library is in prerelease; see
the [Disclaimer](#disclaimer) before relying on it.

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

## Usage

A resource is reached through the `GitHub` aggregator, `github.<area>().<group>().<method>(...)`, for example
`github.issues().issue()`. The method names follow GitHub's operation names, and the Javadoc of each method names the
HTTP request it sends. Request bodies are `...Payload` records and query filters are `...Filter` records; both have a
`...Builder` (`GitHubCreatePayloadBuilder`), and a filter has `empty()`. A lookup of one resource returns an `Optional`
(a 404 is an empty result), and a list returns a `GitHubPage`.

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

**Client lifecycle.** Create one `GitHubClient` per credential: it owns the transport and the rate-limit state. Wrap it in
one `GitHubCaller` and share it; `new GitHub(caller)` gives every resource, created on first use, and
`new GitHubRateLimitApi(caller)` gives a single one. `client.close()` closes the transport, and a second credential needs
its own client and caller. Clients, callers and resources are thread-safe; a `GitHubPage` is not.

## Flavors

The library follows three OpenAPI descriptions, one per *flavor*: github.com (*cloud*), GitHub Enterprise Cloud (*GHEC*)
and GitHub Enterprise Server (*GHES*). Most operations are in all three. A type or method that is not carries `@Flavors`
with the flavors that have it. Give the client a flavor to reject a call its product lacks with
`UnsupportedOperationException` before any request is sent; without one, such a call fails with GitHub's own `404`.

| Product | Client flavor | Base URI |
|---|---|---|
| GitHub.com | `GitHubFlavor.CLOUD`, or unset | `https://api.github.com/` (default) |
| GitHub Enterprise Cloud | `GitHubFlavor.GHEC`, recommended: it shares the base URI with github.com | `https://api.github.com/`, or `https://api.SUBDOMAIN.ghe.com/` with data residency |
| GitHub Enterprise Server | `GitHubFlavor.GHES`, required, with the release (3.21 or 3.22) | `https://HOST/api/v3/` |

```java
GitHubClientConfiguration clientConfiguration = GitHubClientConfiguration.builder()
        .configuration(HttpClientConfiguration.of(URI.create("https://HOST/api/v3/")))
        .flavor(GitHubFlavor.GHES)
        .ghesVersion(new GhesVersion(3, 22))
        .build();
GitHubClient client = GitHubClients.create(AuthenticationProviders.bearer(token), userAgent, clientConfiguration);
```

Data residency, Enterprise Server releases, uploads and the management console are in
[Flavors and GitHub Enterprise](docs/guide/flavors.md).

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

You create an installation auth, so you close it as well: `installationClient.close()` does not close it.

## User guide

The [user guide](docs/guide/README.md) has a chapter for each topic below.

| Topic | In short |
|---|---|
| [Configuration](docs/guide/configuration.md) | The defaults follow GitHub's advice; every option, its default and its limits on github.com, Enterprise Cloud and Enterprise Server |
| [Flavors and GitHub Enterprise](docs/guide/flavors.md) | The `@Flavors` markers, fail-fast, data residency, Enterprise Server releases and the management console |
| [Pagination](docs/guide/pagination.md) | A `GitHubPage` per HTTP page; `all()`, `stream()` and `forEachPage(...)` walk the rest, with a configurable page cap |
| [Rate limiting](docs/guide/rate-limiting.md) | Waited out for you; a wait over `maxRateLimitWait` (5 minutes) throws `GitHubRateLimitException` with `retryAt()` |
| [Authentication in depth](docs/guide/authentication.md) | Installations, token renewal, authenticating as the app, SAML single sign-on, security notes |
| [Immutability](docs/guide/immutability.md) | Immutable records, unmodifiable response collections, absent fields are `null`, your inputs are copied |
| [Exception handling](docs/guide/exception-handling.md) | HTTP errors are unchecked `GitHubApiException` subclasses (401, 403, 404, 422, rate limit); transport failures are `IOException` |
| [Performance](docs/guide/performance.md) | About 0.1 ms and 90 KB of allocation per call on top of the network round trip, with the measurements |
| [Good to know](docs/guide/good-to-know.md) | Newer GitHub data, file uploads, deprecated operations, logging, and the caveats of the schema descriptions |
| [Live smoke tests](docs/guide/smoke-tests.md) | Running the opt-in tests against api.github.com |

## Limitations

- **Authentication.** Bearer tokens (including a refresh supplier), GitHub App JWTs and installation tokens that the client
  fetches and renews are covered. The client does not run interactive OAuth flows (web or device) or the GitHub App
  manifest flow; obtain those credentials yourself and pass them to the matching `AuthenticationProviders` or App helpers.
- **Memory.** A response that is not read with `stream(...)` is read into memory in full, up to `maxResponseBytes`
  (64 MiB by default); a larger body fails with `ResponseTooLargeException`. Prefer `stream(...)` or the download helpers
  for large bodies. See [Performance › Memory](docs/guide/performance.md#memory).

## Comparison with hub4j/github-api

[hub4j/github-api](https://github.com/hub4j/github-api) is the long-established Java library for GitHub and a fine
choice, especially if you like its object-oriented style. The two differ in design, so pick by what you need. The
hub4j column is taken from its [documentation](https://hub4j.github.io/github-api/) as of November 2026.

| | github-rest-client | hub4j/github-api |
|---|---|---|
| Style | A typed class per resource, reached as `github.area().group().method(...)`; requests and responses are records | An object-oriented model: classes such as `GHRepository` and `GHUser` with the operations as methods on them |
| Coverage | Generated from GitHub's OpenAPI descriptions, so an operation in the description has a method | Most of the GitHub APIs; its documentation says some corners are not implemented yet |
| GitHub products | github.com, GitHub Enterprise Cloud and Enterprise Server 3.21 and 3.22, with a per-flavor check that can fail a call before it is sent | github.com and GitHub Enterprise |
| Java | 21 | 11 |
| Maturity | Prerelease, see the [Disclaimer](#disclaimer) | Long-established and widely used |

Choose hub4j/github-api if you need Java 11, an object-oriented API, or a library with a long track record. Choose this
library if you want every operation in GitHub's description typed and immutable, one client for the three products, or
responses that keep decoding when GitHub adds an enum value.

## Disclaimer

This library is built on the OpenAPI JSON schemas that GitHub publishes in
[github/rest-api-description](https://github.com/github/rest-api-description/tree/main/descriptions-next), and the
caveats of that project apply here too (see [Good to know](docs/guide/good-to-know.md#schema-caveats)).

**Only smoke tests have been run on the live GitHub environment, covering particular feature sets. Not every endpoint has
been tested. This library is a prerelease, intended to surface errors in the schemas and the code. Verify the behavior of
any endpoint you depend on before using it in production.**
