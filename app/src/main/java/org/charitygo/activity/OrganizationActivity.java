package org.charitygo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;

import org.charitygo.R;
import org.charitygo.adapter.OrganizationAdapter;
import org.charitygo.adapter.RewardAdapter;
import org.charitygo.model.Organization;

import java.util.ArrayList;

public class OrganizationActivity extends AppCompatActivity {

    static ArrayList<Organization> organizationList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organization);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        createViews();
    }

    protected void goToDonate(View view) {
        Intent intent = new Intent(this, DonateActivity.class);
        intent.putExtra("EXTRA_ID", String.valueOf(view.getId()));
        startActivity(intent);
    }

    public void goToOrganization(View view) {
        Intent intent = new Intent(this, OrganizationInfoActivity.class);
        intent.putExtra("EXTRA_ID", String.valueOf(view.getId()));
        startActivity(intent);
    }

    protected void createViews() {
        RecyclerView list = (RecyclerView) findViewById(R.id.organization_view);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(OrganizationActivity.this);
        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        list.setLayoutManager(mLinearLayoutManager);
        getOrganizations();
        OrganizationAdapter organizationAdapter = new OrganizationAdapter(organizationList);
        list.setAdapter(organizationAdapter);
    }

    private void getOrganizations(){
        organizationList.add(new Organization("Organization 1", 1, 100000, R.drawable.city));
        organizationList.add(new Organization("Organization 2", 2, 200000, R.drawable.city));
        organizationList.add(new Organization("Organization 3", 3, 300000, R.drawable.city));
        organizationList.add(new Organization("Organization 4", 4, 400000, R.drawable.city));
        organizationList.add(new Organization("Organization 5", 5, 500000, R.drawable.city));
        organizationList.add(new Organization("Organization 6", 6, 600000, R.drawable.city));
    }

    //        for (int i = 1; i < 6; i++) {
//            LinearLayout linearLayout = (LinearLayout) findViewById(R.id.organization_linearLayout_cards);
//            View view = getLayoutInflater().inflate(R.layout.organization_cards, linearLayout, false);
//            TextView textName = (TextView) view.findViewById(R.id.info_text);
//            textName.setText("Card " + i);
//            textName.setId(i);
//
//            ImageView imageView = (ImageView) view.findViewById(R.id.info_image);
//            imageView.setId(i);
//
//            Button donate = (Button) view.findViewById(R.id.info_donate);
//            donate.setId(i);
//
//            ImageButton open = (ImageButton) view.findViewById(R.id.info_more);
//            open.setId(i);
//
//            TextView textPoints = (TextView) view.findViewById(R.id.info_points);
//            textPoints.setText(i + "000000 points more");
//
//            linearLayout.addView(view);
//        }
}
