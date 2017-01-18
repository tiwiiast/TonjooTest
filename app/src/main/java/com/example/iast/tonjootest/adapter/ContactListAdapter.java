package com.example.iast.tonjootest.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.toolbox.ImageLoader;
import com.example.iast.tonjootest.R;
import com.example.iast.tonjootest.helper.CustomVolleyRequest;
import com.example.iast.tonjootest.model.ContactDao;

import java.util.List;

/**
 * Created by iast on 1/16/17.
 */

public class ContactListAdapter extends RecyclerView.Adapter<ContactViewHolder>{
    Context context;
    private List<ContactDao> contacs;
    ImageLoader imageLoader;

    public ContactListAdapter(Context context, List<ContactDao> contacs) {
        this.context = context;
        this.contacs = contacs;
    }

    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.row_contact,parent,false);

        return new ContactViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ContactViewHolder holder, int position) {
        if (imageLoader == null)
            imageLoader = CustomVolleyRequest.getInstance(context)
                    .getImageLoader();

        ContactDao contactDao = contacs.get(position);

        holder.nametxt.setText(contactDao.getFirstName()+" "+contactDao.getLastName());
        holder.gendertxt.setText(contactDao.getGender());
        holder.emailtxt.setText(contactDao.getEmail());
        holder.imgView.setImageUrl(contactDao.getUrlImage(),imageLoader);

    }

    @Override
    public int getItemCount() {
        return contacs.size();
    }

}
