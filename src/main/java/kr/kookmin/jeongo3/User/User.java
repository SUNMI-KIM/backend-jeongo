package kr.kookmin.jeongo3.User;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

@Getter
@Setter
@Entity
public class User {

    @Id
    @GeneratedValue(strategy=GenerationType.UUID)
    @Column(name = "USER_ID")
    private String id;

    @Enumerated(EnumType.STRING)
    private UserRole userRole;

    @Column
    private String name;

    @Column
    private String email;

    @Column
    private String gender;

    @Column
    private int age;

    @Column
    private String loginId;

    @Column
    private String password;

    @Column
    private String phoneNum;

    @Column
    private String univ; // 희망 대학, 재학 대학

    @Column
    private String department; // 대학 과, 희망 과

    @Column
    private String DISC; // DISC 테스트 결과

    @Column
    private String image;

    @Column
    @ColumnDefault("0")
    private int report;

    @Column
    @ColumnDefault("0")
    private int point;
}
