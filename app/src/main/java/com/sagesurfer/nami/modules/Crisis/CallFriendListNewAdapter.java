package com.sagesurfer.nami.modules.Crisis;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sagesurfer.nami.R;
import com.sagesurfer.nami.storage.preferences.Preferences;

import java.lang.reflect.Type;
import java.util.ArrayList;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class CallFriendListNewAdapter extends RecyclerView.Adapter<CallFriendListNewAdapter.MyViewHolder> {

    private static final String TAG = CallFriendListNewAdapter.class.getSimpleName();
    public final ArrayList<CallFriend> friendList;
    private final Context mContext;

    int color;

    public CallFriendListNewAdapter(Context mContext, ArrayList<CallFriend> friendList) {
        this.friendList = friendList;
        this.mContext = mContext;

    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        final TextView nameText;
        final TextView teamText;

        MyViewHolder(View view) {
            super(view);
            nameText = (TextView) view.findViewById(R.id.txtFriend_name);
            teamText = (TextView) view.findViewById(R.id.txtFriend_phone);


        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.call_friend_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(final MyViewHolder viewHolder, final int position) {
        int color1 = Color.parseColor("#000000");

        viewHolder.teamText.setTextColor(color1);
        viewHolder.teamText.setText(friendList.get(position).getName());
        viewHolder.nameText.setText(friendList.get(position).getPhone());

    }

    public interface CaseloadListAdapterListener {
        void onDetailsLayoutClicked(CallFriend caseload_);


    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return friendList.size();
    }


}
