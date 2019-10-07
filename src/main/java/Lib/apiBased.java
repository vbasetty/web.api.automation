package Lib;

import Lib.Base;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;

public class apiBased extends Base{


	public static void ApiRequest(String API,int ID) throws ClientProtocolException, IOException, UnsupportedOperationException {
		HttpGet request;
		if(API.contentEquals("gameGeekItem"))
		{
			request = new HttpGet(getConfig(API)+ "&objectid="+ ID +"&objecttype=thing");
		}
		else {
			request = new HttpGet(getConfig(API)+ "&pollid=" + ID);
		}
		HttpClient client = HttpClientBuilder.create().build();
		HttpResponse response = client.execute(request);
		BufferedReader br = null;
		PrintWriter pw = null;
		try {
			br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		String line = "";
		StringBuffer sb = new StringBuffer();
		while((line=br.readLine())!=null)
		{
			sb.append(line);
		}
		try 
		{		
			if(API.contentEquals("gameGeekItem"))
			{
				pw = new PrintWriter(projectLocation+"\\src\\main\\resources\\ResponseFile\\BoardGameGeekItem.html");	
				System.out.println("Storing User Data to BoardGameGeekItem Html file");
			}
			else {

				pw = new PrintWriter(projectLocation+"\\src\\main\\resources\\ResponseFile\\BoardGameGeekPoll.html");	
				System.out.println("Storing Poll Data to BoardGameGeekPoll html file");
			}
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		finally 
		{
			if (pw!=null) {

				pw.write(sb.toString());
				pw.close();
				pw.flush();
			}
		}


	}
}

