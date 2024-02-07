package csit321.cloudcrypt.Controller;

import csit321.cloudcrypt.Entity.UserAccount;
import csit321.cloudcrypt.Repository.UserAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.logging.Level;
import java.util.logging.Logger;

@Controller
public class UserController {

    private static final Logger logger = Logger.getLogger(UserController.class.getName());

    @Autowired
    private UserAccountRepository userAccountRepository;

    @GetMapping("/createAccount")
    public String showCreateAccountForm(Model model) {
        model.addAttribute("userAccount", new UserAccount());
        return "OwnerAdminAccount";
    }
    @GetMapping("/OwnerAdminAccount")
    public String showOwnerAdminAccount(Model model) {
        // Logic to prepare data for the "OwnerAdminAccount" view, if needed
        return "OwnerAdminAccount";
    }

    @PostMapping("/createAccount")
    public String createAccount(UserAccount userAccount) {
        try {
            logger.info("Received user account data: " + userAccount.toString());

            userAccountRepository.save(userAccount);

            logger.info("User account saved successfully!");
            alert("Admin account created successfully!");
            // Redirect to "/OwnerAdminAccount"
            return "redirect:/OwnerAdminAccount";
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error saving user account: " + e.getMessage(), e);
            return "error";
        }
    }

    private void alert(String s) {
    }

    @GetMapping("/test")
    public String test() {
        logger.info("User account saved successfully!");
        return "null";
    }
    
}
