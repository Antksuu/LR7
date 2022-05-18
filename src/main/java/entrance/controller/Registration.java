package entrance.controller;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import entrance.model.FileUploadUtil;
import entrance.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import entrance.model.Role;
import entrance.model.User;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;


@Controller
public class Registration {

    @Autowired
    private UsersRepository usersRepository;

    @GetMapping("/registration")
    public String registration() {

        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(User user, @RequestParam("image") MultipartFile multipartFile, Map<String, Object> model) throws IOException {
        User userFromDb = usersRepository.findByUsername(user.getUsername());

        if (userFromDb != null) {
            model.put("message", "Пользователь с таким логином уже зарегистрирован!");
            return "registration";
        }

        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        user.setPhotos(fileName);
        user.setActive(true);
        user.setRoles(Collections.singleton(Role.USER));
        User savedUser = usersRepository.save(user);

        String uploadDir = "user-photos/" + savedUser.getId();

        FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
        return "redirect:/login";
    }
}
