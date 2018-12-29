package org.charitygo.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import org.charitygo.R;
import org.charitygo.model.Organization;

import java.util.List;

public class OrganizationAdapter extends RecyclerView.Adapter<OrganizationAdapter.OrganizationHolder> {
    private List<Organization> organizationList;

    public OrganizationAdapter(List<Organization> organizationList) {
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
        Organization organization = organizationList.get(i);
        organizationHolder.background.setImageResource(organization.getDrawable());
        organizationHolder.name.setText(organization.getName());
        organizationHolder.points.setText(organization.getPoints() + " points more");
        organizationHolder.donate.setId(organization.getId());
        organizationHolder.more.setId(organization.getId());
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
