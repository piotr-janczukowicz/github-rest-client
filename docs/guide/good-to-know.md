# Good to know

This page is part of the [user guide](README.md). The [README](../../README.md) has the short version.

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

## Schema caveats

The library is built on the OpenAPI JSON schemas that GitHub publishes in
[github/rest-api-description](https://github.com/github/rest-api-description/tree/main/descriptions-next). The caveats of
that project apply here too:

- The `descriptions-next` folder holds the 3.1 version of the description and is subject to breaking changes on the
  `main` branch.
- Not all headers are described in the OpenAPI documents. Expect those to be added over time.
- Certain GitHub API resources use multi-segment path parameters, which the OpenAPI specification does not support.
- A lot of operations are accessible through multiple paths.
- Certain tools have poor support for references to components within the artifact.

The schema repository is licensed under the MIT license.
