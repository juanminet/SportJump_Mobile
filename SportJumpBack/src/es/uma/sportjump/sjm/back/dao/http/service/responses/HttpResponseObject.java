package es.uma.sportjump.sjm.back.dao.http.service.responses; 

public class HttpResponseObject {
    private String json;
    private int status = -1;
    
	public HttpResponseObject(String json, int status) {
		super();
		this.json = json;
		this.status = status;
	}
	
	
	public  HttpResponseObject(int status) {
		super();
		this.status = status;
	}


	public String getJson() {
		return json;
	}
	public void setJson(String json) {
		this.json = json;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}

    
}
