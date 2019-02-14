package io.graversen.twaddle.web;

import io.graversen.twaddle.data.document.Twaddle;
import io.graversen.twaddle.data.entity.User;
import io.graversen.twaddle.data.repository.elastic.ITwaddleRepository;
import io.graversen.twaddle.data.repository.jpa.IUserRepository;
import io.graversen.twaddle.lib.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class WebController
{
    private final IUserRepository userRepository;
    private final ITwaddleRepository twaddleRepository;

    @GetMapping("/")
    public ModelAndView index()
    {
        ModelAndView modelAndView = new ModelAndView("index");
        modelAndView.addObject("users", userRepository.findAll());

        return modelAndView;
    }

    @GetMapping("/users/{userId}")
    public ModelAndView user(@PathVariable String userId)
    {
        if ("random".equalsIgnoreCase(userId))
        {
            userId = Utils.randomOf(userRepository.findAll()).getUserId();
        }

        final Optional<User> userOptional = userRepository.findByUserId(userId);

        if (userOptional.isEmpty())
        {
            return new ModelAndView("redirect:/");
        }

        final long start = System.currentTimeMillis();
        final Page<Twaddle> twaddles =
                twaddleRepository.findByUserId(userId, PageRequest.of(0, 15, Sort.by(Sort.Direction.DESC, "createdAt")));
        final long latency = System.currentTimeMillis() - start;

        ModelAndView modelAndView = new ModelAndView("user");
        modelAndView.addObject("twaddles", twaddles);
        modelAndView.addObject("user", userOptional.get());
        modelAndView.addObject("latency", latency);

        return modelAndView;
    }
}
