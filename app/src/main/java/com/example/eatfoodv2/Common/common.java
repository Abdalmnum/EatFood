package com.example.eatfoodv2.Common;

import com.example.eatfoodv2.Model.CatagoryModel;
import com.example.eatfoodv2.Model.FoodModel;
import com.example.eatfoodv2.Model.UserModel;

import java.math.RoundingMode;
import java.text.DecimalFormat;

public class common {
    public static final String USER_REFERENCES = "Users";
    public static final String POPULAR_CATAGORY_KEY = "MostPopular";
    public static final String BEST_DEALS_KEY = "BestDeals" ;
    public static final int DEFAULT_COULMN_COUNT = 0;
    public static final int FULL_WIDTH_COULMN = 1;
    public static final String CATAGORY_KEY = "Category" ;
    public static final String COMMENT_REF = "Comments" ;

    public static UserModel currentUser;
    public static CatagoryModel catagorySelected;
    public static FoodModel selectedFood;


    public static String formatPrice(double price)
    {
        if(price != 0) {
            DecimalFormat  df = new DecimalFormat("#,##0.00");
            df.setRoundingMode(RoundingMode.UP);
            String finalPrice = new StringBuilder(df.format(price)).toString();
            return finalPrice.replace(".",",");

        }else{

            return "0,00";
        }

    }
}
