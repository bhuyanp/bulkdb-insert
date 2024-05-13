package com.example.bulkdbinsert.service;

import au.com.bytecode.opencsv.CSVReader;
import com.example.bulkdbinsert.db.model.User;
import com.example.bulkdbinsert.vo.UserVO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class UserService {
    public abstract Map<String, String> uploadUsers(MultipartFile multipartFile);

    List<UserVO> readUsersFromFile(MultipartFile userFile) throws IOException {
        List<UserVO> users = new ArrayList<>();

        try (CSVReader reader
                     = new CSVReader(new InputStreamReader(userFile.getInputStream()))) {
            reader.readAll().stream()
                    .skip(1)//skip header
                    .forEach(row ->{
                        UserVO user = new UserVO();
                                user.setFirstName(row[0]);
                                user.setLastName(row[1]);
                                user.setEmail(row[2]);
                                users.add(user);
                            }
                    );
        }
        return users;
    }
}
