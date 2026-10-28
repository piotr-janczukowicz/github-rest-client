package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static io.github.piotr_janczukowicz.test.http.HttpResponses.response;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.github.piotr_janczukowicz.github.api.announcementbanners.AnnouncementBannersCatalogFixtures;
import io.github.piotr_janczukowicz.github.api.announcementbanners.GitHubAnnouncement;
import io.github.piotr_janczukowicz.http.HttpRequest;
import io.github.piotr_janczukowicz.http.HttpStatus;
import io.github.piotr_janczukowicz.http.transport.HttpTransport;
import io.github.piotr_janczukowicz.test.TestClients;
import io.github.piotr_janczukowicz.test.http.HttpTransportMocks;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import io.github.piotr_janczukowicz.util.FileUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

@UnitTest
class GitHubEnterpriseAdminAnnouncementApiTest {

    private HttpTransport transport;

    @BeforeEach
    void setUpTransport() {
        transport = HttpTransportMocks.mock();
    }

    @Test
    void constructor_shouldThrow_whenCallerNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubEnterpriseAdminAnnouncementApi(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("caller");
    }

    @Test
    void getAnnouncement_shouldReturnAnnouncement_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("announcement.json")));

        final GitHubAnnouncement announcement = api().getAnnouncement();

        assertThat(announcement).isEqualTo(AnnouncementBannersCatalogFixtures.announcement());
        assertThat(line(captureSingleRequest())).isEqualTo("GET https://api.github.com/enterprise/announcement");
    }

    @Test
    void setAnnouncement_shouldPatchAndReturnAnnouncement_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("announcement.json")));

        final GitHubAnnouncement announcement =
                api().setAnnouncement(AnnouncementBannersCatalogFixtures.announcement());

        assertThat(announcement).isEqualTo(AnnouncementBannersCatalogFixtures.announcement());
        assertThat(line(captureSingleRequest())).isEqualTo("PATCH https://api.github.com/enterprise/announcement");
    }

    @Test
    void setAnnouncement_shouldThrow_whenPayloadNull() {
        final GitHubEnterpriseAdminAnnouncementApi api = api();

        final Throwable thrown = catchThrowable(() -> api.setAnnouncement(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("payload");
    }

    @Test
    void removeAnnouncement_shouldSendDelete_whenResponseIsNoContent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().removeAnnouncement();

        assertThat(line(captureSingleRequest())).isEqualTo("DELETE https://api.github.com/enterprise/announcement");
    }

    private GitHubEnterpriseAdminAnnouncementApi api() {
        return new GitHubEnterpriseAdminAnnouncementApi(TestClients.caller(transport));
    }

    private static String json(final String fileName) {
        return FileUtils.readString(GitHubEnterpriseAdminAnnouncementApiTest.class, fileName);
    }

    private HttpRequest captureSingleRequest() throws Exception {
        final ArgumentCaptor<HttpRequest> captor = ArgumentCaptor.forClass(HttpRequest.class);
        verify(transport).execute(captor.capture());
        return captor.getValue();
    }

    private static String line(final HttpRequest request) {
        return request.method() + " " + request.uri();
    }
}
