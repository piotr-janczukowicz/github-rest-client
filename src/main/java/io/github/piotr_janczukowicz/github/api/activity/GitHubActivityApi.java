package io.github.piotr_janczukowicz.github.api.activity;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import io.github.piotr_janczukowicz.github.client.GitHubCaller;
import io.github.piotr_janczukowicz.util.LazyValue;

/** GitHub Activity REST resource aggregator for API version {@code 2026-03-10}. */
public final class GitHubActivityApi {

    private final LazyValue<GitHubActivityEventsApi> events;
    private final LazyValue<GitHubActivityFeedsApi> feeds;
    private final LazyValue<GitHubActivityNotificationsApi> notifications;
    private final LazyValue<GitHubActivityStarringApi> starring;
    private final LazyValue<GitHubActivityWatchingApi> watching;

    /**
     * Activity resource aggregator over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubActivityApi(final GitHubCaller caller) {
        requireNonNull(caller, "caller");
        this.events = new LazyValue<>(() -> new GitHubActivityEventsApi(caller));
        this.feeds = new LazyValue<>(() -> new GitHubActivityFeedsApi(caller));
        this.notifications = new LazyValue<>(() -> new GitHubActivityNotificationsApi(caller));
        this.starring = new LazyValue<>(() -> new GitHubActivityStarringApi(caller));
        this.watching = new LazyValue<>(() -> new GitHubActivityWatchingApi(caller));
    }

    /**
     * Event operations ({@code subcategory=events}).
     *
     * @return events API
     */
    public GitHubActivityEventsApi events() {
        return events.get();
    }

    /**
     * Feed operations ({@code subcategory=feeds}).
     *
     * @return feeds API
     */
    public GitHubActivityFeedsApi feeds() {
        return feeds.get();
    }

    /**
     * Notification operations ({@code subcategory=notifications}).
     *
     * @return notifications API
     */
    public GitHubActivityNotificationsApi notifications() {
        return notifications.get();
    }

    /**
     * Starring operations ({@code subcategory=starring}).
     *
     * @return starring API
     */
    public GitHubActivityStarringApi starring() {
        return starring.get();
    }

    /**
     * Watching operations ({@code subcategory=watching}).
     *
     * @return watching API
     */
    public GitHubActivityWatchingApi watching() {
        return watching.get();
    }
}
