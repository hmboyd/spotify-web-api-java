package se.michaelthelin.spotify.requests.data.library;

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
public class RemoveUsersSavedTracksRequestTest extends AbstractDataTest<String> {
  private final RemoveUsersSavedTracksRequest defaultRequest = ITest.SPOTIFY_API
    .removeUsersSavedTracks(ITest.ID_TRACK, ITest.ID_TRACK)
    .setHttpManager(
      TestUtil.MockedHttpManager.returningJson(null))
    .build();
  private final RemoveUsersSavedTracksRequest bodyRequest = ITest.SPOTIFY_API
    .removeUsersSavedTracks(ITest.TRACKS)
    .setHttpManager(
      TestUtil.MockedHttpManager.returningJson(null))
    .build();

  public RemoveUsersSavedTracksRequestTest() throws Exception {
  }

  @Test
  public void shouldComplyWithReference() {
    assertHasAuthorizationHeader(defaultRequest);
    Assert.assertEquals(
      "https://api.spotify.com:443/v1/me/tracks?ids=01iyCAUm8EvOFqVWYJ3dVX%2C01iyCAUm8EvOFqVWYJ3dVX",
      defaultRequest.getUri().toString());

    assertHasAuthorizationHeader(bodyRequest);
    Assertions.assertHasHeader(defaultRequest, "Content-Type", "application/json");
    Assertions.assertHasBodyParameter(bodyRequest,
      "ids",
      ITest.TRACKS);
    Assert.assertEquals("https://api.spotify.com:443/v1/me/tracks",
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
