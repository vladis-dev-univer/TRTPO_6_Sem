package by.bsuir.project.entity;

import java.util.Date;
import java.util.Objects;

public class PublicationComment extends Entity {
    private String text;
    private Date commentDate;
    private UserInfo userInfo;
    private Publication publication;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getCommentDate() {
        return commentDate;
    }

    public void setCommentDate(Date commentDate) {
        this.commentDate = commentDate;
    }

    public Publication getPublication() {
        return publication;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public void setPublication(Publication publication) {
        this.publication = publication;
    }

    public PublicationComment() {
    }

    public PublicationComment(String text, Date commentDate, UserInfo userInfo, Publication publication) {
        this.text = text;
        this.commentDate = commentDate;
        this.userInfo = userInfo;
        this.publication = publication;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PublicationComment)) return false;
        if (!super.equals(o)) return false;
        PublicationComment that = (PublicationComment) o;
        return Objects.equals(getText(), that.getText()) &&
                Objects.equals(getCommentDate(), that.getCommentDate()) &&
                Objects.equals(getUserInfo(), that.getUserInfo()) &&
                Objects.equals(getPublication(), that.getPublication());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getText(), getCommentDate(), getUserInfo(), getPublication());
    }
}
