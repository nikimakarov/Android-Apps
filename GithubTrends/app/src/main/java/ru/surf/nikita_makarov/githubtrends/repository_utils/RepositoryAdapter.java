package ru.surf.nikita_makarov.githubtrends.repository_utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import ru.surf.nikita_makarov.githubtrends.R;
import ru.surf.nikita_makarov.githubtrends.database.DatabaseHelper;
import ru.surf.nikita_makarov.githubtrends.database.RepositoryDetails;

public class RepositoryAdapter extends RecyclerView.Adapter<RepositoryAdapter.RepositoryViewHolder> {

    public List<RepositoryInfo> repositoryList;
    public Context context;
    public DatabaseHelper databaseRepositoryHelper = null;

    public RepositoryAdapter() {
        super();
        repositoryList = new ArrayList<>();
    }

    public void addData(List<RepositoryInfo> addList, Context addContext) {
        context = addContext;
        repositoryList = addList;
        notifyDataSetChanged();
    }

    public void addDataAndSendRepositoriesToDatabase(List<RepositoryInfo> addList, Context addContext){
        try {
            context = addContext;
            repositoryList = addList;
            notifyDataSetChanged();
            final Dao<RepositoryDetails, Integer> repoDao = getHelper().getRepositoryDao();
            for (int i = 0; i < repositoryList.size(); i++) {
                RepositoryDetails repositorySpecimen = new RepositoryDetails(repositoryList.get(i));
                repoDao.create(repositorySpecimen);
            }
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void clearData() {
        repositoryList.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return repositoryList.size();
    }

    @Override
    public RepositoryViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.card_repository, viewGroup, false);
        return new RepositoryViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RepositoryViewHolder repositoryViewHolder, int i) {

        RepositoryInfo ri = repositoryList.get(i);
        final String url = ri.getHtml_url();
        repositoryViewHolder.repositoryTextView.setText(ri.getFull_name());
        String descriptionBasic = "Made" + languageShow(ri.getLanguage()) + " by " + ri.getAuthor();
        repositoryViewHolder.authorWithLanguageTextView.setText(descriptionBasic);
        repositoryViewHolder.descriptionTextView.setText(ri.getDescription());
        repositoryViewHolder.forksTextView.setText(String.format(Locale.ROOT, "%d%n", ri.getForks_count()));
        repositoryViewHolder.starsTextView.setText(String.format(Locale.ROOT, "%d%n",ri.getStargazers_count()));
        repositoryViewHolder.mainLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendToWebPage(url);
            }
        });
    }

    public String languageShow(String language){
        try{
            if (!language.equals("null")){
                return " on " + language;
            } else {
                return "";
            }
        }
        catch(NullPointerException ex){
            return "";
        }
    }

    public void sendToWebPage(String url){
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        context.startActivity(browserIntent);
    }

    public DatabaseHelper getHelper() {
        if (databaseRepositoryHelper == null) {
            databaseRepositoryHelper = OpenHelperManager.getHelper(context,DatabaseHelper.class);
        }
        return databaseRepositoryHelper;
    }

    public class RepositoryViewHolder extends RecyclerView.ViewHolder {

        public TextView repositoryTextView;
        public TextView authorWithLanguageTextView;
        public TextView forksTextView;
        public TextView starsTextView;
        public TextView descriptionTextView;
        public LinearLayout mainLinearLayout;

        public RepositoryViewHolder(View v) {
            super(v);
            mainLinearLayout = (LinearLayout) v.findViewById(R.id.linearLayoutRepository);
            repositoryTextView = (TextView)  v.findViewById(R.id.login);
            authorWithLanguageTextView = (TextView) v.findViewById(R.id.language);
            descriptionTextView = (TextView) v.findViewById(R.id.description);
            forksTextView = (TextView) v.findViewById(R.id.forks);
            starsTextView = (TextView) v.findViewById(R.id.stars);
        }
    }

}