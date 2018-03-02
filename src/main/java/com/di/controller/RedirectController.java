package com.di.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

/**
 * Created by bentengdi on 2018/2/28.
 */
@Controller
@RequestMapping("/book")
@SessionAttributes(value ={"book"})
public class RedirectController {

    @RequestMapping("/index")
    public String index(Model model){
        model.addAttribute("book", "金刚经");
        model.addAttribute("description","不擦擦擦擦擦擦擦车");
        model.addAttribute("price", new Double("1000.00"));
        //跳转之前将数据保存到book、description和price中，因为注解@SessionAttribute中有这几个参数
        return "redirect:get.action";
    }

    /**
     * 在默认情况下，ModelMap 中的属性作用域是 request 级别是，也就是说，当本次请求结束后，
     * ModelMap 中的属性将销毁。如果希望在多个请求中共享 ModelMap 中的属性，
     * 必须将其属性转存到 session 中，这样 ModelMap 的属性才可以被跨请求访问。
     *
     * spring 允许我们有选择地指定 ModelMap 中的哪些属性需要转存到 session 中，
     * 以便下一个请求属对应的 ModelMap 的属性列表中还能访问到这些属性。
     * 这一功能是通过类定义处标注 @SessionAttributes 注解来实现的。
     *
     * 注解@SessionAttribute中设置book，这样值会被放到@SessionAttribute中,
     * 但Redirect跳转时就可以重新获得这些数据了，接下来操作sessionStatus.setComplete()，
     * 则会清除掉所有的数据，这样再次跳转时就无法获取数据了。
     * @param book
     * @param model
     * @param sessionStatus
     * @return
     */
    @RequestMapping("/get")
    public String get(@ModelAttribute("book") String book, ModelMap model,
                      SessionStatus sessionStatus){
        //可以获得book、description和price的参数,但是book的值可以直接传入。
        System.out.println(model.get("book")+";"+model.get("description")+";"+model.get("price"));
        System.out.println(book);
        System.out.println(model.get("book"));
        sessionStatus.setComplete();
        return "redirect:complete.action";
    }

    @RequestMapping("/complete")
    public String complete(ModelMap modelMap){
        //已经被清除，无法获取book的值
        System.out.println(modelMap.get("book"));
        modelMap.addAttribute("book", "妹纸");
        return "sessionAttribute";
    }

}

