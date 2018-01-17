package com.example.pc.phpandroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    // declare variables
    EditText et_name, et_surname, et_age;
    Button bt_insert, bt_show;
    TextView tv_show;
    RequestQueue requestQueue;
    String insertUrl = "http://192.168.33.1/tutorial/insertStudent.php";
    String showUrl = "http://192.168.33.1/tutorial/showStudent.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // casting of the widget to their variables
        et_name = (EditText)findViewById(R.id.et_name);
        et_surname = (EditText)findViewById(R.id.et_surname);
        et_age = (EditText)findViewById(R.id.et_age);
        tv_show = (TextView)findViewById(R.id.tv_show);
        bt_insert = (Button)findViewById(R.id.bt_insert);
        bt_show = (Button)findViewById(R.id.bt_show);

        requestQueue = Volley.newRequestQueue(getApplicationContext());

    }

    // what happen when the user click the show button
    public void showData(View view){
                // create a json object request
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, showUrl, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray students = response.getJSONArray("students");
                            for(int i = 0; i < students.length(); i++){
                                JSONObject student = students.getJSONObject(i);
                                String firstname = student.getString("firstname");
                                String lastname = student.getString("lastname");
                                String age = student.getString("age");

                                tv_show.append(firstname + " " + lastname + " " + age + "\n");
                            }
                            tv_show.append("===\n");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
                requestQueue.add(jsonObjectRequest);
    }

    // what happens when the user clicks the insert button
    public void insertData(View view){
        StringRequest request = new StringRequest(Request.Method.POST, insertUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() { // expand the bracket before the end of the code and add the listener
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){ // we open and close brackets here as well


            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("firstname", et_name.getText().toString());
                parameters.put("lastname", et_surname.getText().toString());
                parameters.put("age", et_age.getText().toString());

                return parameters;
            }
        };
        requestQueue.add(request);

        //alert the user of the insertion
        Toast.makeText(this, "User inserted successfully", Toast.LENGTH_SHORT).show();
    }

}
