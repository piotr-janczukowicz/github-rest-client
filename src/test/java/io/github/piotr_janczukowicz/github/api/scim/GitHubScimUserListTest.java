package io.github.piotr_janczukowicz.github.api.scim;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.github.json.GitHubJsonCodecs;
import io.github.piotr_janczukowicz.json.JsonDecodeException;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.nio.charset.StandardCharsets;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubScimUserListTest {

    @Test
    void builder_shouldKeepEveryField_whenFixtureBuilt() {
        final GitHubScimUserList list = ScimCatalogFixtures.scimUserList();

        assertThat(list.totalResults()).isEqualTo(3);
        assertThat(list.startIndex()).isEqualTo(1);
        assertThat(list.resources()).containsExactly(ScimCatalogFixtures.scimUser());
    }

    @Test
    void constructor_shouldThrowIllegalArgumentException_whenSchemasEmpty() {
        final Throwable thrown = catchThrowable(() -> new GitHubScimUserList(List.of(), 0, 0, 1, List.of()));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void decode_shouldFail_whenTotalResultsIsMissing() {
        final byte[] json = ("{\"schemas\":[\"urn:ietf:params:scim:api:messages:2.0:ListResponse\"],"
                        + "\"itemsPerPage\":1,\"startIndex\":1,\"Resources\":[]}")
                .getBytes(StandardCharsets.UTF_8);

        final Throwable thrown =
                catchThrowable(() -> GitHubJsonCodecs.defaults().decode(json, GitHubScimUserList.class));

        assertThat(thrown).isInstanceOf(JsonDecodeException.class).hasStackTraceContaining("totalResults");
    }
}
