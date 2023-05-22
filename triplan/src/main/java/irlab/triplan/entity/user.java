package irlab.triplan.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "\"user\"")
@NoArgsConstructor
public class user {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer uid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "default_id")
    @JsonIgnore
    private userDefault default_id;

    @Column(name = "user_name")
    private String user_name;

    @Column(name = "access_token")
    private String access_token;
}
