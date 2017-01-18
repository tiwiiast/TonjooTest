package com.example.iast.tonjootest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.iast.tonjootest.adapter.ContactListAdapter;
import com.example.iast.tonjootest.app.AppConfig;
import com.example.iast.tonjootest.helper.Paginator;
import com.example.iast.tonjootest.helper.SessionManager;
import com.example.iast.tonjootest.model.ContactDao;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ContactActivity extends AppCompatActivity {
    RecyclerView rv;
    Button nextBtn, prevBtn;
    Paginator p;
    //= Paginator.TOTAL_NUM_ITEMS / Paginator.ITEMS_PER_PAGE;
    private int currentPage = 0;
    private int length;
    private View mProgressView;

    private List<ContactDao> contactList = new ArrayList<ContactDao>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        setTitle("Contact List");

        //RFERENCE VIEWS
        rv = (RecyclerView) findViewById(R.id.rv_contact);
        nextBtn = (Button) findViewById(R.id.nextBtn);
        prevBtn = (Button) findViewById(R.id.prevBtn);
        prevBtn.setEnabled(false);
        mProgressView = findViewById(R.id.contact_progress);
        p = new Paginator();

        //RECYCLER PROPERTIES
        rv.setLayoutManager(new LinearLayoutManager(this));
        getList();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar actions click
        switch (item.getItemId()) {
            case R.id.action_logout:
                logoutUser();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private void toggleButtons(int totalPages) {
        if (currentPage == totalPages) {
            nextBtn.setEnabled(false);
            prevBtn.setEnabled(true);
        } else if (currentPage == 0) {
            prevBtn.setEnabled(false);
            nextBtn.setEnabled(true);
        } else if (currentPage >= 1 && currentPage <= totalPages) {
            nextBtn.setEnabled(true);
            prevBtn.setEnabled(true);
        }
    }

    public void getList(){
        mProgressView.setVisibility(View.VISIBLE);
        StringRequest strReq = new StringRequest(Request.Method.GET,
                AppConfig.URL_GET_LIST_CONTACT, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    Log.d("MESSAGE", response);
                    JSONObject jObj = new JSONObject(response);
                    Log.d("response request",response.toString());
                    JSONArray jArrayContact = jObj.getJSONArray("data");
                    length=jArrayContact.length();
                    for(int i=0; i<length;i++){
                        JSONObject objContact = jArrayContact.getJSONObject(i);
                        ContactDao cd = new ContactDao();
                        cd.setFirstName(objContact.getString("first_name"));
                        cd.setLastName(objContact.getString("last_name"));
                        cd.setGender(objContact.getString("gender"));
                        cd.setEmail(objContact.getString("email"));
                        cd.setUrlImage(objContact.getString("avatar"));
                        contactList.add(cd);
                    }

                    mProgressView.setVisibility(View.GONE);
                    //ADAPTER
                    rv.setAdapter(new ContactListAdapter(ContactActivity.this, p.generatePage(currentPage,contactList,length)));

                    //NAVIGATE
                    nextBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            currentPage += 1;
                            // enableDisableButtons();
                            rv.setAdapter(new ContactListAdapter(ContactActivity.this, p.generatePage(currentPage,contactList,length)));
                            toggleButtons(length/4);

                        }
                    });
                    prevBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            currentPage -= 1;

                            rv.setAdapter(new ContactListAdapter(ContactActivity.this, p.generatePage(currentPage,contactList,length)));

                            toggleButtons(length/4);
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"Connection Error", Toast.LENGTH_LONG).show();
                mProgressView.setVisibility(View.GONE);
            }
        }) ;
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(strReq);
    }

    public void logoutUser(){
        SessionManager sessionManager= new SessionManager(this);
        sessionManager.logoutUser();
        finish();
    }

}
