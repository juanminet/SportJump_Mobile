package es.uma.sportjump.sjm.back.service.types;

import android.annotation.SuppressLint;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpStatus;

public enum ApplicationStatusType {

	STATUS_OK (HttpStatus.SC_OK),
	STATUS_UNAUTHORIZED (HttpStatus.SC_UNAUTHORIZED),
	STATUS_INTERNAL_SERVER_ERROR (HttpStatus.SC_INTERNAL_SERVER_ERROR),
	STATUS_IO_ERROR(600);
	
	private Integer code;

	private ApplicationStatusType(Integer code) {
		this.code = code;
	}
	
	@SuppressLint("UseSparseArrays") private static final Map<Integer, ApplicationStatusType> REVERSE = new HashMap<Integer, ApplicationStatusType>();
	
	static{
		for(ApplicationStatusType u : ApplicationStatusType.values()){
			REVERSE.put(u.code, u);
		}
	}
	
	public static ApplicationStatusType fromCode(Integer code){
		ApplicationStatusType type = REVERSE.get(code);
		return type;
	}

	public Integer getCode() {
		return code;
	}	
}
