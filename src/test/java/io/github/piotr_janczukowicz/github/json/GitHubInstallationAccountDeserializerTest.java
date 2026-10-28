package io.github.piotr_janczukowicz.github.json;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.github.api.organizations.GitHubInstallation;
import io.github.piotr_janczukowicz.github.api.organizations.GitHubInstallationAccount;
import io.github.piotr_janczukowicz.json.JsonCodec;
import io.github.piotr_janczukowicz.json.JsonDecodeException;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import io.github.piotr_janczukowicz.util.FileUtils;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;
import tools.jackson.databind.DatabindException;

@UnitTest
class GitHubInstallationAccountDeserializerTest {

    @Test
    void deserialize_shouldReturnEnterprise_whenAccountHasSlug() {
        final byte[] json = FileUtils.readBytes(GitHubInstallation.class, "installation-enterprise.json");

        final GitHubInstallation installation = GitHubJsonCodecs.defaults().decode(json, GitHubInstallation.class);

        assertThat(installation.account()).isInstanceOf(GitHubInstallationAccount.Enterprise.class);
    }

    @Test
    void deserialize_shouldReturnSimpleUser_whenAccountHasLogin() {
        final byte[] json = FileUtils.readBytes(GitHubInstallation.class, "installation.json");

        final GitHubInstallation installation = GitHubJsonCodecs.defaults().decode(json, GitHubInstallation.class);

        assertThat(installation.account()).isInstanceOf(GitHubInstallationAccount.SimpleUser.class);
    }

    @Test
    void deserialize_shouldReturnNull_whenAccountIsNull() {
        final byte[] json = FileUtils.readBytes(GitHubInstallation.class, "installation-null-account.json");

        final GitHubInstallation installation = GitHubJsonCodecs.defaults().decode(json, GitHubInstallation.class);

        assertThat(installation.account()).isNull();
    }

    @Test
    void deserialize_shouldThrow_whenAccountShapeUnknown() {
        final byte[] json = "{\"id\":1}".getBytes(StandardCharsets.UTF_8);

        final JsonCodec codec = GitHubJsonCodecs.defaults();

        final Throwable thrown = catchThrowable(() -> codec.decode(json, GitHubInstallationAccount.class));

        assertThat(thrown)
                .isInstanceOf(JsonDecodeException.class)
                .cause()
                .isInstanceOf(DatabindException.class)
                .hasMessageContaining("account shape");
    }
}
