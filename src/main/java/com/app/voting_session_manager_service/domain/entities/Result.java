package com.app.voting_session_manager_service.domain.entities;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Document("result_document")
public class Result implements Serializable {
    private String rullingTitle;
    private Integer sim;
    private Integer nao;

    public Result(String rullingTitle, Integer sim, Integer nao) {
        this.rullingTitle = rullingTitle;
        this.sim = sim;
        this.nao = nao;
    }

    public String getRullingTitle() {
        return rullingTitle;
    }

    public void setRullingTitle(String rullingTitle) {
        this.rullingTitle = rullingTitle;
    }

    public Integer getSim() {
        return sim;
    }

    public void setSim(Integer sim) {
        this.sim = sim;
    }

    public Integer getNao() {
        return nao;
    }

    public void setNao(Integer nao) {
        this.nao = nao;
    }

    public String toJson() throws JsonProcessingException {
        var ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        return ow.writeValueAsString(this);
    }
}
