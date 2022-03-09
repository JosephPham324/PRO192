package comicbookrentalshop;

/**
 *
 * @author Pham Nhat Quang CE170036
 */
public class ComicBook {

    private int ID;
    private String Title;
    private double bookRentalPrice;
    private String Author;
    private int Volume;

    /**
     * Get ID of book
     *
     * @return
     */
    public int getID() {
        return ID;
    }

    /**
     * Get title of book
     *
     * @return
     */
    public String getTitle() {
        return Title;
    }

    /**
     * Get this book's rental price
     *
     * @return
     */
    public double getBookRentalPrice() {
        return bookRentalPrice;
    }

    /**
     * Get name of author
     *
     * @return
     */
    public String getAuthor() {
        return Author;
    }

    /**
     * Get volume value
     *
     * @return
     */
    public int getVolume() {
        return Volume;
    }

    /**
     * Set book ID
     *
     * @param ID
     */
    public void setID(int ID) {
        this.ID = ID;
    }

    /**
     * Set title for book
     *
     * @param Title
     * @throws comicbookrentalshop.ComicBookException
     */
    public void setTitle(String Title) throws ComicBookException {
        if (Title.equals("")) {
            throw new ComicBookException("Title can't be empty");
        }
        this.Title = Title;
    }

    /**
     * Set rental price
     *
     * @param bookRentalPrice
     */
    public void setBookRentalPrice(double bookRentalPrice) {
        this.bookRentalPrice = bookRentalPrice;
    }

    /**
     * Set author name
     *
     * @param Author
     */
    public void setAuthor(String Author) {
        this.Author = Author;
    }

    /**
     * Set volume
     *
     * @param Volume
     */
    public void setVolume(int Volume) {
        this.Volume = Volume;
    }

    /**
     * Create new ComicBook
     *
     * @param ID
     * @param Title
     * @param bookRentalPrice
     * @param Author
     * @param Volume
     * @throws comicbookrentalshop.ComicBookException
     */
    public ComicBook(int ID, String Title, double bookRentalPrice, String Author, int Volume) throws ComicBookException {
        this.setID(ID);
        this.setTitle(Title);
        this.setBookRentalPrice(bookRentalPrice);
        this.setAuthor(Author);
        this.setVolume(Volume);
    }

    /**
     * Create new ComicBook
     */
    public ComicBook() {
    }
}
