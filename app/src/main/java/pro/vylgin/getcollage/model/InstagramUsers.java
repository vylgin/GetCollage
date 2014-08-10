package pro.vylgin.getcollage.model;

import java.util.List;

public class InstagramUsers {
    private List<InstagramUser> data;

    public List<InstagramUser> getData() {
        return data;
    }

    public void setData(List<InstagramUser> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        StringBuilder users = new StringBuilder();
        for (InstagramUser instagramUser : this.getData()) {
            users.append(instagramUser.getUsername()).append(" ");
        }

        return users.toString();
    }
}
