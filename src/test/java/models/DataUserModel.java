package models;

import lombok.Data;

@Data
public class DataUserModel {

    private int id;
    private String email, first_name, last_name, avatar;
}
