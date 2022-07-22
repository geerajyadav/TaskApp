package com.example.taskapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.taskapp.Modal.Modal;
import com.example.taskapp.Server.APIS;
import com.example.taskapp.Server.InternetConnection;
import com.example.taskapp.Storage.DataPrefrence;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {


  private   ArrayList<Modal.Datum> DatumArraList=new ArrayList<>();
    private ArrayList<Modal.Job> JobArraList=new ArrayList<>();
    private ArrayList<Modal.Education> EducationArraList=new ArrayList<>();
    private ParentAdapter parentAdapter;
    private EdcationAdapter edcationAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView rv_parent;
    JobAdapter jobAdapter;
     RecyclerView rv_job;
     RecyclerView rv_education;
     ProgressBar progress_circular;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        swipeRefreshLayout=findViewById(R.id.refresh);
        progress_circular=findViewById(R.id.progress_circular);
        rv_parent=findViewById(R.id.rv_parent);
        rv_parent.setHasFixedSize(true);
        rv_parent.setNestedScrollingEnabled(false) ;
        rv_parent.setLayoutManager(new GridLayoutManager(this,1));

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (new InternetConnection().isNetworkAvailable(getApplicationContext()))
                GETDataFromAPI();
                else {
                    GetOfflineData();
                    ToastInternet();                }

            }
        });
        if (new InternetConnection().isNetworkAvailable(getApplicationContext()))
            GETDataFromAPI();
        else {
            GetOfflineData();
            ToastInternet();
        }

    }

    private void GETDataFromAPI()
    {
        DatumArraList.clear();
        JobArraList.clear();
        EducationArraList.clear();

        progress_circular.setVisibility(View.VISIBLE);


        StringRequest request=new StringRequest(Request.Method.GET, APIS.URL_GET_ALL_DETAILS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("ResponseHome",response);

                if (response!=null)
                {
                    new DataPrefrence(getApplicationContext()).SETOFFLINEDATA(response);
                    try {
                        JSONObject jsonObject=new JSONObject(response);
                        JSONArray data=jsonObject.getJSONArray("data");
                        for (int i=0;i<data.length();i++)
                        {

                                JSONObject object1=data.getJSONObject(i);
                                Modal.Datum datamodal=new Modal.Datum();
                                datamodal.setFirstname(object1.getString("firstname"));
                                datamodal.setPicture(object1.getString("picture"));
                                try {
                                    datamodal.setGender(object1.getString("gender"));

                                }
                                catch (Exception e)
                                {
                                    e.printStackTrace();
                                }
                                DatumArraList.add(datamodal);
                                JSONArray jobarra=object1.getJSONArray("job");
                                for (int j=0;j<jobarra.length();j++)
                                {
                                    JSONObject jobobject=jobarra.getJSONObject(j);
                                    Modal.Job jobmodal=new Modal.Job();
                                    jobmodal.setExp(jobobject.getInt("exp"));
                                    jobmodal.setRole(jobobject.getString("role"));
                                    jobmodal.setOrganization(jobobject.getString("organization"));
                                    JobArraList.add(jobmodal);


                                }
                            JSONArray eddarra=object1.getJSONArray("education");
                            for (int k=0;k<eddarra.length();k++)
                            {
                                JSONObject jobobject=eddarra.getJSONObject(k);
                                Modal.Education educationmodal=new Modal.Education();
                                educationmodal.setInstitution(jobobject.getString("institution"));
                                educationmodal.setDegree(jobobject.getString("degree"));
                                EducationArraList.add(educationmodal);


                            }





                        }
                        swipeRefreshLayout.setRefreshing(false);
                        progress_circular.setVisibility(View.GONE);
                        parentAdapter=new ParentAdapter(MainActivity.this);
                        rv_parent.setAdapter(parentAdapter);







                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("ErrorHome",error.toString());


            }
        }){

        };
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(request);
    }


    public class ParentAdapter extends RecyclerView.Adapter<ParentAdapter.ViewHolder> {

        Context context;

        public ParentAdapter(Context context) {
            this.context=context;
        }

        // stores and recycles views as they are scrolled off screen
        public class ViewHolder extends RecyclerView.ViewHolder {

          private   TextView tv_name,tv_gender;
            private  LinearLayout ll_view;
            private LinearLayout ll_child;
            private ImageView circleImageView;


            ViewHolder(View itemView) {

                super(itemView);
                tv_name=itemView.findViewById(R.id.tv_name);
                tv_gender=itemView.findViewById(R.id.tv_gender);
                circleImageView=itemView.findViewById(R.id.image);

                ll_view=itemView.findViewById(R.id.ll_view);
                ll_child=itemView.findViewById(R.id.ll_child);
                rv_education=itemView.findViewById(R.id.rv_education);
                rv_job=itemView.findViewById(R.id.rv_job);

                rv_education.setHasFixedSize(true);
                rv_education.setNestedScrollingEnabled(false) ;
                rv_education.setLayoutManager(new GridLayoutManager(getApplicationContext(),1));

                rv_job.setHasFixedSize(true);
                rv_job.setNestedScrollingEnabled(false) ;
                rv_job.setLayoutManager(new GridLayoutManager(getApplicationContext(),1));





            }

        }
        // inflates the row layout from xml when needed
        @Override
        public ParentAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_parent_holder, parent, false);
            ParentAdapter.ViewHolder viewholder= new ParentAdapter.ViewHolder(view);

            return viewholder;
        }

        // binds the data to the TextView in each row
        @Override
        public void onBindViewHolder(final ParentAdapter.ViewHolder holder, final int position) {




            holder.tv_name.setText(String.valueOf(DatumArraList.get(position).getFirstname()));
            if (!String.valueOf(DatumArraList.get(position).getGender()).equalsIgnoreCase("null"))
            holder.tv_gender.setText(String.valueOf(DatumArraList.get(position).getGender()));
            else
                holder.tv_gender.setText("NA");


            holder.ll_view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (holder.ll_child.getVisibility()== View.VISIBLE)
                    {
                        holder.ll_child.setVisibility(View.GONE);
                    }
                    else {
                        holder.ll_child.setVisibility(View.VISIBLE);

                    }
                }
            });
            Picasso.get().load(DatumArraList.get(position).getPicture()).error(R.drawable.ic_launcher_background).noFade().fit().into(holder.circleImageView);

            try {

                jobAdapter=new JobAdapter(MainActivity.this);
                rv_job.setAdapter(jobAdapter);

                edcationAdapter=new EdcationAdapter(MainActivity.this);
                rv_education.setAdapter(edcationAdapter);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

        }

        // total number of rows
        @Override
        public int getItemCount() {
            return DatumArraList.size();
        }



    }


    public class JobAdapter extends RecyclerView.Adapter<JobAdapter.ViewHolder> {

        Context context;

        public JobAdapter(Context context) {
            this.context=context;
        }

        // stores and recycles views as they are scrolled off screen
        public class ViewHolder extends RecyclerView.ViewHolder {

            private   TextView tv_role,tv_exp,tv_organi;


            ViewHolder(View itemView) {

                super(itemView);
                tv_role=itemView.findViewById(R.id.tv_role);
                tv_exp=itemView.findViewById(R.id.tv_experience);
                tv_organi=itemView.findViewById(R.id.tv_organization);




            }

        }
        // inflates the row layout from xml when needed
        @Override
        public JobAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_job_holder, parent, false);
            JobAdapter.ViewHolder viewholder= new JobAdapter.ViewHolder(view);

            return viewholder;
        }

        // binds the data to the TextView in each row
        @Override
        public void onBindViewHolder(final JobAdapter.ViewHolder holder, final int position) {




            holder.tv_role.setText(String.valueOf(JobArraList.get(position).getRole()));
            holder.tv_exp.setText(String.valueOf(JobArraList.get(position).getExp()).concat(" Years"));
            holder.tv_organi.setText(String.valueOf(JobArraList.get(position).getOrganization()));


        }

        // total number of rows
        @Override
        public int getItemCount() {
            return JobArraList.size();
        }



    }

    public class EdcationAdapter extends RecyclerView.Adapter<EdcationAdapter.ViewHolder> {

        Context context;

        public EdcationAdapter(Context context) {
            this.context=context;
        }

        // stores and recycles views as they are scrolled off screen
        public class ViewHolder extends RecyclerView.ViewHolder {

            private   TextView tv_ints,tv_degree;


            ViewHolder(View itemView) {

                super(itemView);
                tv_degree=itemView.findViewById(R.id.tv_degree);
                tv_ints=itemView.findViewById(R.id.tv_inst);




            }

        }
        // inflates the row layout from xml when needed
        @Override
        public EdcationAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_ed_holder, parent, false);
            EdcationAdapter.ViewHolder viewholder= new EdcationAdapter.ViewHolder(view);

            return viewholder;
        }

        // binds the data to the TextView in each row
        @Override
        public void onBindViewHolder(final EdcationAdapter.ViewHolder holder, final int position) {




            holder.tv_degree.setText(String.valueOf(EducationArraList.get(position).getDegree()));
            holder.tv_ints.setText(String.valueOf(EducationArraList.get(position).getInstitution()));


        }

        // total number of rows
        @Override
        public int getItemCount() {
            return EducationArraList.size();
        }



    }
private void GetOfflineData()
{
    DatumArraList.clear();
    JobArraList.clear();
    EducationArraList.clear();
    progress_circular.setVisibility(View.VISIBLE);

    try {
        JSONObject jsonObject=new JSONObject(new DataPrefrence(getApplicationContext()).GETOFFLINEDATA());
        if (jsonObject!=null)
        {
            JSONArray data=jsonObject.getJSONArray("data");
            for (int i=0;i<data.length();i++)
            {

                JSONObject object1=data.getJSONObject(i);
                Modal.Datum datamodal=new Modal.Datum();
                datamodal.setFirstname(object1.getString("firstname"));
                datamodal.setPicture(object1.getString("picture"));
                try {
                    datamodal.setGender(object1.getString("gender"));

                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
                DatumArraList.add(datamodal);
                JSONArray jobarra=object1.getJSONArray("job");
                for (int j=0;j<jobarra.length();j++)
                {
                    JSONObject jobobject=jobarra.getJSONObject(j);
                    Modal.Job jobmodal=new Modal.Job();
                    jobmodal.setExp(jobobject.getInt("exp"));
                    jobmodal.setRole(jobobject.getString("role"));
                    jobmodal.setOrganization(jobobject.getString("organization"));
                    JobArraList.add(jobmodal);


                }
                JSONArray eddarra=object1.getJSONArray("education");
                for (int k=0;k<eddarra.length();k++)
                {
                    JSONObject jobobject=eddarra.getJSONObject(k);
                    Modal.Education educationmodal=new Modal.Education();
                    educationmodal.setInstitution(jobobject.getString("institution"));
                    educationmodal.setDegree(jobobject.getString("degree"));
                    EducationArraList.add(educationmodal);


                }





            }
            swipeRefreshLayout.setRefreshing(false);
            progress_circular.setVisibility(View.GONE);
            parentAdapter=new ParentAdapter(MainActivity.this);
            rv_parent.setAdapter(parentAdapter);







        }

    } catch (JSONException e) {
        e.printStackTrace();
    }
}
    private void ToastInternet()
    {
        LayoutInflater li = getLayoutInflater();
        //Getting the View object as defined in the customtoast.xml file
        View layout = li.inflate(R.layout.check_internet_toast,(ViewGroup) findViewById(R.id.custom_toast_layout));

        //Creating the Toast object
        Toast toast = new Toast(getApplicationContext());
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setGravity(Gravity.BOTTOM, 0, 0);
        toast.setView(layout);//setting the view of custom toast layout
        toast.show();
    }

}