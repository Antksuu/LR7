package entrance.controller;
import entrance.model.FileUploadUtil;
import entrance.model.Role;
import entrance.repository.UsersRepository;
import entrance.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import entrance.model.User;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.GetMapping;
import java.io.IOException;
import java.util.Collections;


@Controller
public class Account {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private UserService userService;

    @GetMapping(value = "/")
    public String home(@AuthenticationPrincipal UserDetails user, Model model) {
        User u =usersRepository.findByUsername(user.getUsername());
        model.addAttribute("currentUser", u);
        return "index";
    }

    @GetMapping(value = "/account")
    public String account(@AuthenticationPrincipal UserDetails user, Model model) {
        User u = usersRepository.findByUsername(user.getUsername());
        u.updateCounter();
        usersRepository.save(u);
        model.addAttribute("currentUser", u);
        model.addAttribute("photosImagePath", u.getPhotosImagePath());
        return "/account";
    }

    @GetMapping("/update/{id}")
    public String upD(@PathVariable("id") long id, Model model) {
        User user= (User) userService.findById(id);
        model.addAttribute("user", user);
        return "update";
    }

    @PostMapping("/update")

public String accountUp(User user, @RequestParam("image") MultipartFile multipartFile) throws IOException {
        usersRepository.findByUsername(user.getUsername());
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        user.setPhotos(fileName);
        user.updateCounter();
        user.setActive(true);
        user.setRoles(Collections.singleton(Role.USER));
        User savedUser = usersRepository.save(user);

        String uploadDir = "user-photos/" + savedUser.getId();

        FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
        return "redirect:/account";

    }
}




