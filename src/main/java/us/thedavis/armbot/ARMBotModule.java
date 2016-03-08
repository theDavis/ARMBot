package us.thedavis.armbot;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import com.google.inject.name.Names;

import net.dean.jraw.RedditClient;
import net.dean.jraw.http.UserAgent;
import net.dean.jraw.http.oauth.Credentials;
import us.thedavis.armbot.annotatedbindings.BotAppID;
import us.thedavis.armbot.annotatedbindings.BotPlatform;
import us.thedavis.armbot.annotatedbindings.BotVersion;
import us.thedavis.armbot.reddit.MinimalRedditClient;
import us.thedavis.armbot.reddit.RedditClientAPI;

public class ARMBotModule extends AbstractModule {

	@Override
	protected void configure() {
		try{
			Properties props = new Properties();
			props.load(new FileReader("config.properties"));
			Names.bindProperties(binder(), props);
			bind(String.class).annotatedWith(BotPlatform.class).toInstance("desktop");
			bind(String.class).annotatedWith(BotAppID.class).toInstance("ARMBot");
			bind(String.class).annotatedWith(BotVersion.class).toInstance("0.0.1");
			bind(RedditClientAPI.class).to(MinimalRedditClient.class);
		} catch (IOException ex) {
			//
		}
	}
	
	@Provides
	UserAgent provideUserAgent(@BotPlatform String platform,
							   @BotAppID String appid,
							   @BotVersion String version,
							   @Named("reddit-user") String user){
		return UserAgent.of(platform, appid, version, user);
	}
	
	@Provides
	Credentials provideCredentials(@Named("reddit-user") String user,
								   @Named("reddit-pass") String pass,
								   @BotAppID String appid,
								   @Named("reddit-secret") String secret){
		return Credentials.script(user, pass, appid, secret);
	}
	
	@Provides @Singleton
	RedditClient provideRedditClient(UserAgent agent){
		return new RedditClient(agent);
	}

}
