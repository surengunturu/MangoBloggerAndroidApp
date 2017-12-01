package com.mangobloggerandroid.app.fragment;


import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.database.FirebaseDatabase;
import com.mangobloggerandroid.app.interfaces.FragmentDataPasser;
import com.mangobloggerandroid.app.util.PreferenceUtil;
import com.mangobloggerandroid.app.adapter.FirebaseDataAdapter;
import com.mangobloggerandroid.app.R;
import com.mangobloggerandroid.app.model.BlogModel;
import com.mangobloggerandroid.app.model.BlogPostCallback;
import com.mangobloggerandroid.app.model.JsonApi;
import com.mangobloggerandroid.app.model.Posts;


import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class FirebaseListFragment extends Fragment {
    private static final String URL = "url";
    private static final String POST_TYPE = "post_type";

    private List<BlogModel> mBlogList;  /* List of a Model class */
    private FirebaseDataAdapter firebaseDataAdapter;

    private RecyclerView recyclerView;
    private ProgressBar mProgressBar;
    private FragmentDataPasser mDataPasser;

    private Firebase mFirebaseRef;
    private int lastPoistion;
    private boolean switchWindow = false;
    private String mUrl;
    private String mPostType;
    private List<Posts> postsList;
    private Context mContext;



    public FirebaseListFragment() {
        // Required empty public constructor
    }



    public static FirebaseListFragment newInstance(String url, String post_type) {
        FirebaseListFragment fragment = new FirebaseListFragment();
        Bundle args = new Bundle();
        args.putString(URL, url);
        args.putString(POST_TYPE, post_type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        mContext = getContext();

        FirebaseAnalytics analytics = FirebaseAnalytics.getInstance(getContext());
        analytics.setCurrentScreen(getActivity(), getClass().getSimpleName(), getClass().getSimpleName());


        if (getArguments() != null) {
            mUrl = getArguments().getString(URL);
            mPostType = getArguments().getString(POST_TYPE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_recycler_view, container ,false);

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
//        mShimmerView = (ListShimmerView) view.findViewById(R.id.shimmer_view);
        mProgressBar = (ProgressBar) view.findViewById(R.id.progress_bar);
//        mShimmerView.setVisibility(View.VISIBLE);
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
        passData();
        downloadTermsViaWordpress();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Activity activity = getActivity();
        if (activity instanceof FragmentDataPasser) {
            mDataPasser = (FragmentDataPasser) context;

        }
    }

    private void passData() {
        mDataPasser.onDataPass(0);
    }

    @Override
    public void onResume() {
        super.onResume();
        FirebaseAnalytics analytics = FirebaseAnalytics.getInstance(getContext());
        analytics.setCurrentScreen(getActivity(), getClass().getSimpleName(), "Home Screen");

        if (switchWindow) {
            switchWindow = false;
            recyclerView.getLayoutManager().scrollToPosition(lastPoistion);
        }
    }

    private void setupAdapter() {
        if(postsList != null) {
            for(int i=0; i<postsList.size(); i++) {
                Posts post = postsList.get(i);
                String title = post.getTitle_plain();
                String imageUrl ="null";
                if(post.getAttachment() !=null) {
                    imageUrl = post.getAttachment().getUrl();
                }
                String content = post.getContent();
                BlogModel blogModel = new BlogModel(title, content, imageUrl);
                mBlogList.add(blogModel);
            }
            mProgressBar.setEnabled(false);
            mProgressBar.setVisibility(View.GONE);
            PreferenceUtil.writeTerms(mContext, mBlogList, mPostType.equals("ux_term"));
        } else if(PreferenceUtil.isTermsSynced(mContext, mPostType.equals("ux_term"))) {
            mBlogList = PreferenceUtil.getTerms(mContext, mPostType.equals("ux_term"));
            mProgressBar.setEnabled(false);
            mProgressBar.setVisibility(View.GONE);
        }
        else {
            downloadTermsViaFirebase();
        }
        firebaseDataAdapter = new FirebaseDataAdapter(mBlogList, getActivity());
        recyclerView.setAdapter(firebaseDataAdapter);
        recyclerView.getLayoutManager().scrollToPosition(lastPoistion);
//        mShimmerView.setVisibility(View.GONE);


    }

    private void downloadTermsViaWordpress() {
//        mShimmerView.setVisibility(View.VISIBLE);
        mProgressBar.setEnabled(true);
        mProgressBar.setVisibility(View.VISIBLE);

        if(!PreferenceUtil.isTermsSynced(mContext, mPostType.equals("ux_term"))) {
            final RestAdapter adapter = new RestAdapter.Builder()
                    .setEndpoint("https://mangoblogger.com")
                    .build();

            JsonApi jsonApi = adapter.create(JsonApi.class);

            jsonApi.getTerms("get_posts", mPostType, "1000", new Callback<BlogPostCallback>() {
                @Override
                public void success(BlogPostCallback blogPostCallback, Response response) {
                    if (blogPostCallback.getStatus().equals("ok")) {
                        postsList = blogPostCallback.getPosts();
                        setupAdapter();
                    } else {
                        Log.e("Blog Post : ", blogPostCallback.getStatus() + response.getUrl());
                    }
                }

                @Override
                public void failure(RetrofitError error) {
                    Log.e("Blog Post : Fail", error.getLocalizedMessage() + error.getUrl());
                    setupAdapter();
                }
            });


        } else {
            setupAdapter();
        }
    }

    private void downloadTermsViaFirebase() {
        mProgressBar.setVisibility(View.VISIBLE);
        mProgressBar.setEnabled(true);
        mFirebaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                mBlogList.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {

                    BlogModel blogModel = dataSnapshot1.getValue(BlogModel.class);
                    mBlogList.add(blogModel);

                }

                setupAdapter();
                mProgressBar.setEnabled(false);
                mProgressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

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
