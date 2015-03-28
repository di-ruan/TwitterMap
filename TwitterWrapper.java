import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.TwitterException;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.conf.ConfigurationBuilder;
import com.mongodb.DB;
import com.mongodb.DBCollection;

/**
 * The main program to get the data from twitter by calling twitter API and store those tweets in the MongoDB collection
 */
public final class TwitterWrapper {
	/**
	 * @param args
	 */
	public static void main(String[] args) throws TwitterException {
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true)
				.setOAuthConsumerKey("px6y14zKyN61PmSPLNYQsEKqZ")
				.setOAuthConsumerSecret(
						"w6cMvW1GLFotQC6MZDg219iBnf6DXMO6zZqvrjT3pChCdHWzZi")
				.setOAuthAccessToken(
						"2843768163-F15vwR4U6wCDHxcJP77KND1T0hwo3uGFzajn8zT")
				.setOAuthAccessTokenSecret(
						"ChnTa4CZunA43Eu1HXL8OExbKyo3DHTlp1CVBpFBpx7tx");

		TwitterStream twitterStream = new TwitterStreamFactory(cb.build())
				.getInstance();
		StatusListener listener = new StatusListener() {

			@Override
			public void onStatus(Status status) {
				String userName = status.getUser().getScreenName();
				String text = status.getText();
				// if (text.indexOf(keyword) != -1) {
				if (status.getGeoLocation() != null) {
					twitter4j.GeoLocation loc = status.getGeoLocation();
					System.out.println("@" + userName + " - "
							+ status.getText());
					System.out.println("Date: " + status.getCreatedAt());
					System.out.println("id: " + status.getId());
					System.out.println("text: " + text);
					System.out.println("Location:" + loc);
					Tweet tw = new Tweet(userName, status.getCreatedAt(),
							status.getId(), text,loc.getLongitude(), loc.getLatitude());
					try {
						MongoWrapper.addToDB(tw.getDBObject());
					} catch (Exception e) {
						e.printStackTrace();
					}
				}

			}

			@Override
			public void onDeletionNotice(
					StatusDeletionNotice statusDeletionNotice) {
				// System.out.println("Got a status deletion notice id:" +
				// statusDeletionNotice.getStatusId());
			}

			@Override
			public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
				System.out.println("Got track limitation notice:"
						+ numberOfLimitedStatuses);
			}

			@Override
			public void onScrubGeo(long userId, long upToStatusId) {
				System.out.println("Got scrub_geo event userId:" + userId
						+ " upToStatusId:" + upToStatusId);
			}

			@Override
			public void onStallWarning(StallWarning warning) {
				System.out.println("Got stall warning:" + warning);
			}

			@Override
			public void onException(Exception ex) {
				ex.printStackTrace();
			}
		};
		twitterStream.addListener(listener);
		twitterStream.sample();
	}
}
