package edu.tacoma.uw.jasonli7.team12project.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import edu.tacoma.uw.jasonli7.team12project.R;
import edu.tacoma.uw.jasonli7.team12project.model.DeviceContent;
import edu.tacoma.uw.jasonli7.team12project.model.InfoHolder;
import edu.tacoma.uw.jasonli7.team12project.model.Review;

/**
 * Team 12 Group project.
 *
 * @author Daniel Stocksett.
 *
 * @version 3rd Aug 2020.
 *
 * An activity that creates a scrolling list of clickable reviews.
 */
public class ReviewListActivity extends AppCompatActivity {

    public static final String ARG_Device_ID = "device_id";
    private static String mDeviceName;
    private final ReviewListActivity mParentActivity = null;

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchReviewAddFragment();
            }
        });

        if (findViewById(R.id.review_detail_container) != null) {
            mTwoPane = true;
        }

        View recyclerView = findViewById(R.id.review_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);
    }

    /**
     * helper method for fab.
     */
    private void launchReviewAddFragment() {

            Intent intent = new Intent(this, AddReviewActivity.class);
            intent.putExtra(AddReviewFragment.ARG_REGISTER, mDeviceName);
            startActivity(intent);

    }

    /**
     * Contains the click listener.
     *
     * @param recyclerView
     */
    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        mDeviceName =getIntent().getStringExtra(
                ReviewListActivity.ARG_Device_ID);
        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(this,
                DeviceContent.ITEM_MAP.get(mDeviceName).getReviews(), mTwoPane));
    }

    /**
     * Recycler view listener.
     */
    public static class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final ReviewListActivity mParentActivity;
        private final List<Review> mValues;
        private final boolean mTwoPane;

        private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Review item = (Review) view.getTag();

                InfoHolder.InfoPass.setmReview(item);
                if (mTwoPane) {
                    Bundle arguments = new Bundle();
                    arguments.putString(ReviewDetailFragment.ARG_REVIEW_ID, mDeviceName);
                    ReviewDetailFragment fragment = new ReviewDetailFragment();
                    fragment.setArguments(arguments);
                    mParentActivity.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.review_detail_container, fragment)
                            .commit();
                } else {
                    Context context = view.getContext();
                    Intent intent = new Intent(context, ReviewDetailActivity.class);
                    intent.putExtra(ReviewDetailFragment.ARG_REVIEW_ID, mDeviceName);

                    context.startActivity(intent);
                }
            }
        };

        /**
         * Unchanged.
         *
         * @param parent
         * @param items
         * @param twoPane
         */
        SimpleItemRecyclerViewAdapter(ReviewListActivity parent,
                                      List<Review> items,
                                      boolean twoPane) {
            mParentActivity = parent;
            mValues = items;
            mTwoPane = twoPane;

        }

        /**
         * Unchanged.
         *
         * @param parent
         * @param viewType
         * @return
         */
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.review_list_content, parent, false);

            return new ViewHolder(view);
        }

        /**
         * Unchanged.
         *
         * @param holder
         * @param position
         */
        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.mIdView.setText(mValues.get(position).getmUserName());
            holder.mContentView.setText(String.valueOf(mValues.get(position).getRate()).substring(0,3) + "/5");
            holder.itemView.setTag(mValues.get(position));
            holder.itemView.setOnClickListener(mOnClickListener);
        }

        /**
         * Unchanged.
         *
         * @return
         */
        @Override
        public int getItemCount() {
            return mValues.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            final TextView mIdView;
            final TextView mContentView;

            ViewHolder(View view) {
                super(view);
                mIdView = (TextView) view.findViewById(R.id.id_text);
                mContentView = (TextView) view.findViewById(R.id.content);
            }
        }
    }
}