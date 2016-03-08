package us.thedavis.armbot.reddit;

import net.dean.jraw.http.NetworkException;
import net.dean.jraw.http.oauth.OAuthException;

public interface RedditClientAPI {
	
	public void authenticate() throws NetworkException, OAuthException;
	
	public void deauthenticate();
	
	public boolean isAuthenticated();
}
