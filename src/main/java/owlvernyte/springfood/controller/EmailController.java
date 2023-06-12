package owlvernyte.springfood.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import owlvernyte.springfood.entity.EmailDescription;
import owlvernyte.springfood.service.EmailService;

import java.util.List;

@Controller
@RequestMapping("/email")
public class EmailController {

    Logger logger = LoggerFactory.getLogger(EmailController.class);

    @Autowired
    private EmailService emailService;

    @GetMapping
    public String index(Model model) {
        model.addAttribute("email", new EmailDescription());
        return "home/contact";
    }

//    @PostMapping("/text")
//    public String sendEmail(@ModelAttribute("email") EmailDescription email, BindingResult bindingResult, Model model) {
//        try {
//            if (bindingResult != null && bindingResult.hasErrors()) {
//                List<String> errors = bindingResult
//                        .getAllErrors()
//                        .stream()
//                        .map(ObjectError::getDefaultMessage)
//                        .toList();
//
//                model.addAttribute("errors", errors);
//                return "home/contact";
//            }
//            emailService.sendEmail(email);
//            return "home/email-success";
//        } catch (Exception e) {
//            logger.error("text email cant sent", e);
//            return "not-found";
//        }
//    }

    @PostMapping
    public String sendHtmlEmail(@ModelAttribute("email") EmailDescription email, BindingResult bindingResult, Model model) {
        try {
            if (bindingResult != null && bindingResult.hasErrors()) {
                List<String> errors = bindingResult
                        .getAllErrors()
                        .stream()
                        .map(ObjectError::getDefaultMessage)
                        .toList();

                model.addAttribute("errors", errors);
                return "home/contact";
            }
            emailService.sendHtmlEmail(email);
            return "home/email-success";
        } catch (Exception e) {
            logger.error("html email cant sent html", e);
            return "not-found";
        }
    }
}
