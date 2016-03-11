package us.thedavis.armbot.reddit;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;

import net.dean.jraw.RedditClient;
import net.dean.jraw.http.NetworkException;
import net.dean.jraw.http.oauth.Credentials;
import net.dean.jraw.http.oauth.OAuthData;
import net.dean.jraw.http.oauth.OAuthHelper;

public class TestMinimalRedditClient {
	
	@Test
	public void testAuthentication_Success() throws Exception{
		RedditClient client = mock(RedditClient.class);
		when(client.getOAuthHelper()).thenReturn(mock(OAuthHelper.class));
		MinimalRedditClient mrc = new MinimalRedditClient(client, getCredentials());
		mrc.authenticate();
		verify(client, atLeastOnce()).authenticate(any(OAuthData.class));
	}

	public String testSomeOtherMethod(){
		return "SomeOtherMethod";
	}
	
	@Test(expected=NetworkException.class)
	public void testAuthentication_Failure() throws Exception{
		RedditClient client = mock(RedditClient.class);
		when(client.getOAuthHelper()).thenReturn(mock(OAuthHelper.class));
		doThrow(NetworkException.class).when(client).authenticate(any(OAuthData.class));
		MinimalRedditClient mrc = new MinimalRedditClient(client, getCredentials());
		mrc.authenticate();
	}
	
	@Test
	public void testDeauthenticate() throws Exception{
		RedditClient client = mock(RedditClient.class);
		OAuthHelper helper = mock(OAuthHelper.class);
		when(client.getOAuthHelper()).thenReturn(helper);
		MinimalRedditClient mrc = new MinimalRedditClient(client, getCredentials());
		mrc.deauthenticate();
		verify(client, times(1)).getOAuthHelper();
		verify(helper, times(1)).revokeAccessToken(any(Credentials.class));
		verify(client, times(1)).deauthenticate();
	}
	
	@Test
	public void testIsAuthenticated_True() throws Exception{
		RedditClient client = mock(RedditClient.class);
		when(client.isAuthenticated()).thenReturn(true);
		MinimalRedditClient mrc = new MinimalRedditClient(client, getCredentials());
		assertTrue(mrc.isAuthenticated());
	}
	
	@Test
	public void testIsAuthenticated_False() throws Exception{
		RedditClient client = mock(RedditClient.class);
		when(client.isAuthenticated()).thenReturn(false);
		MinimalRedditClient mrc = new MinimalRedditClient(client, getCredentials());
		assertFalse(mrc.isAuthenticated());
	}
}
