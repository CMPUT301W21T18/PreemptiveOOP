package com.example.preemptiveoop.post;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.preemptiveoop.R;
import com.example.preemptiveoop.post.model.Post;
import com.example.preemptiveoop.post.model.Question;

import java.util.ArrayList;

/**
 * The ExpArrayAdatper class is an ArrayAdapter class to be used by QuestionListActivity and
 * ReplyListFragment to display a single item in the list.
 * @param <T>   The type of post.
 */
public class PostArrayAdapter <T extends Post> extends ArrayAdapter<T> {
    private Context context;
    private  ArrayList<T> posts;

    public PostArrayAdapter(@NonNull Context context, ArrayList<T> posts) {
        super(context, 0, posts);
        this.context = context;
        this.posts = posts;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (convertView == null) {
            view = LayoutInflater.from(context).inflate(R.layout.activity_question_list_content, parent,false);
        }

        Post selectPost = posts.get(position);

        TextView tvTitle = view.findViewById(R.id.TextView_question_title_content);
        TextView tvBody = view.findViewById(R.id.TextView_question_body_content);
        TextView tvCreator = view.findViewById(R.id.TextView_question_creator_content);
        TextView tvDate = view.findViewById(R.id.TextView_quetion_date_conent);
        TextView tvNumReply = view.findViewById(R.id.TextView_question_reply_num_content);

        tvTitle.setText("\n\n   \uD83C\uDFF7️"+selectPost.getTitle());
        tvBody.setText("\n   \uD83D\uDCDD"+selectPost.getBody());

        tvCreator.setText("\n   \uD83E\uDD13Created by: " + selectPost.getPoster());
        tvDate.setText("   \uD83D\uDDD3️"+selectPost.getCreationDate().toString());

        if (selectPost instanceof Question)
            tvNumReply.setText("   \uD83D\uDCE9Replies: " + ((Question) selectPost).getReplies().size());
        return view;
    }
}
