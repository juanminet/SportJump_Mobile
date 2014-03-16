package es.uma.sportjump.sjm.back.dao.http.client;


import org.apache.http.client.HttpClient;
import org.apache.http.params.HttpParams;

public interface HttpState {
	/**
	 * Return the instance of httpclient stored
	 * @return HttpClient instance of httpclient stored
	 */
	public HttpClient getHttpClient();	
	
	/**
	 * Return the instance of httpParams stored
	 * @return HttpParams instance of httpParams stored
	 */
	public HttpParams getHttpParams();

}
