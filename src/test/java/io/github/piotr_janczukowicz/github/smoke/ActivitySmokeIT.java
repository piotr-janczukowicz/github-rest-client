package io.github.piotr_janczukowicz.github.smoke;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.github.api.GitHub;
import io.github.piotr_janczukowicz.github.api.activity.GitHubActivityStarringApi;
import io.github.piotr_janczukowicz.github.api.activity.GitHubActivityWatchingApi;
import io.github.piotr_janczukowicz.github.api.activity.GitHubSetRepoSubscriptionPayload;
import io.github.piotr_janczukowicz.github.api.activity.GitHubThread;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubRepositorySubscription;
import io.github.piotr_janczukowicz.github.api.users.GitHubSimpleUser;
import io.github.piotr_janczukowicz.github.pagination.GitHubPage;
import java.io.IOException;
import java.util.Optional;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("smoke")
class ActivitySmokeIT {

    private static final SmokeEnvironment ENV = SmokeEnvironment.fromEnv();

    @BeforeAll
    static void requireToken() {
        ENV.requireConfigured();
    }

    @Test
    void starring_shouldRoundTrip_whenTheSandboxIsStarredAndUnstarred() throws IOException {
        final GitHub github = ENV.github();
        final GitHubActivityStarringApi starring = github.activity().starring();
        final boolean wasStarred = starring.checkRepoIsStarredByAuthenticatedUser(ENV.owner(), ENV.repo());
        try {
            starring.starRepoForAuthenticatedUser(ENV.owner(), ENV.repo());
            final boolean starred = starring.checkRepoIsStarredByAuthenticatedUser(ENV.owner(), ENV.repo());
            starring.unstarRepoForAuthenticatedUser(ENV.owner(), ENV.repo());
            final boolean unstarred = starring.checkRepoIsStarredByAuthenticatedUser(ENV.owner(), ENV.repo());

            assertThat(starred).isTrue();
            assertThat(unstarred).isFalse();
        } finally {
            if (wasStarred) {
                starring.starRepoForAuthenticatedUser(ENV.owner(), ENV.repo());
            } else {
                starring.unstarRepoForAuthenticatedUser(ENV.owner(), ENV.repo());
            }
        }
    }

    @Test
    void subscription_shouldRoundTrip_whenTheSandboxIsWatchedAndUnwatched() throws IOException {
        final GitHub github = ENV.github();
        final GitHubActivityWatchingApi watching = github.activity().watching();
        final Optional<GitHubRepositorySubscription> previous = watching.getRepoSubscription(ENV.owner(), ENV.repo());
        try {
            final GitHubRepositorySubscription set = watching.setRepoSubscription(
                    ENV.owner(), ENV.repo(), new GitHubSetRepoSubscriptionPayload(true, false));
            watching.deleteRepoSubscription(ENV.owner(), ENV.repo());
            final Optional<GitHubRepositorySubscription> deleted =
                    watching.getRepoSubscription(ENV.owner(), ENV.repo());

            assertThat(set.subscribed()).isTrue();
            assertThat(deleted).isEmpty();
        } finally {
            if (previous.isPresent()) {
                watching.setRepoSubscription(
                        ENV.owner(),
                        ENV.repo(),
                        new GitHubSetRepoSubscriptionPayload(
                                previous.get().subscribed(), previous.get().ignored()));
            }
        }
    }

    @Test
    void listNotifications_shouldParse_whenReadingTheInbox() throws IOException {
        final GitHubPage<GitHubThread> page =
                ENV.github().activity().notifications().listNotificationsForAuthenticatedUser();

        assertThat(page.items()).isNotNull();
    }

    @Test
    void listStargazersForRepo_shouldParse_whenReadingTheSandbox() throws IOException {
        final GitHubPage<GitHubSimpleUser> page =
                ENV.github().activity().starring().listStargazersForRepo(ENV.owner(), ENV.repo());

        assertThat(page.items()).isNotNull();
    }
}
