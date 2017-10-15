package com.mangobloggerandroid.app.fragment;


import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.firebase.database.FirebaseDatabase;
import com.mangobloggerandroid.app.adapter.FirebaseDataAdapter;
import com.mangobloggerandroid.app.R;
import com.mangobloggerandroid.app.model.BlogModel;


import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class FirebaseListFragment extends Fragment {
    private static final String URL = "url";

    private List<BlogModel> mBlogList;  /* List of a Model class */
    private FirebaseDataAdapter firebaseDataAdapter;

    private RecyclerView recyclerView;
    private ProgressBar mProgressBar;

    private Firebase mFirebaseRef;
    private int lastPoistion;
    private boolean switchWindow = false;
    private String mUrl;


    public FirebaseListFragment() {
        // Required empty public constructor
    }



    public static FirebaseListFragment newInstance(String url) {
        FirebaseListFragment fragment = new FirebaseListFragment();
        Bundle args = new Bundle();
        args.putString(URL, url);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mUrl = getArguments().getString(URL);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_recycler_view, container ,false);

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        mProgressBar = (ProgressBar) view.findViewById(R.id.progress_bar);
        mProgressBar.setVisibility(View.VISIBLE);
        final GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 1);

        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(1, 20, true));


        mBlogList = new ArrayList<>();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        mFirebaseRef = new Firebase(mUrl); /* connect to firebase*/


        return view;
    }


   @Override
    public void  onStart() {
        super.onStart();

       mFirebaseRef.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(DataSnapshot dataSnapshot) {

               mBlogList.clear();
               for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {

                   BlogModel blogModel = dataSnapshot1.getValue(BlogModel.class);
                   mBlogList.add(blogModel);

               }

               firebaseDataAdapter = new FirebaseDataAdapter(mBlogList, getActivity());
               recyclerView.setAdapter(firebaseDataAdapter);
               recyclerView.getLayoutManager().scrollToPosition(lastPoistion);
               mProgressBar.setVisibility(View.GONE);
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



    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        private GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }
}
