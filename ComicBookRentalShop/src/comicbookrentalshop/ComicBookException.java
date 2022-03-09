package comicbookrentalshop;

/**
 *
 * @author Pham Nhat Quang CE170036
 */
public class ComicBookException extends Exception {

    /**
     * Create new ComicBookException
     *
     * @param message
     */
    public ComicBookException(String message) {
        super(message);
    }

    @Override
    public String toString() {
        return "ComicBookException: " + super.getMessage();
    }
}
