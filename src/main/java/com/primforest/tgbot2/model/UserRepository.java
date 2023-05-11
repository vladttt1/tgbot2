package com.primforest.tgbot2.model;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository  extends MongoRepository<User,Long> {
}
