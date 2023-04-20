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
    private Integer id;

    @Column(name = "group_name")
    private String name;

    @Column(name = "group_code")
    private String code;

    @Column(name = "group_pw")
    private String pw;

    @Column(name = "group_path")
    private String path;

}

