import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.EntityBuilder;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.musicMaker.tfritzy.LogInHttpService.Application;
import com.musicMaker.tfritzy.LogInHttpService.DatabaseConnectionManager;
import com.musicMaker.tfritzy.LogInHttpService.Queries;

public class AddFriendIntegrationTests {
	
	@Autowired
	DatabaseConnectionManager connection;
	
	@Before
	public void start() {
		Application.main(null);
		
	}
	
	@Test
	public void testToAddFriend() throws Exception {
		JSONObject json = new JSONObject();
		json.put("username", "tfritzy");
		json.put("password", "pass");
		json.put("addFriend", "Bach");
		
		sendPost(json);
		
		assertEquals(Queries.doesRelationshipAlreadyExist("tfritzy", "Bach", connection), true);
	}
	
	
	// HTTP POST request
	private void sendPost(JSONObject postBody) throws Exception {
		
		
		String USER_AGENT = "Mozilla/5.0";
		
		String url = "http://localhost:8080/addFriend";
		
		HttpClient client = new DefaultHttpClient();
		

		HttpPost post = new HttpPost(url);

		
		HttpEntity entity = new ByteArrayEntity(postBody.toString().getBytes("UTF-8"));
        post.setEntity(entity);
		// add header
		post.setHeader("User-Agent", USER_AGENT);
		post.setHeader("Content-Type", "application/json");

		List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
		urlParameters.add(new BasicNameValuePair("sn", "C02G8416DRJM"));
		urlParameters.add(new BasicNameValuePair("cn", ""));
		urlParameters.add(new BasicNameValuePair("locale", ""));
		urlParameters.add(new BasicNameValuePair("caller", ""));
		urlParameters.add(new BasicNameValuePair("num", "12345"));

		post.setEntity(new UrlEncodedFormEntity(urlParameters));

		HttpResponse response = client.execute(post);
		System.out.println("\nSending 'POST' request to URL : " + url);
		System.out.println("Post parameters : " + post.getEntity());
		System.out.println("Response Code : " +
                                    response.getStatusLine().getStatusCode());

		BufferedReader rd = new BufferedReader(
                        new InputStreamReader(response.getEntity().getContent()));

		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}

		System.out.println(result.toString());

	}
}
