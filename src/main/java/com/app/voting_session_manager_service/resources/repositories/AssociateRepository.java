package com.app.voting_session_manager_service.resources.repositories;

import com.app.voting_session_manager_service.domain.entities.Associate;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssociateRepository extends MongoRepository<Associate, ObjectId> {
}
