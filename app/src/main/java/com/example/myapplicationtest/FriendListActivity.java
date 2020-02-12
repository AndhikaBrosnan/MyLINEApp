package com.example.myapplicationtest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplicationtest.Model.Profile;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class FriendListActivity extends AppCompatActivity {

    private ArrayList<Profile> mProfileList;
    private RequestQueue mRequestQueue;
    ListAdapter listAdapter;
    RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_list);

        TextView profileName = (TextView)findViewById(R.id.textViewName);
        TextView profileStatus = (TextView)findViewById(R.id.textViewStat);
        CircleImageView profileAvatar = (CircleImageView) findViewById(R.id.profile_image);

        Intent intent = getIntent();
        String displayName = intent.getStringExtra("display_name");
        String displayStatus = intent.getStringExtra("status_message");
        String avatarURL = intent.getStringExtra("picture_url");

        mRecyclerView = findViewById(R.id.friendsListRV);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));


        // TODO: 2/12/2020 setText from intent Extra
        profileName.setText(displayName);
        profileStatus.setText(displayStatus);
        Picasso.get().load(avatarURL).into(profileAvatar);

        // TODO: 2/12/2020 Adapter using profiles_api.json
        mProfileList = new ArrayList<>();
        mRequestQueue = Volley.newRequestQueue(this);

        listAdapter = new ListAdapter(getApplicationContext(),mProfileList);
        mRecyclerView.setAdapter(listAdapter);

        parseJSON();


        // TODO: 2/12/2020 Get List in RecyclerView\

    }

    private void parseJSON() {
        String url = "https://api.myjson.com/bins/1cfrl8";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("profiles");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject prof = jsonArray.getJSONObject(i);

                                String profileName = prof.getString("name");
                                String imageUrl = prof.getString("imgUrl");
                                String profileStatus = prof.getString("status");

                                mProfileList.add(new Profile(profileName, profileStatus,imageUrl));
                            }



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        mRequestQueue.add(request);
    }


}
