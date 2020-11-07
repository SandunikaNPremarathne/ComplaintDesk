package com.example.complaintapp.Util;

public class complaintModel {
    private String category;
    private String date;
    private String time;
    private String cdate;
    private String ctime;
    private String location;
    private String institute;
    private String uid;
    private String comid;
    private String details;
    private String people;
    private String image;
    private String comments;
    private String status;
    private String comments_of_admin;

    public complaintModel(){

    }

    public complaintModel(String category,String cdate,String ctime,String comid,String details,String image,String date,String time,String location,String institute,String people,String uid,String comments,String status,String comments_of_admin){
        this.category=category;
        this.cdate=cdate;
        this.ctime=ctime;
        this.date = date;
        this.time = time;
        this.location = location;
        this.setInstitute(institute);
        this.uid = uid;
        this.comid = comid;
        this.details = details;
        this.people = people;
        this.image=image;
        this.comments=comments;
        this.status=status;
        this.comments_of_admin=comments_of_admin;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getComid() {
        return comid;
    }

    public void setComid(String comid) {
        this.comid = comid;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getPeople() {
        return people;
    }

    public void setPeople(String people) {
        this.people = people;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getComments_of_admin() {
        return comments_of_admin;
    }

    public void setComments_of_admin(String comments_of_admin) {
        this.comments_of_admin = comments_of_admin;
    }

    public String getCtime() {
        return ctime;
    }

    public void setCtime(String ctime) {
        this.ctime = ctime;
    }

    public String getCdate() {
        return cdate;
    }

    public void setCdate(String cdate) {
        this.cdate = cdate;
    }

    public String getInstitute() {
        return institute;
    }

    public void setInstitute(String institute) {
        this.institute = institute;
    }
}
