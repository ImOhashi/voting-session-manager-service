package com.app.voting_session_manager_service.domain.entities;

import org.bson.BsonType;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonRepresentation;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document("rulling_document")
public class Rulling {

    @PersistenceCreator
    public Rulling(String _id, String title, String description, List<Vote> votesList) {
        this._id = _id;
        this.title = title;
        this.description = description;
        this.votesList = votesList;
    }

    public Rulling(Builder builder) {
        this.title = builder.title;
        this.description = builder.description;
        this.votesList = builder.votesList;
    }

    public void addNewVote(Vote vote) {
        this.votesList.add(vote);
    }

    @Id
    @BsonId
    @BsonRepresentation(BsonType.OBJECT_ID)
    private String _id;

    private String title;
    private String description;
    private List<Vote> votesList;

    public static class Builder {
        private String title;
        private String description;
        private List<Vote> votesList;

        private Rulling build() {
            return new Rulling(this);
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder setVotesList(List<Vote> votesList) {
            this.votesList = votesList;
            return this;
        }
    }
}