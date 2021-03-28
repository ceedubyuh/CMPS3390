package cwomack.a10;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements TextView.OnEditorActionListener{

    private ListView listView;
    private EditText input;
    private TabLayout tabs;
    private ArrayList<ListItem> items;
    private ArrayAdapter<ListItem> itemsAdapter;
    private String selectedCollection = "Todo";
    private FirebaseFirestore db = FirebaseFirestore.getInstance();


    /**
     * Main function for creating the content view of the app
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //ListView Code
        listView = findViewById(R.id.lvItems);
        items = new ArrayList<>();
        itemsAdapter = new ArrayAdapter<>(this, R.layout.list_item_layout, items);
        listView.setAdapter(itemsAdapter);
        setupLongClickHandler();
        //End ListView Code

        //EditText Code
        input = findViewById(R.id.etInput);
        input.setOnEditorActionListener(this);
        //End EditText Code

        //TabLayout Code
        tabs = findViewById(R.id.tabLayout);
        setUpTabClickListener();
        //End TabLayout Code

        updateList();
    }

    /**
     * Void function that updates the list from Firebase when app is opened, or tab is changed/
     */
    private void updateList() {
        showToast("Getting List", Toast.LENGTH_SHORT);
        Database.getList(db, selectedCollection, items, itemsAdapter);
    }

    /**
     * Function to clear the list when a new tab is clicked
     */
    private void setUpTabClickListener() {
        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //Clear Items
                items.clear();
                //Set the selected collection
                selectedCollection = tab.getText().toString();
                //Load Items from collection
                updateList();
                //notify data set changed
                itemsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    /**
     * Function to handle long presses on list items and delete them from the app and Firebase
     */
    private void setupLongClickHandler() {
    listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView <?> parent, View view, int position, long id) {
            ListItem tmpItem = items.remove(position);
            itemsAdapter.notifyDataSetChanged();
            Database.removeItem(db, selectedCollection, items, itemsAdapter, tmpItem);
            showToast("Removed Item", Toast.LENGTH_SHORT);
            return true;
            }
        });
    }

    /**
     * Function to display a Toast message in the middle of the screen when certain events are triggered
     * @param message toast message
     * @param length how long the toast remains on screen
     */
    private void showToast(String message, int length) {
        Toast toast = Toast.makeText(this, message, length);
        toast.setGravity(Gravity.CENTER, 0, -30);
        toast.show();
    }

    /**
     * Function to add items to the list and Firebase
     * @param v The in app text view
     * @param actionId
     * @param event
     * @return true
     */
    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if(event == null || event.getAction() == KeyEvent.ACTION_UP) {
            ListItem tmpItem = new ListItem(input.getText().toString());
            items.add(tmpItem);
            input.setText("");
            itemsAdapter.notifyDataSetChanged();
            Database.add(db, selectedCollection, tmpItem);
            showToast("Item Added", Toast.LENGTH_SHORT);
        }
        return true;
    }
}