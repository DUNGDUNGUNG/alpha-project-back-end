package com.codegym.alphaprojectbackend.controller;

import com.codegym.alphaprojectbackend.message.response.ResponseMessage;
import com.codegym.alphaprojectbackend.model.*;
import com.codegym.alphaprojectbackend.service.UserService;
import com.codegym.alphaprojectbackend.service.impl.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api")
public class AuthorizedRestAPIs {
  @Autowired
  private UserService userService;

  @Autowired
  private UserDetailsServiceImpl userDetailsService;

  @Autowired
  private JwtAuthTokenFilter authenticationJwtTokenFilter;

  @Autowired
  private JwtProvider jwtProvider;

  @Autowired
  private PasswordEncoder encoder;


  @GetMapping("/test/user")
//  @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
  public String userAccess() {
    return ">>> User Contents!";
  }

  @GetMapping("/test/pm")
//  @PreAuthorize("hasRole('ADMIN')")
  public String projectManagementAccess() {
    return ">>> Project Management Board";
  }

  @GetMapping("/test/admin")
//  @PreAuthorize("hasRole('ADMIN')")
  public String adminAccess() {
    return ">>> Admin Contents";
  }

  @PutMapping(value = "/update-info", consumes = "multipart/form-data")
//  @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
  public ResponseEntity<?> updateInfo(HttpServletRequest request, @Valid @ModelAttribute User updateInfoForm) {
    User user = userService.getUserByAuth();
    if (isExistedByPhoneNumber(user, updateInfoForm.getPhoneNumber())) {
      return new ResponseEntity<>(new ResponseMessage("Fail -> Phone number is already in use"),
              HttpStatus.BAD_REQUEST);
    }
    user.setFirstName(updateInfoForm.getFirstName());
    user.setLastName(updateInfoForm.getLastName());
    user.setAddress(updateInfoForm.getAddress());
    user.setBirthday(updateInfoForm.getBirthday());
    user.setGender(updateInfoForm.getGender());
    user.setPhoneNumber(updateInfoForm.getPhoneNumber());
    user.setAvatarUrl(updateInfoForm.getAvatarUrl());

    userService.save(user);
    UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());
    JwtResponse jwtResponse = new JwtResponse(authenticationJwtTokenFilter.getJwt(request), userDetails.getUsername(),
      userDetails.getAuthorities(), user.getAvatarUrl());
    return ResponseEntity.ok(jwtResponse);

  }

  @GetMapping("update-info")
//  @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
  public ResponseEntity<User> getUpdateInfoForm(){
    User user = userService.getUserByAuth();
    return new ResponseEntity<User>(user, HttpStatus.OK);
  }

  @PutMapping(value = "update-password")
//  @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
  public ResponseEntity<?> updatePassword(@RequestBody UpdatePasswordForm updatePasswordForm, HttpServletRequest request) {
    try {
      User user = getUser(request);
      String newPassword = encoder.encode(updatePasswordForm.getNewPassword());
      if (newPassword != null) {
        boolean matches = encoder.matches(updatePasswordForm.getCurrentPassword(), user.getPassword());
        if (matches) {
          user.setPassword(newPassword);
          userService.save(user);
          return new ResponseEntity<>(new ResponseMessage("Password successfully reset"), HttpStatus.OK);
        }
        else {
          return new ResponseEntity<>(new ResponseMessage("Incorrect password"),
            HttpStatus.BAD_REQUEST);
        }
      }
      return new ResponseEntity<>(new ResponseMessage("New Password must not be blank"), HttpStatus.BAD_REQUEST);
    }
    catch (UsernameNotFoundException exception) {
      return new ResponseEntity<>(new ResponseMessage(exception.getMessage()), HttpStatus.NOT_FOUND);
    }
  }


  private boolean isExistedByPhoneNumber(User user, String phoneNumber) {
    UserPrinciple userPrinciple = UserPrinciple.build(user);
    UserPrinciple userExistsByPhoneNumber = UserPrinciple.build(userService.findByPhoneNumber(phoneNumber));
    return userService.existsByPhoneNumber(phoneNumber) && (!userPrinciple.equals(userExistsByPhoneNumber));
  }

  private User getUser(HttpServletRequest request) throws UsernameNotFoundException {
    String jwt = authenticationJwtTokenFilter.getJwt(request);
    String userName = jwtProvider.getUserNameFromJwtToken(jwt);
    User user = userService.findByEmail(userName).orElseThrow(
      () -> new UsernameNotFoundException("User Not Found with -> username or email : " + userName));
    return user;
  }
}

