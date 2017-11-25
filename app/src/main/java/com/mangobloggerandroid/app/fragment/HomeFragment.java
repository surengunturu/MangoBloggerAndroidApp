package com.mangobloggerandroid.app.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.mangobloggerandroid.app.PreferenceUtil;
import com.mangobloggerandroid.app.R;
import com.mangobloggerandroid.app.adapter.HomeBaseAdapter;
import com.mangobloggerandroid.app.model.BlogPostCallback;
import com.mangobloggerandroid.app.model.HomeGroup;
import com.mangobloggerandroid.app.model.HomeItem;
import com.mangobloggerandroid.app.model.JsonApi;
import com.mangobloggerandroid.app.model.Posts;
import com.mangobloggerandroid.app.util.AppUtils;
import com.mangobloggerandroid.app.view.ListShimmerView;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by ujjawal on 10/10/17.
 *
 */

public class HomeFragment extends Fragment {

    FragmentOnClickListener mListener;

    private RecyclerView mRecyclerView;
    private List<Posts> postsList;
//    private ProgressBar mProgressBar;
    private ListShimmerView mShimmerView;

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseAnalytics analytics = FirebaseAnalytics.getInstance(getContext());
        analytics.setCurrentScreen(getActivity(), getClass().getSimpleName(), getClass().getSimpleName());

        if (getArguments() != null) {
//           retreive args
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recycler_view, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        mProgressBar = (ProgressBar) view.findViewById(R.id.progress_bar);
        mShimmerView = (ListShimmerView) view.findViewById(R.id.shimmer_view);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setHasFixedSize(true);

        downloadRecentBlogPosts();


    }

    @Override
    public void onResume() {
        super.onResume();
        FirebaseAnalytics analytics = FirebaseAnalytics.getInstance(getContext());
        analytics.setCurrentScreen(getActivity(), getClass().getSimpleName(), "Home Screen");

    }

    /* @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // verify that host activity implements the callback interface
        try {
            // cast to AvatarPickerDialogListener so we can send events to host
            mListener = (FragmentOnClickListener) context;
        } catch(ClassCastException e) {
            // the activity doesn't implement the interface, throw exception
            throw new ClassCastException(context.toString() +
                    " activity must implement AvatarPickerDialogListener ");
        }
    }*/

    private void setupAdapter() {
        HomeBaseAdapter itemAdapter = new HomeBaseAdapter(getContext());

        itemAdapter.addSnap(new HomeGroup(Gravity.START, HomeBaseAdapter.CARD_SIZE_SMALL, "Explore Mangoblogger", getExploreItems()));
        itemAdapter.addSnap(new HomeGroup(Gravity.START, HomeBaseAdapter.CARD_SIZE_MEDIUM, "Our Recent Blogs", getRecentBlogs()));
        itemAdapter.addSnap(new HomeGroup(Gravity.CENTER, HomeBaseAdapter.CARD_SIZE_PAGER, "Our Services", getServices()));

        mRecyclerView.setAdapter(itemAdapter);


    }

    private List<HomeItem> getExploreItems() {
        List<HomeItem> exploreItems = new ArrayList<>();
        exploreItems.add(new HomeItem("Analytics Terms",
                "https://mangoblogger-android-app.firebaseio.com/analytics", "analytics_term",
                R.mipmap.analytics_cover, false));
        exploreItems.add(new HomeItem("Ux Terms",
                "https://mangoblogger-android-app.firebaseio.com/ux_terms", "ux_term",
                R.mipmap.uxterms_cover, false));
        exploreItems.add(new HomeItem("Blogs",
                "https://www.mangoblogger.com/mangoblogger-blog/", "null",
                R.mipmap.blog_cover, true));

        return exploreItems;
    }

    private List<HomeItem> getRecentBlogs() {
        List<HomeItem> blogs = new ArrayList<>();
        if(postsList != null) {
            for(Posts post:postsList) {
                String title = post.getTitle_plain();
                String url = post.getUrl();
                String author = post.getAuthor().getName();
                int id = R.mipmap.blog_cover;
                HomeItem homeItem = new HomeItem(title, url, author, id, true);
                if(post.getAttachment() != null) {
                    homeItem.setImageUrl(post.getAttachment().getUrl());
                }
                blogs.add(homeItem);
            }
            PreferenceUtil.writeDataString(getContext(),
                    PreferenceUtil.BLOG_LIST_SYNC_DATE, AppUtils.getCurrentDate());
            AppUtils.setList(getContext(), PreferenceUtil.PREFERENCE_BLOG_LIST, blogs);

        } else if(PreferenceUtil.isDataSynced(getContext())) {
            blogs = AppUtils.getListFromSharedPreferences(getContext(), PreferenceUtil.PREFERENCE_BLOG_LIST);
        }
        else {

            blogs.add(new HomeItem("Indian Mobile Congress 2017",
                    "https://www.mangoblogger.com/blog/highlights-of-india-mobile-congress-2017/",
                    "By : Yatin", R.mipmap.recent_blog_one_cover, true));
            blogs.add(new HomeItem("Add Social Login In WordPress Site",
                    "https://www.mangoblogger.com/blog/wordpress-plugin-installation/",
                    "By : Yatin", R.mipmap.recent_blog_two_cover, true));
            blogs.add(new HomeItem("Guide : Google Tag Manager Installation",
                    "https://www.mangoblogger.com/blog/google-tag-manager-installation-website/",
                    "By : Yatin", R.mipmap.recent_blog_three_cover, true));
            blogs.add(new HomeItem("What is Google Analytics",
                    "https://www.mangoblogger.com/blog/what-is-google-analytics/",
                    "By : Siddhant", R.mipmap.recent_blog_four_cover, true));
            blogs.add(new HomeItem("All About Pixel Tracking",
                    "https://www.mangoblogger.com/blog/all-about-tracking-pixel/",
                    "By : Mangoblogger", R.mipmap.recent_blog_five_cover, true));
        }
//        mProgressBar.setEnabled(false);
//        mProgressBar.setVisibility(View.GONE);
        mShimmerView.setVisibility(View.GONE);
        return blogs;
    }

    private List<HomeItem> getServices()  {
        List<HomeItem> services = new ArrayList<>();
        services.add(new HomeItem("Analytics",
                "https://www.mangoblogger.com/product/google-analytics-dashboard/",
                "$100", R.mipmap.service_analytics_cover, true));
        services.add(new HomeItem("Kickstarter Package",
                "https://www.mangoblogger.com/product/google-analytics-and-google-tag-manager/",
                "$200", R.mipmap.service_kickstarter_cover, true));
        services.add(new HomeItem("SEO Consultation",
                "https://www.mangoblogger.com/product/seo-consultation/",
                "$200", R.mipmap.service_seo_cover, true));
        services.add(new HomeItem("Ux Consultation",
                "https://www.mangoblogger.com/product/ux-consultation/",
                "$500", R.mipmap.service_ux_consulation_cover, true));

        return services;
    }

    public interface FragmentOnClickListener {
        void onItemClick(HomeItem homeItem);
    }

    private void downloadRecentBlogPosts() {
//        mProgressBar.setEnabled(true);
//        mProgressBar.setVisibility(View.VISIBLE);
        mShimmerView.setVisibility(View.VISIBLE);

        if(!PreferenceUtil.isDataSynced(getContext())) {
            final RestAdapter adapter = new RestAdapter.Builder()
                    .setEndpoint("https://mangoblogger.com")
                    .build();

            JsonApi jsonApi = adapter.create(JsonApi.class);

            jsonApi.getRecentBlogPosts("1", new Callback<BlogPostCallback>() {
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

}
