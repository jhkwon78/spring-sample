package com.exmybatis;

import com.exmybatis.dao.MyDAO;
import com.exmybatis.domain.User;
import com.exmybatis.service.MyService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.sql.SQLException;
import java.util.*;


@Controller
@PropertySource("classpath:some.properties")
public class MainController {
    protected Logger logger = Logger.getLogger(this.getClass());

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private Environment environment;

    @Autowired
    private MyService myService;

    @Autowired
    private MyDAO myDAO;

    @Value("${hello.msg:default}")
    private String helloMsg;

    @RequestMapping(value = "/*", method = RequestMethod.GET)
    public ResponseEntity<String> pageNotFound() {
        return new ResponseEntity<String>("Page not found", HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        return "login";
    }

    @RequestMapping(value="/welcome", method = RequestMethod.GET)
    public String welcome(Locale locale) {
        logger.debug(locale);
        logger.debug(this.helloMsg);
        String helloMsg = environment.getProperty("hello.msg");
        String curMsg = messageSource.getMessage("greeting.msg", null, "default", locale);
        String engMsg = messageSource.getMessage("greeting.msg", null, Locale.US);
        return "welcome";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(HttpSession session, ModelMap modelMap,
                        @Valid User user, BindingResult bindingResult, Locale locale) throws Exception {

        logger.debug(user);
        logger.debug(locale);

        if(bindingResult.hasErrors()){
            logger.debug("Binding Result has error!");
            List<ObjectError> errors = bindingResult.getAllErrors();
            for(ObjectError error : errors){
                logger.debug(error.getDefaultMessage());
            }

            return "login";
        }

        if (user.getUserPassword().equalsIgnoreCase("admin")) {
            modelMap.addAttribute("email", user.getEmailAddress());
            session.setAttribute("email", user.getEmailAddress());
            return "redirect:/welcome";
        }

        return "redirect:/pages/login_error.html";
    }

    @RequestMapping(value = "/getSuperAdmin", method = RequestMethod.GET, produces="text/plain;charset=UTF-8")
    public @ResponseBody String getSuperAdmin() throws SQLException {
        String superAdmin = myDAO.getSuperAdmin("SA");
        logger.info(superAdmin);
        return superAdmin;
    }

    @RequestMapping(value = "/adminUserNameList", method = RequestMethod.GET, produces="text/plain;charset=UTF-8")
    public @ResponseBody List<String> getAdminUserNameList() throws SQLException {
        List<String> adminUserNameList = myDAO.getAdminUserNameList();
        logger.info(adminUserNameList);
        return adminUserNameList;
    }

    @RequestMapping(value = "/allUserNameList", method = RequestMethod.GET, produces="text/plain;charset=UTF-8")
    public @ResponseBody List<String> getAllUserNameList() throws SQLException {
        List<String> allUserNameList = myDAO.getAllUserNameList();
        return allUserNameList;
    }

    @RequestMapping(value = "/updateUserName", method = RequestMethod.GET, produces="text/plain;charset=UTF-8")
    public @ResponseBody List<String> updateUserName() throws SQLException {
        myDAO.updateAdminName();
        return myDAO.getAllUserNameList();
    }

    @RequestMapping(value = "/find", method = RequestMethod.GET, produces="text/plain;charset=UTF-8")
    public @ResponseBody List<String> find() throws SQLException {

        List<String> userEmailList = myDAO.findAdminEMail();
        logger.info(userEmailList);
        return userEmailList;
    }

    @RequestMapping(value = "/user", method = RequestMethod.GET, produces="text/plain;charset=UTF-8")
    public @ResponseBody List<User> user() throws SQLException {
        List<User> allUsers = myDAO.getAllUser();
        return allUsers;
    }

    @RequestMapping(value = "/pagingUser", method = RequestMethod.GET, produces="text/plain;charset=UTF-8")
    public @ResponseBody List<User> pagingUser() throws SQLException {
        List<User> pagingUsers = myDAO.getPagingUserList(2, 3);
        logger.info(pagingUsers);
        return pagingUsers;
    }

    @RequestMapping(value = "/saveAdmin", method = RequestMethod.GET, produces="text/plain;charset=UTF-8")
    public @ResponseBody String sa() throws Exception {

        myService.saveAdmins();

        return "err";
    }
}

