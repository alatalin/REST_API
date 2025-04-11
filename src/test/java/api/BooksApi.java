package api;

import io.qameta.allure.Step;
import models.AddBookModel;
import models.AuthModel;
import models.IsbnBookModel;
import models.responce.BookArrayResponse;
import models.responce.BooksListResponse;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static specs.ApiSpec.*;
import static utils.TestData.BOOKS_URL;

public class BooksApi {

    @Step("API. Добавляем книгу")
    public BooksListResponse addBook(String isb, String token, String userId) {

        List<IsbnBookModel> books = new ArrayList<>();
        books.add(new IsbnBookModel(isb));

        AddBookModel bookData = new AddBookModel();
        bookData.setUserId(userId);
        bookData.setCollectionOfIsbns(books);
        return given(requestSpec)
               .header("Authorization", "Bearer " + token)
               .body(bookData)
               .when()
               .post(BOOKS_URL)
               .then()
               .spec(responseSpecStatus201)
               .extract().as(BooksListResponse.class);
    }

    @Step("API. Получаем список книг")
    public BookArrayResponse getBooks() {
        return given(requestSpec)
               .when()
               .get(BOOKS_URL)
               .then()
               .spec(responseSpecStatus200)
               .extract().as(BookArrayResponse.class);
    }

    @Step("API. Удаляем все книги")
    public void deleteBooks(AuthModel loginResponse) {
        given(requestSpec)
        .header("Authorization", "Bearer " + loginResponse.getToken())
        .queryParam("UserId", loginResponse.getUserId())
        .when()
        .delete(BOOKS_URL)
        .then()
        .spec(responseSpecStatus204);
    }
}
