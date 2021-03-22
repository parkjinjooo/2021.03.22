package model;

public class TeacherDTO {
    // 필드
    private int id;
    private String name;
    private String username;
    private String password;
    private int group;

    // getters/setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getGroup() {
        return group;
    }

    public void setGroup(int group) {
        this.group = group;
    }

    // equals()
    public boolean equals(Object o) {
        if (o instanceof TeacherDTO) {
            TeacherDTO t = (TeacherDTO) o;
            if (id == t.id) {
                return true;
            }
        }
        return false;
    }

}
