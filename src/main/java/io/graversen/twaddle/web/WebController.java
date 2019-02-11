package io.graversen.twaddle.web;

import io.graversen.twaddle.data.repository.jpa.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller("web")
@RequiredArgsConstructor
public class WebController
{
    private final IUserRepository userRepository;

    @GetMapping
    public ModelAndView index()
    {
        ModelAndView modelAndView = new ModelAndView("index");
        modelAndView.addObject("users", userRepository.findAll());

        return modelAndView;
    }
}
