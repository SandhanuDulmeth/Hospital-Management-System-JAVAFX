package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Resource {
    private Integer id;
    private String type;
    private String name;
    private String status;
    private Integer allocatedTo;

    public Resource(String type, String name, String status, Integer allocatedTo) {
        this.type = type;
        this.name = name;
        this.status = status;
        this.allocatedTo = allocatedTo;
    }
}
