package appdeveloper.meenakshi.com.Call_Parlour;

import android.app.Activity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.SupportActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.support.v4.app.Fragment;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.view.ViewGroup.LayoutParams.FILL_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
import static android.widget.LinearLayout.VERTICAL;

public class AddMoreServices extends Fragment {
    private Button addmoremember, removeitems, saveitems;
    private Activity activity;
    private LinearLayout layout;
    static  int number_of_count = 0;
    StringBuilder str = new StringBuilder();
    private SendMoreServiceDataTask mSendMoreServiceDataTask = null;
    ArrayList<EditText[]> outerArr = new ArrayList<EditText[]>();
    public static AddMoreServices newInstance() {
        AddMoreServices fragment = new AddMoreServices();
        return fragment;
    }

    public AddMoreServices() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_addmoreservices_tab, container, false);

        activity=getActivity();
        addmoremember=(Button)rootView.findViewById(R.id.family_detail_addbutton);
        removeitems=(Button)rootView.findViewById(R.id.remove);
        saveitems=(Button)rootView.findViewById(R.id.saveitems);
        layout=(LinearLayout)rootView.findViewById(R.id.family_detail_linear);
        addmoremember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(layout.getChildCount()<11)

                {
                    LinearLayout linearLayout = new LinearLayout(activity);
                    linearLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    linearLayout.setOrientation(LinearLayout.HORIZONTAL);
                    EditText new_services = new EditText(activity);
                    new_services.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f));
                    new_services.setHint("Name of the service");

                    EditText cost_of_new_services = new EditText(activity);
                    cost_of_new_services.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f));
                    cost_of_new_services.setHint("Cost");

                    cost_of_new_services.setGravity(Gravity.CENTER);
                    EditText[] edittexts = {new_services, cost_of_new_services};

                    outerArr.add(edittexts);
                    linearLayout.addView(new_services);
                    linearLayout.addView(cost_of_new_services);
                    layout.addView(linearLayout);
                }
            }
        });

        removeitems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.v("AddmoreServices", "count is " + layout.getChildCount());
                layout.removeViewAt(layout.getChildCount()-1);
                outerArr.remove(layout.getChildCount());
            }
        });
          saveitems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            String new_services_with_cost = "";

                //retrieve using:
                for(int i=0;i<outerArr.size();i++){

                    EditText[] myString= new EditText[9];
                    myString = outerArr.get(i);
                    str.append(i+1 + ". ");
                    for(int j=0;j<myString.length;j++){
                        String new_services =  myString[j].getText().toString();
                        String cost = myString[++j].getText().toString();
                        str = str.append(new_services).append("-").append(cost);
                    }
                    str.append("\n" );

                }
                Log.v("addmoreservices", "***************String is "+ str.toString());
                sendMoreDataToServer();

            }
        });

        return rootView;
    }

    private void sendMoreDataToServer() {

        mSendMoreServiceDataTask = new SendMoreServiceDataTask(str.toString());
        mSendMoreServiceDataTask.execute();
    }

    public class SendMoreServiceDataTask extends AsyncTask<Void, Void, Boolean> {

        private final String mMoreServicesData;



        private ProgressDialog progressDialog;

        SendMoreServiceDataTask(String moreServiceData)
        {
            mMoreServicesData =  moreServiceData;


        }


        protected void onPreExecute() {
            progressDialog = new ProgressDialog(getContext());
            progressDialog.setCancelable(false);
            progressDialog.setTitle("Please Wait...");
            progressDialog.setMessage("Loading");
            //if (User_Registration.this.isFinishing() == false)// To avoid android.view.WindowManager$BadTokenException
            progressDialog.show();

        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            try {

                Log.v("More Services Details", " ***********i am in try 1");
                URL server_url = new URL("http://www.google.co.in/");

                // iCommon.SSLv3_bugfix();
                HttpURLConnection urlc = (HttpURLConnection) server_url.openConnection();
                urlc.setDoOutput(true);
                urlc.setDoInput(true);
                urlc.setRequestMethod("POST");
                urlc.setRequestProperty("Content-Language", "en-US");
                urlc.setRequestProperty("Accept-Encoding", "identity");

                HashMap<String, String> param = new HashMap<>();

                // param.put("id", iCommon.get_UserID(context));
                //  param.put("did", iCommon.get_AndroidID(context));
                //param.put("name", mName);
                param.put("more Services", mMoreServicesData);


                DataOutputStream os = new DataOutputStream(urlc.getOutputStream());
                os.writeBytes(iCommon.getPostDataString(param));
                Log.v("More Services Details", iCommon.getPostDataString(param));
                os.flush();
                os.close();
            } catch (ProtocolException e) {
                Log.v("More Services Details", " i am in catch 1");
                e.printStackTrace();
            } catch (MalformedURLException e) {
                Log.v("More Services Details", " i am in catch 2");
                e.printStackTrace();
            } catch (IOException e) {
                Log.v("More Services Details", " i am in catch 3");
                e.printStackTrace();
            }

         /*   try {
                // Simulate network access.
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                return false;
            }

            for (String credential : DUMMY_CREDENTIALS) {
                String[] pieces = credential.split(":");
                if (pieces[0].equals(mEmail)) {
                    // Account exists, return true if the password matches.
                    return pieces[1].equals(mPassword);
                }
            }*/

            //  register the new account in app storage here.

           /* iStorage.savePropertyWithValue(getBaseContext(),iStorage.FILE_NAME,);
            iStorage.savePropertyWithValue(getBaseContext(),iStorage.FILE_EMAIL,mEmail);
            iStorage.savePropertyWithValue(getBaseContext(),iStorage.FILE_PASSWORD,mPassword);
            iStorage.savePropertyWithValue(getBaseContext(),iStorage.FILE_AGE,mAge);
            iStorage.savePropertyWithValue(getBaseContext(),iStorage.FILE_LOCATION,mLocation);
            iStorage.savePropertyWithValue(getBaseContext(),iStorage.FILE_CONTACT,mContact);*/

            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mSendMoreServiceDataTask = null;
            // showProgress(false);

            if (success) {
                //finish();
                Log.v("More Services Details", "**********success");
                progressDialog.dismiss();
            } /*else {
                mPasswordView.setError(getString(R.string.error_incorrect_password));
                mPasswordView.requestFocus();
            }*/
        }

    }

}