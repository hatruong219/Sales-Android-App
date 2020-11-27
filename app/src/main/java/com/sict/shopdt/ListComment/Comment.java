package com.sict.shopdt.ListComment;

public class Comment {
    String NameUserComment;
    int StarComment;
    String DateComment;
    String ContentComment;


    String ImgUserComment;

    public Comment(String nameUserComment, int starComment, String dateComment, String contentComment, String imgUserComment) {
        NameUserComment = nameUserComment;
        StarComment = starComment;
        DateComment = dateComment;
        ContentComment = contentComment;
        ImgUserComment = imgUserComment;
    }

    public Comment(){

    }

    public String getNameUserComment() {
        return NameUserComment;
    }

    public void setNameUserComment(String nameUserComment) {
        NameUserComment = nameUserComment;
    }

    public int getStarComment() {
        return StarComment;
    }

    public void setStarComment(int starComment) {
        StarComment = starComment;
    }

    public String getDateComment() {
        return DateComment;
    }

    public void setDateComment(String dateComment) {
        DateComment = dateComment;
    }

    public String getContentComment() {
        return ContentComment;
    }

    public void setContentComment(String contentComment) {
        ContentComment = contentComment;
    }
    public String getImgUserComment() {
        return ImgUserComment;
    }

    public void setImgUserComment(String imgUserComment) {
        ImgUserComment = imgUserComment;
    }
}
