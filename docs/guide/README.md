# User guide

The [README](../../README.md) is the short version: a client in a few lines, what to configure and what to catch. These
pages are the detail behind it. Read them in order, or jump to the one you need.

Configuration, authentication, rate limiting and exception handling belong to the transport, and are described in the
[github-client user guide](https://github.com/piotr-janczukowicz/github-client/blob/main/docs/guide/README.md).

| Chapter | What it covers |
|---|---|
| 1. [Performance](performance.md) | Per-call overhead, pagination, concurrency and throughput, memory, client lifecycle, with the measurements and the methodology |
| 2. [Live smoke tests](smoke-tests.md) | Running the opt-in tests against api.github.com with a fine-grained token and a sandbox repository, what they cover and how to read a failure |
| 3. [Flavors and GitHub Enterprise](flavors.md) | The three flavors, the `@Flavors` markers, fail-fast, data residency, Enterprise Server releases and the management console |
| 4. [Pagination](pagination.md) | Pages, `Link` headers, walking with `all()`, `stream()` and `forEachPage(...)`, page size and the page cap |
| 5. [Immutability](immutability.md) | What a response guarantees and how your inputs are copied |
| 6. [Good to know](good-to-know.md) | Newer GitHub data, file uploads, deprecated operations, threads and logging, and the caveats of the schema descriptions |
| 7. [Generic API](generic-api.md) | The escape hatch for a broken or missing typed method: describing a call, what it keeps from the client, what it refuses and its limits |
