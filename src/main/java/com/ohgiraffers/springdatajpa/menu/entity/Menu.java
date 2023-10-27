package com.ohgiraffers.springdatajpa.menu.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;


@Setter
@Getter
@ToString
@Entity
@Table(name = "tbl_menu")
@SequenceGenerator(
        name = "seq_menu_code_generator",
        sequenceName = "seq_menu_code",
        allocationSize = 1

)
public class Menu {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "seq_menu_code_generator"
    )
    private int menuCode;
    private String menuName;
    private int menuPrice;
    private int categoryCode;
    private String orderableStatus;

    public Menu(){

    }
}
