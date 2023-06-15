package com.example.moreba2.mvc.model.dto;

public class Qna_RippleDTO {
    int rippleId;
    String name;
    String content;
    String insertDate;
    String memberId;
    int num;
    String boardName;

    public Qna_RippleDTO(){

    }
    public int getRippleId() {
        return rippleId;
    }

    public void setRippleId(int rippleId) {
        this.rippleId = rippleId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getInsertDate() {
        return insertDate;
    }

    public void setInsertDate(String insertDate) {
        this.insertDate = insertDate;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getBoardName() {
        return boardName;
    }

    public void setBoardName(String boardName) {
        this.boardName = boardName;
    }
}

