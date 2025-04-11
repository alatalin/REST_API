package models;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AddBookModel {
    String userId;
    List<IsbnBookModel> collectionOfIsbns;
}
