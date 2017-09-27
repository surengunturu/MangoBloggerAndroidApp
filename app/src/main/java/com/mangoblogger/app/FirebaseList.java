package com.mangoblogger.app;


import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class FirebaseList extends Fragment {

    List<BlogModel> list = new ArrayList<>();  /* List of a Model class */
    FirebaseDataAdapter firebaseDataAdapter;

    RecyclerView recyclerView;
    private Context context;


    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    Firebase ref = new Firebase("https://fir-test-51eed.firebaseio.com/Blogger"); /* connect to firebase*/

    private int lastPoistion;
    private String blog = "blog";
    private boolean switchWindow = false;


    public FirebaseList() {
        // Required empty public constructor
    }



    public static FirebaseList newInstance() {
        FirebaseList firebaseList = new FirebaseList();
        return firebaseList;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_firebase_list, container ,false);

        recyclerView = (RecyclerView) view.findViewById(R.id.list_db);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(new GridSpacing(10));

        return view;
    }


   @Override
    public void  onStart() {
        super.onStart();

       ref.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(DataSnapshot dataSnapshot) {

               list.clear();
               for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {

                   BlogModel blogModel = dataSnapshot1.getValue(BlogModel.class);
                   list.add(blogModel);

               }

               firebaseDataAdapter = new FirebaseDataAdapter(list, getActivity());
               recyclerView.setAdapter(firebaseDataAdapter);
               recyclerView.getLayoutManager().scrollToPosition(lastPoistion);
               firebaseDataAdapter.setOnItemClickListener(new FirebaseDataAdapter.OnItemClickListener() {
                   @Override
                   public void itemClick(String title, String description, String image, int Position) {

                       switchWindow = true;
                       lastPoistion = Position;
                       Intent intent=new Intent(getActivity(),DescriptionActivity.class);
                       intent.putExtra("TITLE", title);
                       intent.putExtra("DESCRIPTION", description);
                       intent.putExtra("IMAGE", image);
                       startActivity(intent);
                       getActivity().overridePendingTransition(R.anim.enter_anim,R.anim.exit_anim);

                   }
               });



           }

           @Override
           public void onCancelled(FirebaseError firebaseError) {

           }
       });


    }

    @Override
    public void onResume() {
        super.onResume();
        if (switchWindow) {
            switchWindow = false;
            recyclerView.getLayoutManager().scrollToPosition(lastPoistion);
        }
    }

    /*
        * This GripSpacing class used for making space between items in recyclerview =
        * */
    class GridSpacing extends RecyclerView.ItemDecoration {
        int spacing;
        int span;

        public GridSpacing(int spacing) {
            this.spacing = spacing;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            outRect.left = spacing - span;
            outRect.right = spacing - span;
            outRect.top = spacing;
            outRect.bottom = spacing;
        }

    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}
