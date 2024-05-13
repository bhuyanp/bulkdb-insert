package com.example.bulkdbinsert.service;

import com.example.bulkdbinsert.documentdb.model.User;
import com.example.bulkdbinsert.documentdb.repo.UserRepo;
import com.example.bulkdbinsert.vo.UserVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@RequiredArgsConstructor
@Profile("mongo")
public class UserMongoService extends UserService {
    private final UserRepo userRepo;

    @Override
    public Map<String, String> uploadUsers(MultipartFile multipartFile) {
        StopWatch sw = new StopWatch();
        sw.start();
        Map<String, String> response = new LinkedHashMap<>();
        try {
            response.put("File Name", multipartFile.getOriginalFilename());
            List<UserVO> userVos = readUsersFromFile(multipartFile);
            List<User> users = userVos.stream().map(uvo -> User.builder()
                    .firstName(uvo.getFirstName())
                    .lastName(uvo.getLastName())
                    .email(uvo.getEmail()).build()).toList();


            //userRepo.saveAll(users);

            //users.forEach(userRepo::save);

            List<CompletableFuture<Void>> futureList = new ArrayList<>(users.size());
            users.forEach(user->
                    futureList.add(CompletableFuture.runAsync(()->userRepo.save(user)))
            );
            CompletableFuture.allOf(futureList.toArray(CompletableFuture[]::new)).join();

            response.put("Total Records", "" + users.size());
        } catch (IOException e) {
            String message = "Error occurred while processing " + multipartFile.getOriginalFilename()
                    + "." + e.getMessage();
            log.error(message, e);
            response.put("Error:", message);
        }
        sw.stop();
        log.info("Time taken(secs): {}", sw.getTotalTimeSeconds());
        response.put("TimeTaken", "" + sw.getTotalTimeSeconds());
        return response;
    }
}
