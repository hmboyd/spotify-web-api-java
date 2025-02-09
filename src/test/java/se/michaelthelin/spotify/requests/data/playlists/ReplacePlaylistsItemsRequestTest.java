package se.michaelthelin.spotify.requests.data.playlists;

import org.apache.hc.core5.http.ParseException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import se.michaelthelin.spotify.Assertions;
import se.michaelthelin.spotify.ITest;
import se.michaelthelin.spotify.TestUtil;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.requests.data.AbstractDataTest;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import static org.junit.Assert.assertNull;

@RunWith(MockitoJUnitRunner.class)
public class ReplacePlaylistsItemsRequestTest extends AbstractDataTest<String> {
  private final ReplacePlaylistsItemsRequest defaultRequest = ITest.SPOTIFY_API
    .replacePlaylistsItems(ITest.ID_PLAYLIST, new String[]{"spotify:track:" + ITest.ID_TRACK, "spotify:track:" + ITest.ID_TRACK})
    .setHttpManager(
      TestUtil.MockedHttpManager.returningJson(null))
    .build();
  private final ReplacePlaylistsItemsRequest bodyRequest = ITest.SPOTIFY_API
    .replacePlaylistsItems(ITest.ID_PLAYLIST, ITest.TRACKS)
    .setHttpManager(
      TestUtil.MockedHttpManager.returningJson(null))
    .build();

  public ReplacePlaylistsItemsRequestTest() throws Exception {
  }

  @Test
  public void shouldComplyWithReference() {
    assertHasAuthorizationHeader(defaultRequest);
    Assert.assertEquals(
      "https://api.spotify.com:443/v1/playlists/3AGOiaoRXMSjswCLtuNqv5/tracks?uris=spotify%3Atrack%3A01iyCAUm8EvOFqVWYJ3dVX%2Cspotify%3Atrack%3A01iyCAUm8EvOFqVWYJ3dVX",
      defaultRequest.getUri().toString());

    assertHasAuthorizationHeader(bodyRequest);
    Assertions.assertHasHeader(defaultRequest, "Content-Type", "application/json");
    Assertions.assertHasBodyParameter(
      bodyRequest,
      "uris",
      ITest.TRACKS);
    Assert.assertEquals(
      "https://api.spotify.com:443/v1/playlists/3AGOiaoRXMSjswCLtuNqv5/tracks",
      bodyRequest.getUri().toString());
  }

  @Test
  public void shouldReturnDefault_sync() throws IOException, SpotifyWebApiException, ParseException {
    shouldReturnDefault(defaultRequest.execute());
  }

  @Test
  public void shouldReturnDefault_async() throws ExecutionException, InterruptedException {
    shouldReturnDefault(defaultRequest.executeAsync().get());
  }

  public void shouldReturnDefault(final String string) {
    assertNull(
      string);
  }
}
