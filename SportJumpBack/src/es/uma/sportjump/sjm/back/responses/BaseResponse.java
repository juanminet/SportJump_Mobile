package es.uma.sportjump.sjm.back.responses;

import es.uma.sportjump.sjm.back.types.ApplicationStatusType;

public class BaseResponse extends Response{

	private static final long serialVersionUID = 7742744905193950073L;
	
	private ApplicationStatusType statusType;

	public ApplicationStatusType getStatusType() {
		return statusType;
	}

	public void setStatusType(ApplicationStatusType statusType) {
		this.statusType = statusType;
	}	
}
