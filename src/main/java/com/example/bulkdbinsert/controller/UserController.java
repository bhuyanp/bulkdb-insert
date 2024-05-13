package com.example.bulkdbinsert.controller;

import com.example.bulkdbinsert.service.UserMySqlService;
import com.example.bulkdbinsert.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

@Slf4j
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<List<Map<String,String>>> uploadUsers(@RequestParam(value = "files") MultipartFile[] files) {

        List<Map<String,String>> response = new ArrayList<>(files.length);
        IntStream.range(0, files.length).forEach(i -> response.add(userService.uploadUsers(files[i])));

        return ResponseEntity.ok(response);

    }
}
