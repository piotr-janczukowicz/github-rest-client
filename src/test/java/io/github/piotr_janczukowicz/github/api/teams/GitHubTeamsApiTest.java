package io.github.piotr_janczukowicz.github.api.teams;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.github.client.GitHubClient;
import io.github.piotr_janczukowicz.test.TestClients;
import io.github.piotr_janczukowicz.test.http.HttpTransportMocks;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubTeamsApiTest {

    private GitHubClient client;

    @BeforeEach
    void setUpClient() {
        client = TestClients.client(HttpTransportMocks.mock());
    }

    @Test
    void constructor_shouldThrow_whenClientNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubTeamsApi(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("caller");
    }

    @Test
    void team_shouldReturnSameInstance_whenCalledTwice() {
        final GitHubTeamsApi api = new GitHubTeamsApi(TestClients.caller(client));

        final GitHubTeamApi first = api.team();
        final GitHubTeamApi second = api.team();

        assertThat(first).isSameAs(second);
    }

    @Test
    void members_shouldReturnSameInstance_whenCalledTwice() {
        final GitHubTeamsApi api = new GitHubTeamsApi(TestClients.caller(client));

        final GitHubTeamsMembersApi first = api.members();
        final GitHubTeamsMembersApi second = api.members();

        assertThat(first).isSameAs(second);
    }

    @Test
    void externalGroups_shouldReturnSameInstance_whenCalledTwice() {
        final GitHubTeamsApi api = new GitHubTeamsApi(TestClients.caller(client));

        final GitHubTeamsExternalGroupsApi first = api.externalGroups();
        final GitHubTeamsExternalGroupsApi second = api.externalGroups();

        assertThat(first).isSameAs(second);
    }

    @Test
    void teamSync_shouldReturnSameInstance_whenCalledTwice() {
        final GitHubTeamsApi api = new GitHubTeamsApi(TestClients.caller(client));

        final GitHubTeamsTeamSyncApi first = api.teamSync();
        final GitHubTeamsTeamSyncApi second = api.teamSync();

        assertThat(first).isSameAs(second);
    }
}
