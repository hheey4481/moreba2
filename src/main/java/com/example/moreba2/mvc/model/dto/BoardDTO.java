package com.example.moreba2.mvc.model.dto;

public class BoardDTO {
    int num;
    String id;
    String name;
    String subject;
    String content;
    String regist_day;
    int hit;
    String ip;

    String filename;
    long filesize;

    int accuss;
    int likeCount;

    public BoardDTO(){

    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getRegist_day() {
        return regist_day;
    }

    public void setRegist_day(String regist_day) {
        this.regist_day = regist_day;
    }

    public int getHit() {
        return hit;
    }

    public void setHit(int hit) {
        this.hit = hit;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public long getFilesize() {
        return filesize;
    }

    public void setFilesize(long filesize) {
        this.filesize = filesize;
    }

    public int getAccuss() {
        return accuss;
    }

    public void setAccuss(int accuss) {
        this.accuss = accuss;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeyCount) {
        this.likeCount = likeyCount;
    }
}
