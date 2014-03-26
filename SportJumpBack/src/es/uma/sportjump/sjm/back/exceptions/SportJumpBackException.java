package es.uma.sportjump.sjm.back.exceptions;

public class SportJumpBackException extends RuntimeException{


	private static final long serialVersionUID = -994142164740270925L;
	
	public SportJumpBackException(){
		super();
	}
	
	public SportJumpBackException(String str){
		super(str);
	}
	
	public SportJumpBackException(Throwable t){
		super(t);
	}
	
	public SportJumpBackException(String str, Throwable t){
		super(str,t);
	}
	

}
