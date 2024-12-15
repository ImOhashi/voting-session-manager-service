package com.app.voting_session_manager_service.domain.entities;

import org.bson.BsonType;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonRepresentation;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("associate_document")
public class Associate {

    @PersistenceCreator
    public Associate(String _id, String name, String cpf) {
        this._id = _id;
        this.name = name;
        this.cpf = cpf;
    }

    private Associate(Builder builder) {
        this.name = builder.name;
        this.cpf = builder.cpf;
    }

    @Id
    @BsonId
    @BsonRepresentation(BsonType.OBJECT_ID)
    private String _id;

    private String name;
    private String cpf;

    public static class Builder {
        private String name;
        private String cpf;

        public Associate build() {
            return new Associate(this);
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setCpf(String cpf) {
            this.cpf = cpf;
            return this;
        }
    }
}
