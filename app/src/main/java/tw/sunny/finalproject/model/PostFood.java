package tw.sunny.finalproject.model;

import android.widget.ImageView;

/**
 * Created by lixinting on 2016/8/29.
 */
public class PostFood {
    private String describe;
    private String storeaddress;
    private String userid;
    private ImageView photo;


    public static PostFood getFakeFoodPost(){
        PostFood postFood = new PostFood();

        postFood.describe = "#20160627 今天第一頓飯";
        postFood.storeaddress = "內湖西湖市場";
        postFood.userid = "view_1024@yahoo.com.tw";

        return postFood;
    }
}


