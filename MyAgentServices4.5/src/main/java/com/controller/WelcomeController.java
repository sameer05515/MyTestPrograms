package com.controller;

import com.basic.common.access.AccessPoint;
import com.ils.ibatis.user.UserIbatis;
import com.pojo.UserPojo;
import com.service.UserService;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.util.List;

@Controller
@SessionAttributes("name")
public class WelcomeController {

    @GetMapping("/")
    public String getAllUsers() {

        SqlSession session = AccessPoint.getDBTemplate().openSession();
        List<UserPojo> list = null;
        try {
            UserIbatis mapper = session.getMapper(UserIbatis.class);
            list = mapper.getAll();
        } catch (Exception e) {
            session.rollback();
        } finally {
            session.close();
        }

        System.out.println(list);

//        return "Hello " + (list != null && !list.isEmpty() ? list.size() : 0);
        return "redirect:login";
    }

    @Autowired
    UserService userService;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String showLoginPage(ModelMap model) {
        List<UserPojo> list= userService.getAllUsers();
        model.put("allUsers", list);
        return "login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String showWelcomePage(ModelMap model,
                                  @RequestParam String name, @RequestParam String password) {
        boolean isValidUser = userService.validateUser(name, password);
        if (!isValidUser) {
            model.put("errorMessage", "Invalid Credentials");
            return "login";
        }
        model.put("name", name);
        model.put("password", password);
        return "welcome";
    }

//    @RequestMapping(value = {"/", "/welcome**"}, method = RequestMethod.GET)
//    public ModelAndView defaultPage() {
//
//        ModelAndView model = new ModelAndView();
//        model.addObject("title", "Spring Security Login Form - Database Authentication");
//        model.addObject("message", "This is default page!");
//        model.setViewName("hello");
//        return model;
//
//    }
//
//
//    @RequestMapping(value = "/admin**", method = RequestMethod.GET)
//    public ModelAndView adminPage() {
//
//        ModelAndView model = new ModelAndView();
//        model.addObject("title", "Spring Security Login Form - Database Authentication");
//        model.addObject("message", "This page is for ROLE_ADMIN only!");
//        model.setViewName("admin");
//
//        return model;
//
//    }
//
//    @RequestMapping(value = "/logoutAction", method = RequestMethod.POST)
//    public void logout(@RequestParam(value = "error", required = false) String error,
//                       @RequestParam(value = "logout", required = false) String logout, HttpServletResponse response, HttpServletRequest request) throws IOException {
//
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//
//        HttpSession session = request.getSession(false);
//        LogoutLoggingBeforeModel logoutLoggingBeforeModel = new LogoutLoggingBeforeModel();
//        logoutLoggingBeforeModel.setLogoutSessionId(session.getId());
//        logoutLoggingBeforeModel.setLogoutUserId(authentication.getName());
//        Integer loginLoggingIdPkTemp = (Integer) session.getAttribute("loginLoggingIdPk");
//
//        String loginLoggingIdPk = loginLoggingIdPkTemp.toString();
//        logoutLoggingBeforeModel.setLoginLoggingIdPk(loginLoggingIdPk);
//        // Calculate Current Logout Time
//        java.util.Date currentDate = new java.util.Date();
//        java.sql.Timestamp timestampDate = new java.sql.Timestamp(currentDate.getTime());
//        String loginCurrentTime = timestampDate.toString(); //
//
//        logoutLoggingBeforeModel.setLogoutCurrentTime(loginCurrentTime);
//
//        LogoutLoggingService logoutLoggingService = new LogoutLoggingServiceImpl();
//
//        logoutLoggingService.insertLogoutLoggingBeforeInformation(logoutLoggingBeforeModel);
//        handleLogOutResponseCookie(request, response);
//        response.sendRedirect("j_spring_security_logout");
//
//    }
//
//    @RequestMapping(value = "/login", method = RequestMethod.GET)
//    public ModelAndView login(@RequestParam(value = "error", required = false) String error,
//                              @RequestParam(value = "redirect", required = false) String redirect, @RequestParam(value = "logout", required = false) String logout, @RequestParam(value = "sessionExpired", required = false) String sessionExp, HttpServletRequest request, HttpServletResponse response) {
//        HttpSession session = request.getSession(false);
//
//        ModelAndView model = new ModelAndView();
//        if (error != null) {
//            model.addObject("error", "Invalid username and password!");
//        }
//
//        if (logout != null) {
//            if (session == null) {
//                session = request.getSession();
//            }
//
//            session.invalidate();
//            System.out.println(" ******************************   Session ID : " + session.getId());
//
//            handleLogOutResponseCookie(request, response);
//            model.addObject("msg", "You've been logged out successfully.");
//            System.out.println("----------------------------------------------------------");
//        }
//        if (redirect != null) {
//            session.invalidate();
//            System.out.println(" ******************************   Session ID : " + session.getId());
//
//            handleLogOutResponseCookie(request, response);
//            model.addObject("msg", "");
//            System.out.println("----------------------------------------------------------");
//        }
//        if (sessionExp != null) {
//            // session.invalidate();
//            //HttpSession session = request.getSession(false);
//            //System.out.println(" ******************************   Session ID : "+session.getId());
//            model.addObject("msg", "Your session has been expired.");
//        }//rediect
//
//        model.setViewName("login");
//
//        return model;
//
//    }
//
//    private static void handleLogOutResponseCookie(HttpServletRequest request, HttpServletResponse response) {
//        System.out.println("____________________________________________________");
//        Cookie[] cookies = request.getCookies();
//        if (cookies != null) {
//            System.out.println("Before delete       " + cookies.length);
//            for (Cookie cookie : cookies) {
//                System.out.println(cookie.getName() + "----" + cookie.getValue() + "---" + cookie.getPath());
//                cookie.setMaxAge(0);
//                cookie.setValue(null);
//                cookie.setPath("/");
//                response.addCookie(cookie);
//            }
//            Cookie[] cookiesl = request.getCookies();
//            System.out.println("BeforeA delete       " + cookies.length);
//            if (cookiesl != null) {
//                for (Cookie cookie : cookiesl) {
//                    System.out.println(cookie.getName() + "----" + cookie.getValue() + "---" + cookie.getPath());
//                }
//            }
//            System.out.println("After delete       " + cookies.length);
//        }
//
//    }
//
//    //for 403 access denied page
//    @RequestMapping(value = "/403", method = RequestMethod.GET)
//    public ModelAndView accesssDenied1() {
//
//        ModelAndView model = new ModelAndView();
//
//        //check if user is login
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        if (!(auth instanceof AnonymousAuthenticationToken)) {
//            UserDetails userDetail = (UserDetails) auth.getPrincipal();
//            System.out.println(userDetail);
//
//            model.addObject("username", userDetail.getUsername());
//
//        }
//
//        model.setViewName("403");
//        return model;
//
//    }
//
//    //for browser not support
//    @RequestMapping(value = "/browserNotSupportAction", method = RequestMethod.GET)
//    public String browserNotSupport(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        boolean browserEligibilityStatus = false;
//        browserEligibilityStatus = checkBrowserEligibilityAtServerSide(request, response);
//
//        if (browserEligibilityStatus == false) {
//            return "browserNotSupport";
//        } else {
//            return "redirect:logincheck";
//        }
//
//    }
//
//    private boolean checkBrowserEligibilityAtServerSide(HttpServletRequest request, HttpServletResponse response) {
//
//        String userAgent = request.getHeader("user-agent");
//        // 1. IE
//        if (userAgent.contains("Trident/") && userAgent.contains("rv:")) {
//
//            String strVersion = userAgent.substring(userAgent.lastIndexOf("rv:") + 3, userAgent.lastIndexOf("rv:") + 5);
//
//            try {
//
//                if (strVersion.contains(".")) {
//                    strVersion = strVersion + 0;
//                }
//
//                float ieBrowserVersionTemp = Float.valueOf(strVersion);
//                int ieBrowserVersion = (int) ieBrowserVersionTemp;
//
//                System.out.println("ieBrowserVersion : " + ieBrowserVersion);
//
//                if (ieBrowserVersion >= 11) {
//
//                    return true;
//                } else {
//                    return false;
//                }
//            } catch (Exception e) {
//                return false;
//            }
//        } // 2. Opera Browser
//        else if (userAgent.contains("OPR/")) {
//
//            String strVersion = userAgent.substring(userAgent.lastIndexOf("OPR/") + 4, userAgent.lastIndexOf("OPR/") + 6);
//
//            try {
//                if (strVersion.contains(".")) {
//                    strVersion = strVersion + 0;
//                }
//
//                float operaBrowserVersionTemp = Float.valueOf(strVersion);
//                int operaBrowserVersion = (int) operaBrowserVersionTemp;
//
//                if (operaBrowserVersion >= 20) {
//                    return true;
//                } else {
//                    return false;
//                }
//            } catch (Exception e) {
//                return false;
//            }
//        } // 3. Chrome or Chromium Browser
//        else if (userAgent.contains("Chrome/") && !(userAgent.contains("OPR/"))) {
//
//            String strVersion = userAgent.substring(userAgent.lastIndexOf("Chrome/") + 7, userAgent.lastIndexOf("Chrome/") + 9);
//
//            try {
//                if (strVersion.contains(".")) {
//                    strVersion = strVersion + 0;
//                }
//
//                float chromeBrowserVersionTemp = Float.valueOf(strVersion);
//                int chromeBrowserVersion = (int) chromeBrowserVersionTemp;
//
//                if (chromeBrowserVersion >= 30) {
//                    return true;
//                } else {
//                    return false;
//                }
//            } catch (Exception e) {
//                return false;
//            }
//        } // 4. Mozila Firefox
//        else if (userAgent.contains("Firefox/")) {
//
//            String strVersion = userAgent.substring(userAgent.lastIndexOf("Firefox/") + 8, userAgent.lastIndexOf("Firefox/") + 10);
//            try {
//                if (strVersion.contains(".")) {
//                    strVersion = strVersion + 0;
//                }
//
//                float firefoxBrowserVersionTemp = Float.valueOf(strVersion);
//                int firefoxBrowserVersion = (int) firefoxBrowserVersionTemp;
//
//                if (firefoxBrowserVersion >= 30) {
//                    return true;
//                } else {
//                    return false;
//                }
//            } catch (Exception e) {
//                return false;
//            }
//        } // 5. Safari
//        else if (userAgent.contains("AppleWebKit/") && userAgent.contains("Safari/") && !(userAgent.contains("Firefox/")) || !(userAgent.contains("Chrome/"))) {
//
//            String strVersion = userAgent.substring(userAgent.lastIndexOf("Version/") + 8, userAgent.lastIndexOf("Version/") + 10);
//
//            try {
//                if (strVersion.contains(".")) {
//                    strVersion = strVersion + 0;
//                }
//
//                float safariBrowserVersionTemp = Float.valueOf(strVersion);
//                int safariBrowserVersion = (int) safariBrowserVersionTemp;
//
//                if (safariBrowserVersion >= 6) {
//                    return true;
//                } else {
//                    return false;
//                }
//            } catch (Exception e) {
//                return false;
//            }
//        }
//
//        return false;
//    }
//
//    @RequestMapping(value = "/iitdlogin", method = RequestMethod.GET)
//    public ModelAndView iitdlogin(@RequestParam(value = "code", required = true) String code,
//                                  @RequestParam(value = "state", required = true) String state,
//                                  HttpServletRequest request, HttpServletResponse response) {
//
//        ModelAndView model = new ModelAndView();
//        model.addObject("state", state);
//        model.addObject("code", code);
//        model.setViewName("loginiitd");
//        return model;
//    }
}
