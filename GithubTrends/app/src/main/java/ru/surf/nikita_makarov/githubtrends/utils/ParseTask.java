package ru.surf.nikita_makarov.githubtrends.utils;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;
import java.util.TimeZone;

public class ParseTask extends AsyncTask<Void, Void, String> {

        public HttpURLConnection urlConnection = null;
        public BufferedReader reader = null;
        public String resultJson = "";
        final public String urlToRepositories = "https://api.github.com/search/repositories?";
        final public String created = "q=created:>";
        final public String sort = "&sort=stars&order=desc";
        final public String LOG_TAG = "ParseTask";

        @Override
        protected String doInBackground(Void... params) {
            try {
                URL url = new URL(urlToRepositories + created + dateInterval("today") + sort);

                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();

                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }

                resultJson = buffer.toString();

            } catch (Exception e) {
                e.printStackTrace();
            }
            return resultJson;
        }

        @Override
        protected void onPostExecute(String strJson) {
            super.onPostExecute(strJson);
            JSONObject dataJsonObject;
            try {
                dataJsonObject = new JSONObject(strJson);
                JSONArray items = dataJsonObject.getJSONArray("items");
                for (int i = 0; i < 20; i++) {
                    JSONObject item = items.getJSONObject(i);
                    JSONObject owner = item.getJSONObject("owner");
                    String login = owner.getString("login");
                    String name = item.getString("name");
                    String id = item.getString("id");
                    String url = item.getString("url");
                    String language = item.getString("language");
                    int forks = item.getInt("forks_count");
                    int stars = item.getInt("stargazers_count");
                    Log.d(LOG_TAG, "login: " + login);
                    Log.d(LOG_TAG, "name: " + name);
                    Log.d(LOG_TAG, "id: " + id);
                    Log.d(LOG_TAG, "stars: " + stars);
                    Log.d(LOG_TAG, "forks: " + forks);
                    Log.d(LOG_TAG, "url: " + url);
                    Log.d(LOG_TAG, "language: " + language);

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        public String dateInterval(String dateFilter){
            Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            String zero = month < 10 ? "0" : "";
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            switch (dateFilter){
                case "today": return (year + "-" + zero + month + "-" + day);
                case "week" : return (year + "-" + zero + month + "-" + (day-7));
                case "month": return (year + "-" + zero + (month-1) + "-" + day);
                case "year": return ((year-1) + "-" + zero + month + "-" + day);
                default: return (year + "-" + zero + month + "-" + day);
            }
        }
}
