package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;
import org.apache.tomcat.jni.Address;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Entity
@SequenceGenerator(name = "seq_member_id", sequenceName = "seq_member_id", initialValue = 1, allocationSize = 1)
@Getter
@Setter
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_member_id")
    @Column(name = "member_id")
    private Long id;

    @NotBlank
    private String name;

    @Embedded
    private Address address;

    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>();

    private Member(String name) {
        this.name = name;
    }

    public static Member fromName(String name) {
        return new Member(name);
    }
}