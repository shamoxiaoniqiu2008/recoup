package util;

public class CustomException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4438674046789259658L;
	
	/**
	 * @Description:
	 * @param:
	 * @return:
	 * @throws:
	 */
	public CustomException() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @Description:
	 * @param:
	 * @return:
	 * @throws:
	 */
	public CustomException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @Description:
	 * @param:
	 * @return:
	 * @throws:
	 */
	public CustomException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @Description:
	 * @param:
	 * @return:
	 * @throws:
	 */
	public CustomException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

}
