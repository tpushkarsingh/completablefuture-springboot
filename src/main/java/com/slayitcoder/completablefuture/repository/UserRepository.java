package com.slayitcoder.completablefuture.repository;

import com.slayitcoder.completablefuture.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Integer> {
}
