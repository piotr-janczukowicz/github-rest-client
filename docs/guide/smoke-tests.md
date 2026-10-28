# Live smoke tests

Opt-in tests that call the real api.github.com with a fine-grained token. `mvn verify` never runs them.

## Setup

1. Create an empty sandbox repository in your organization with a README (default: `piotr-janczukowicz/github-api-smoke`). Its name must contain `smoke`; the tests refuse any other repository.
2. Create a fine-grained personal access token. Resource owner: the organization. Repository access: only the sandbox repository.
   - Repository permissions, read and write: Contents, Issues, Pull requests, Actions, Administration, Webhooks, Deploy keys, Variables.
   - Repository permissions, read: Secrets, Metadata.
   - The owner of the sandbox may be a personal account or an organization; the tests only read its public profile.
3. Export the token. The names do not start with `GITHUB_`, which GitHub rejects for Actions variables and secrets.

```bash
export SMOKE_GITHUB_TOKEN=github_pat_...
export SMOKE_GITHUB_REPO=piotr-janczukowicz/github-api-smoke   # optional, this is the default
```

## Run

```bash
mvn -Psmoke verify
mvn -Psmoke verify -Dit.test=IssuesSmokeIT
```

Without `SMOKE_GITHUB_TOKEN` every smoke class is skipped.

## What is covered

| Class | Area |
|---|---|
| `PingSmokeIT` | token works, sandbox reachable |
| `ReadSmokeIT` | rate limit, meta, emojis, licenses, gitignore, codes of conduct, owner account, branches, pagination, 404, 401 |
| `IssuesSmokeIT` | issue, label, milestone, comment |
| `GitSmokeIT` | blob, tree, commit, ref, file contents, pull request |
| `RepoSettingsSmokeIT` | release and asset, webhook, deploy key, Actions variable, topics, secrets public key |
| `ProtocolSmokeIT` | since and page-number pagination, `pages()`/`stream()`, search envelope, cursor page, issue lock/unlock (PUT/DELETE with 204), 302 redirect, markdown media types, 422 validation |
| `ConditionalSmokeIT` | conditional cache: the second read of a resource spends no rate-limit quota (a 304) |
| `CleanupSmokeIT` | sweeper of leftovers of crashed runs |

Not covered: starring (fine-grained tokens cannot call those endpoints), GHES and `/manage/v1/`, billing, organization-level writes, apps and installation tokens, Actions secret writes (they need a sealed-box encryption library on the test classpath).

Every resource a test creates is named `smoke-<runId>-...` (variables: `SMOKE_<RUNID>_...`) and is deleted in a `finally` block. `CleanupSmokeIT` removes leftovers of earlier runs.

## Reading a failure

A failing smoke test means the generated client and the live API disagree. Fix it in the generator inputs (spec patches, scripts, the sync skill), not by hand-editing generated code. "Resource not accessible by personal access token" (403) means the token lacks one of the permissions listed above.
