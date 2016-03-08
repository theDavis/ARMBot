package us.thedavis.armbot.reddit;

import com.google.inject.Inject;

import net.dean.jraw.RedditClient;
import net.dean.jraw.http.NetworkException;
import net.dean.jraw.http.oauth.Credentials;
import net.dean.jraw.http.oauth.OAuthException;

public class MinimalRedditClient implements RedditClientAPI {
	
	private RedditClient client;
	private Credentials credentials;
	
	@Inject
	MinimalRedditClient(RedditClient client, Credentials credentials){
		this.client = client;
		this.credentials = credentials;
	}

	@Override
	public void authenticate() throws NetworkException, OAuthException {
		client.authenticate(client.getOAuthHelper().easyAuth(credentials));
	}

	@Override
	public void deauthenticate() {
		client.getOAuthHelper().revokeAccessToken(credentials);
		client.deauthenticate();
	}

	@Override
	public boolean isAuthenticated() {
		return client.isAuthenticated();
	}
}
