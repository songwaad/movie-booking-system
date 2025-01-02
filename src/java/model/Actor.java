package model;

public class Actor {
    String actorId;
    String fname;
    String lname;
    String imageUrl;

    public Actor(String actorId, String fname, String lname, String imageUrl) {
        this.actorId = actorId;
        this.fname = fname;
        this.lname = lname;
        this.imageUrl = imageUrl;
    }

    public Actor() {}

    public String getActorId() {
        return actorId;
    }

    public void setActorId(String actorId) {
        this.actorId = actorId;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
