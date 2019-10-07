package BoardGameGeek;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeTest;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.testng.annotations.AfterTest;
import Lib.Base;
import Lib.webBased;
import Lib.apiBased;

public class languageDependencePollTest {
  @Test
  public void languageDependenceValidation() throws InterruptedException, ClientProtocolException, UnsupportedOperationException, IOException {
	  
	  webBased.signIn();
	  webBased.openRandomGameInUserCollections();
	  apiBased.ApiRequest("gameGeekItem", Base.objectId);
	  Base.GetPollId();
	  apiBased.ApiRequest("gameGeekPoll", Base.pollId);
	  Base.GetExpectedLanguage();
	  webBased.languageDependenceValidation(Base.expectedLanguage);
  }
  
  @BeforeTest
  public void beforeTest() throws InterruptedException {
	  Base.loadConfig();
	  webBased.openBrowser();  
  }

  @AfterTest
  public void afterTest() {
	  
	  webBased.teardown();
  }

}
