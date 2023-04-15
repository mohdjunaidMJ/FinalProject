/**
 * This is main activity class for the News application.
 * It displays a list of news articles that match a user's search query or displays top headlines when no search query is entered.
 * The activity also allows the user to view a list of their favorite articles and displays a dialog with information about the app when the About Us button is clicked.
 * The activity inflates a menu that includes a Help option. Clicking on the Help option displays an AlertDialog with information about logging in.
 * The activity uses Retrofit to retrieve news articles from the News API.
 * The activity also uses SharedPreferences to save the user's last searched topic and retrieve it
 * when the activity is recreated.
 */
package algonquin.cst2335.finalproject;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {


    RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout;
    EditText etQuery;
    Button btnSearch,btnAboutUs, btnViewTopics;
    Dialog dialog;
    final String API_KEY = "TWs86Gd9xFjOkJpQeDvJkVHz3uU0HGdg";
    final String SHARED_PREFS_FILE = "searched_topic_prefs";
    final String KEY_SEARCHED_TOPIC = "searched_topic_key";
    Adapter adapter;
    List<Articles>  articles = new ArrayList<>();
    List<Articles> favoriteArticles = new ArrayList<>();

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        swipeRefreshLayout = findViewById(R.id.swipeRefresh);
        recyclerView = findViewById(R.id.recyclerView);

        etQuery = findViewById(R.id.etQuery);
        btnSearch = findViewById(R.id.btnSearch);
        btnAboutUs = findViewById(R.id.aboutUs);
        btnViewTopics = findViewById(R.id.btnViewTopics);
        dialog = new Dialog(MainActivity.this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        sharedPreferences = getSharedPreferences(SHARED_PREFS_FILE, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        String lastSearchedTopic = sharedPreferences.getString(KEY_SEARCHED_TOPIC, "");
        etQuery.setText(lastSearchedTopic);

        if (!lastSearchedTopic.isEmpty()) {
            loadArticlesFromSearch();
        }


        swipeRefreshLayout.setOnRefreshListener(() -> {
            String query = etQuery.getText().toString().trim();
            if (!query.isEmpty()) {
                retrieveArticles(query);
            } else {
                retrieveTopArticles();
            }
        });

        btnSearch.setOnClickListener(v -> {
            String query = etQuery.getText().toString().trim();
            if (!query.isEmpty()) {
                // Save the searched topic to shared preferences
                editor.putString(KEY_SEARCHED_TOPIC, query);
                editor.apply();
                loadArticlesFromSearch();
            } else {
                Toast.makeText(MainActivity.this, "Please enter a search term", Toast.LENGTH_SHORT).show();
            }
        });

        btnAboutUs.setOnClickListener(v -> showDialog());

        btnViewTopics.setOnClickListener(v -> showFavoriteArticles());
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        // Get the "app_bar_log_help" menu item
        MenuItem helpItem = menu.findItem(R.id.app_bar_log_help);

        // Set an OnMenuItemClickListener if the menu item exists
        if (helpItem != null) {
            helpItem.setOnMenuItemClickListener(item -> {
                // Build the AlertDialog
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle(getString(R.string.help_title));
                builder.setMessage(getString(R.string.login_help_menu));
                // Add an "OK" button to dismiss the dialog

                builder.setPositiveButton("OK", (dialog, which) -> dialog.dismiss());
                // Show the AlertDialog
                builder.show();

                return true;
            });
        }
        return true; // Return true to indicate that the menu was successfully created
    }
    private void retrieveTopArticles() {
    }

    private void showFavoriteArticles() {
        if (favoriteArticles.isEmpty()) {
            Toast.makeText(MainActivity.this, "You have no saved articles.", Toast.LENGTH_SHORT).show();
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("Favorite Articles");
            builder.create().show();
        }
    }

    private void loadArticlesFromSearch() {
        String query = etQuery.getText().toString().trim();
        if (!query.isEmpty()) retrieveArticles(query);
    }

    private void retrieveArticles(String query) {
        swipeRefreshLayout.setRefreshing(true);
        Call<Headlines> call = ApiClient.getInstance().getApi().getArticles(query, API_KEY);
        call.enqueue(new Callback<Headlines>() {
            private View view;

            @Override
            public void onResponse(Call<Headlines> call, Response<Headlines> response) {
                if (response.isSuccessful() && response.body().getArticles() != null) {
                    swipeRefreshLayout.setRefreshing(false);
                    articles.clear();
                    articles = response.body().getArticles();
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<Headlines> call, Throwable t) {
                swipeRefreshLayout.setRefreshing(false);
                Snackbar.make(view, t.getLocalizedMessage(), Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    public void showDialog(){
        Button btnClose;
        dialog.setContentView(R.layout.about_us_pop_up);
        dialog.show();
        btnClose = dialog.findViewById(R.id.close);

        btnClose.setOnClickListener(v -> dialog.dismiss());
    }
}