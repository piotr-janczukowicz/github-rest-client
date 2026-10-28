package io.github.piotr_janczukowicz.github.json;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.github.api.copilotspaces.CopilotSpacesCatalogFixtures;
import io.github.piotr_janczukowicz.github.api.copilotspaces.GitHubCopilotSpaceOwner;
import io.github.piotr_janczukowicz.github.api.organizations.GitHubOrganizationSimple;
import io.github.piotr_janczukowicz.github.api.users.GitHubSimpleUser;
import io.github.piotr_janczukowicz.json.JsonCodec;
import io.github.piotr_janczukowicz.json.JsonDecodeException;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import io.github.piotr_janczukowicz.util.FileUtils;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;
import tools.jackson.databind.DatabindException;

@UnitTest
class GitHubCopilotSpaceOwnerDeserializerTest {

    private static final JsonCodec CODEC = GitHubJsonCodecs.defaults();

    @Test
    void deserialize_shouldReturnOrganization_whenHooksUrlPresent() {
        final byte[] json = FileUtils.readBytes(GitHubOrganizationSimple.class, "organization-simple.json");

        final GitHubCopilotSpaceOwner decoded = CODEC.decode(json, GitHubCopilotSpaceOwner.class);

        assertThat(decoded).isEqualTo(CopilotSpacesCatalogFixtures.copilotSpaceOwnerOrganization());
    }

    @Test
    void deserialize_shouldReturnUser_whenLoginPresent() {
        final byte[] json = FileUtils.readBytes(GitHubSimpleUser.class, "simple-user-octocat.json");

        final GitHubCopilotSpaceOwner decoded = CODEC.decode(json, GitHubCopilotSpaceOwner.class);

        assertThat(decoded).isEqualTo(CopilotSpacesCatalogFixtures.copilotSpaceOwnerUser());
    }

    @Test
    void deserialize_shouldThrow_whenShapeUnknown() {
        final byte[] json = "{\"id\":1}".getBytes(StandardCharsets.UTF_8);

        final Throwable thrown = catchThrowable(() -> CODEC.decode(json, GitHubCopilotSpaceOwner.class));

        assertThat(thrown)
                .isInstanceOf(JsonDecodeException.class)
                .cause()
                .isInstanceOf(DatabindException.class)
                .hasMessageContaining("owner shape");
    }
}
