package com.example.covid_19vaccination;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import Adapter.FaqAdapter;
import Model.FaqModel;

public class FaqActivity extends AppCompatActivity {
    RecyclerView mRecyclerView;
    List<FaqModel> mFaqModelList;

    int images[] = {R.drawable.faq_logo1, R.drawable.faq_logo2, R.drawable.faq_logo3, R.drawable.faq_logo4, R.drawable.faq_logo5, R.drawable.faq_logo6, R.drawable.faq_logo7, R.drawable.faq_logo8, R.drawable.faq_logo9};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq);
        setTitle("Frequently Asked Questions");
        mRecyclerView = findViewById(R.id.recyclerViewFAQ);

        initData();
        setRecyclerView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.faq_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_homeFaq:
                Intent intent = new Intent(this, Dashboard.class);
                startActivity(intent);
                finish();
                break;

            default:
                // Do nothing
        }
        return super.onOptionsItemSelected(item);
    }

    private void setRecyclerView() {
        FaqAdapter mFaqAdapter = new FaqAdapter(mFaqModelList, images);
        mRecyclerView.setAdapter(mFaqAdapter);
        mRecyclerView.setHasFixedSize(true);
    }

    private void initData() {
        mFaqModelList = new ArrayList<>();

        mFaqModelList.add(new FaqModel(getString(R.string.faqQuestion1), getString(R.string.faqAnswer1)));
        mFaqModelList.add(new FaqModel(getString(R.string.faqQuestion2), getString(R.string.faqAnswer2)));
        mFaqModelList.add(new FaqModel(getString(R.string.faqQuestion3), getString(R.string.faqAnswer3)));
        mFaqModelList.add(new FaqModel(getString(R.string.faqQuestion4), getString(R.string.faqAnswer4)));
        mFaqModelList.add(new FaqModel(getString(R.string.faqQuestion5), getString(R.string.faqAnswer5)));
        mFaqModelList.add(new FaqModel(getString(R.string.faqQuestion6), getString(R.string.faqAnswer6)));
        mFaqModelList.add(new FaqModel(getString(R.string.faqQuestion7), getString(R.string.faqAnswer7)));
        mFaqModelList.add(new FaqModel(getString(R.string.faqQuestion8), getString(R.string.faqAnswer8)));
        mFaqModelList.add(new FaqModel(getString(R.string.faqQuestion9), getString(R.string.faqAnswer9)));
    }
}