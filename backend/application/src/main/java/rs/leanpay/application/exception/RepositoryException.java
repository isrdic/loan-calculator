package rs.leanpay.application.exception;

@SuppressWarnings("unused")
public class RepositoryException extends Exception {

    public static final String ENTITY_NOT_FOUND = "{0} not found";

    public RepositoryException(final String pMessage) {
        super(pMessage);
    }

    public RepositoryException(final String pMessage, final Throwable throwable) {
        super(pMessage, throwable);
    }

    public RepositoryException(final Throwable throwable) {
        super(throwable);
    }
}
