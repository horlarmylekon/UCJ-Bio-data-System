/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UCJBiodataSystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@CrossOrigin
@Controller
@RequestMapping("/portal")
public class LoginController {



    @GetMapping(path = "/login")
    public String login() {
        return "login";
    }


    @GetMapping(path = "/home")
    public String showHomePage() {
        return "index";
    }

    @GetMapping("/about")
    public String aboutPage(){
        return "about";
    }


}
