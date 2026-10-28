package io.github.piotr_janczukowicz.github.api.users;

import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class UsersCatalogFixtures {

    private UsersCatalogFixtures() {}

    public static GitHubSimpleUser octocat() {
        return GitHubSimpleUserBuilder.builder()
                .login("octocat")
                .id(1L)
                .nodeId("MDQ6VXNlcjE=")
                .avatarUrl("https://github.com/images/error/octocat_happy.gif")
                .gravatarId("")
                .url("https://api.github.com/users/octocat")
                .htmlUrl("https://github.com/octocat")
                .followersUrl("https://api.github.com/users/octocat/followers")
                .followingUrl("https://api.github.com/users/octocat/following{/other_user}")
                .gistsUrl("https://api.github.com/users/octocat/gists{/gist_id}")
                .starredUrl("https://api.github.com/users/octocat/starred{/owner}{/repo}")
                .subscriptionsUrl("https://api.github.com/users/octocat/subscriptions")
                .organizationsUrl("https://api.github.com/users/octocat/orgs")
                .reposUrl("https://api.github.com/users/octocat/repos")
                .eventsUrl("https://api.github.com/users/octocat/events{/privacy}")
                .receivedEventsUrl("https://api.github.com/users/octocat/received_events")
                .type("User")
                .siteAdmin(false)
                .name("monalisa octocat")
                .email("octocat@github.com")
                .starredAt("2020-11-02T16:12:33Z")
                .userViewType(GitHubUserViewType.PUBLIC)
                .build();
    }

    public static GitHubSimpleUser octocatWithoutOptionalFields() {
        return GitHubSimpleUserBuilder.builder(octocat())
                .name(null)
                .email(null)
                .starredAt(null)
                .userViewType(null)
                .build();
    }

    public static GitHubUser publicUserOctocat() {
        return new GitHubUser(
                "octocat",
                1L,
                "MDQ6VXNlcjE=",
                GitHubUserViewType.PUBLIC,
                "https://github.com/images/error/octocat_happy.gif",
                "",
                "https://api.github.com/users/octocat",
                "https://github.com/octocat",
                "https://api.github.com/users/octocat/followers",
                "https://api.github.com/users/octocat/following{/other_user}",
                "https://api.github.com/users/octocat/gists{/gist_id}",
                "https://api.github.com/users/octocat/starred{/owner}{/repo}",
                "https://api.github.com/users/octocat/subscriptions",
                "https://api.github.com/users/octocat/orgs",
                "https://api.github.com/users/octocat/repos",
                "https://api.github.com/users/octocat/events{/privacy}",
                "https://api.github.com/users/octocat/received_events",
                "User",
                false,
                "monalisa octocat",
                "GitHub",
                "https://github.com/blog",
                "San Francisco",
                "octocat@github.com",
                "octocat@github.com",
                false,
                "There once was...",
                "monatheoctocat",
                2,
                1,
                20,
                0,
                Instant.parse("2008-01-14T04:33:35Z"),
                Instant.parse("2008-01-14T04:33:35Z"),
                new GitHubPlan(0, "free", 976_562_499, 0),
                0,
                0,
                0,
                0,
                0);
    }

    public static GitHubAuthenticatedUser privateUserOctocat() {
        return new GitHubAuthenticatedUser(
                "octocat",
                1L,
                "MDQ6VXNlcjE=",
                GitHubUserViewType.PRIVATE,
                "https://github.com/images/error/octocat_happy.gif",
                "",
                "https://api.github.com/users/octocat",
                "https://github.com/octocat",
                "https://api.github.com/users/octocat/followers",
                "https://api.github.com/users/octocat/following{/other_user}",
                "https://api.github.com/users/octocat/gists{/gist_id}",
                "https://api.github.com/users/octocat/starred{/owner}{/repo}",
                "https://api.github.com/users/octocat/subscriptions",
                "https://api.github.com/users/octocat/orgs",
                "https://api.github.com/users/octocat/repos",
                "https://api.github.com/users/octocat/events{/privacy}",
                "https://api.github.com/users/octocat/received_events",
                "User",
                false,
                "monalisa octocat",
                "GitHub",
                "https://github.com/blog",
                "San Francisco",
                "octocat@github.com",
                "octocat@github.com",
                false,
                "There once was...",
                "monatheoctocat",
                2,
                1,
                20,
                0,
                Instant.parse("2008-01-14T04:33:35Z"),
                Instant.parse("2008-01-14T04:33:35Z"),
                81,
                100,
                100,
                10_000,
                8,
                true,
                new GitHubPlan(0, "Medium", 400, 20),
                false,
                "uid=octocat,ou=users,dc=github,dc=com");
    }

    public static GitHubSimpleUser jquery() {
        return GitHubSimpleUserBuilder.builder()
                .login("jquery")
                .id(70_142L)
                .nodeId("MDQ6VXNlcjcwMTQy")
                .avatarUrl("https://avatars.githubusercontent.com/u/70142?v=4")
                .gravatarId("")
                .url("https://api.github.com/users/jquery")
                .htmlUrl("https://github.com/jquery")
                .followersUrl("https://api.github.com/users/jquery/followers")
                .followingUrl("https://api.github.com/users/jquery/following{/other_user}")
                .gistsUrl("https://api.github.com/users/jquery/gists{/gist_id}")
                .starredUrl("https://api.github.com/users/jquery/starred{/owner}{/repo}")
                .subscriptionsUrl("https://api.github.com/users/jquery/subscriptions")
                .organizationsUrl("https://api.github.com/users/jquery/orgs")
                .reposUrl("https://api.github.com/users/jquery/repos")
                .eventsUrl("https://api.github.com/users/jquery/events{/privacy}")
                .receivedEventsUrl("https://api.github.com/users/jquery/received_events")
                .type("Organization")
                .siteAdmin(false)
                .name("jQuery")
                .email("open-source@jquery.com")
                .starredAt("2020-11-02T16:12:33Z")
                .userViewType(GitHubUserViewType.PUBLIC)
                .build();
    }

    public static GitHubSimpleUser monalisa() {
        return GitHubSimpleUserBuilder.builder()
                .login("monalisa")
                .id(2L)
                .nodeId("MDQ6VXNlcjI=")
                .avatarUrl("https://alambic.github.com/avatars/u/2?")
                .gravatarId("")
                .url("https://api.github.com/users/monalisa")
                .htmlUrl("https://github.com/monalisa")
                .followersUrl("https://api.github.com/users/monalisa/followers")
                .followingUrl("https://api.github.com/users/monalisa/following{/other_user}")
                .gistsUrl("https://api.github.com/users/monalisa/gists{/gist_id}")
                .starredUrl("https://api.github.com/users/monalisa/starred{/owner}{/repo}")
                .subscriptionsUrl("https://api.github.com/users/monalisa/subscriptions")
                .organizationsUrl("https://api.github.com/users/monalisa/orgs")
                .reposUrl("https://api.github.com/users/monalisa/repos")
                .eventsUrl("https://api.github.com/users/monalisa/events{/privacy}")
                .receivedEventsUrl("https://api.github.com/users/monalisa/received_events")
                .type("User")
                .siteAdmin(true)
                .name("Mona Lisa")
                .email("monalisa@github.com")
                .starredAt("2020-11-02T16:12:33Z")
                .userViewType(GitHubUserViewType.PUBLIC)
                .build();
    }

    public static GitHubListAttestationsBulkPayload listAttestationsBulkPayload() {
        return GitHubListAttestationsBulkPayloadBuilder.builder()
                .subjectDigests(List.of("sha256:abc"))
                .build();
    }

    static GitHubCreateGpgKeyPayload createGpgKeyPayload() {
        return GitHubCreateGpgKeyPayloadBuilder.builder()
                .name("name")
                .armoredPublicKey("key")
                .build();
    }

    static GitHubCreatePublicSshKeyPayload createPublicSshKeyPayload() {
        return GitHubCreatePublicSshKeyPayloadBuilder.builder()
                .title("title")
                .key("ssh-rsa AAA")
                .build();
    }

    static GitHubCreateSshSigningKeyPayload createSshSigningKeyPayload() {
        return GitHubCreateSshSigningKeyPayloadBuilder.builder()
                .title("title")
                .key("ssh-ed25519 AAA")
                .build();
    }

    static GitHubUpdateAuthenticatedUserPayload updateAuthenticatedUserPayload() {
        return GitHubUpdateAuthenticatedUserPayloadBuilder.builder()
                .name("mona")
                .build();
    }

    static GitHubUpdateEmailsPayload updateEmailsPayload() {
        return GitHubUpdateEmailsPayloadBuilder.builder()
                .emails(List.of("octocat@github.com"))
                .build();
    }

    static GitHubUpdateSocialAccountsPayload updateSocialAccountsPayload() {
        return GitHubUpdateSocialAccountsPayloadBuilder.builder()
                .accountUrls(List.of("https://twitter.com/octocat"))
                .build();
    }

    static GitHubGpgKey gpgKey() {
        return new GitHubGpgKey(
                3L,
                "Octocat's GPG Key",
                2L,
                "3262EFF25BA0D270",
                "xsBNBFayYZ...",
                List.of(new GitHubGpgKeyEmail("octocat@users.noreply.github.com", true)),
                List.of(new GitHubGpgKeySubkey(
                        4L,
                        3L,
                        "4A595D4C72EE49C7",
                        "zsBNBFayYZ...",
                        List.of(new GitHubGpgKeyEmail("subkey@users.noreply.github.com", true)),
                        List.of(nestedSubkeyRef()),
                        false,
                        true,
                        true,
                        false,
                        "2016-03-24T11:31:04-06:00",
                        "2016-03-24T11:31:04-07:00",
                        "nested-raw-key",
                        false)),
                true,
                false,
                false,
                true,
                Instant.parse("2016-03-24T17:31:04Z"),
                Instant.parse("2016-03-24T18:31:04Z"),
                false,
                "string");
    }

    static GitHubKey key() {
        return new GitHubKey(
                "2Sg8iYjAxxmI2LvUXpJjkYrMxURPc8r+dB7TJyvv1234",
                2L,
                "https://api.github.com/user/keys/2",
                "ssh-rsa AAAAB3NzaC1yc2EAAA",
                Instant.parse("2020-06-11T21:31:57Z"),
                false,
                false,
                Instant.parse("2021-01-01T12:00:00Z"));
    }

    static GitHubKeySimple keySimple() {
        return new GitHubKeySimple(
                1L, "ssh-rsa AAA...", Instant.parse("2014-12-10T15:53:42Z"), Instant.parse("2015-06-01T09:30:00Z"));
    }

    static GitHubSimpleUser usersTwo() {
        return GitHubSimpleUserBuilder.builder()
                .login("two")
                .id(2L)
                .nodeId("n2")
                .avatarUrl("https://example.com/a")
                .gravatarId("")
                .url("https://api.github.com/users/two")
                .htmlUrl("https://github.com/two")
                .followersUrl("https://api.github.com/users/two/followers")
                .followingUrl("https://api.github.com/users/two/following{/other_user}")
                .gistsUrl("https://api.github.com/users/two/gists{/gist_id}")
                .starredUrl("https://api.github.com/users/two/starred{/owner}{/repo}")
                .subscriptionsUrl("https://api.github.com/users/two/subscriptions")
                .organizationsUrl("https://api.github.com/users/two/orgs")
                .reposUrl("https://api.github.com/users/two/repos")
                .eventsUrl("https://api.github.com/users/two/events{/privacy}")
                .receivedEventsUrl("https://api.github.com/users/two/received_events")
                .type("User")
                .siteAdmin(false)
                .build();
    }

    public static GitHubSocialAccount socialAccount() {
        return new GitHubSocialAccount("twitter", "https://twitter.com/github");
    }

    static GitHubSshSigningKey sshSigningKey() {
        return new GitHubSshSigningKey(
                "2Sg8iYjAxxmI2LvUXpJjkYrMxURPc8r+dB7TJyvv1234",
                2L,
                "ssh-rsa AAAAB3NzaC1yc2EAAA",
                Instant.parse("2020-06-11T21:31:57Z"));
    }

    public static GitHubAttestations attestations() {
        return new GitHubAttestations(List.of(
                new GitHubAttestation(
                        1L,
                        "https://tmastaging.blob.core.windows.net/attestations/1/2024/11/08/4.json.sn?se=2024-11-09T17%3A13%3A43Z&sig=jPSbbJnIEshUyCjBLU5Ykq0uuGc5UYxTZE1%2FhdBNMXk%3D&sp=r&spr=https%2Chttp&sr=b&st=2024-11-08T17%3A13%3A43Z&sv=2024-08-04",
                        "user"),
                new GitHubAttestation(
                        1L,
                        "https://tmastaging.blob.core.windows.net/attestations/1/2024/11/08/5.json.sn?se=2024-11-09T17%3A13%3A43Z&sig=jPSbbJnIEshUyCjBLU5Ykq0uuGc5UYxTZE1%2FhdBNMXk%3D&sp=r&spr=https%2Chttp&sr=b&st=2024-11-08T17%3A13%3A43Z&sv=2024-08-04",
                        "user")));
    }

    static Map<String, Object> nestedSubkeyRef() {
        final Map<String, Object> ref = new LinkedHashMap<>();
        ref.put("id", 5);
        return ref;
    }

    static List<GitHubEmail> emailsOne() {
        return List.of(new GitHubEmail("octocat@github.com", true, true, "public"));
    }

    public static GitHubAttestationsBulk attestationsBulk() {
        final Map<String, List<GitHubAttestationDigestItem>> digests = new LinkedHashMap<>();
        digests.put(
                "sha256:abc123",
                List.of(
                        new GitHubAttestationDigestItem(1L, "https://example.com/bundle/1.json"),
                        new GitHubAttestationDigestItem(2L, "https://example.com/bundle/2.json")));
        digests.put("sha512:def456", null);
        return new GitHubAttestationsBulk(
                digests, new GitHubAttestationsPageInfo(true, false, "cursor-next", "cursor-prev"));
    }
}
