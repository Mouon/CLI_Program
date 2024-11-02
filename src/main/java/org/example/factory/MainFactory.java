package org.example.factory;

import org.example.controller.CustomController;
import org.example.controller.HomeController;
import org.example.controller.MainController;
import org.example.service.CheckoutService;
import org.example.service.ProfileChangeService;
import org.example.service.book.BookManageServive;
import org.example.view.CustomView;
import org.example.view.HomeView;
import org.example.view.host.HostAddBookView;
import org.example.view.host.HostManageBookView;
import org.example.view.host.HostMyPageView;
import org.example.view.login.LogoutView;
import org.example.view.profileChange.PasswordChangeView;
import org.example.view.user.UserCheckoutView;
import org.example.view.user.UserMyPageView;
import org.example.controller.*;
import org.example.file.BlackListFileManager;
import org.example.file.BookFileManager;
import org.example.file.CheckoutFileManager;
import org.example.file.UserFileManager;
import org.example.service.user.LoginService;
import org.example.service.user.RegisterService;
import org.example.service.validater.ValidationService;
import org.example.view.*;

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
     * @return MainController 인스턴스
     */
    public MainController mainController() {
        return new MainController(controllerList());
    }

    /**
     * 모든 커스텀 컨트롤러의 리스트를 생성하고 반환합니다.
     * @return CustomController 객체들의 List
     */
    public List<CustomController> controllerList() {
        List<CustomController> controllers = new ArrayList<>();
        controllers.add(HomeController());
        controllers.add(registerController());
        controllers.add(loginController());
        controllers.add(userController());
        controllers.add(hostController());
        controllers.add(profileChangeController());
        return controllers;
    }

    /**
     * ====== Controllers ======
     */

    /**
     * HomeController 인스턴스를 생성하고 반환합니다.
     * @return HomeController 인스턴스
     */
    public HomeController HomeController() {
        return new HomeController(HomeViewList());
    }
    public RegisterController registerController() {
        return new RegisterController(RegisterList());
    }
    public LoginController loginController(){
        return new LoginController(LoginList());
    }
    public UserController userController(){
        return new UserController(UserList());
    }
    public HostController hostController(){
        return new HostController(HostList());
    }
    public ProfileChangeController profileChangeController(){
        return new ProfileChangeController(ProfileChangeList());
    }
    /**
     * ====== VIEW -> LIST ======
     */

    /**
     * Home 관련 뷰들의 리스트를 생성하고 반환합니다.
     * @return CustomView 객체들의 List
     */
    public List<CustomView> HomeViewList() {
        List<CustomView> HomeViewArray = new ArrayList<>();
        // view 추가 시작
        HomeViewArray.add(homeView());
        // view 추가 종료
        return HomeViewArray;
    }

    public List<CustomView> RegisterList(){
        List<CustomView> registerViewArray = new ArrayList<>();
        // view 추가 시작
        registerViewArray.add(registerView());
        registerViewArray.add(userRegisterView());
        registerViewArray.add(hostRegisterView());
        // view 추가 종료

        return registerViewArray;
    }

    public List<CustomView> LoginList(){
        List<CustomView> loginViewArray = new ArrayList<>();
        // view 추가 시작
        loginViewArray.add(memberLoginView());
        loginViewArray.add(userDateView());
        loginViewArray.add(hostDateView());
        loginViewArray.add(userLoginView());
        loginViewArray.add(hostLoginView());
        loginViewArray.add(logoutView());
        // view 추가 종료

        return loginViewArray;
    }

    public List<CustomView> UserList(){
        List<CustomView> userViewArray = new ArrayList<>();
        // view 추가 시작
        userViewArray.add(userMenuView());
        userViewArray.add(userMyPageView());
        userViewArray.add(userCheckoutView());
        // view 추가 종료

        return userViewArray;
    }
    public List<CustomView> HostList(){
        List<CustomView> hostViewArray = new ArrayList<>();
        // view 추가 시작
        hostViewArray.add(hostMenuView());
        hostViewArray.add(hostMyPageView());
        hostViewArray.add(hostManageBookView());
        hostViewArray.add(hostAddBookView());
        // view 추가 종료

        return hostViewArray;
    }

    public List<CustomView> ProfileChangeList(){
        List<CustomView> profileChangeViewArray = new ArrayList<>();
        //view 추가 시작
        profileChangeViewArray.add(passwordChangeView());
        //view 추가 종료

        return profileChangeViewArray;
    }

    /**
     * ===== SERVICE =====
     */
    public ValidationService validationService(){
        return new ValidationService();
    }

    public RegisterService registerService(){
        return new RegisterService(userFileManager());
    }

    public LoginService loginService(){
        return new LoginService(userFileManager());
    }

    public ProfileChangeService profileChangeService(){
        return new ProfileChangeService(userFileManager());
    }

    public CheckoutService checkoutService(){
        return new CheckoutService(userFileManager(),checkoutFileManager(),bookFileManager());
    }

    public BookManageServive bookManageService(){
        return new BookManageServive(bookFileManager());
    }

    /**
     * ====== VIEWS ======
     */
    public HomeView homeView() {
        return new HomeView(validationService());
    }
    // 회원가입 관련 뷰 모음
    public RegisterView registerView(){
        return new RegisterView(validationService());
    }
    public UserRegisterView userRegisterView(){
        return new UserRegisterView(validationService(),registerService());
    }
    public HostRegisterView hostRegisterView(){
        return new HostRegisterView(validationService(),registerService());
    }

    // 로그인 관련 뷰 모음
    public MemberLoginView memberLoginView(){
        return new MemberLoginView(validationService());
    }
    public UserDateView userDateView(){
        return new UserDateView(validationService());
    }
    public HostDateView hostDateView(){
        return new HostDateView(validationService());
    }
    public UserLoginView userLoginView(){
        return new UserLoginView(validationService(), loginService());
    }
    public HostLoginView hostLoginView(){
        return new HostLoginView(validationService(), loginService());
    }

    //로그아웃 관련 뷰
    public LogoutView logoutView() { return new LogoutView(); }

    //유저 메뉴 관련 뷰
    public UserMenuView userMenuView(){
        return new UserMenuView(validationService()); // 여기에 무언가가 필요하다면 추가되어야함
    }
    public UserMyPageView userMyPageView() {
        return new UserMyPageView(validationService()); //이후에 필요하면 parameter 추가
    }
    public UserCheckoutView userCheckoutView(){
        return new UserCheckoutView(validationService(),checkoutService());
    }

    //호스트 메뉴 관련 뷰
    public HostMenuView hostMenuView(){
        return new HostMenuView(validationService()); // 여기에 무언가가 필요하다면 추가되어야함
    }
    public HostMyPageView hostMyPageView() {
        return new HostMyPageView(validationService());//이후에 필요하면 parameter 추가
    }
    public HostManageBookView hostManageBookView(){
        return new HostManageBookView(validationService());
    }
    public HostAddBookView hostAddBookView(){
        return new HostAddBookView(validationService(),bookManageService());
    }
    
    //프로필 정보 변경 관련 뷰
    public PasswordChangeView passwordChangeView(){
        return new PasswordChangeView(validationService(), profileChangeService());
    }


    /**
     * ====== FileManager ======
     */

    /**
     * @description UserFileManager 인스턴스를 생성하고 반환합니다.
     * @return UserFileManager 인스턴스
     */
    public UserFileManager userFileManager(){
        return new UserFileManager();
    }

    /**
     * @description CheckoutFileManager 인스턴스를 생성하고 반환합니다.
     * @return CheckoutFileManager 인스턴스
     */
    public CheckoutFileManager checkoutFileManager(){
        return new CheckoutFileManager();
    }

    /**
     * @description BookFileManager 인스턴스를 생성하고 반환합니다.
     * @return BookFileManager 인스턴스
     */
    public BookFileManager bookFileManager(){
        return new BookFileManager();
    }

    /**
     * @description BlackListFileManager 인스턴스를 생성하고 반환합니다.
     * @return BlackListFileManager 인스턴스
     */
    public BlackListFileManager blackListFileManager(){
        return new BlackListFileManager();
    }


}