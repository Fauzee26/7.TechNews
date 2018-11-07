package fauzi.hilmy.technews;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import fauzi.hilmy.technews.response.ArticlesItem;
import fauzi.hilmy.technews.response.Source;

import static fauzi.hilmy.technews.WebActivity.EXTRA_LINK;
import static fauzi.hilmy.technews.WebActivity.EXTRA_NAME;
import static fauzi.hilmy.technews.WebActivity.EXTRA_SOURCE;

public class AdapterNews extends RecyclerView.Adapter<AdapterNews.MyViewHolder> {

    List<ArticlesItem> articlesItems;
    Context context;

    AdapterNews(List<ArticlesItem> articlesItems, Context context) {
        this.articlesItems = articlesItems;
        this.context = context;
    }

    @NonNull
    @Override
    public AdapterNews.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);
        return new MyViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull AdapterNews.MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        final Source sources = articlesItems.get(position).getSource();
        holder.txtSource.setText(sources.getName());
        holder.txtTitle.setText(articlesItems.get(position).getTitle());
        holder.txtAuthor.setText("by: " + articlesItems.get(position).getAuthor());

        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        try {
            Date date = dateFormat.parse(articlesItems.get(position).getPublishedAt());
            @SuppressLint("SimpleDateFormat") SimpleDateFormat newDateFormat = new SimpleDateFormat("EEEE, dd MMM yyyy");
            String date_release = newDateFormat.format(date);

            @SuppressLint("SimpleDateFormat") SimpleDateFormat hourFormat = new SimpleDateFormat("HH:mm");
            String date_hour = hourFormat.format(date);
            holder.txtDate.setText(date_release + " at " + date_hour);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Picasso.get()
                .load(articlesItems.get(position).getUrlToImage())
                .fit()
                .into(holder.imgNews);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, WebActivity.class);
                intent.putExtra(EXTRA_NAME, articlesItems.get(position).getTitle());
                intent.putExtra(EXTRA_LINK, articlesItems.get(position).getUrl());
                intent.putExtra(EXTRA_SOURCE, sources.getName());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return articlesItems.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imgNews;
        TextView txtDate, txtAuthor, txtSource, txtTitle;

        MyViewHolder(View itemView) {
            super(itemView);
            imgNews = itemView.findViewById(R.id.imgBerita);
            txtDate = itemView.findViewById(R.id.txtDate);
            txtAuthor = itemView.findViewById(R.id.txtAuthor);
            txtSource = itemView.findViewById(R.id.txtSource);
            txtTitle = itemView.findViewById(R.id.txtTitle);
        }
    }
}