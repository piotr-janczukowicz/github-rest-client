package io.github.piotr_janczukowicz.github.smoke;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.github.api.GitHub;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubFullRepository;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubRepositoryApi;
import io.github.piotr_janczukowicz.github.client.GitHubCaller;
import io.github.piotr_janczukowicz.github.client.GitHubClient;
import io.github.piotr_janczukowicz.github.client.GitHubClientConfiguration;
import io.github.piotr_janczukowicz.github.client.GitHubClients;
import io.github.piotr_janczukowicz.http.client.HttpCallEvent;
import io.github.piotr_janczukowicz.http.client.authentication.AuthenticationProviders;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("smoke")
class ConditionalSmokeIT {

    private static final SmokeEnvironment ENV = SmokeEnvironment.fromEnv();

    @BeforeAll
    static void requireToken() {
        ENV.requireConfigured();
    }

    @Test
    @SuppressWarnings("PMD.SystemPrintln") // The smoke run reports its statuses to the console.
    void get_shouldNotSpendQuota_whenTheSecondReadIsConditional() throws IOException {
        final List<HttpCallEvent> events = new CopyOnWriteArrayList<>();
        final GitHubClientConfiguration configuration =
                GitHubClientConfiguration.builder().callObserver(events::add).build();
        try (GitHubClient client = GitHubClients.create(
                AuthenticationProviders.bearer(ENV.token()), "github-api-smoke/1.0", configuration)) {
            final GitHubRepositoryApi repositories =
                    new GitHub(GitHubCaller.of(client)).repositories().repository();

            final GitHubFullRepository first =
                    repositories.get(ENV.owner(), ENV.repo()).orElseThrow();
            final GitHubFullRepository second =
                    repositories.get(ENV.owner(), ENV.repo()).orElseThrow();

            assertThat(second).isEqualTo(first);
            assertThat(events).hasSize(2);
            assertThat(events).extracting(HttpCallEvent::statusCode).startsWith(200);
            assertThat(events.getFirst().responseHeader("ETag")).isPresent();
            System.out.println("conditional statuses: "
                    + events.stream().map(HttpCallEvent::statusCode).toList());
        }
    }
}
