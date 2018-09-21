package com.example.bgroseclose.interviewquestion.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bgroseclose.interviewquestion.R;
import com.example.bgroseclose.interviewquestion.Repository.RepositoryActivity;
import com.example.bgroseclose.interviewquestion.Repository.RepositoryModel;
import com.example.bgroseclose.interviewquestion.WebView.WebViewActivity;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class RepositoryAdapter extends RecyclerView.Adapter<RepositoryAdapter.ViewHolder> {

    Context context;
    private List<RepositoryModel> mRepositories;

    public RepositoryAdapter(List<RepositoryModel> repositories) {
        mRepositories = repositories;
    }

    @NonNull
    @Override
    public RepositoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View respositoryView = inflater.inflate(R.layout.repostiory_recycler_view_holder, parent, false);

        ViewHolder viewHolder = new ViewHolder(respositoryView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final RepositoryAdapter.ViewHolder holder, final int position) {
        final RepositoryModel repository = mRepositories.get(position);

        try {
            Date updatedDate = new SimpleDateFormat("dd/MM/yyyy").parse(repository.getUpdateDate());
            holder.repositoryUpdatedDate.setText(updatedDate.toString());
        } catch (ParseException e) {
            e.printStackTrace();
            holder.repositoryUpdatedDate.setText(repository.getUpdateDate());
        }

        holder.repositoryTitle.setText(repository.getName());
        if(repository.getDescription() == "") {
            holder.repositoryDescription.setText("No Description");
        }
        holder.repositoryDescription.setText(repository.getDescription());
        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, WebViewActivity.class);
                intent.putExtra(context.getString(R.string.passed_url_extra), repository.getUrl());
                intent.putExtra(context.getString(R.string.passed_title), repository.getName());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mRepositories.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView repositoryTitle;
        public TextView repositoryDescription;
        public TextView repositoryUpdatedDate;
        public RelativeLayout parentLayout;

        public ViewHolder(View itemView) {
            super(itemView);

            parentLayout = itemView.findViewById(R.id.repository_holder);
            repositoryTitle = itemView.findViewById(R.id.repository_title);
            repositoryDescription = itemView.findViewById(R.id.repository_description);
            repositoryUpdatedDate = itemView.findViewById(R.id.repository_updated_date);
        }
    }
}
