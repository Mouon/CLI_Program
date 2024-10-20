package org.example.factory;

import org.example.Main;
import org.example.controller.CustomController;
import org.example.controller.HomeController;
import org.example.controller.MainController;
import org.example.view.CustomView;
import org.example.view.HomeView;
import java.util.ArrayList;
import java.util.List;

/**
 * @class MainFactory
 * @description 팩토리 클래스
 *
 * 이 클래스는 애플리케이션의 주요 구성 요소들을 생성하고 관리합니다.
 * 모든 서비스, 컨트롤러, 뷰는 이 팩토리에 등록되어야 합니다.
 *
 * @note 구현 객체가 변경될 경우 이 클래스를 수정해야 합니다.
 */
public class MainFactory {

    /**
     * 메인 컨트롤러를 생성하고 반환합니다.
     *
     * @return MainController 인스턴스
     */
    public MainController mainController() {
        return new MainController(controllerList());
    }

    /**
     * 모든 커스텀 컨트롤러의 리스트를 생성하고 반환합니다.
     *
     * @return CustomController 객체들의 List
     */
    public List<CustomController> controllerList() {
        List<CustomController> controllers = new ArrayList<>();
        controllers.add(HomeController());
        return controllers;
    }

    /**
     * ====== Controllers ======
     */

    /**
     * HomeController 인스턴스를 생성하고 반환합니다.
     *
     * @return HomeController 인스턴스
     */
    public HomeController HomeController() {
        return new HomeController(HomeViewList());
    }

    /**
     * ====== VIEW -> LIST ======
     */

    /**
     * Home 관련 뷰들의 리스트를 생성하고 반환합니다.
     *
     * @return CustomView 객체들의 List
     */
    public List<CustomView> HomeViewList() {
        List<CustomView> HomeViewArray = new ArrayList<>();
        // view 추가 시작
        HomeViewArray.add(homeView());
        // view 추가 종료
        return HomeViewArray;
    }

    /**
     * ====== VIEWS ======
     */

    /**
     * HomeView 인스턴스를 생성하고 반환합니다.
     *
     * @return HomeView 인스턴스
     */
    public HomeView homeView() {
        return new HomeView();
    }
}