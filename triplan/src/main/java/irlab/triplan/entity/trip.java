package irlab.triplan.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "\"trip\"")
@NoArgsConstructor
public class trip {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "trip_id")
    private Integer t_id;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id", foreignKey = @ForeignKey(name = "trip_FK"))
    private group tGroup;

    @Column(name = "trip_name")
    private String t_name;

    @Column(name = "trip_path")
    private String t_path;

    @Column(name = "start_date")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private LocalDate s_date;

    @Column(name = "end_date")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private LocalDate e_date;
}
