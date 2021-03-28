package com.example.preemptiveoop.post.model;

public class Reply extends Post {

    private String questionId;

    Reply(){}

    public Reply(String targetExpId, String poster, String questionId, String title, String body){
        super(targetExpId, poster, title, body);
        this.questionId = questionId;
    }

    public String getQuestionId() {
        return questionId;
    }
}
