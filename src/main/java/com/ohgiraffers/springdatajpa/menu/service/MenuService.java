package com.ohgiraffers.springdatajpa.menu.service;


import com.ohgiraffers.springdatajpa.menu.dto.MenuDTO;
import com.ohgiraffers.springdatajpa.menu.entity.Menu;
import com.ohgiraffers.springdatajpa.menu.repository.MenuRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class MenuService {


    //의존성 주입
    private  final MenuRepository menuRepository;

    private  final ModelMapper modelMapper;


    public  MenuService(MenuRepository menuRepository,ModelMapper modelMapper){
        this.menuRepository = menuRepository;
        this.modelMapper=modelMapper;
    }


    //엔터티변경을 막고자 비영속객체로 데이터타입선언
    public MenuDTO findMenuByCode(int menuCode) {
        
        //Entity로 조회한 뒤 비영속 객체인 MenuDTO로 변환해서 반환한다
        //findById를 사용시 optinal로 반환
        //optinal클래스란? 만약 널일경우 여러가지 취급할수있는 메소드도 함께만든 클래스
        Menu menu = menuRepository.findById(menuCode).orElseThrow(IllegalAccessError::new);

        //modelMapper가 없을경우
        //MenuDTO menuDTO = new MenuDTO();
        // menuDTO.setMenuCode(menu.getMenuCode());
        //modelMapper라이브러리 : 객체의 필드 값을 다른 객체의 필드 값으로 자동으로 맵핑
        //DTO form에서 입력한 데이터를 DB에 저장하는 경우 DTO -> Entity 변환 과정이 필요하며, DB에서 데이터를 조회하여 form 형태로 보여주는 경우 Entity -> DTO 변환 과정이 필요
        return modelMapper.map(menu,MenuDTO.class); //menu엔티티 값을 MenuDTO 값으로 옮겨담음
    }
}
