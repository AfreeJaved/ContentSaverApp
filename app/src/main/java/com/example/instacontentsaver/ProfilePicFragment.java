package com.example.instacontentsaver;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.apache.commons.lang3.StringUtils;

public class ProfilePicFragment extends Fragment {

    String URL="NULL";
    ImageView mparticularprofilepic;
    EditText getprofilepiclink;
    Button getprofilepic;
    Button downloadprofilepic;
    String profilepicurl="1";
    private Uri uri2;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.profilepic_fragment,null);
        getprofilepic=v.findViewById(R.id.getprofilepic);
        getprofilepiclink=v.findViewById(R.id.getprofilepiclink);
        downloadprofilepic=v.findViewById(R.id.downloadprofilepic);
        mparticularprofilepic=v.findViewById(R.id.particularprofilepic);

        getprofilepic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                URL =getprofilepiclink.getText().toString().trim();
                if(getprofilepiclink.equals("NULL"))
                {
                    Toast.makeText(getContext(),"First Enter URL",Toast.LENGTH_SHORT).show();

                }
                else
                {
                    String result2= StringUtils.substringBefore(URL,"/?");
                    URL=result2+"/?__a=1";
                    processdata();
                }

            }
        });

        downloadprofilepic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!profilepicurl.equals("1")){

                    DownloadManager.Request request=new DownloadManager.Request(uri2);
                    request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI|DownloadManager.Request.NETWORK_MOBILE);
                    request.setTitle("Download");
                    request.setDescription(".......");
                    request.allowScanningByMediaScanner();
                    request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
                    request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DCIM,""+System.currentTimeMillis()+".jpeg");
                    DownloadManager manager=(DownloadManager) getActivity().getSystemService(Context.DOWNLOAD_SERVICE);
                    manager.enqueue(request);
                    Toast.makeText(getContext(), "Downloaded", Toast.LENGTH_SHORT).show();

                }
                else{
                    Toast.makeText(getContext(), "No profile picture to download", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return v;
    }
    private void processdata(){

        StringRequest request=new StringRequest(URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.create();

                MainURL mainURL = gson.fromJson(response, MainURL.class);
                profilepicurl = mainURL.getGraphql().getShortcode_media().getDisplay_url();
                uri2 = Uri.parse(profilepicurl);
                Glide.with(getContext()).load(uri2).into(mparticularprofilepic);


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(),"Not able fetch data",Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue queue= Volley.newRequestQueue(getContext());
        queue.add(request);

    }
}
