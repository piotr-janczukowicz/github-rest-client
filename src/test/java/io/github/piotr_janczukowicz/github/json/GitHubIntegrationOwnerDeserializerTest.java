package io.github.piotr_janczukowicz.github.json;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.github.api.apps.GitHubIntegrationOwner;
import io.github.piotr_janczukowicz.github.api.organizations.GitHubEnterprise;
import io.github.piotr_janczukowicz.github.api.users.GitHubSimpleUser;
import io.github.piotr_janczukowicz.json.JsonCodec;
import io.github.piotr_janczukowicz.json.JsonDecodeException;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import io.github.piotr_janczukowicz.util.FileUtils;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;
import tools.jackson.databind.DatabindException;

@UnitTest
class GitHubIntegrationOwnerDeserializerTest {

    private static final JsonCodec CODEC = GitHubJsonCodecs.defaults();

    @Test
    void deserialize_shouldReturnEnterprise_whenSlugPresent() {
        final byte[] json = FileUtils.readBytes(GitHubEnterprise.class, "enterprise.json");

        final GitHubIntegrationOwner decoded = CODEC.decode(json, GitHubIntegrationOwner.class);

        assertThat(decoded).isInstanceOf(GitHubIntegrationOwner.Enterprise.class);
    }

    @Test
    void deserialize_shouldReturnUser_whenLoginPresent() {
        final byte[] json = FileUtils.readBytes(GitHubSimpleUser.class, "simple-user-octocat.json");

        final GitHubIntegrationOwner decoded = CODEC.decode(json, GitHubIntegrationOwner.class);

        assertThat(decoded).isInstanceOf(GitHubIntegrationOwner.SimpleUser.class);
    }

    @Test
    void deserialize_shouldThrow_whenShapeUnknown() {
        final byte[] json = "{\"id\":1}".getBytes(StandardCharsets.UTF_8);

        final Throwable thrown = catchThrowable(() -> CODEC.decode(json, GitHubIntegrationOwner.class));

        assertThat(thrown)
                .isInstanceOf(JsonDecodeException.class)
                .cause()
                .isInstanceOf(DatabindException.class)
                .hasMessageContaining("owner shape");
    }
}
