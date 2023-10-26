package com.ohgiraffers.springdatajpa.menu.repository;

import com.ohgiraffers.springdatajpa.menu.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;




//db랑 연결 (내가 컨트롤하고싶은 대상 엔티티가 들어감,id 속성 pk에 해당하는 데이터타입 )
public interface MenuRepository  extends JpaRepository<Menu, Integer> { }
