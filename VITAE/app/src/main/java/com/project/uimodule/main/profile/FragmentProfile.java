// Developer: Ahmet Kaymak
// Date: 27.02.2016

package com.project.uimodule.main.profile;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ahmetkaymak.vitae.R;
import com.project.postmodule.UserPost;
import com.project.uimodule.main.timeline.adapter.UserPostAdapter;
import com.project.restservice.ApiClient;
import com.project.usermodule.Patient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentProfile extends Fragment {

    private List<UserPost> postList = new ArrayList<>();
    private RecyclerView recyclerView;
    private UserPostAdapter mAdapter;
    private View profileView;

    private TextView userName;
    private TextView userId;
    private TextView aboutMe;
    private ImageView profile_picture;
    private Bitmap bitmap;
    private String userID;

    public FragmentProfile(String userID) {
        this.userID=userID;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        profileView = inflater.inflate( R.layout.fragment_profile, container, false );
        userName = (TextView) profileView.findViewById( R.id.fragment_profile_txtUserName );
        userId = (TextView) profileView.findViewById( R.id.fragment_profile_txtUserId );
        aboutMe = (TextView) profileView.findViewById( R.id.fragment_profile_txtAboutMe );
        profile_picture = (ImageView) profileView.findViewById( R.id.fragment_profile_circle_image );

        getPatientProfileInformations();
        getUserProfilePost();

        return profileView;
    }

    private void getPatientProfileInformations() {
        try {
            ApiClient.userApi().getPatientProfileInformation(userID).enqueue( new Callback<Patient>() {
                @Override
                public void onResponse(Call<Patient> call, Response<Patient> response) {
                    if (response.isSuccessful()) {
                        userName.setText( response.body().getUserName() );
                        userId.setText( response.body().getUserId() );
                        aboutMe.setText( response.body().getAboutMe() );

                        bitmap = BitmapFactory.decodeResource( profileView.getResources(),R.drawable.my_picture );
                        Bitmap circleBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
                        BitmapShader shader = new BitmapShader( bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP );
                        Paint paint = new Paint();
                        paint.setShader( shader );
                        paint.setAntiAlias( true );
                        Canvas c = new Canvas( circleBitmap );
                        c.drawCircle( bitmap.getWidth() / 2, bitmap.getHeight() / 2, bitmap.getWidth() / 2, paint );
                        profile_picture.setImageBitmap( circleBitmap );
                    }
                }

                @Override
                public void onFailure(Call<Patient> call, Throwable t) {
                    Log.e( "UserTimeline", t.getMessage() );
                    Toast.makeText( getActivity(), t.getMessage(), Toast.LENGTH_LONG ).show();
                }
            } );
        } catch (Exception e) {
            Log.e( "UserTimeline", e.getMessage() );
            Toast.makeText( this.getContext(), e.getMessage(), Toast.LENGTH_LONG ).show();
        }
    }

    private void getUserProfilePost() {
        try {
            ApiClient.postApi().getUserPostById(userID).enqueue( new Callback<UserPost>() {
                @Override
                public void onResponse(Call<UserPost> call, Response<UserPost> response) {
                    if (response.isSuccessful()) {
                        postList = (ArrayList) response.body().getPosts();
                        recyclerView = (RecyclerView) profileView.findViewById( R.id.fragment_profile_recycler_view );
                        mAdapter = new UserPostAdapter( postList );
                        recyclerView.setHasFixedSize( true );
                        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager( getActivity() );
                        recyclerView.setLayoutManager( mLayoutManager );
                        recyclerView.setItemAnimator( new DefaultItemAnimator() );
                        recyclerView.setAdapter( mAdapter );
                        mAdapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onFailure(Call<UserPost> call, Throwable t) {
                    Log.e( "UserTimeline", t.getMessage() );
                    Toast.makeText( getActivity(), t.getMessage(), Toast.LENGTH_LONG ).show();
                }
            } );
        } catch (Exception e) {
            Log.e( "UserTimeline", e.getMessage() );
            Toast.makeText( this.getContext(), e.getMessage(), Toast.LENGTH_LONG ).show();
        }
    }
}
