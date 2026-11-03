# Flavors and GitHub Enterprise

This page is part of the [user guide](README.md). The [README](../../README.md) has the short version.

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

## GitHub Enterprise Cloud with data residency

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

## GitHub Enterprise Server

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
