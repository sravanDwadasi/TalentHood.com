package com.Sravan.TalentHood.controller;

import com.Sravan.TalentHood.entity.RecruiterProfile;
import com.Sravan.TalentHood.entity.Users;
import com.Sravan.TalentHood.repository.RecruiterProfileRepository;
import com.Sravan.TalentHood.repository.UsersRepository;
import com.Sravan.TalentHood.services.RecruiterProfileService;
import com.Sravan.TalentHood.util.FileUploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.util.StringUtils;

import java.util.Objects;
import java.util.Optional;

import static org.thymeleaf.util.StringUtils.*;

@Controller
@RequestMapping("recruiter-profile")
public class RecruiterProfileController {

    private  final UsersRepository usersRepository;
    private final RecruiterProfileService recruiterProfileService;

    @Autowired
    public RecruiterProfileController(UsersRepository usersRepository, RecruiterProfileService recruiterProfileService) {
        this.usersRepository = usersRepository;
        this.recruiterProfileService = recruiterProfileService;
    }

    @GetMapping("/")
    public String recruiterProfile(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(!(authentication instanceof AnonymousAuthenticationToken)){
            String currentUserName = authentication.getName();
            Users users = usersRepository.findByEmail(currentUserName).orElseThrow(() -> new UsernameNotFoundException(("Could not " + "find user")));
            Optional<RecruiterProfile> recruiterProfile = recruiterProfileService.getOne(users.getUserId());

            recruiterProfile.ifPresent(profile -> model.addAttribute("profile", profile));
        }
        return "recruiter_profile";
    }

    @PostMapping("/addNew")
    public String addNew(RecruiterProfile recruiterProfile, @RequestParam("image")MultipartFile multipartFile, Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!(authentication instanceof AnonymousAuthenticationToken)){
            String currentUserName = authentication.getName();
            Users users = usersRepository.findByEmail(currentUserName).orElseThrow(() -> new UsernameNotFoundException(("Could not " + "find user")));
            recruiterProfile.setUserId(users);
            recruiterProfile.setUserAccountId(users.getUserId());
        }
        model.addAttribute("profile", recruiterProfile);
        String fileName = "";
        if(!multipartFile.getOriginalFilename().equals("")){
            fileName = StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));
            recruiterProfile.setProfilePhoto(fileName);
        }
        RecruiterProfile savedUser = recruiterProfileService.addNew(recruiterProfile);

        String uploadDir = "photos/recruiter/" + savedUser.getUserAccountId();
        try {
            FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return "redirect:/dashboard/";
    }
}
