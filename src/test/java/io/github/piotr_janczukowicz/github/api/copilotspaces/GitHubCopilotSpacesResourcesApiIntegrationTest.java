package io.github.piotr_janczukowicz.github.api.copilotspaces;

import static io.github.piotr_janczukowicz.http.HttpHeaderNames.CONTENT_TYPE;
import static io.github.piotr_janczukowicz.http.HttpMethod.DELETE;
import static io.github.piotr_janczukowicz.http.HttpMethod.GET;
import static io.github.piotr_janczukowicz.http.HttpMethod.POST;
import static io.github.piotr_janczukowicz.http.HttpMethod.PUT;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.CODEC;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.copilotSpacesApi;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.createdJson;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.jsonBytes;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.noContent;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.notFound;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.okJson;
import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.test.http.StubHttpServer;
import io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport;
import io.github.piotr_janczukowicz.testsupport.IntegrationTest;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

@IntegrationTest
class GitHubCopilotSpacesResourcesApiIntegrationTest {

    private static final String ORG = "octo-org";
    private static final String USERNAME = "octocat";
    private static final long SPACE_NUMBER = 1L;
    private static final long RESOURCE_ID = 7L;
    private static final String ORG_RESOURCES_PATH = "/orgs/octo-org/copilot-spaces/1/resources";
    private static final String ORG_RESOURCE_PATH = ORG_RESOURCES_PATH + "/7";
    private static final String USER_RESOURCES_PATH = "/users/octocat/copilot-spaces/1/resources";
    private static final String USER_RESOURCE_PATH = USER_RESOURCES_PATH + "/7";

    @RegisterExtension
    static final StubHttpServer HTTP = new StubHttpServer();

    @Test
    void listResourcesForOrg_shouldReturnResources_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                ORG_RESOURCES_PATH,
                okJson(jsonBytes(
                        GitHubCopilotSpacesResourcesApiIntegrationTest.class, "copilot-space-resources-page.json")));

        final GitHubCopilotSpaceResourcesPage page;
        try (final GitHubApiWireMockSupport.CopilotSpacesApiClient spaces = copilotSpacesApi(HTTP)) {
            page = spaces.api().resources().listResourcesForOrg(ORG, SPACE_NUMBER);
        }

        final GitHubCopilotSpaceResourcesPage expected = CODEC.decode(
                jsonBytes(GitHubCopilotSpacesResourcesApiIntegrationTest.class, "copilot-space-resources-page.json"),
                GitHubCopilotSpaceResourcesPage.class);

        assertThat(page).isEqualTo(expected);
        HTTP.verifyRequested(GET, ORG_RESOURCES_PATH);
    }

    @Test
    void createResourceForOrg_shouldSendPayload_whenResponseIsCreated() throws Exception {
        HTTP.stub(
                POST,
                ORG_RESOURCES_PATH,
                createdJson(jsonBytes(
                        GitHubCopilotSpacesResourcesApiIntegrationTest.class, "copilot-space-resource.json")));
        final GitHubCreateResourcePayload payload = CopilotSpacesCatalogFixtures.createResourcePayload();

        final GitHubCopilotSpaceResource resource;
        try (final GitHubApiWireMockSupport.CopilotSpacesApiClient spaces = copilotSpacesApi(HTTP)) {
            resource = spaces.api().resources().createResourceForOrg(ORG, SPACE_NUMBER, payload);
        }

        final GitHubCopilotSpaceResource expected = CODEC.decode(
                jsonBytes(GitHubCopilotSpacesResourcesApiIntegrationTest.class, "copilot-space-resource.json"),
                GitHubCopilotSpaceResource.class);

        assertThat(resource).isEqualTo(expected);
        HTTP.verifyRequested(POST, ORG_RESOURCES_PATH);
        HTTP.verifyHeader(POST, ORG_RESOURCES_PATH, CONTENT_TYPE, "application/json");
        HTTP.verifyBody(POST, ORG_RESOURCES_PATH, new String(CODEC.encode(payload), StandardCharsets.UTF_8));
    }

    @Test
    void createResourceForOrg_shouldReturnResource_whenResponseIsOk() throws Exception {
        HTTP.stub(
                POST,
                ORG_RESOURCES_PATH,
                okJson(jsonBytes(GitHubCopilotSpacesResourcesApiIntegrationTest.class, "copilot-space-resource.json")));

        final GitHubCopilotSpaceResource resource;
        try (final GitHubApiWireMockSupport.CopilotSpacesApiClient spaces = copilotSpacesApi(HTTP)) {
            resource = spaces.api()
                    .resources()
                    .createResourceForOrg(ORG, SPACE_NUMBER, CopilotSpacesCatalogFixtures.createResourcePayload());
        }

        assertThat(resource.id()).isEqualTo(RESOURCE_ID);
    }

    @Test
    void getResourceForOrg_shouldReturnResource_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                ORG_RESOURCE_PATH,
                okJson(jsonBytes(GitHubCopilotSpacesResourcesApiIntegrationTest.class, "copilot-space-resource.json")));

        final GitHubCopilotSpaceResource resource;
        try (final GitHubApiWireMockSupport.CopilotSpacesApiClient spaces = copilotSpacesApi(HTTP)) {
            resource = spaces.api()
                    .resources()
                    .getResourceForOrg(ORG, SPACE_NUMBER, RESOURCE_ID)
                    .orElseThrow();
        }

        final GitHubCopilotSpaceResource expected = CODEC.decode(
                jsonBytes(GitHubCopilotSpacesResourcesApiIntegrationTest.class, "copilot-space-resource.json"),
                GitHubCopilotSpaceResource.class);

        assertThat(resource).isEqualTo(expected);
        HTTP.verifyRequested(GET, ORG_RESOURCE_PATH);
    }

    @Test
    void getResourceForOrg_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        HTTP.stub(GET, ORG_RESOURCE_PATH, notFound());

        final Optional<GitHubCopilotSpaceResource> resource;
        try (final GitHubApiWireMockSupport.CopilotSpacesApiClient spaces = copilotSpacesApi(HTTP)) {
            resource = spaces.api().resources().getResourceForOrg(ORG, SPACE_NUMBER, RESOURCE_ID);
        }

        assertThat(resource).isEmpty();
    }

    @Test
    void updateResourceForOrg_shouldSendPayload_whenResponseIsOk() throws Exception {
        HTTP.stub(
                PUT,
                ORG_RESOURCE_PATH,
                okJson(jsonBytes(GitHubCopilotSpacesResourcesApiIntegrationTest.class, "copilot-space-resource.json")));
        final GitHubUpdateResourcePayload payload = CopilotSpacesCatalogFixtures.updateResourcePayload();

        final GitHubCopilotSpaceResource resource;
        try (final GitHubApiWireMockSupport.CopilotSpacesApiClient spaces = copilotSpacesApi(HTTP)) {
            resource = spaces.api().resources().updateResourceForOrg(ORG, SPACE_NUMBER, RESOURCE_ID, payload);
        }

        final GitHubCopilotSpaceResource expected = CODEC.decode(
                jsonBytes(GitHubCopilotSpacesResourcesApiIntegrationTest.class, "copilot-space-resource.json"),
                GitHubCopilotSpaceResource.class);

        assertThat(resource).isEqualTo(expected);
        HTTP.verifyRequested(PUT, ORG_RESOURCE_PATH);
        HTTP.verifyHeader(PUT, ORG_RESOURCE_PATH, CONTENT_TYPE, "application/json");
        HTTP.verifyBody(PUT, ORG_RESOURCE_PATH, new String(CODEC.encode(payload), StandardCharsets.UTF_8));
    }

    @Test
    void deleteResourceForOrg_shouldDelete_whenResponseIsNoContent() throws Exception {
        HTTP.stub(DELETE, ORG_RESOURCE_PATH, noContent());

        try (final GitHubApiWireMockSupport.CopilotSpacesApiClient spaces = copilotSpacesApi(HTTP)) {
            spaces.api().resources().deleteResourceForOrg(ORG, SPACE_NUMBER, RESOURCE_ID);
        }

        HTTP.verifyRequested(DELETE, ORG_RESOURCE_PATH);
    }

    @Test
    void listResourcesForUser_shouldReturnResources_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                USER_RESOURCES_PATH,
                okJson(jsonBytes(
                        GitHubCopilotSpacesResourcesApiIntegrationTest.class, "copilot-space-resources-page.json")));

        final GitHubCopilotSpaceResourcesPage page;
        try (final GitHubApiWireMockSupport.CopilotSpacesApiClient spaces = copilotSpacesApi(HTTP)) {
            page = spaces.api().resources().listResourcesForUser(USERNAME, SPACE_NUMBER);
        }

        final GitHubCopilotSpaceResourcesPage expected = CODEC.decode(
                jsonBytes(GitHubCopilotSpacesResourcesApiIntegrationTest.class, "copilot-space-resources-page.json"),
                GitHubCopilotSpaceResourcesPage.class);

        assertThat(page).isEqualTo(expected);
        HTTP.verifyRequested(GET, USER_RESOURCES_PATH);
    }

    @Test
    void createResourceForUser_shouldSendPayload_whenResponseIsCreated() throws Exception {
        HTTP.stub(
                POST,
                USER_RESOURCES_PATH,
                createdJson(jsonBytes(
                        GitHubCopilotSpacesResourcesApiIntegrationTest.class, "copilot-space-resource.json")));
        final GitHubCreateResourcePayload payload = CopilotSpacesCatalogFixtures.createResourcePayload();

        final GitHubCopilotSpaceResource resource;
        try (final GitHubApiWireMockSupport.CopilotSpacesApiClient spaces = copilotSpacesApi(HTTP)) {
            resource = spaces.api().resources().createResourceForUser(USERNAME, SPACE_NUMBER, payload);
        }

        final GitHubCopilotSpaceResource expected = CODEC.decode(
                jsonBytes(GitHubCopilotSpacesResourcesApiIntegrationTest.class, "copilot-space-resource.json"),
                GitHubCopilotSpaceResource.class);

        assertThat(resource).isEqualTo(expected);
        HTTP.verifyRequested(POST, USER_RESOURCES_PATH);
        HTTP.verifyHeader(POST, USER_RESOURCES_PATH, CONTENT_TYPE, "application/json");
        HTTP.verifyBody(POST, USER_RESOURCES_PATH, new String(CODEC.encode(payload), StandardCharsets.UTF_8));
    }

    @Test
    void createResourceForUser_shouldReturnResource_whenResponseIsOk() throws Exception {
        HTTP.stub(
                POST,
                USER_RESOURCES_PATH,
                okJson(jsonBytes(GitHubCopilotSpacesResourcesApiIntegrationTest.class, "copilot-space-resource.json")));

        final GitHubCopilotSpaceResource resource;
        try (final GitHubApiWireMockSupport.CopilotSpacesApiClient spaces = copilotSpacesApi(HTTP)) {
            resource = spaces.api()
                    .resources()
                    .createResourceForUser(
                            USERNAME, SPACE_NUMBER, CopilotSpacesCatalogFixtures.createResourcePayload());
        }

        assertThat(resource.id()).isEqualTo(RESOURCE_ID);
    }

    @Test
    void getResourceForUser_shouldReturnResource_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                USER_RESOURCE_PATH,
                okJson(jsonBytes(GitHubCopilotSpacesResourcesApiIntegrationTest.class, "copilot-space-resource.json")));

        final GitHubCopilotSpaceResource resource;
        try (final GitHubApiWireMockSupport.CopilotSpacesApiClient spaces = copilotSpacesApi(HTTP)) {
            resource = spaces.api()
                    .resources()
                    .getResourceForUser(USERNAME, SPACE_NUMBER, RESOURCE_ID)
                    .orElseThrow();
        }

        final GitHubCopilotSpaceResource expected = CODEC.decode(
                jsonBytes(GitHubCopilotSpacesResourcesApiIntegrationTest.class, "copilot-space-resource.json"),
                GitHubCopilotSpaceResource.class);

        assertThat(resource).isEqualTo(expected);
        HTTP.verifyRequested(GET, USER_RESOURCE_PATH);
    }

    @Test
    void getResourceForUser_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        HTTP.stub(GET, USER_RESOURCE_PATH, notFound());

        final Optional<GitHubCopilotSpaceResource> resource;
        try (final GitHubApiWireMockSupport.CopilotSpacesApiClient spaces = copilotSpacesApi(HTTP)) {
            resource = spaces.api().resources().getResourceForUser(USERNAME, SPACE_NUMBER, RESOURCE_ID);
        }

        assertThat(resource).isEmpty();
    }

    @Test
    void updateResourceForUser_shouldSendPayload_whenResponseIsOk() throws Exception {
        HTTP.stub(
                PUT,
                USER_RESOURCE_PATH,
                okJson(jsonBytes(GitHubCopilotSpacesResourcesApiIntegrationTest.class, "copilot-space-resource.json")));
        final GitHubUpdateResourcePayload payload = CopilotSpacesCatalogFixtures.updateResourcePayload();

        final GitHubCopilotSpaceResource resource;
        try (final GitHubApiWireMockSupport.CopilotSpacesApiClient spaces = copilotSpacesApi(HTTP)) {
            resource = spaces.api().resources().updateResourceForUser(USERNAME, SPACE_NUMBER, RESOURCE_ID, payload);
        }

        final GitHubCopilotSpaceResource expected = CODEC.decode(
                jsonBytes(GitHubCopilotSpacesResourcesApiIntegrationTest.class, "copilot-space-resource.json"),
                GitHubCopilotSpaceResource.class);

        assertThat(resource).isEqualTo(expected);
        HTTP.verifyRequested(PUT, USER_RESOURCE_PATH);
        HTTP.verifyHeader(PUT, USER_RESOURCE_PATH, CONTENT_TYPE, "application/json");
        HTTP.verifyBody(PUT, USER_RESOURCE_PATH, new String(CODEC.encode(payload), StandardCharsets.UTF_8));
    }

    @Test
    void deleteResourceForUser_shouldDelete_whenResponseIsNoContent() throws Exception {
        HTTP.stub(DELETE, USER_RESOURCE_PATH, noContent());

        try (final GitHubApiWireMockSupport.CopilotSpacesApiClient spaces = copilotSpacesApi(HTTP)) {
            spaces.api().resources().deleteResourceForUser(USERNAME, SPACE_NUMBER, RESOURCE_ID);
        }

        HTTP.verifyRequested(DELETE, USER_RESOURCE_PATH);
    }
}
