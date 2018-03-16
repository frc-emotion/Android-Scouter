package com.team2658.scouter;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by Gokul Swaminathan on 3/14/2018.
 */

public interface ForumsWebService {

    @POST("e/1FAIpQLSddTcjsg72awkFgC7B7zAsaJX6y3VaxCv5UPOBfp0QDMcbwDQ/formResponse")
    @FormUrlEncoded
    Call<Void> completeForum(
            @Field("entry.829700598") String scoutName,
            @Field("entry.499828888") String teamNum,
            @Field("entry.1179559190") String autoLine,
            @Field("entry.1876919081") String autoHome,
            @Field("entry.1980565669") String autoOpponent,
            @Field("entry.2107845895") String autoScale,
            @Field("entry.799149728") String telHome,
            @Field("entry.721096757") String telOpponent,
            @Field("entry.898469043") String telScale,
            @Field("entry.1297945363") String cubesDropped,
            @Field("entry.2079952567") String penalties,
            @Field("entry.652412432") String climbProcess
    );
}
