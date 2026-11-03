# User guide

The [README](../../README.md) is the short version: a client in a few lines, what to configure and what to catch. These
pages are the detail behind it. Read them in order, or jump to the one you need.

| Chapter | What it covers |
|---|---|
| 1. [Rate limiting in depth](rate-limiting.md) | Primary and secondary limits, buckets, credentials, long waits, the conditional cache, polling, concurrency and pacing, monitoring |
| 2. [Authentication in depth](authentication.md) | Rotating tokens, GitHub App installations and the token lifecycle, authenticating as the app, client credentials, SAML single sign-on, security |
| 3. [Performance](performance.md) | Per-call overhead, pagination, concurrency and throughput, memory, client lifecycle, with the measurements and the methodology |
| 4. [Live smoke tests](smoke-tests.md) | Running the opt-in tests against api.github.com with a fine-grained token and a sandbox repository, what they cover and how to read a failure |
| 5. [Configuration](configuration.md) | Every client option with its default and its limits on github.com, Enterprise Cloud and Enterprise Server, and examples |
| 6. [Flavors and GitHub Enterprise](flavors.md) | The three flavors, the `@Flavors` markers, fail-fast, data residency, Enterprise Server releases and the management console |
| 7. [Pagination](pagination.md) | Pages, `Link` headers, walking with `all()`, `stream()` and `forEachPage(...)`, page size and the page cap |
| 8. [Immutability](immutability.md) | What a response guarantees and how your inputs are copied |
| 9. [Exception handling](exception-handling.md) | Every exception that reaches your code, what the client handles for you and what you should handle |
| 10. [Good to know](good-to-know.md) | Newer GitHub data, file uploads, deprecated operations, threads and logging, and the caveats of the schema descriptions |
