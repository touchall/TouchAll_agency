package allpointech.agency.network.http.exception;

public class RequestException extends Exception {
	private static final long serialVersionUID = -5566944619156007657L;

	public RequestException() {
		super();
	}

	public RequestException(String detailMessage, Throwable throwable) {
		super(detailMessage, throwable);
	}

	public RequestException(String detailMessage) {
		super(detailMessage);
	}

	public RequestException(Throwable throwable) {
		super(throwable);
	}
	


}
