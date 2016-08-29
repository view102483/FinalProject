package tw.sunny.finalproject.model;

import java.util.Map;

/**
 * Created by lixinting on 2016/8/29.
 */
public class LoginUser {
    private String name;
    private int age;
    private double height;
    private double weight;

    public static LoginUser genFakeData() {
        LoginUser user = new LoginUser();
        user.name = "昕庭";
        user.age = 21;
        return user;
    }
}
