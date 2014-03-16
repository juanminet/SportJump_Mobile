package es.uma.sportjump.sjm.back.test;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.util.Random;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import android.app.Application;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.util.Log;
import android.widget.Toast;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import es.uma.sportjump.sjm.service.R;

@Singleton
public class FooService {
	@Inject Application application;
    @Inject Vibrator vibrator;
    @Inject Random random;

    public void say(String something) {
        // Make a Toast, using the current context as returned by the Context Provider
        Toast.makeText(application, "Astroboy says, \"" + something + "\"", Toast.LENGTH_LONG).show();
    }

    public void brushTeeth() {
        vibrator.vibrate(new long[]{0, 200, 50, 200, 50, 200, 50, 200, 50, 200, 50, 200, 50, 200, 50, 200, 50, 200, 50, 200, 50, 200, 50,  }, -1);
    }

    public String punch() {
        final String expletives[] = new String[]{"POW!", "BANG!", "KERPOW!", "OOF!"};
        return expletives[random.nextInt(expletives.length)];
    }
    
    public String apiTest(Handler handler){
    	RequestOperation rq = 	  new RequestOperation(handler);
    	
    	rq.execute("");
    	
    	return null;
    }
    
    private class RequestOperation extends AsyncTask<String, Void, String>{

    	public RequestOperation(Handler handler) {
			super();
			this.handler = handler;
		}

		Handler handler;
    	
    	
		@Override
		protected String doInBackground(String... params) {
			return doRequest();
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			Bundle data = new Bundle();
			data.putString("json", result);
			Message msg = new Message();
			msg.setData(data);
			
			handler.handleMessage(msg);
		}
		
		
    	
    }
    
    
    
    @SuppressWarnings("unused")
	public String doRequest(){
    	String username = "jumanji";
		String password = "yumiyumi";
		String host = "192.168.1.13";
		//String host = "10.70.0.165";		
		String port = "8080";
		String securePport = "8443";
		String domain = "/SportJumpWEB";
		String apiPath = "/rest";
		
		//https
		String secureUrlBasePath = "https://" + host  + ":" + securePport + domain + apiPath;
		String secureUrl = secureUrlBasePath + "/testservice";
		
		//http
		String noSecureUrlBasePath = "http://" + host  + ":" + port + domain + apiPath;
		String noSecureUurl = noSecureUrlBasePath + "/testservice";
		
		String url = secureUrl;		   
		Log.i("TAG", url);
		  
		HostnameVerifier hostnameVerifier = org.apache.http.conn.ssl.SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER;
       
		
		
		HttpParams httpParams = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(httpParams, 15000);
		HttpConnectionParams.setSoTimeout(httpParams, 15000);
		
        SSLSocketFactory socketFactory = SSLSocketFactory.getSocketFactory();
        socketFactory.setHostnameVerifier((X509HostnameVerifier) hostnameVerifier);
        
		SchemeRegistry schemeRegistry = new SchemeRegistry();
		schemeRegistry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 8080));
		schemeRegistry.register(new Scheme("https", customSSLSocketFactory(), 8443));
		
		HttpsURLConnection.setDefaultHostnameVerifier(hostnameVerifier);
		
		//create a pool connection manager
		ThreadSafeClientConnManager cm = new ThreadSafeClientConnManager(httpParams, schemeRegistry);
		
		//declare httpclient
		DefaultHttpClient httpclient = new DefaultHttpClient(cm, httpParams);    	
    	
    	httpclient.getCredentialsProvider().setCredentials(
    			new AuthScope(AuthScope.ANY_HOST, AuthScope.ANY_PORT),
                new UsernamePasswordCredentials(username, password)); 
    	Object content = null;
    	
    	try {        
	        HttpGet httpget = new HttpGet(url);
	        httpget.setHeader("Content-Type", "application/json");
	        
	        HttpResponse response;
	        response = httpclient.execute(httpget);
			//response = httpclient.execute(targetHost,httpget);
			//response =  httpclient.execute(targetHost, httpget, localContext);
			
	        HttpEntity entity = response.getEntity();
	        content = EntityUtils.toString(entity);
	        Log.d("TAG", "OK: " + content.toString());        
    	} catch (ClientProtocolException e) {			
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    	String res = (content != null)? content.toString() : null;
    	
    	return res;
    
    }
    
    
    private SSLSocketFactory customSSLSocketFactory() {
        try {         
            KeyStore trustedCertificates = KeyStore.getInstance("BKS");
            InputStream in = application.getResources().openRawResource(R.raw.sportjump);
            try {                
                trustedCertificates.load(in, "sportjump".toCharArray());
            } finally {
                in.close();
            }           
            SSLSocketFactory sslSocketFactory = new SSLSocketFactory(trustedCertificates);           
            sslSocketFactory.setHostnameVerifier(SSLSocketFactory.STRICT_HOSTNAME_VERIFIER);
            return sslSocketFactory;
        } catch (Exception e) {
            throw new AssertionError(e);
        }
    }
    	
    	
    	
    	
}
