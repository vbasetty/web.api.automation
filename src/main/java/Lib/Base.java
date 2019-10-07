package Lib;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.WebDriver;


public class Base {

	static String projectLocation = System.getProperty("user.dir");
	static String browser,gameGeekItem,gameGeekPoll;
	static WebDriver driver;
	public static int objectId;
	public  static int pollId;
	public static String expectedLanguage;
	static Properties propConfig = null;
	static Properties propTestData = null;

	public static void loadConfig(){
		propConfig = new Properties();
		InputStream in = null;
		try{
			in = new FileInputStream(projectLocation + "\\config.properties");
			propConfig.load(in);
		}catch(IOException e){
			e.printStackTrace();
		}finally{
			if(in != null){
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}
	}

	public static String getConfig(String itemName){
		return propConfig.getProperty(itemName);
	}

	public static String GetExpectedLanguage() {

		// get the html file to parse its content
		File htmlFile = new File(projectLocation + "\\src\\main\\resources\\ResponseFile\\BoardGameGeekPoll.html");
		try {
			// parse the html file
			Document doc = Jsoup.parse(htmlFile, "UTF-8");
			Elements tableBody = doc.getElementsByTag("tbody");
			expectedLanguage = findMessageWithMaxVotes(tableBody);
			System.out.println("This is the Expected Language dependency which has max votes:" + expectedLanguage);

		} catch (IOException e) {
			e.printStackTrace();
		}
		return expectedLanguage;
	}

	public static int GetPollId() {

		File htmlFile = new File(projectLocation + "\\src\\main\\resources\\ResponseFile\\BoardGameGeekItem.html");
		try {
			// parse the html file
			Document doc = Jsoup.parse(htmlFile, "UTF-8");
			Element pollValue = doc.getElementById("pollid");
			String value = pollValue.attr("value");
			pollId= Integer.parseInt(value);
			System.out.println("PollId of the Random Game Selected is:" + pollId);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return pollId;
	}

	public static String findMessageWithMaxVotes(Elements elements) {
		Elements messages=null;
		Elements votes=null;
		for(Element element: elements) {
			messages=element.getElementsByClass("rowlabel");
			votes=element.getElementsByClass("votes");
		}
		List<Integer> allVotes= new ArrayList<Integer>();
		for(int index=0; index<votes.size(); index++) {
			allVotes.add(Integer.parseInt(votes.get(index).text()));
		}
		int numberOfVotes= allVotes.indexOf(Collections.max(allVotes));
		if (numberOfVotes == 0) {
			return ("(no votes)");
		}
		else {		
			return messages.get(allVotes.indexOf(Collections.max(allVotes))).text();
		}
	}

	public static int getObjectId(String Url) {
		if(Url.contains("://")){
			Url = Url.substring(Url.indexOf("://")+3);
			Url = Url.substring(Url.indexOf("/") + 1);
			Url = Url.substring(Url.indexOf("/") + 1);
		} else {
			Url = Url.substring(Url.indexOf("/")+1);
		}
		Url = Url.substring(0,Url.lastIndexOf('/'));
		objectId = Integer.parseInt(Url);
		return objectId;
	}
}
