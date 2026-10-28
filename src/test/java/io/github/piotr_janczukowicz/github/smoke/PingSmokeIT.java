package io.github.piotr_janczukowicz.github.smoke;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.github.api.repositories.GitHubFullRepository;
import io.github.piotr_janczukowicz.github.api.users.GitHubAuthenticatedUser;
import io.github.piotr_janczukowicz.github.api.users.GitHubUser;
import io.github.piotr_janczukowicz.github.api.users.GitHubUserView;
import java.io.IOException;
import java.util.Optional;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("smoke")
class PingSmokeIT {

    private static final SmokeEnvironment ENV = SmokeEnvironment.fromEnv();

    @BeforeAll
    static void requireToken() {
        ENV.requireConfigured();
    }

    @Test
    void getAuthenticatedUser_shouldReturnALogin_whenTokenIsValid() throws IOException {
        final GitHubUserView user = ENV.github().users().user().getAuthenticatedUser();

        final String login =
                switch (user) {
                    case final GitHubUser plain -> plain.login();
                    case final GitHubAuthenticatedUser authenticated -> authenticated.login();
                };

        assertThat(login).isNotBlank();
    }

    @Test
    void get_shouldReturnTheSandbox_whenTokenHasAccess() throws IOException {
        final Optional<GitHubFullRepository> repository =
                ENV.github().repositories().repository().get(ENV.owner(), ENV.repo());

        assertThat(repository).map(GitHubFullRepository::fullName).contains(ENV.owner() + "/" + ENV.repo());
    }
}
