package com.example.commonInfraProxy.repository;

import com.example.commonInfraProxy.entity.UserEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<UserEntity,String> {
    Boolean existsByUserId(String id);
}