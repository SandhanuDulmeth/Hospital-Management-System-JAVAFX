package model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Users {
    @NotEmpty(message = "Please enter a ID")
    private Integer id;
    @NotEmpty(message = "Please enter a name")
    private String name;
    @Email(message = "Please enter a valid email address")
    private String email;
    private String password;

    public Users(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public Users(Integer id) {
        this.id = id;
    }
}
