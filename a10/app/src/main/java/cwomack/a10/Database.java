package cwomack.a10;

import android.util.Log;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Main function to create new items in the Firebase database
 */
public class Database {
    public static void add(FirebaseFirestore db, String selectedCollection, ListItem item) {
        Map<String, Object> listItem = new HashMap<>();
        listItem.put("item", item);
        db.collection(selectedCollection)
                .add(listItem)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("DATABASE", "Item added:" + documentReference);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("DATABASE", "Failed to add item: " + Arrays.toString(e.getStackTrace()));
                    }
                });
    }

    /**
     * getList function to load the collection when app is relaunched, or tab is selected
     * @param db database parameter
     * @param selectedCollection collected that is selected with tabs
     * @param items each item in the list
     * @param itemsAdapter the list itself
     */
    public static void getList(FirebaseFirestore db, String selectedCollection,
                               ArrayList<ListItem> items, ArrayAdapter<ListItem> itemsAdapter) {
        db.collection(selectedCollection)
                .orderBy("item.dttm")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(QueryDocumentSnapshot doc : task.getResult()){
                                long dttm = doc.getLong("item.dttm");
                                String item = doc.getString("item.item");
                                items.add(new ListItem(dttm, item));
                            }
                            itemsAdapter.notifyDataSetChanged();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("DATABASE", "Failed to get list: " + Arrays.toString(e.getStackTrace()));
                    }
                });
    }

    /**
     * removeItem function removes items when they are long pressed
     *@param db database parameter
     *@param selectedCollection collected that is selected with tabs
     * @param items each item in the list
     * @param itemsAdapter the list itself
     */
    public static void removeItem(FirebaseFirestore db, String selectedCollection,
                                  ArrayList<ListItem> items, ArrayAdapter<ListItem> itemsAdapter, ListItem removedItem){
        db.collection(selectedCollection).whereEqualTo("item.dttm", removedItem.getDttm())
                .whereEqualTo("item.item", removedItem.getItem())
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for(DocumentSnapshot doc : queryDocumentSnapshots){
                            db.collection(selectedCollection).document(doc.getId()).delete();
                        }
                    }
                });
    }
}
