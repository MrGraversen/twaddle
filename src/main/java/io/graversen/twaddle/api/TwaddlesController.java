package io.graversen.twaddle.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("twaddles")
public class TwaddlesController
{
    @PostMapping
    public ResponseEntity<Void> createTwaddle()
    {
        return ResponseEntity.ok().build();
    }
}
