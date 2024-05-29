package com.example.bulkdbinsert.service;

import com.example.bulkdbinsert.annotation.Timed;
import com.example.bulkdbinsert.db.model.User;
import com.example.bulkdbinsert.db.repo.UserRepo;
import com.example.bulkdbinsert.vo.UserVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Slf4j
@Service
@RequiredArgsConstructor
@Profile("mysql")
public class UserMySqlService extends UserService {
    private final UserRepo userRepo;
    private final Executor fixedThreadPool;
    @Value("${bulkupload.insert.mode:bulk}")
    private BulkUploadMode mode;

    @Timed
    @Override
    public Map<String, String> uploadUsers(MultipartFile multipartFile) {
        Map<String, String> response = new LinkedHashMap<>();
        try {
            response.put("File Name", multipartFile.getOriginalFilename());
            List<UserVO> userVOS = readUsersFromFile(multipartFile);
            List<User> users = userVOS.stream().map(uvo -> User.builder()
                    .firstName(uvo.getFirstName())
                    .lastName(uvo.getLastName())
                    .email(uvo.getEmail()).build()).toList();

            log.info("Upload mode: {}", mode);

            switch (mode) {
                case bulk -> userRepo.saveAll(users);
                case single -> users.forEach(userRepo::save);
                case singleparallel -> {
                    List<CompletableFuture<Void>> futureList = new ArrayList<>(users.size());
                    Executor cachedThreadPool = Executors.newCachedThreadPool(Executors.defaultThreadFactory());
                    users.forEach(user ->
                            futureList.add(CompletableFuture
                                    .supplyAsync(() -> "mysql")
                                    .thenRunAsync(() -> {
                                        log.info("User Name:{}", user.getFirstName());
                                        userRepo.save(user);
                                    },fixedThreadPool))
                    );
                    CompletableFuture.allOf(futureList.toArray(CompletableFuture[]::new)).join();
                }
            }

            response.put("Total Records", "" + users.size());
        } catch (IOException e) {
            String message = "Error occurred while processing " + multipartFile.getOriginalFilename()
                    + "." + e.getMessage();
            log.error(message, e);
            response.put("Error:", message);
        }
        return response;
    }

}
