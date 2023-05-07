package irlab.triplan.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "\"default\"")
public class userDefault {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "defalut_id")
    private Integer defalut_id;

    @Column(name = "default_path")
    private String default_path;
}
