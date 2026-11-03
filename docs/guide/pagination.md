# Pagination

This page is part of the [user guide](README.md). The [README](../../README.md) has the short version.

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
