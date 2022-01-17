package entrance.controller;

import entrance.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import entrance.model.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;

@Controller
public class Account {

    @Autowired
    private UsersRepository usersRepository;

    @Value("${upload.path")
    private String uploadPath;

    @GetMapping(value = "/")
    public String home() {
        return "index";
    }

    @GetMapping(value = "/account")
    public String account(@AuthenticationPrincipal UserDetails user,/** @RequestParam("image") MultipartFile multipartFile,*/Model model) {

        User u = usersRepository.findByUsername(user.getUsername());
        u.updateCounter();
        usersRepository.save(u);
        model.addAttribute("currentUser", u);
        return "account";
    }


}




