package org.consume.com.user.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.consume.com.user.model.UserModel;
import org.consume.com.user.service.UserInterface;
import org.consume.com.util.redirect.RedirectUtil;
import org.consume.com.util.resultJson.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@RestController
@RequestMapping("/people")
public class PeopleController {

    @Autowired
    private UserInterface service;

    /**
     * init
     *
     * @param pageNow Integer
     * @return ModelAndView
     */
    @RequiresPermissions(value = "people:init")
    @HystrixCommand(fallbackMethod = "init_error")
    @GetMapping("/init")
    public ModelAndView init(@RequestParam(value = "pageNow") Integer pageNow,
                             @RequestParam(value = "pageSize") Integer pageSize) {
        Map<Integer, Object> map = service.findAllPage(pageNow, pageSize);
        return new ModelAndView("/people/index")
                .addObject("page", map.get(1))
                .addObject("pages", map.get(2))
                .addObject("pageNum", map.get(3));
    }

    public ModelAndView init_error(Integer pageNow, Integer pageSize) {
        return new ModelAndView("/people/index")
                .addObject("page", null)
                .addObject("pages", 1)
                .addObject("pageNum", 1);
    }

    /**
     * 个人资料
     *
     * @param id
     * @return
     */
    @HystrixCommand(fallbackMethod = "id_error")
    @RequiresAuthentication
    @GetMapping("/account/{id}")
    public ModelAndView getById(@PathVariable("id") String id) {
        ResponseResult result = service.getById(id);
        return new ModelAndView("/user/index")
                .addObject("model", result);
    }

    public ModelAndView id_error(String id) {
        return new ModelAndView("/user/index")
                .addObject("model", null);
    }

    /**
     * 跳转修改密码页面
     *
     * @return ModelAndView
     */
    @RequiresAuthentication
    @GetMapping("/toPassword")
    public ModelAndView toPassword() {
        return new ModelAndView("/people/password");
    }

    /**
     * 修改密码
     *
     * @param password  String
     * @param password1 String
     * @param idNumber  String
     * @return ModelAndView
     */
    @HystrixCommand(fallbackMethod = "password_error")
    @RequiresAuthentication
    @PostMapping("/password")
    public ModelAndView password(@RequestParam("password") String password,
                                 @RequestParam("password1") String password1,
                                 @RequestParam("idNumber") String idNumber) {
        Subject subject = SecurityUtils.getSubject();
        UserModel user = (UserModel) subject.getSession().getAttribute("user");
        ResponseResult<UserModel> responseResult = service.upPWD(password, password1, idNumber, user.getUuid());
        if (responseResult.isSuccess())
            return new ModelAndView(RedirectUtil.getRddirect() + "/people/passwordOK");
        else {
            String error = "";
            if (responseResult.getErrorcode().equals("501"))
                error = "两次输入的密码不一致";
            else if (responseResult.getErrorcode().equals("502"))
                error = "用户未找到";
            else if (responseResult.getErrorcode().equals("503"))
                error = "身份验证错误";
            else if (responseResult.getErrorcode().equals("500"))
                error = "数据库操作失败";
            return new ModelAndView("/people/password")
                    .addObject("error", error);
        }
    }

    public ModelAndView password_error(String password, String password1, String idNumber) {

        return new ModelAndView("/people/password")
                .addObject("error", "接口断开");
    }


    /**
     * 修改密码成功，重定向
     *
     * @return ModelAndView
     */
    @RequiresAuthentication
    @GetMapping("/passwordOK")
    public ModelAndView passwordOK() {
        return new ModelAndView("/people/passwordOK");
    }


    /**
     * 删除账户
     *
     * @param id String
     * @return ModelAndView 重定向 /people/init/0
     */
    @HystrixCommand(fallbackMethod = "del_error")
    @RequiresPermissions("people:del")
    @GetMapping("/del")
    public ResponseResult del(@RequestParam("id") String id) {
        ResponseResult responseResult = service.del(id);
        return responseResult;
    }

    public ResponseResult del_error(String id) {
        ResponseResult responseResult = new ResponseResult();
        responseResult.setSuccess(false);
        responseResult.setErrorcode("404");
        responseResult.setMessage("接口断开");
        return responseResult;
    }

//    @Autowired
//    private UserService service;
//
//    @Autowired
//    private StructureService structureService;
//
//    @Autowired
//    private RegisterService registerService;
//
//    /**
//     * init
//     *
//     * @param pageNow Integer
//     * @return ModelAndView
//     */
//    @RequiresPermissions(value = "people:init")
//    @GetMapping("/init/{pageNow}")
//    public ModelAndView init(@PathVariable(value = "pageNow", required = false) Integer pageNow) {
//        Page<UserModel> page = service.findAllPage(pageNow, 5);
//        return new ModelAndView("/people/index")
//                .addObject("page", page);
//    }
//
//    /**
//     * 跳转修改密码页面
//     *
//     * @return ModelAndView
//     */
//    @RequiresAuthentication
//    @GetMapping("/toPassword")
//    public ModelAndView toPassword() {
//        return new ModelAndView("/people/password");
//    }
//
//    /**
//     * 修改密码
//     *
//     * @param password  String
//     * @param password1 String
//     * @param idNumber  String
//     * @return ModelAndView
//     */
//    @RequiresAuthentication
//    @PostMapping("/password")
//    public ModelAndView password(@RequestParam("password") String password,
//                                 @RequestParam("password1") String password1,
//                                 @RequestParam("idNumber") String idNumber) {
//        String error = "";
//        if (password.equalsIgnoreCase(password1)) {
//            Subject subject = SecurityUtils.getSubject();
//            UserModel user = (UserModel) subject.getSession().getAttribute("user");
//            if (idNumber.equalsIgnoreCase(user.getIdNumber())) {
//                boolean isMatch = Pattern.matches("^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,16}$", password);
//                if (isMatch) {
//                    String pwd = Base64Util.encode(password);
//                    service.upPWD(pwd, user.getAccount());
//                    return new ModelAndView(RedirectUtil.getRddirect() + "/people/passwordOK");
//                } else {
//                    error = Htmls.SPAN("red", "密码由字母和数字组成,且长度为8-16（包含）");
//                }
//            } else {
//                error = Htmls.SPAN("red", "验证信息有误，请确认");
//                return new ModelAndView("/people/password")
//                        .addObject("error", error);
//            }
//        } else {
//            error = Htmls.SPAN("red", "两次输入的密码不一致");
//        }
//        return new ModelAndView("/people/password")
//                .addObject("error", error);
//    }
//
//    /**
//     * 修改密码成功，重定向
//     *
//     * @return ModelAndView
//     */
//    @RequiresAuthentication
//    @GetMapping("/passwordOK")
//    public ModelAndView passwordOK() {
//        return new ModelAndView("/people/passwordOK");
//    }
//
//    /**
//     * 删除账户
//     *
//     * @param id String
//     * @return ModelAndView 重定向 /people/init/0
//     */
//    @RequiresPermissions("people:del")
//    @GetMapping("/del/{id}")
//    public ModelAndView del(@PathVariable("id") String id) {
//        service.del(id);
//        return new ModelAndView(RedirectUtil.getRddirect() + "/people/init/0");
//    }
//
//    /**
//     * 重置密码
//     *
//     * @param id
//     * @param account
//     * @return
//     */
//    @RequiresPermissions("people:resetting")
//    @GetMapping("/resetting/{id}/{account}")
//    public ModelAndView resetting(@PathVariable("id") String id, @PathVariable("account") String account) {
//        String pwd = Base64Util.encode("mimachushihua123");
//        int i = service.resetting(id, pwd);
//        if (i > 0)
//            return new ModelAndView(RedirectUtil.getRddirect() + "/people/init/0")
//                    .addObject("error", account + "密码初始化成功");
//        return new ModelAndView(RedirectUtil.getRddirect() + "/people/init/0")
//                .addObject("error", account + "密码初始化失败");
//    }
//
//    /**
//     * 调岗-此功能延后
//     *
//     * @param id String
//     * @return ModelAndView
//     */
//    @RequiresPermissions(value = "people:toPosts")
//    @GetMapping("/toPosts/{id}")
//    public ModelAndView toPosts(@PathVariable("id") String id) {
////        UserModel model = service.getById(id);
//        return null;
//    }
//
//    /**
//     * 跳转修改页面
//     *
//     * @param id String
//     * @return ModelAndView
//     */
//    @RequiresPermissions(value = "people:update")
//    @GetMapping("/toUpdate/{id}")
//    public ModelAndView toUpdate(@PathVariable("id") String id) {
//        UserModel model = service.getById(id);
//        List<StructureModel> dw = structureService.getByParent(model.getCompany());
//        List<StructureModel> bm = structureService.getEqual(model.getDepartment());
//        List<StructureModel> zw = structureService.getEqual(model.getPosition());
//        return new ModelAndView("/people/update")
//                .addObject("model", model)
//                .addObject("dw", dw)
//                .addObject("bm", bm)
//                .addObject("zw", zw);
//    }
//
//    /**
//     * 修改
//     *
//     * @param model UserModel
//     * @return ModelAnView
//     */
//    @RequiresPermissions(value = "people:update")
//    @PostMapping("/update")
//    public ModelAndView update(@Validated({UPDATE.class}) @ModelAttribute("model") UserModel model, BindingResult result) {
//        if (result.hasErrors()) {
//            List<StructureModel> dw = structureService.getByParent(model.getCompany());
//            List<StructureModel> bm = structureService.getEqual(model.getDepartment());
//            List<StructureModel> zw = structureService.getEqual(model.getPosition());
//            return new ModelAndView("/people/update")
//                    .addObject("model", model)
//                    .addObject("dw", dw)
//                    .addObject("bm", bm)
//                    .addObject("zw", zw)
//                    .addObject("error",
//                            result.getFieldError().getDefaultMessage());
//        }
//        int i = service.put2(model);
//        if (i > 0)
//            return new ModelAndView(RedirectUtil.getRddirect() + "/people/init/0")
//                    .addObject("error", Htmls.SPAN("green", "资料修改成功"));
//        List<StructureModel> dw = structureService.getByParent(model.getCompany());
//        List<StructureModel> bm = structureService.getEqual(model.getDepartment());
//        List<StructureModel> zw = structureService.getEqual(model.getPosition());
//        return new ModelAndView("/people/update")
//                .addObject("model", model)
//                .addObject("dw", dw)
//                .addObject("bm", bm)
//                .addObject("zw", zw)
//                .addObject("error", Htmls.SPAN("red", "资料修改失败"));
//    }
//
//    @RequiresPermissions(value = "people:add")
//    @GetMapping("/toAdd")
//    public ModelAndView toAdd() {
//        List<StructureModel> companys = structureService.findAll("0");
//        List<StructureModel> department = structureService.findAlls("0", "2");
//        return new ModelAndView("/people/add")
//                .addObject("model", new UserModel())
//                .addObject("companys", companys)
//                .addObject("department", department);
//    }
//
//    /**
//     * 添加用户
//     *
//     * @param model  UserModel
//     * @param result BindingResult
//     * @return ModelAndView
//     */
//    @RequiresPermissions(value = "people:add")
//    @PostMapping("/add")
//    public ModelAndView add(@Validated({ADD.class}) @ModelAttribute("model") UserModel model, BindingResult result) {
////数据验证
//        if (result.hasErrors()) {
//            return new ModelAndView("/people/add")
//                    .addObject("model", model);
//        }
////账户是否重复
//        UserModel model1 = service.getByAccount(model.getAccount());
//        if (model1 != null) {
//            List<StructureModel> companys = structureService.findAll("0");
//            List<StructureModel> department = structureService.findAlls("0", "2");
//            return new ModelAndView("/people/add")
//                    .addObject("model", new UserModel())
//                    .addObject("companys", companys)
//                    .addObject("department", department)
//                    .addObject("error", "该账户已注册");
//        }
////新增账户
//        model.setAcctype(2);
//        String pwd = Base64Util.encode(model.getPasswrod());
//        model.setPasswrod(pwd);
//        int i = service.add(model);
//        if (i > 0) {
////            操作数据库成功
//            RegisterModel r = new RegisterModel();
//            r.setUuid(GetUuid.getUUID());
//            r.setAccount(model.getAccount());
//            r.setSystimes(System.currentTimeMillis());
//            registerService.add(r);
//            return new ModelAndView(RedirectUtil.getRddirect() + "/people/init/0");
//        }
////        操作数据库失败
//        List<StructureModel> companys = structureService.findAll("0");
//        List<StructureModel> department = structureService.findAlls("0", "2");
//        return new ModelAndView("/people/add")
//                .addObject("model", new UserModel())
//                .addObject("companys", companys)
//                .addObject("department", department)
//                .addObject("error", "添加失败");
//    }
}
