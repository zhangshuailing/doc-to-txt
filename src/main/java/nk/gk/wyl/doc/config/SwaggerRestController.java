package nk.gk.wyl.doc.config;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
* @Description:    定义接口访问在线api文档
* @Author:         zhangshuailing
* @CreateDate:     2020/10/15 12:45
* @UpdateUser:     zhangshuailing
* @UpdateDate:     2020/10/15 12:45
* @UpdateRemark:   修改内容
* @Version:        1.0
*/
@Controller
@RequestMapping("")
public class SwaggerRestController {

    @GetMapping("")
    public String index(){
        return "redirect:/swagger-ui.html";
    }
}
