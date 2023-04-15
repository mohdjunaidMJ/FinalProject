package algonquin.cst2335.finalproject;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class menu_main extends AppCompatActivity {



    @Override
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
}