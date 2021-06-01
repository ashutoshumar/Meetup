package com.ashutosh.meetup.notification;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiService {
    @Headers({
            "Content-Type:application/json",
            "Authorization:key=AAAA-4B0v0s:APA91bFMfZwtD4o4F5hBDj1s8x1upeIBfGwuB7DJi_T-VA8mctEB-DAn5hAqztTMIvnbCiQByy93ajS75mm-h82Hv38ynM-ZV5H3RMhdXETw9_-vCihTvSwRXbGXUPCxm8x0-nKQBL3S"
    })

    @POST("fcm/send")
    Call<MyResponse> sendNotification(@Body Sender body);
}
