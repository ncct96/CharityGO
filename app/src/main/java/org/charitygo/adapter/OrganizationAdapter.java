package org.charitygo.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import org.charitygo.R;
import org.charitygo.activity.OrganizationInfoActivity;
import org.charitygo.model.Organization;

import java.util.List;

public class OrganizationAdapter extends RecyclerView.Adapter<OrganizationAdapter.OrganizationHolder> {
    private List<Organization> organizationList;
    private Context context;

    public OrganizationAdapter(Context context, List<Organization> organizationList) {
        this.context = context;
        this.organizationList = organizationList;
    }

    @Override
    public int getItemCount() {
        return organizationList.size();
    }

    @Override
    public OrganizationAdapter.OrganizationHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View userView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.organization_cards, viewGroup, false);

        return new OrganizationAdapter.OrganizationHolder(userView);
    }

    @Override
    public void onBindViewHolder(OrganizationAdapter.OrganizationHolder organizationHolder, int i) {
        final Organization organization = organizationList.get(i);
        organizationHolder.background.setImageResource(organization.getDrawable());
        organizationHolder.name.setText(organization.getName());
        organizationHolder.points.setText(organization.getPoints() + " points more");

        final String id = organization.getKey();
        organizationHolder.more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent intent = new Intent(context, OrganizationInfoActivity.class);
                intent.putExtra("EXTRA_ID", id);
                context.startActivity(intent);
            }
        });
        organizationHolder.background.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent intent = new Intent(context, OrganizationInfoActivity.class);
                intent.putExtra("EXTRA_ID", id);
                context.startActivity(intent);
            }
        });
    }

    public static class OrganizationHolder extends RecyclerView.ViewHolder {
        protected ImageView background;
        protected TextView name;
        protected TextView points;
        protected Button donate;
        protected ImageButton more;

        public OrganizationHolder(View v) {
            super(v);
            background = v.findViewById(R.id.info_image);
            name = v.findViewById(R.id.info_text);
            points = v.findViewById(R.id.info_points);
            donate = v.findViewById(R.id.info_donate);
            more = v.findViewById(R.id.info_more);
        }
    }


}
