package io.graversen.twaddle.api;

import io.graversen.twaddle.data.entity.User;
import io.graversen.twaddle.data.model.TwaddleModel;
import io.graversen.twaddle.data.model.TwaddlesModel;
import io.graversen.twaddle.data.repository.elastic.ITwaddleRepository;
import io.graversen.twaddle.lib.Utils;
import io.graversen.twaddle.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.function.Consumer;

@RestController
@RequestMapping("api/hashtags")
@RequiredArgsConstructor
public class HashTagsController
{
    private final ITwaddleRepository twaddleRepository;
    private final UserService userService;

    @GetMapping("{hashTag}")
    public ResponseEntity<TwaddlesModel> twaddlesByHashTag(@PathVariable String hashTag)
    {
        final List<TwaddleModel> twaddles = twaddleRepository.findByHashTags(hashTag, PageRequest.of(0, 50))
                .map(Utils.mapTwaddle())
                .getContent();

        twaddles.forEach(setUserName());

        return ResponseEntity.ok(new TwaddlesModel(twaddles));
    }

    private Consumer<TwaddleModel> setUserName()
    {
        return twaddleModel ->
        {
            final User user = userService.getUser(twaddleModel.getUserId()).orElseGet(User::defaultUser);
            twaddleModel.setUsername(user.getUsername());
        };
    }
}
