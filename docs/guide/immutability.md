# Immutability

This page is part of the [user guide](README.md). The [README](../../README.md) has the short version.

What you get back cannot be changed by accident, and what you hand in cannot be changed behind the library's back.

## Output

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

## Input

A payload, filter or other object that you build and pass to a method copies every list, set and map you give it. Changing
your own collection afterwards does not change the request. A `null` collection that the type allows stays `null`; a `null`
element or value is rejected with a `NullPointerException`.

```java
List<String> labels = new ArrayList<>(List.of("gpu"));
GitHubAddCustomLabelsToSelfHostedRunnerForRepoPayload payload =
        new GitHubAddCustomLabelsToSelfHostedRunnerForRepoPayload(labels);
labels.add("arm"); // not in payload.labels()
```
