package Haedal.bootcamp.spring.Controller;

import Haedal.bootcamp.spring.Domain.User;
import Haedal.bootcamp.spring.Dto.response.UserSimpleResponseDto;
import Haedal.bootcamp.spring.Service.AuthService;
import Haedal.bootcamp.spring.Service.FollowService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class FollowController {
    private final FollowService followService;
    private final AuthService authService;

    @Autowired
    public FollowController(FollowService followService, AuthService authService) {
        this.followService = followService;
        this.authService = authService;
    }

    @PostMapping("/follows/{followingId}")
    public ResponseEntity<Void> followUser(@PathVariable Long followingId, HttpServletRequest request) {
        User currentUser = authService.getCurrentUser(request);

        followService.followUser(currentUser, followingId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/follows/{followingId}")
    public ResponseEntity<Void> unfollowUser(@PathVariable Long followingId, HttpServletRequest request) {
        User currentUser = authService.getCurrentUser(request);

        followService.unfollowUser(currentUser, followingId);
        return ResponseEntity.ok().build();
    }


    @GetMapping("/follows/{userId}/following")
    public ResponseEntity<List<UserSimpleResponseDto>> getFollowingUsers(@PathVariable Long userId, HttpServletRequest request) {
        User currentUser = authService.getCurrentUser(request);
        List<UserSimpleResponseDto> followingUsers = followService.getFollowingUsers(currentUser, userId);
        return ResponseEntity.ok(followingUsers);
    }

    @GetMapping("/follows/{userId}/follower")
    public ResponseEntity<List<UserSimpleResponseDto>> getFollowerUsers(@PathVariable Long userId, HttpServletRequest request) {
        User currentUser = authService.getCurrentUser(request);
        List<UserSimpleResponseDto> followerUsers = followService.getFollowerUsers(currentUser, userId);
        return ResponseEntity.ok(followerUsers);
    }
}
