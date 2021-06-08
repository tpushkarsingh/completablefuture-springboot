package com.slayitcoder.completablefuture.service;

import com.slayitcoder.completablefuture.entity.User;
import com.slayitcoder.completablefuture.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;


@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    Logger log = LoggerFactory.getLogger(UserService.class);

    @Async
    public CompletableFuture<List<User>> saveUser (MultipartFile file) {
        Long start = System.currentTimeMillis();
        List<User> users = parseCSVFile(file);
        log.info("saving users list of size {}",users.size() ," "+Thread.currentThread().getName());
        users = userRepository.saveAll(users);
        Long end = System.currentTimeMillis();
        log.info("Total execution to save records {}",(end-start));
        return CompletableFuture.completedFuture(users);
    }

    @Async
    public CompletableFuture<List<User>> findAllUsers(){
        log.info("get list of all the users by thread {}", Thread.currentThread().getName());
        return CompletableFuture.completedFuture(userRepository.findAll());
    }
    private List<User> parseCSVFile(final MultipartFile file) {
        final List<User> users = new ArrayList<>();
        try(final BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()))){
            String line;
            while((line = br.readLine())!=null){
                final String [] data = line.split(",");
                final User user = new User();
                user.setName(data[0]);
                user.setEmail(data[1]);
                user.setGender(data[2]);
                users.add(user);

            }

        }
        catch(IOException e){

        }

        return users;
    }

}
