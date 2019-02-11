package com.example.betterfly;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class organizationInfoAdaptor extends ArrayAdapter<Organization> {
    private Activity context;
    private List<Organization> organizationList;

    public organizationInfoAdaptor( Activity context,List<Organization> organizationList){
        super(context,R.layout.list,organizationList);
        this.context=context;
        this.organizationList=organizationList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View listView=inflater.inflate(R.layout.list, null, true);

        TextView organizationName=(TextView)listView.findViewById(R.id.orgName);
       // TextView organizationStatus=(TextView)listView.findViewById(R.id.orgStatus);

        Organization organization= organizationList.get(position);
        organizationName.setText(organization.name);
        organization.setStatus(Status.PROCESSING);

        return listView;


    }
}
