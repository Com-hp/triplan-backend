package irlab.triplan.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "\"group\"")
public class group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_id")
    private Integer group_id;

    @Column(name = "group_name")
    private String group_name;

    @Column(name = "group_code")
    private String group_code;

    @Column(name = "group_pw")
    private String group_pw;

    @Column(name = "group_path")
    private String group_path;

}

