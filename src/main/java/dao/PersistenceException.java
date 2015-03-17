package dao;

public class PersistenceException extends RuntimeException {
	private static final long serialVersionUID = -3963294121191776156L;
	
	public PersistenceException(Throwable t) {
		super(t);
	}
	
	public PersistenceException(String message) {
		super(message);
	}
	
	public PersistenceException() {
		super();
	}
	
	public PersistenceException(String message, Throwable t) {
		super(message, t);
	}

}
