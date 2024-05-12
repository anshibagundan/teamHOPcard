package jp.ac.ritsumei.ise.phy.exp2.is0674hk.dementia_quiz;

import android.location.Location;
import android.service.autofill.UserData;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

import java.util.List;

    public interface ApiService {

        @GET("/quiz-tfs/")
        Call<List<Quiz_TF>> getQuiz_tfs();
        @GET("/quiz-tfs/{id}/")
        Call<Quiz_TF> getQuiz_tfsById(@Path("id") int quiz_tfsId);
        @GET("/act-tfs/")
        Call<List<Act_TF>> getAct_tfs();
        @GET("/act-tfs/{id}/")
        Call<Act_TF> getAct_tfsById(@Path("id") int act_tfsId);


        @POST("/quiz-selects/")
        Call<Void> insertQuiz_selectData(@Body Quiz_select data);
        @POST("/act-selects/")
        Call<Void> insertAct_selectData(@Body Act_select data);



    }
