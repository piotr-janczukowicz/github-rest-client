package io.github.piotr_janczukowicz.github.api.ratelimit;

final class RatelimitCatalogFixtures {

    private RatelimitCatalogFixtures() {}

    static GitHubRateLimit rateLimit() {
        return rateLimit(5_000, 4_999, 1_590_701_888, 1);
    }

    static GitHubRateLimitResources rateLimitResources() {
        return new GitHubRateLimitResources(
                rateLimit(),
                rateLimit(30, 29, 1_590_701_888, 1),
                rateLimit(),
                rateLimit(10, 9, 1_590_701_888, 1),
                rateLimit(100, 99, 1_590_701_888, 1),
                rateLimit(),
                rateLimit(10_000, 9_999, 1_590_701_888, 1),
                rateLimit(15_000, 14_999, 1_590_701_888, 1),
                rateLimit(100, 99, 1_590_701_888, 1),
                rateLimit(100, 99, 1_590_701_888, 1),
                rateLimit(10, 9, 1_590_701_888, 1),
                rateLimit(200, 199, 1_590_701_888, 1));
    }

    static GitHubRateLimitOverview rateLimitOverview() {
        return new GitHubRateLimitOverview(rateLimitResources());
    }

    static GitHubRateLimitOverview rateLimitOverviewWithoutCodeSearch() {
        return new GitHubRateLimitOverview(new GitHubRateLimitResources(
                rateLimit(),
                rateLimit(30, 29, 1_590_701_888, 1),
                rateLimit(5_000, 4_000, 1_590_701_888, 1_000),
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null));
    }

    private static GitHubRateLimit rateLimit(final int limit, final int remaining, final long reset, final int used) {
        return new GitHubRateLimit(limit, remaining, reset, used);
    }
}
