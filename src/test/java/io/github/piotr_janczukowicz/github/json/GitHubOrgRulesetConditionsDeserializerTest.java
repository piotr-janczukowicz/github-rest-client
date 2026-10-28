package io.github.piotr_janczukowicz.github.json;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.github.api.organizations.GitHubOrgRulesetConditions;
import io.github.piotr_janczukowicz.json.JsonCodec;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubOrgRulesetConditionsDeserializerTest {

    @Test
    void deserialize_shouldReturnNameBranch_whenRepositoryNamePresent() {
        final byte[] json = """
                {"ref_name":{"include":["refs/heads/main"],"exclude":[]},"repository_name":{"include":["*"],"exclude":[],"protected":false}}
                """.getBytes(StandardCharsets.UTF_8);

        final GitHubOrgRulesetConditions decoded =
                GitHubJsonCodecs.defaults().decode(json, GitHubOrgRulesetConditions.class);

        assertThat(decoded).isInstanceOf(GitHubOrgRulesetConditions.RepositoryNameAndRefName.class);
    }

    @Test
    void deserialize_shouldReturnIdBranch_whenRepositoryIdPresent() {
        final byte[] json = "{\"repository_id\":{\"repository_ids\":[1296269]}}".getBytes(StandardCharsets.UTF_8);

        final GitHubOrgRulesetConditions decoded =
                GitHubJsonCodecs.defaults().decode(json, GitHubOrgRulesetConditions.class);

        assertThat(decoded).isInstanceOf(GitHubOrgRulesetConditions.RepositoryIdAndRefName.class);
    }

    @Test
    void deserialize_shouldReturnPropertyBranch_whenRepositoryPropertyPresent() {
        final byte[] json =
                "{\"repository_property\":{\"include\":[],\"exclude\":[]}}".getBytes(StandardCharsets.UTF_8);

        final GitHubOrgRulesetConditions decoded =
                GitHubJsonCodecs.defaults().decode(json, GitHubOrgRulesetConditions.class);

        assertThat(decoded).isInstanceOf(GitHubOrgRulesetConditions.RepositoryPropertyAndRefName.class);
    }

    @Test
    void deserialize_shouldReturnUnknown_whenNoTargetPresent() {
        final byte[] json = "{\"ref_name\":{\"include\":[\"refs/heads/main\"]}}".getBytes(StandardCharsets.UTF_8);

        final GitHubOrgRulesetConditions decoded =
                GitHubJsonCodecs.defaults().decode(json, GitHubOrgRulesetConditions.class);

        assertThat(decoded)
                .isEqualTo(new GitHubOrgRulesetConditions.Unknown(
                        Map.of("ref_name", Map.of("include", List.of("refs/heads/main")))));
    }

    @Test
    void deserialize_shouldReturnUnknown_whenMultipleTargetsPresent() {
        final byte[] json = "{\"repository_name\":{\"include\":[\"*\"]},\"repository_id\":{\"repository_ids\":[1]}}"
                .getBytes(StandardCharsets.UTF_8);

        final GitHubOrgRulesetConditions decoded =
                GitHubJsonCodecs.defaults().decode(json, GitHubOrgRulesetConditions.class);

        assertThat(decoded)
                .isInstanceOfSatisfying(
                        GitHubOrgRulesetConditions.Unknown.class,
                        unknown ->
                                assertThat(unknown.properties()).containsOnlyKeys("repository_name", "repository_id"));
    }

    @Test
    void deserialize_shouldReturnUnknown_whenObjectIsEmpty() {
        final byte[] json = "{}".getBytes(StandardCharsets.UTF_8);

        final GitHubOrgRulesetConditions decoded =
                GitHubJsonCodecs.defaults().decode(json, GitHubOrgRulesetConditions.class);

        assertThat(decoded).isEqualTo(new GitHubOrgRulesetConditions.Unknown(Map.of()));
    }

    @Test
    void deserialize_shouldReturnUnknown_whenTargetIsAccompaniedByUnknownKey() {
        final byte[] json = "{\"repository_id\":{\"repository_ids\":[1]},\"organization_id\":{\"ids\":[2]}}"
                .getBytes(StandardCharsets.UTF_8);

        final GitHubOrgRulesetConditions decoded =
                GitHubJsonCodecs.defaults().decode(json, GitHubOrgRulesetConditions.class);

        assertThat(decoded)
                .isInstanceOfSatisfying(
                        GitHubOrgRulesetConditions.Unknown.class,
                        unknown ->
                                assertThat(unknown.properties()).containsOnlyKeys("repository_id", "organization_id"));
    }

    @Test
    void roundTrip_shouldKeepJson_whenConditionsAreUnknown() {
        final String json = "{\"organization_id\":{\"ids\":[2]}}";
        final JsonCodec codec = GitHubJsonCodecs.defaults();
        final GitHubOrgRulesetConditions decoded =
                codec.decode(json.getBytes(StandardCharsets.UTF_8), GitHubOrgRulesetConditions.class);

        final String encoded = new String(codec.encode(decoded), StandardCharsets.UTF_8);

        assertThat(encoded).isEqualTo(json);
    }

    @Test
    void deserialize_shouldReturnNull_whenJsonNull() {
        final GitHubOrgRulesetConditions decoded = GitHubJsonMappers.defaults()
                .readValue("null".getBytes(StandardCharsets.UTF_8), GitHubOrgRulesetConditions.class);

        assertThat(decoded).isNull();
    }
}
