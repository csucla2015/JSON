package de.vogella.android.json;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.GZIPInputStream;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class ReadJson extends Activity {
	/** Called when the activity is first created. */
	List<Cookie> cookies;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		//HttpClient httpclient = new DefaultHttpClient();
		String result="";
		 DefaultHttpClient httpClient = new DefaultHttpClient();
		 
		    HttpPost httpGet = new HttpPost("http://www.beatyourrecord.com/Services/Tournaments/Login/");//	"http://www.beatyourrecord.com/Services/Tournaments/Login?login=julia&password=1111");
		try {
		   
		    List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("Login", "justinpelton"));
            params.add(new BasicNameValuePair("password", "justin"));
            UrlEncodedFormEntity ent = new UrlEncodedFormEntity(params,HTTP.UTF_8);
            httpGet.setEntity(ent);
		    HttpResponse httpResponse = httpClient.execute(httpGet);
		    HttpEntity httpEntity = httpResponse.getEntity();
		    String output = EntityUtils.toString(httpEntity);
		    
			TextView view = (TextView) findViewById(R.id.result2);
			Log.v("here", output);
			view.setText(output);
			result=output;
			result=result.substring(30);
			int k = result.indexOf('"');
			result = result.substring(k+1);
			k=result.indexOf('"');
			result=result.substring(0,k);
		    System.out.println("Initial set of cookies:");
	        cookies = httpClient.getCookieStore().getCookies();
	        if (cookies.isEmpty()) {
	            System.out.println("None");
	        } else {//coment
	            for (int i = 0; i < cookies.size(); i++) {
	                System.out.println("- " + cookies.get(i).toString());
	            }
	        }
	        	String test = "http://www.beatyourrecord.com/Services/Tournaments/GetTournaments/?sessionId=778b8f1c-a947-4841-9bd5-17eef0e2b04c";
			
			TextView view1 = (TextView) findViewById(R.id.result1);
			view1.setText("Request being sent : " + test);
			Log.v("HERE", test);
		
			try {
				CookieStore cookieStore = new BasicCookieStore();
				cookieStore.addCookie(cookies.get(1));

				HttpContext localContext = new BasicHttpContext();
				localContext.setAttribute(ClientContext.COOKIE_STORE, cookieStore);
			    HttpClient client = new DefaultHttpClient();  
		       
		        HttpGet get = new HttpGet(test);
		        
		        HttpResponse responseGet = client.execute(get,localContext);  
		        HttpEntity resEntityGet = responseGet.getEntity();
		        Log.v("HERE2", test);
			    String output11 = EntityUtils.toString(resEntityGet);
				TextView view11 = (TextView) findViewById(R.id.result);
				view.setText(output11);
			}
			catch (Exception e) {
			    e.printStackTrace();
			}
			
		
		}
		catch (Exception e) {
		    e.printStackTrace();
		}
		
			
			
		
		
		
		
		
	}
	
}