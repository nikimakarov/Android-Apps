package ru.surf.nikita_makarov.githubtrends.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.surf.nikita_makarov.githubtrends.R;
import ru.surf.nikita_makarov.githubtrends.repository_utils.RepositoryAdapter;
import ru.surf.nikita_makarov.githubtrends.repository_utils.RepositoryInfo;
import ru.surf.nikita_makarov.githubtrends.repository_utils.RepositoryInfoResponse;
import ru.surf.nikita_makarov.githubtrends.utils.GitHubService;

public class PageFragment extends Fragment {

    public int pageNumber;
    public String API_URL = "https://api.github.com/";
    public String chosenDate = "created:2016-07-05";
    public RecyclerView mRecyclerView;
    public RepositoryAdapter repositoryAdapter;
    public List<RepositoryInfo> result;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageNumber = getArguments() != null ? getArguments().getInt("num") : 1;
        result = new ArrayList<>();
    }

    @Override
    public void onStart(){
        super.onStart();
        retrofitStart();
    }

    public static PageFragment newInstance(int page) {
        PageFragment fragment = new PageFragment();
        Bundle args = new Bundle();
        args.putInt("num", page);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mRecyclerView = (RecyclerView) getView().findViewById(R.id.recycler_main);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        repositoryAdapter = new RepositoryAdapter();
        repositoryAdapter.addData(result, getContext());
        mRecyclerView.setAdapter(repositoryAdapter);
    }

    public void retrofitStart() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient httpClient = new OkHttpClient.Builder().addInterceptor(interceptor).build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .build();

        GitHubService gitHubService = retrofit.create(GitHubService.class);
        Call<RepositoryInfoResponse> call = gitHubService.repositories(chosenDate, "stars", "desc", "1", "20");
        call.enqueue(new Callback<RepositoryInfoResponse>() {
            @Override
            public void onResponse(Call<RepositoryInfoResponse> call, Response<RepositoryInfoResponse> response) {
                result = response.body().getItems();
                repositoryAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<RepositoryInfoResponse> call, Throwable t) {
                Log.d("Error: ", t.getMessage());
            }
        });
    }

    public String dateInterval(String dateFilter) {
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        String zero1 = month < 10 ? "0" : "";
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        String zero2 = day < 10 ? "0" : "";
        switch (dateFilter) {
            case "today":
                return (year + "-" + zero1 + month + "-" + zero2 + day);
            case "week":
                return (year + "-" + zero1 + month + "-" + zero2 + (day - 7));
            case "month":
                return (year + "-" + zero1 + (month - 1) + "-" + zero2 + day);
            case "year":
                return ((year - 1) + "-" + zero1 + month + "-" + zero2 + day);
            default:
                return (year + "-" + zero1 + month + "-" + zero2 + day);
        }
    }
}
