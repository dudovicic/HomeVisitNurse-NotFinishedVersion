package hr.ferit.dudovicic.homevisitnurse;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;

import java.io.BufferedReader;

import java.io.InputStreamReader;

import java.io.InputStream;

import java.util.ArrayList;

import android.app.Activity;

import android.app.ProgressDialog;

import android.util.Log;

import org.json.JSONArray;

import org.json.JSONObject;

import org.apache.http.HttpEntity;

import org.apache.http.HttpResponse;

import org.apache.http.client.HttpClient;

import org.apache.http.client.methods.HttpPost;

import org.apache.http.impl.client.DefaultHttpClient;


import android.os.AsyncTask;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;


public class Search extends AppCompatActivity {
    //final static String urlAddress="http://192.168.0.103/PROJEKT_RMA/connection.php";
    //String address="http://10.0.2.2/PROJEKT_RMA/connection.php";
    Activity context;

    HttpPost httppost;

    StringBuffer buffer;

    HttpResponse response;

    HttpClient httpclient;

    ProgressDialog pd;

    CustomAdapter adapter;

    ListView listProduct;

    ArrayList<Product> records;


    private RelativeLayout llContainer;
    private SearchView searchview;
    private ListView lvProducts;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        context=this;

        records=new ArrayList<Product>();


        listProduct=(ListView)findViewById(R.id.product_list);

        adapter=new CustomAdapter(context, R.layout.list_item,R.id.pro_name, records);

        listProduct.setAdapter(adapter);

        searchview = (SearchView) findViewById(R.id.searchview);


        searchview.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String spid) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String spid) {
                adapter.getFilter().filter(spid);

                return false;
            }
        });


    }






    public void onStart(){

        super.onStart();

        //execute background task

        BackTask bt=new BackTask();

        bt.execute();



    }



    //background process to make a request to server and list product information

    private class BackTask extends AsyncTask<Void,Void,Void>{

        protected void onPreExecute(){

            super.onPreExecute();

            pd = new ProgressDialog(context);

            pd.setTitle("Retrieving data");

            pd.setMessage("Please wait.");

            pd.setCancelable(true);

            pd.setIndeterminate(true);

            pd.show();



        }



        protected Void doInBackground(Void...params){



            InputStream is=null;

            String result="";

            try{



                httpclient=new DefaultHttpClient();

                httppost= new HttpPost("http://10.0.2.2/PROJEKT_RMA/getproducts.php");

                response=httpclient.execute(httppost);

                HttpEntity entity = response.getEntity();

                // Get our response as a String.

                is = entity.getContent();



            }catch(Exception e){



                if(pd!=null) pd.dismiss(); //close the dialog if error occurs

                Log.e("ERROR", e.getMessage());



            }



            //convert response to string

            try{

                BufferedReader reader = new BufferedReader(new InputStreamReader(is,"utf-8"),8);

                StringBuilder sb = new StringBuilder();

                String line = null;

                while ((line = reader.readLine()) != null) {

                    sb.append(line+"\n");

                }

                is.close();

                result=sb.toString();

            }catch(Exception e){

                Log.e("ERROR", "Error converting result "+e.toString());



            }



            //parse json data

            try{

                // Remove unexpected characters that might be added to beginning of the string
                result=result.substring(result.indexOf("["));

                JSONArray jArray =new JSONArray(result);

                for(int i=0;i<jArray.length();i++){

                    JSONObject json_data =jArray.getJSONObject(i);

                    Product p=new Product();

                    p.setpName(json_data.getString("Name_surname"));

                    p.setuPrice(json_data.getString("Address"));



                    records.add(p);



                }





            }

            catch(Exception e){

                Log.e("ERROR", "Error pasting data "+e.toString());





            }



            return null;

        }





        protected void onPostExecute(Void result){



            if(pd!=null) pd.dismiss(); //close dialog

            Log.e("size", records.size() + "");

            adapter.notifyDataSetChanged(); //notify the ListView to get new records



        }



    }



}
