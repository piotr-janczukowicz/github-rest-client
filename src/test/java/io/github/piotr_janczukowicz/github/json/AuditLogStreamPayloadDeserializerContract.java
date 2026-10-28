package io.github.piotr_janczukowicz.github.json;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.github.api.enterpriseadmin.GitHubAmazonS3AccessKeysConfig;
import io.github.piotr_janczukowicz.github.api.enterpriseadmin.GitHubAmazonS3OidcConfig;
import io.github.piotr_janczukowicz.github.api.enterpriseadmin.GitHubAzureBlobConfig;
import io.github.piotr_janczukowicz.github.api.enterpriseadmin.GitHubAzureHubConfig;
import io.github.piotr_janczukowicz.github.api.enterpriseadmin.GitHubDatadogConfig;
import io.github.piotr_janczukowicz.github.api.enterpriseadmin.GitHubGoogleCloudConfig;
import io.github.piotr_janczukowicz.github.api.enterpriseadmin.GitHubHecConfig;
import io.github.piotr_janczukowicz.github.api.enterpriseadmin.GitHubSplunkConfig;
import io.github.piotr_janczukowicz.json.JsonCodec;
import io.github.piotr_janczukowicz.json.JsonDecodeException;
import java.nio.charset.StandardCharsets;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import tools.jackson.core.exc.StreamReadException;
import tools.jackson.databind.DatabindException;
import tools.jackson.databind.json.JsonMapper;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
abstract class AuditLogStreamPayloadDeserializerContract<P> {

    protected static final JsonCodec CODEC = GitHubJsonCodecs.defaults();
    protected static final String SPLUNK = "{\"domain\":\"splunk.example.com\",\"port\":8088,\"key_id\":\"key-1\","
            + "\"encrypted_token\":\"encrypted-token\",\"ssl_verify\":true}";
    protected static final String AZURE_BLOB =
            "{\"key_id\":\"key-1\",\"encrypted_sas_url\":\"encrypted-sas-url\",\"container\":\"audit-logs\"}";

    private static final JsonMapper TREE = JsonMapper.builder().build();
    private static final String AZURE_HUB =
            "{\"name\":\"octo-hub\",\"encrypted_connstring\":\"encrypted-connstring\",\"key_id\":\"key-1\"}";
    private static final String OIDC = "{\"bucket\":\"octo-bucket\",\"region\":\"us-east-1\",\"key_id\":\"key-1\","
            + "\"authentication_type\":\"oidc\",\"arn_role\":\"arn:aws:iam::123456789012:role/audit-logs\"}";
    private static final String ACCESS_KEYS =
            "{\"bucket\":\"octo-bucket\",\"region\":\"us-east-1\",\"key_id\":\"key-1\","
                    + "\"authentication_type\":\"access_keys\",\"encrypted_secret_key\":\"secret\","
                    + "\"encrypted_access_key_id\":\"access\"}";
    private static final String HEC = "{\"domain\":\"hec.example.com\",\"port\":8088,\"key_id\":\"key-1\","
            + "\"encrypted_token\":\"encrypted-token\",\"path\":\"/services/collector\",\"ssl_verify\":true}";
    private static final String GOOGLE =
            "{\"bucket\":\"octo-bucket\",\"key_id\":\"key-1\",\"encrypted_json_credentials\":\"credentials\"}";
    private static final String DATADOG =
            "{\"encrypted_token\":\"encrypted-token\",\"site\":\"US\",\"key_id\":\"key-1\"}";

    protected abstract Class<P> payloadType();

    protected abstract Object vendorSpecific(P payload);

    protected abstract P splunkPayload();

    protected Stream<Arguments> extraVariants() {
        return Stream.empty();
    }

    protected Stream<Arguments> extraInvalidBodies() {
        return Stream.empty();
    }

    @Test
    void deserialize_shouldBindAzureBlobConfig_whenAzureBlobStorage() {
        final P payload = decode(body("Azure Blob Storage", AZURE_BLOB));

        assertThat(vendorSpecific(payload))
                .isInstanceOfSatisfying(
                        GitHubAzureBlobConfig.class,
                        config -> assertThat(config.container()).isEqualTo("audit-logs"));
    }

    @Test
    void deserialize_shouldBindAzureHubConfig_whenAzureEventHubs() {
        final P payload = decode(body("Azure Event Hubs", AZURE_HUB));

        assertThat(vendorSpecific(payload))
                .isInstanceOfSatisfying(
                        GitHubAzureHubConfig.class,
                        config -> assertThat(config.name()).isEqualTo("octo-hub"));
    }

    @Test
    void deserialize_shouldBindOidcConfig_whenAmazonS3AndOidc() {
        final P payload = decode(body("Amazon S3", OIDC));

        assertThat(vendorSpecific(payload)).isInstanceOf(GitHubAmazonS3OidcConfig.class);
    }

    @Test
    void deserialize_shouldBindAccessKeysConfig_whenAmazonS3AndAccessKeys() {
        final P payload = decode(body("Amazon S3", ACCESS_KEYS));

        assertThat(vendorSpecific(payload)).isInstanceOf(GitHubAmazonS3AccessKeysConfig.class);
    }

    @Test
    void deserialize_shouldThrowDecodeException_whenAmazonS3AuthenticationUnknown() {
        final String vendor = ACCESS_KEYS.replace("access_keys", "saml");

        final Class<P> type = payloadType();
        final byte[] bytes = body("Amazon S3", vendor).getBytes(StandardCharsets.UTF_8);

        final Throwable thrown = catchThrowable(() -> CODEC.decode(bytes, type));

        assertThat(thrown).isInstanceOf(JsonDecodeException.class).cause().isInstanceOf(DatabindException.class);
    }

    @Test
    void deserialize_shouldBindSplunkConfig_whenSplunk() {
        final P payload = decode(body("Splunk", SPLUNK));

        assertThat(vendorSpecific(payload))
                .isInstanceOfSatisfying(
                        GitHubSplunkConfig.class,
                        config -> assertThat(config.port()).isEqualTo(8088));
    }

    @Test
    void deserialize_shouldBindHecConfig_whenHttpsEventCollector() {
        final P payload = decode(body("HTTPS Event Collector", HEC));

        assertThat(vendorSpecific(payload))
                .isInstanceOfSatisfying(
                        GitHubHecConfig.class,
                        config -> assertThat(config.path()).isEqualTo("/services/collector"));
    }

    @Test
    void deserialize_shouldBindGoogleCloudConfig_whenGoogleCloudStorage() {
        final P payload = decode(body("Google Cloud Storage", GOOGLE));

        assertThat(vendorSpecific(payload)).isInstanceOf(GitHubGoogleCloudConfig.class);
    }

    @Test
    void deserialize_shouldBindDatadogConfig_whenDatadog() {
        final P payload = decode(body("Datadog", DATADOG));

        assertThat(vendorSpecific(payload)).isInstanceOf(GitHubDatadogConfig.class);
    }

    @Test
    void deserialize_shouldThrowDecodeException_whenStreamTypeUnknown() {
        final Class<P> type = payloadType();
        final byte[] bytes = body("Sumo Logic", SPLUNK).getBytes(StandardCharsets.UTF_8);

        final Throwable thrown = catchThrowable(() -> CODEC.decode(bytes, type));

        assertThat(thrown).isInstanceOf(JsonDecodeException.class).cause().isInstanceOf(DatabindException.class);
    }

    @Test
    void deserialize_shouldThrowDecodeException_whenVendorDoesNotMatchStreamType() {
        final Class<P> type = payloadType();
        final byte[] bytes = body("Splunk", AZURE_BLOB).getBytes(StandardCharsets.UTF_8);

        final Throwable thrown = catchThrowable(() -> CODEC.decode(bytes, type));

        assertThat(thrown).isInstanceOf(JsonDecodeException.class).cause().isInstanceOf(DatabindException.class);
    }

    @Test
    void deserialize_shouldThrowDecodeException_whenVendorSpecificMissing() {
        final Class<P> type = payloadType();
        final byte[] bytes = "{\"enabled\":true,\"stream_type\":\"Splunk\"}".getBytes(StandardCharsets.UTF_8);

        final Throwable thrown = catchThrowable(() -> CODEC.decode(bytes, type));

        assertThat(thrown)
                .isInstanceOf(JsonDecodeException.class)
                .cause()
                .isInstanceOf(DatabindException.class)
                .hasMessageContaining("vendor_specific");
    }

    @Test
    void deserialize_shouldThrowDecodeException_whenEnabledMissing() {
        final Class<P> type = payloadType();
        final String json = "{\"stream_type\":\"Splunk\",\"vendor_specific\":" + SPLUNK + "}";
        final byte[] bytes = json.getBytes(StandardCharsets.UTF_8);

        final Throwable thrown = catchThrowable(() -> CODEC.decode(bytes, type));

        assertThat(thrown)
                .isInstanceOf(JsonDecodeException.class)
                .cause()
                .isInstanceOf(DatabindException.class)
                .hasMessageContaining("enabled");
    }

    @Test
    void encode_shouldWriteVendorFieldsWithoutTypeInfo_andRoundTrip() {
        final P payload = splunkPayload();

        final String encoded = new String(CODEC.encode(payload), StandardCharsets.UTF_8);

        assertThat(encoded).contains("\"vendor_specific\":{").doesNotContain("@type");
        assertThat(decode(encoded)).isEqualTo(payload);
    }

    @ParameterizedTest
    @MethodSource("variants")
    void encode_shouldReproduceWireJson_whenPayloadDecodedFromVariant(final String streamType, final String vendor) {
        final String wire = body(streamType, vendor);
        final P payload = decode(wire);

        final String encoded = new String(CODEC.encode(payload), StandardCharsets.UTF_8);

        assertSoftly(softly -> {
            softly.assertThat(TREE.readTree(encoded)).isEqualTo(TREE.readTree(wire));
            softly.assertThat(decode(encoded)).isEqualTo(payload);
        });
    }

    @ParameterizedTest
    @MethodSource("invalidBodies")
    void deserialize_shouldThrowDecodeException_whenBodyIsInvalid(final String json, final String fragment) {
        final Class<P> type = payloadType();
        final byte[] bytes = json.getBytes(StandardCharsets.UTF_8);

        final Throwable thrown = catchThrowable(() -> CODEC.decode(bytes, type));

        assertThat(thrown)
                .isInstanceOf(JsonDecodeException.class)
                .cause()
                .isInstanceOf(DatabindException.class)
                .hasMessageContaining(fragment);
    }

    @Test
    void deserialize_shouldBindSplunkConfigAndDropPath_whenHecBodySentAsSplunk() {
        final P payload = decode(body("Splunk", HEC));

        assertThat(vendorSpecific(payload)).isInstanceOf(GitHubSplunkConfig.class);
    }

    @Test
    void deserialize_shouldThrowDecodeException_whenKeyIsDuplicated() {
        final String json =
                "{\"enabled\":true,\"enabled\":false,\"stream_type\":\"Splunk\",\"vendor_specific\":" + SPLUNK + "}";

        final Class<P> type = payloadType();
        final byte[] bytes = json.getBytes(StandardCharsets.UTF_8);

        final Throwable thrown = catchThrowable(() -> CODEC.decode(bytes, type));

        assertThat(thrown)
                .isInstanceOf(JsonDecodeException.class)
                .cause()
                .isInstanceOf(StreamReadException.class)
                .hasMessageContaining("Duplicate");
    }

    Stream<Arguments> variants() {
        return Stream.concat(
                Stream.of(
                        Arguments.of("Azure Blob Storage", AZURE_BLOB),
                        Arguments.of("Azure Event Hubs", AZURE_HUB),
                        Arguments.of("Amazon S3", OIDC),
                        Arguments.of("Amazon S3", ACCESS_KEYS),
                        Arguments.of("Splunk", SPLUNK),
                        Arguments.of("HTTPS Event Collector", HEC),
                        Arguments.of("Google Cloud Storage", GOOGLE),
                        Arguments.of("Datadog", DATADOG)),
                extraVariants());
    }

    Stream<Arguments> invalidBodies() {
        return Stream.concat(
                Stream.of(
                        Arguments.of("{\"enabled\":true,\"vendor_specific\":" + SPLUNK + "}", "stream_type"),
                        Arguments.of(
                                "{\"enabled\":true,\"stream_type\":null,\"vendor_specific\":" + SPLUNK + "}",
                                "stream_type"),
                        Arguments.of(body("Splunk", "null"), "vendor_specific"),
                        Arguments.of(body("Splunk", "[]"), "from Array value"),
                        Arguments.of(body("Splunk", "\"text\""), "from String value"),
                        Arguments.of(
                                body("Amazon S3", OIDC.replace("\"authentication_type\":\"oidc\",", "")),
                                "authentication_type"),
                        Arguments.of(body("Amazon S3", OIDC.replace("oidc", "saml")), "oidc or access_keys"),
                        Arguments.of(body("HTTPS Event Collector", SPLUNK), "path")),
                extraInvalidBodies());
    }

    protected final P decode(final String json) {
        return CODEC.decode(json.getBytes(StandardCharsets.UTF_8), payloadType());
    }

    protected static String body(final String streamType, final String vendorSpecific) {
        return "{\"enabled\":true,\"stream_type\":\"" + streamType + "\",\"vendor_specific\":" + vendorSpecific + "}";
    }
}
