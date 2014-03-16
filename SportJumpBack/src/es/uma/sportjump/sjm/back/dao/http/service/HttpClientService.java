package es.uma.sportjump.sjm.back.dao.http.service;


import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import android.util.Log;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import es.uma.sportjump.sjm.back.dao.http.client.HttpManager;
import es.uma.sportjump.sjm.back.dao.http.client.HttpState;
import es.uma.sportjump.sjm.back.dao.http.service.responses.HttpResponseObject;


/**
 * Execute the Httpclient against the server
 */
@Singleton
public class HttpClientService {
	
	@Inject HttpManager httpManager;

    private static final String TAG = "[" + HttpClientService.class.getCanonicalName() + "]";


    /**
     * Get method in order to obtain info from server via REST Web Service.
     *
     * @param serverUri Server Uri
     * @return ServerResponse object with response fields of server
     * @throws ClientProtocolException in case of bad communication protocol between server and client
     */
    public HttpResponseObject executeHttpClientGet(String serverUri, String user, String password) throws IOException {        
       
        HttpResponse httpResponse = newHttpClientInstance(serverUri, user, password).execute(newHttpGet(serverUri));
     
        return getServerResponseObjectFromHttpResponse(httpResponse);
    }    


   
    /**
     * Returns the HttpGet instance for the given Uri
     *
     * @param resourceUri
     * @return HttpGet
     */
    private HttpGet newHttpGet(String resourceUri) {
        HttpGet httpGet = new HttpGet(resourceUri);
        //httpGet.setHeader("Connection", "keep-alive");
        return httpGet;
    }

   


    /**
     * Returns a new HttpParams with the conexion parameters
     *
     * @return HttpParams
     */
    private static HttpParams newHttpParams() {
        HttpParams httpParams = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(httpParams, 15000);
        HttpConnectionParams.setSoTimeout(httpParams, 15000);

        return httpParams;
    }

    /**
     * Returns the HttpState instance for the fiven url, user and password
     *
     * @param url
     * @param user
     * @param password
     * @return HttpState
     */
    private HttpState updateHttpState(String url, String user, String password) {
        return httpManager.getHttpState(url, user, password, newHttpParams());
    }


    /**
     * Returns the HttpClient instance for the given url, user and password
     *
     * @param url
     * @param user
     * @param password
     * @return HttpClient
     */
    private HttpClient newHttpClientInstance(String url, String user, String password) {
        return updateHttpState(url, user, password).getHttpClient();
    }

   
    
    /**
     * Transforms Server httResponse into a ServerResponseObject
     *
     * @param httpResponse
     * @return ServerResponseObject
     * @throws IOException
     */
    private HttpResponseObject getServerResponseObjectFromHttpResponse(HttpResponse httpResponse) throws IOException {
        HttpEntity entity = httpResponse.getEntity();
        String response = EntityUtils.toString(entity);
        
        Log.i(TAG, "Server response -----> " + response);
        return new HttpResponseObject(response,httpResponse.getStatusLine().getStatusCode());
    }
     
    
    
   
}
