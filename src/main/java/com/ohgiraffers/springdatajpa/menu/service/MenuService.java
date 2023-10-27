package com.ohgiraffers.springdatajpa.menu.service;


import com.ohgiraffers.springdatajpa.menu.dto.CategoryDTO;
import com.ohgiraffers.springdatajpa.menu.dto.MenuDTO;
import com.ohgiraffers.springdatajpa.menu.entity.Category;
import com.ohgiraffers.springdatajpa.menu.entity.Menu;
import com.ohgiraffers.springdatajpa.menu.repository.CategoryRepository;
import com.ohgiraffers.springdatajpa.menu.repository.MenuRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MenuService {


    //의존성 주입
    private  final MenuRepository menuRepository;

    private  final CategoryRepository categoryRepository;

    private  final ModelMapper modelMapper;


    public  MenuService(MenuRepository menuRepository, CategoryRepository categoryRepository,ModelMapper modelMapper){
        this.menuRepository = menuRepository;
        this.modelMapper=modelMapper;
        this.categoryRepository =categoryRepository;
    }


    //엔터티변경을 막고자 비영속객체로 데이터타입선언  Id로  entity 조회: findById
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


    //모든 entity 조회 findAll
    public List<MenuDTO> findMenuList(){
                                                    //정렬해서 보여줌
     List<Menu> menuList = menuRepository.findAll(Sort.by("menuCode").descending());
    //배열이나 컬렉션요소들을 순차적으로 보여주기위해 stream()그안에
     return menuList.stream().map(menu -> modelMapper.map(menu,MenuDTO.class)).collect(Collectors.toList());
    }
        //페이징처리
    public Page<MenuDTO> findMenuList(Pageable pageable){

        pageable = PageRequest.of(pageable.getPageNumber() <=0 ?0 : pageable.getPageNumber() - 1,
                pageable.getPageSize(),
                Sort.by("menuCode").descending());

        Page<Menu> menuList =  menuRepository.findAll(pageable);

        return menuList.map(menu -> modelMapper.map(menu,MenuDTO.class));
    }
    //검색된 가격초과된 메뉴 보여주기
    public List<MenuDTO> findMenuPrice(Integer menuPrice) {

        //List<Menu> menuList = menuRepository.findByMenuPriceGreaterThan(menuPrice);
       // List<Menu> menuList = menuRepository.findByMenuPriceGreaterThanOrderByMenuPrice(menuPrice);
        List<Menu> menuList = menuRepository.findByMenuPriceGreaterThan(menuPrice,Sort.by("menuPrice").descending());
        return menuList.stream().map(menu -> modelMapper.map(menu, MenuDTO.class)).collect(Collectors.toList());
    }

    /* 4.JPQL or Native  Query Test*/
    public  List<CategoryDTO> findAllCategory(){

        List<Category> categoryList = categoryRepository.findAllCategory();
        return categoryList.stream().map(category -> modelMapper.map(category,CategoryDTO.class))
                .collect(Collectors.toList());
    }

    /* 5.Entity 저장*/
    @Transactional
    public void registNewMenu(MenuDTO menu) {
        //Entity형식으로 저장
        menuRepository.save(modelMapper.map(menu,Menu.class));
    }

    /*6. Entity 수정 */
    @Transactional
    public void modifyMenu(MenuDTO menu) {

        Menu foundMenu = menuRepository.findById(menu.getMenuCode()).orElseThrow(IllegalAccessError::new);
        foundMenu.setMenuName(menu.getMenuName());
    }

    @Transactional
    public void deleteMenu(Integer menuCode) {
            menuRepository.deleteById(menuCode);

    }

    public List<MenuDTO> serchmenuName(String menuName) {

        List<Menu> menuList =  menuRepository.findByMenuNameContaining(menuName);

        return menuList.stream().map(menu -> modelMapper.map(menu, MenuDTO.class)).collect(Collectors.toList());
    }

    public List<MenuDTO> serchmenuPrice(Integer menuPrice1, Integer menuPrice2) {

        List<Menu> menuList =  menuRepository.findByMenuPriceBetween(menuPrice1,menuPrice2);
        return menuList.stream().map(menu -> modelMapper.map(menu, MenuDTO.class)).collect(Collectors.toList());
    }



    public List<MenuDTO> serchCategoryCode(Integer categoryCode) {
        List<Menu> menuList =  menuRepository.findByCategoryCode(categoryCode);

        return menuList.stream().map(menu -> modelMapper.map(menu, MenuDTO.class)).collect(Collectors.toList());
    }
}
