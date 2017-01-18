package com.example.iast.tonjootest.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.example.iast.tonjootest.R;


/**
 * Created by Oclemy on 9/13/2016 for ProgrammingWizards Channel and http://www.camposha.com.
 */
public class ContactViewHolder extends RecyclerView.ViewHolder {

    NetworkImageView imgView;
    TextView nametxt;
    TextView gendertxt;
    TextView emailtxt;

        public ContactViewHolder(View itemView) {
            super(itemView);

            imgView= (NetworkImageView) itemView.findViewById(R.id.rowContact_image);
            nametxt= (TextView) itemView.findViewById(R.id.rowContact_name);
            gendertxt = (TextView) itemView.findViewById(R.id.rowContact_gender);
            emailtxt = (TextView) itemView.findViewById(R.id.rowContact_email);

        }



}
