# User guide

The [README](../../README.md) is the short version: a client in a few lines, what to configure and what to catch. These
pages are the detail behind its sections. Read them in order, or jump to the one you need.

| Chapter | What it covers | Behind the README section |
|---|---|---|
| 1. [Rate limiting in depth](rate-limiting.md) | Primary and secondary limits, buckets, credentials, long waits, the conditional cache, polling, concurrency and pacing, monitoring | [Rate limiting](../../README.md#rate-limiting) |
| 2. [Authentication in depth](authentication.md) | Rotating tokens, GitHub App installations and the token lifecycle, authenticating as the app, client credentials, SAML single sign-on, security | [Authentication](../../README.md#authentication) |
| 3. [Performance](performance.md) | Per-call overhead, pagination, concurrency and throughput, memory, client lifecycle, with the measurements and the methodology | [Performance](../../README.md#performance) |
| 4. [Live smoke tests](smoke-tests.md) | Running the opt-in tests against api.github.com with a fine-grained token and a sandbox repository, what they cover and how to read a failure | [Good to know](../../README.md#good-to-know) |
| 5. [Configuration](configuration.md) | Every client option with its default and its limits on github.com, Enterprise Cloud and Enterprise Server, and examples | [Configure only what you need](../../README.md#configure-only-what-you-need) |
| 6. [Flavors and GitHub Enterprise](flavors.md) | The three flavors, the `@Flavors` markers, fail-fast, data residency, Enterprise Server releases and the management console | [Flavors](../../README.md#flavors) |
| 7. [Pagination](pagination.md) | Pages, `Link` headers, walking with `all()`, `stream()` and `forEachPage(...)`, page size and the page cap | [Pagination](../../README.md#pagination) |
| 8. [Immutability](immutability.md) | What a response guarantees and how your inputs are copied | [Immutability](../../README.md#immutability) |
| 9. [Exception handling](exception-handling.md) | Every exception that reaches your code, what the client handles for you and what you should handle | [Exception handling](../../README.md#exception-handling) |
