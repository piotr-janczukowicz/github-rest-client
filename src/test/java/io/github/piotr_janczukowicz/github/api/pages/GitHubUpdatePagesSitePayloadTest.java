package io.github.piotr_janczukowicz.github.api.pages;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.json.OptionalNull;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubUpdatePagesSitePayloadTest {

    @Test
    void constructor_shouldThrow_whenAllFieldsNull() {
        final GitHubUpdatePagesSitePayloadBuilder builder = GitHubUpdatePagesSitePayloadBuilder.builder();

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("cname, httpsEnforced, buildType, source, or sitePublic");
    }

    @Test
    void constructor_shouldAcceptHttpsEnforced_whenOnlyHttpsEnforcedSet() {
        final GitHubUpdatePagesSitePayload payload = GitHubUpdatePagesSitePayloadBuilder.builder()
                .httpsEnforced(true)
                .build();

        assertThat(payload.httpsEnforced()).isTrue();
    }

    @Test
    void constructor_shouldAcceptCname_whenOnlyOptionalNullCnameSet() {
        final GitHubUpdatePagesSitePayload explicitNull = GitHubUpdatePagesSitePayloadBuilder.builder()
                .cname(OptionalNull.ofNull())
                .build();
        final GitHubUpdatePagesSitePayload presentCname = GitHubUpdatePagesSitePayloadBuilder.builder()
                .cname(OptionalNull.of("docs.example.com"))
                .build();

        assertSoftly(softly -> {
            softly.assertThat(explicitNull.cname()).isNotNull();
            softly.assertThat(explicitNull.cname().value()).isNull();
            softly.assertThat(presentCname.cname()).isNotNull();
            softly.assertThat(presentCname.cname().value()).isEqualTo("docs.example.com");
        });
    }

    @Test
    void constructor_shouldAcceptBuildType_whenOnlyBuildTypeSet() {
        final GitHubUpdatePagesSitePayload payload = GitHubUpdatePagesSitePayloadBuilder.builder()
                .buildType(GitHubPagesBuildType.WORKFLOW)
                .build();

        assertThat(payload.buildType()).isEqualTo(GitHubPagesBuildType.WORKFLOW);
    }

    @Test
    void constructor_shouldAcceptSource_whenOnlySourceSet() {
        final GitHubUpdatePagesSitePayload payload = GitHubUpdatePagesSitePayloadBuilder.builder()
                .source(GitHubUpdatePagesSiteSource.preset(GitHubPagesSourcePreset.GH_PAGES))
                .build();

        assertThat(payload.source()).isEqualTo(GitHubUpdatePagesSiteSource.preset(GitHubPagesSourcePreset.GH_PAGES));
    }

    @Test
    void constructor_shouldAcceptSitePublic_whenOnlySitePublicSet() {
        final GitHubUpdatePagesSitePayload payload =
                GitHubUpdatePagesSitePayloadBuilder.builder().sitePublic(true).build();

        assertThat(payload.sitePublic()).isTrue();
    }
}
