package allpointech.agency.network.http.exception;

public class ParsingException extends Exception {

	private static final long serialVersionUID = -3111076013515881441L;

	public ParsingException() {
		super();
	}

	public ParsingException(String detailMessage, Throwable throwable) {
		super(detailMessage, throwable);
	}

	public ParsingException(String detailMessage) {
		super(detailMessage);
	}

	public ParsingException(Throwable throwable) {
		super(throwable);
	}
	


}
