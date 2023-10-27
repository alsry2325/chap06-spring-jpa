package com.ohgiraffers.springdatajpa.menu.repository;

import com.ohgiraffers.springdatajpa.menu.entity.Category;
import com.ohgiraffers.springdatajpa.menu.entity.Menu;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


//db랑 연결 (내가 컨트롤하고싶은 대상 엔티티가 들어감,id 속성 pk에 해당하는 데이터타입 )
public interface MenuRepository  extends JpaRepository<Menu, Integer> {

    //전달 받은 menuPrice보다 큰 menuPrice를 가진 메뉴 목록 조회
    List<Menu> findByMenuPriceGreaterThan(Integer menuPrice);

    //전달 받은 menuPrice보다 큰 menuPrice를 가진 메뉴 목록을 메뉴 가격  오름차순으로 조회
    List<Menu> findByMenuPriceGreaterThanOrderByMenuPrice(Integer menuPrice);
    //전달 받은 menuPrice보다 큰 menuPrice를 가진 메뉴 목록을 메뉴 가격  내림차순으로 조회
    List<Menu> findByMenuPriceGreaterThan(Integer menuPrice , Sort sort);

    List<Menu> findByMenuNameContaining(String menuName);

    List<Menu> findByMenuPriceBetween(Integer menuPrice1, Integer menuPrice2);

    List<Menu> findByCategoryCode(Integer categoryCode);
}



