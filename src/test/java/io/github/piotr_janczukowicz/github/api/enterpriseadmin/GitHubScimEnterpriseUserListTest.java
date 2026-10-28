package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.github.json.GitHubJsonCodecs;
import io.github.piotr_janczukowicz.json.JsonDecodeException;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubScimEnterpriseUserListTest {

    @Test
    void builder_shouldKeepEveryField_whenFixtureBuilt() {
        final GitHubScimEnterpriseUserList value = EnterpriseAdminCatalogFixtures.scimEnterpriseUserList();

        assertThat(GitHubScimEnterpriseUserListBuilder.builder(value).build()).isEqualTo(value);
    }

    @Test
    void decode_shouldFail_whenTotalResultsIsMissing() {
        final byte[] json = ("{\"schemas\":[\"urn:ietf:params:scim:api:messages:2.0:ListResponse\"],"
                        + "\"itemsPerPage\":1,\"startIndex\":1,\"Resources\":[]}")
                .getBytes(StandardCharsets.UTF_8);

        final Throwable thrown =
                catchThrowable(() -> GitHubJsonCodecs.defaults().decode(json, GitHubScimEnterpriseUserList.class));

        assertThat(thrown).isInstanceOf(JsonDecodeException.class).hasStackTraceContaining("totalResults");
    }
}
