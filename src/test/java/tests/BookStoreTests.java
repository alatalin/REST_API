package tests;

import api.AuthApi;
import login.WithLogin;
import models.AuthModel;
import models.responce.BookArrayResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("api")
public class BookStoreTests extends TestBase {

    private static final int BOOK_INDEX = 0;

    @Test
    @DisplayName("Успешное удаление книги из списка профиля")
    @WithLogin
    void successDeleteBookFromProfileTest() {
        BookArrayResponse collection = booksApi.getBooks();
        AuthModel authResponse = new AuthApi().login();

        booksApi.deleteBooks(authResponse);

        String isbn = collection.getBooks()[BOOK_INDEX].getIsbn();
        String title = collection.getBooks()[BOOK_INDEX].getTitle();
        booksApi.addBook(isbn, authResponse.getToken(), authResponse.getUserId());

        profilePage
                .openPage()
                .verifyBookIsInList(title)
                .deleteBook()
                .verifyDeleteBook()
                .verifyBookIsNotInList(title);
    }
}
