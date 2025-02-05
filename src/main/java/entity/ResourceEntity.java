package entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class ResourceEntity {

    private Integer id;
    private String type;
    private String name ;
    private String status ;
    private Integer allocatedTo ;

    public ResourceEntity(String type, String name, String status, Integer allocatedTo) {
        this.type = type;
        this.name = name;
        this.status = status;
        this.allocatedTo = allocatedTo;
    }
}
