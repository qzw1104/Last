package lfr.upc.edu.last;

public class DBAdapter {

    private static final String DB_NAME = "";
    private static final String USER_TABLE = "";
    private static final String BOOK_TABLE = "";
    private static final int DB_VERSION = 1;
    private static final String USER_NAME = "_id";
    private static final String USER_KEY = "key";
    private static final String USER_ID = "idd";


    private static final String BOOK_ID = "id";
    private static final String BOOK_NAME = "name";
    private static final String BOOK_WRITER = "writer";
    private static final String BOOK_PUBLISHER = "publisher";
    private static final int BOOK_TOTALNUMBER = 5;
    private static final int BOOK_LEAVENUMBER = 1;

    public int open() {

        return 1;
    }
}
