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
            @Field("entry.829700598") String scoutName,     //Scouter Name
            @Field("entry.499828888") String teamNum,       //Team Number
            @Field("entry.1179559190") String autoLine,     //Did it cross the Auto-Line?
            @Field("entry.1876919081") String autoHome,     //Did it put a cube in home switch during autonomous?
            @Field("entry.1980565669") String autoOpponent, //Did it put a cube in opponent switch during autonomous?
            @Field("entry.2107845895") String autoScale,    //Did it put a cube in the scale during autonomous?
            @Field("entry.799149728") String telHome,       //Cubes in home during teleop
            @Field("entry.721096757") String telOpponent,   //Cubes in opponent during teleop
            @Field("entry.898469043") String telScale,      //Cubes in scale during teleop
            @Field("entry.1297945363") String cubesDropped, //Number of cubes dropped
            @Field("entry.2079952567") String penalties,    //Number of penalties
            @Field("entry.652412432") String climbProcess   //Describe the climb process
    );
}
