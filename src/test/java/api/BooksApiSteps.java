package api;

import io.qameta.allure.Step;
import models.AddBookModel;
import models.AuthModel;
import models.IsbnBookModel;
import models.response.BookArrayResponse;
import models.response.BooksListResponse;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static specs.ApiSpec.*;
import static utils.TestData.BOOKS_URL;

public class BooksApiSteps {

    @Step("API. Добавляем книгу")
    public void addBook(String isb, String token, String userId) {

        List<IsbnBookModel> books = new ArrayList<>();
        books.add(new IsbnBookModel(isb));

        AddBookModel bookData = new AddBookModel();
        bookData.setUserId(userId);
        bookData.setCollectionOfIsbns(books);
        given(requestSpec)
                .header("Authorization", "Bearer " + token)
                .body(bookData)
                .when()
                .post(BOOKS_URL)
                .then()
                .spec(getResponseSpecStatusCode(201))
                .extract().as(BooksListResponse.class);
    }

    @Step("API. Получаем список книг")
    public BookArrayResponse getBooks() {
        return given(requestSpec)
               .when()
               .get(BOOKS_URL)
               .then()
               .spec(getResponseSpecStatusCode(200))
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
        .spec(getResponseSpecStatusCode(204));
    }
}
