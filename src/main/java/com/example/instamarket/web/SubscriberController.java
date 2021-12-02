package com.example.instamarket.web;

import com.example.instamarket.service.SubscriberService;
import com.example.instamarket.service.impl.InstamarketUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class SubscriberController {
    private final SubscriberService subscriberService;

    private static Logger LOGGER = LoggerFactory.getLogger(SubscriberController.class);

    public SubscriberController(SubscriberService subscriberService) {
        this.subscriberService = subscriberService;
    }

    @PostMapping("/subscribe/{id}")
    public String subscribe(@PathVariable Long id, @RequestParam Long offerId, @AuthenticationPrincipal InstamarketUser user, RedirectAttributes redirectAttributes){
        subscriberService.subscribe(id, user.getUserIdentifier());

        redirectAttributes.addFlashAttribute("subscription", "You have subscribed successfully!");

        if(offerId != null){
            LOGGER.info("Requested offer url {}", offerId);

            return "redirect:/offers/" + offerId + "/details";
        }else{
            return "redirect:/home";
        }
    }

    @PostMapping("/unsubscribe/{id}")
    public String unsubscribe(@PathVariable Long id, @RequestParam Long offerId, @AuthenticationPrincipal InstamarketUser user, RedirectAttributes redirectAttributes){
        subscriberService.unsubscribe(id, user.getUserIdentifier());

        redirectAttributes.addFlashAttribute("subscription", "You have unsubscribed successfully!");

        if(offerId != null){
            LOGGER.info("Requested offer url {}", offerId);

            return "redirect:/offers/" + offerId + "/details";
        }else{
            return "redirect:/home";
        }
    }
}
