package utils;

public class UserSession {
    private static UserSession instance;
    private int userId;
    private String username;

    private UserSession() {}

    public static UserSession getInstance() {
        if (instance == null) {
            instance = new UserSession();
        }
        return instance;
    }

    public void setUser(int userId, String username) {
        this.userId = userId;
        this.username = username;
    }

    public void clearSession() {
        userId = 0;
        username = null;
    }

    public int getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }
}