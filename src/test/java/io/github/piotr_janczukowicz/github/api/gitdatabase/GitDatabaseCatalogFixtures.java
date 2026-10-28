package io.github.piotr_janczukowicz.github.api.gitdatabase;

import io.github.piotr_janczukowicz.github.api.search.SearchCatalogFixtures;
import io.github.piotr_janczukowicz.json.OptionalNull;
import java.time.Instant;
import java.util.List;

final class GitDatabaseCatalogFixtures {

    private static final String SHA = "7638417db6d59f3c431d3e1f261cc637155684cd";
    private static final String NODE_ID = "MDM6VGFnOTQwYmQzMzYyNDhlZmFlMGY5ZWU1YmM3YjJkNWM5ODU4ODdiMTZhYw==";
    private static final String BASE_URL = "https://api.github.com/repos/octocat/Hello-World/git";
    private static final Instant DATE = Instant.parse("2014-08-08T20:02:04Z");

    private GitDatabaseCatalogFixtures() {}

    static GitHubShortBlob shortBlob() {
        return GitHubShortBlobBuilder.builder()
                .url(BASE_URL + "/blobs/" + SHA)
                .sha(SHA)
                .build();
    }

    static GitHubBlob blob() {
        return GitHubBlobBuilder.builder()
                .content("Q29udGVudCBvZiB0aGUgYmxvYg==")
                .encoding("base64")
                .url(BASE_URL + "/blobs/" + SHA)
                .sha(SHA)
                .size(19)
                .nodeId(NODE_ID)
                .highlightedContent("<div>Content of the blob</div>")
                .build();
    }

    static GitHubGitCommit gitCommit() {
        return GitHubGitCommitBuilder.builder()
                .sha(SHA)
                .nodeId(NODE_ID)
                .url(BASE_URL + "/commits/" + SHA)
                .author(gitCommitUser())
                .committer(gitCommitUser())
                .message("Fix #42")
                .tree(gitCommitTree())
                .parents(List.of(gitCommitParent()))
                .verification(gitCommitVerification())
                .htmlUrl("https://github.com/octocat/Hello-World/commit/" + SHA)
                .build();
    }

    static GitHubGitCommitUser gitCommitUser() {
        return GitHubGitCommitUserBuilder.builder()
                .date(DATE)
                .email("monalisa.octocat@example.com")
                .name("Monalisa Octocat")
                .build();
    }

    static GitHubGitCommitTree gitCommitTree() {
        return GitHubGitCommitTreeBuilder.builder()
                .sha(SHA)
                .url(BASE_URL + "/trees/" + SHA)
                .build();
    }

    static GitHubGitCommitParent gitCommitParent() {
        return GitHubGitCommitParentBuilder.builder()
                .sha("7d1b31e74ee336d15cbd21741bc88a537ed063a0")
                .url(BASE_URL + "/commits/7d1b31e74ee336d15cbd21741bc88a537ed063a0")
                .htmlUrl("https://github.com/octocat/Hello-World/commit/7d1b31e74ee336d15cbd21741bc88a537ed063a0")
                .build();
    }

    static GitHubGitCommitVerification gitCommitVerification() {
        return GitHubGitCommitVerificationBuilder.builder()
                .verified(false)
                .reason("unsigned")
                .signature("-----BEGIN PGP SIGNATURE-----\n\nwl4/Tl8K\n-----END PGP SIGNATURE-----")
                .payload("tree 827efc6d56897b048c772eb4087f854f46256132")
                .verifiedAt("2014-08-09T08:02:04Z")
                .build();
    }

    static GitHubGitRef gitRef() {
        return GitHubGitRefBuilder.builder()
                .ref("refs/heads/featureA")
                .nodeId("MDM6UmVmcmVmcy9oZWFkcy9mZWF0dXJlQQ==")
                .url(BASE_URL + "/refs/heads/featureA")
                .object(gitRefObject())
                .build();
    }

    static GitHubGitRefObject gitRefObject() {
        return GitHubGitRefObjectBuilder.builder()
                .type("commit")
                .sha("aa218f56b14c9653891f9e74264a383fa43fefbd")
                .url(BASE_URL + "/commits/aa218f56b14c9653891f9e74264a383fa43fefbd")
                .build();
    }

    static GitHubGitTag gitTag() {
        return GitHubGitTagBuilder.builder()
                .nodeId(NODE_ID)
                .tag("v0.0.1")
                .sha("940bd336248efae0f9ee5bc7b2d5c985887b16ac")
                .url(BASE_URL + "/tags/940bd336248efae0f9ee5bc7b2d5c985887b16ac")
                .message("Initial public release")
                .tagger(gitTagTagger())
                .object(gitTagObject())
                .verification(SearchCatalogFixtures.unsignedVerification())
                .build();
    }

    static GitHubGitTagTagger gitTagTagger() {
        return GitHubGitTagTaggerBuilder.builder()
                .date("2014-11-07T22:01:45Z")
                .email("schacon@gmail.com")
                .name("Scott Chacon")
                .build();
    }

    static GitHubGitTagObject gitTagObject() {
        return GitHubGitTagObjectBuilder.builder()
                .sha("c3d0be41ecbe669545ee3e94d31ed9a4bc91ee3c")
                .type("commit")
                .url(BASE_URL + "/commits/c3d0be41ecbe669545ee3e94d31ed9a4bc91ee3c")
                .build();
    }

    static GitHubGitTree gitTree() {
        return GitHubGitTreeBuilder.builder()
                .sha("9fb037999f264ba9a7fc6274d15fa3ae2ab98312")
                .url(BASE_URL + "/trees/9fb037999f264ba9a7fc6274d15fa3ae2ab98312")
                .truncated(false)
                .tree(List.of(gitTreeEntry()))
                .build();
    }

    static GitHubGitTreeEntry gitTreeEntry() {
        return GitHubGitTreeEntryBuilder.builder()
                .path("file.rb")
                .mode("100644")
                .type("blob")
                .sha("44b4fc6d56897b048c772eb4087f854f46256132")
                .size(132)
                .url(BASE_URL + "/blobs/44b4fc6d56897b048c772eb4087f854f46256132")
                .build();
    }

    static GitHubCreateBlobPayload createBlobPayload() {
        return GitHubCreateBlobPayloadBuilder.builder()
                .content("Content of the blob")
                .encoding("utf-8")
                .build();
    }

    static GitHubCreateCommitPayload createCommitPayload() {
        return GitHubCreateCommitPayloadBuilder.builder()
                .message("my commit message")
                .tree("827efc6d56897b048c772eb4087f854f46256132")
                .parents(List.of("7d1b31e74ee336d15cbd21741bc88a537ed063a0"))
                .author(createCommitAuthor())
                .committer(createCommitCommitter())
                .signature("-----BEGIN PGP SIGNATURE-----\n\niQIzBAAB\n-----END PGP SIGNATURE-----")
                .build();
    }

    static GitHubCreateCommitAuthor createCommitAuthor() {
        return GitHubCreateCommitAuthorBuilder.builder()
                .name("Mona Octocat")
                .email("octocat@github.com")
                .date(Instant.parse("2008-07-09T04:13:30Z"))
                .build();
    }

    static GitHubCreateCommitCommitter createCommitCommitter() {
        return GitHubCreateCommitCommitterBuilder.builder()
                .name("Mona Octocat")
                .email("octocat@github.com")
                .date(Instant.parse("2008-07-09T04:13:30Z"))
                .build();
    }

    static GitHubCreateRefPayload createRefPayload() {
        return GitHubCreateRefPayloadBuilder.builder()
                .ref("refs/heads/featureA")
                .sha("aa218f56b14c9653891f9e74264a383fa43fefbd")
                .build();
    }

    static GitHubUpdateRefPayload updateRefPayload() {
        return GitHubUpdateRefPayloadBuilder.builder()
                .sha("aa218f56b14c9653891f9e74264a383fa43fefbd")
                .force(true)
                .build();
    }

    static GitHubCreateTagPayload createTagPayload() {
        return GitHubCreateTagPayloadBuilder.builder()
                .tag("v0.0.1")
                .message("initial version")
                .object("c3d0be41ecbe669545ee3e94d31ed9a4bc91ee3c")
                .type(GitHubCreateTagType.COMMIT)
                .tagger(createTagTagger())
                .build();
    }

    static GitHubCreateTagTagger createTagTagger() {
        return GitHubCreateTagTaggerBuilder.builder()
                .name("Monalisa Octocat")
                .email("octocat@github.com")
                .date(Instant.parse("2011-06-17T21:53:35Z"))
                .build();
    }

    static GitHubCreateTreePayload createTreePayload() {
        return GitHubCreateTreePayloadBuilder.builder()
                .tree(List.of(createTreeEntry()))
                .baseTree("9fb037999f264ba9a7fc6274d15fa3ae2ab98312")
                .build();
    }

    static GitHubGetTreeFilter getTreeFilter() {
        return GitHubGetTreeFilterBuilder.builder().recursive("1").build();
    }

    static GitHubCreateTreeEntry createTreeEntry() {
        return GitHubCreateTreeEntryBuilder.builder()
                .path("file.rb")
                .mode(GitHubCreateTreeEntryMode.FILE)
                .type(GitHubCreateTreeEntryType.BLOB)
                .sha(OptionalNull.of("44b4fc6d56897b048c772eb4087f854f46256132"))
                .content("puts 'hello'")
                .build();
    }
}
