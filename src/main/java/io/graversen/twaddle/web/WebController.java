package io.graversen.twaddle.web;

import io.graversen.twaddle.data.entity.User;
import io.graversen.twaddle.data.model.UserModel;
import io.graversen.twaddle.data.repository.elastic.ITwaddleRepository;
import io.graversen.twaddle.data.repository.jpa.IUserRepository;
import io.graversen.twaddle.lib.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class WebController
{
    private final IUserRepository userRepository;
    private final ITwaddleRepository twaddleRepository;
    private final List<String> adjectives;

    @Value("${twaddle.viewSize}")
    private int viewSize;

    @GetMapping("/")
    public ModelAndView index()
    {
        final List<UserModel> userModels = userRepository.findAll()
                .stream()
                .map(mapUserModel())
                .sorted(Comparator.comparing(UserModel::getTwaddles).reversed())
                .collect(Collectors.toList());

        ModelAndView modelAndView = new ModelAndView("index");
        modelAndView.addObject("users", userModels);

        return modelAndView;
    }

    @GetMapping("/users")
    public ModelAndView user()
    {
        return new ModelAndView("redirect:/");
    }

    @GetMapping("/users/{userId}")
    public ModelAndView userById(@PathVariable String userId)
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

        ModelAndView modelAndView = new ModelAndView("user-view");
        modelAndView.addObject("user", userOptional.get());
        modelAndView.addObject("viewSize", viewSize);

        return modelAndView;
    }

    @GetMapping("/twaddles/{hashTag}")
    public ModelAndView twaddlesByHashTag(@PathVariable String hashTag)
    {
        ModelAndView modelAndView = new ModelAndView("twaddles-by-hashtag-view");
        modelAndView.addObject("hashTag", hashTag);

        return modelAndView;
    }

    private Function<User, UserModel> mapUserModel()
    {
        return user ->
        {
            final long twaddles = twaddleRepository.countByUserId(user.getUserId());

            return new UserModel(
                    user.getUserId(),
                    user.getUsername(),
                    Utils.readableTimeFormatter().format(user.getCreatedAt()),
                    Utils.randomOf(adjectives),
                    twaddles
            );
        };
    }
}
