package tests;

import api.AuthApiSteps;
import login.WithLogin;
import models.AuthModel;
import models.LoginModel;
import models.response.BookArrayResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static utils.TestData.loginModel;

@Tag("api")
public class BookStoreTests extends TestBase {

    private static final int BOOK_INDEX = 0;

    @Test
    @DisplayName("Успешное удаление книги из списка профиля")
    @WithLogin
    void successDeleteBookFromProfileTest() {
        BookArrayResponse collection = booksApiSteps.getBooks();
        AuthModel authResponse = AuthApiSteps.login(loginModel);

        booksApiSteps.deleteBooks(authResponse);

        String isbn = collection.getBooks()[BOOK_INDEX].getIsbn();
        String title = collection.getBooks()[BOOK_INDEX].getTitle();
        booksApiSteps.addBook(isbn, authResponse.getToken(), authResponse.getUserId());

        profilePage
                .openPage()
                .verifyBookIsInList(title)
                .deleteBook()
                .verifyDeleteBook()
                .verifyBookIsNotInList(title);
    }
}
