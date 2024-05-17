package Core;

public class UserSession {
    private static UserSession instance;
    private User user;
    private String resetEmail;
    private String userEmail;

    private UserSession() {

    }

    public static synchronized UserSession getInstance() {
        if (instance == null) {
            instance = new UserSession();
        }
        return instance;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getResetEmail() {
        return resetEmail;
    }

    public void setResetEmail(String resetEmail) {
        this.resetEmail = resetEmail;
    }

    public void clearSession() {
        user = null;
        resetEmail = null;
    }

    public String getUserEmail(){
        return userEmail;
    }
    public void setUserEmail(String userEmail){
        this.userEmail = userEmail;
    }
}
