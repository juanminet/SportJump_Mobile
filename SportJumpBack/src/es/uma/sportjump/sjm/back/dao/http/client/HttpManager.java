package es.uma.sportjump.sjm.back.dao.http.client;



import java.io.InputStream;
import java.security.KeyStore;

import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.HttpParams;

import android.app.Application;
import android.util.Log;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import es.uma.sportjump.sjm.service.R;

@Singleton
public class HttpManager {
	@Inject Application application;
	
	/**
	 * HttpState store data about url, user, password, params and one instance of httpclient previously used
	 * @author usuario
	 *
	 */
	@SuppressWarnings("unused")
	private class HttpStateImpl implements HttpState {
		private String url;
		private String user;
		private String password;
		private HttpParams httpParams;
		private HttpClient httpclient;


		private HttpStateImpl() {
			url = null;
			user = null;
			password = null;
			httpParams = null;
			httpclient = null;
		}

		private HttpStateImpl(String url, String user, String password, HttpParams params) {
			super();
			this.url = url;
			this.user = user;
			this.password = password;
			this.httpParams = params;
		}

		private String getUrl() {
			return url;
		}

		private void setUrl(String url) {
			this.url = url;
		}

		private String getUser() {
			return user;
		}

		private void setUser(String user) {
			this.user = user;
		}

		private String getPassword() {
			return password;
		}


		private void setPassword(String password) {
			this.password = password;
		}

		public HttpClient getHttpClient() {
			return httpclient;
		}

		private void setHttpclient(HttpClient httpclient) {
			this.httpclient = httpclient;
		}

		public HttpParams getHttpParams() {
			return httpParams;
		}

		private void setHttpParams(HttpParams httpParams) {
			this.httpParams = httpParams;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result	+ ((password == null) ? 0 : password.hashCode());
			result = prime * result + ((url == null) ? 0 : url.hashCode());
			result = prime * result + ((user == null) ? 0 : user.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			HttpStateImpl other = (HttpStateImpl) obj;

			if (password == null) {
				if (other.password != null)
					return false;
			} else if (!password.equals(other.password))
				return false;

			if (user == null) {
				if (other.user != null)
					return false;
			} else if (!user.equals(other.user))
				return false;
			return true;
		}


	}

	private  final String TAG = "[HttpManager]";

	private  HttpStateImpl state = null;

	public HttpManager() {
		state = null;
	}

	/**
	 * Returns a instance of HttpState for the given parameters received
	 * If the paramenters received are the same it has the instance of httpclient stored,
	 * this method returns the same instance of httpclient, but returns a new instance of httpclient
	 * @param url
	 * @param user
	 * @param password
	 * @param params
	 * @return HttpState instance of HttpState for the given parameters received
	 */
	public HttpState getHttpState(String url, String user, String password, HttpParams params) {
		HttpStateImpl stateAux = new HttpStateImpl(url, user, password, params);

	
		if ((state == null) || (!state.equals(stateAux))){


			//Register schema, http & https
			//the socket factory EasySSLSocketFactory manage self-signed certificates
	        SchemeRegistry schemeRegistry = new SchemeRegistry();
	        schemeRegistry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 8080));
	        schemeRegistry.register(new Scheme("https", customSSLSocketFactory(), 8443));

	        //create a pool connection manager
	        ThreadSafeClientConnManager cm = new ThreadSafeClientConnManager(params, schemeRegistry);

	        //declare httpclient
	        DefaultHttpClient client = new DefaultHttpClient(cm, params);

	        //put credentials and scope
	        UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(user, password);

	        client.getCredentialsProvider().setCredentials(
	        		AuthScope.ANY,
	        		credentials);

	        stateAux.setHttpclient(client);



	        state = stateAux;
	        Log.d(TAG,"HTTPCLIENT---- New HttpClient created");

		}else{
            state.setUrl(url);
			stateAux = state;
			 Log.d(TAG," HTTPCLIENT---- Old HttpClient selected");
		}

		return stateAux;
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
