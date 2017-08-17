package appdeveloper.meenakshi.com.Call_Parlour;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.HashMap;

/**
 * Created by Ashish on 06/01/2017.
 */

public class TipsandToesDetails extends Fragment {
    EditText mPedicure;
    EditText mMedicure;
    EditText mFootspa;
    EditText mNewService, mNewServiceCost;
    Button mAddMore;
    private SendTipToeDetailsToServer mSendTipAndToeDataTask = null;
    public static TipsandToesDetails newInstance() {
        TipsandToesDetails fragment = new TipsandToesDetails();
        return fragment;
    }

    public TipsandToesDetails() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.activity_tipsandtoes__tab, container, false);
        mPedicure = (EditText) rootView.findViewById(R.id.pedicure_cost);
        mMedicure = (EditText) rootView.findViewById(R.id.manicure_cost);
        mFootspa = (EditText) rootView.findViewById(R.id.foot_spa__cost);
        mNewService = (EditText) rootView.findViewById(R.id.new_service);
        mNewServiceCost = (EditText) rootView.findViewById(R.id.new_service_cost);

        mAddMore = (Button) rootView.findViewById(R.id.add_more);
        return rootView;
    }

    public void addMoreServices(View view)
    {
        mNewService.setVisibility(View.VISIBLE);
        mNewServiceCost.setVisibility(View.VISIBLE);
    }
    private void attemptLogin() {
        if (mSendTipAndToeDataTask!= null) {
            return;
        }

        // Reset errors.
        mPedicure.setError(null);
        mMedicure.setError(null);
        mFootspa.setError(null);


        // Store values at the time of the login attempt.
        String pedicure = mPedicure.getText().toString();
        String menicure = mMedicure.getText().toString();
        String footspa = mFootspa.getText().toString();

        boolean cancel = false;
        View focusView = null;


       /* if (!TextUtils.isEmpty()) {
            mFruit_Facial.setError(getString(R.string.error_field_required));
            focusView = mFruit_Facial;
            cancel = true;
        }
        if (!TextUtils.isEmpty(lotus_facial)) {
            mLotus_Facial.setError(getString(R.string.error_field_required));
            focusView = mLotus_Facial;
            cancel = true;
        }
        if (!TextUtils.isEmpty(vlcc_facial)) {
            mVLCC_Facial.setError(getString(R.string.error_field_required));
            focusView = mVLCC_Facial;
            cancel = true;
        }*/



        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            //showProgress(true);
            mSendTipAndToeDataTask = new SendTipToeDetailsToServer(pedicure, menicure, footspa);
            mSendTipAndToeDataTask.execute();
        }
    }

    public class SendTipToeDetailsToServer extends AsyncTask<Void, Void, Boolean> {

        private final String mPedicure;
        private final String mMedicure;
        private final String mFootSpa;


        private ProgressDialog progressDialog;

        SendTipToeDetailsToServer(String pedicure, String menicure, String footspa)
        {
            mPedicure = pedicure;
            mMedicure = menicure;
            mFootSpa = footspa;

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

                Log.v("Tip and Toe Details", " ***********i am in try 1");
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
                param.put("pedicure", mPedicure);
                param.put("menicure", mMedicure);
                param.put("foot spa", mFootSpa);

                DataOutputStream os = new DataOutputStream(urlc.getOutputStream());
                os.writeBytes(iCommon.getPostDataString(param));
                Log.v("Tip and Toe Details", iCommon.getPostDataString(param));
                os.flush();
                os.close();
            } catch (ProtocolException e) {
                Log.v("Tip and Toe Details", " i am in catch 1");
                e.printStackTrace();
            } catch (MalformedURLException e) {
                Log.v("Tip and Toe Details", " i am in catch 2");
                e.printStackTrace();
            } catch (IOException e) {
                Log.v("Tip and Toe Details", " i am in catch 3");
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
            mSendTipAndToeDataTask = null;
            // showProgress(false);

            if (success) {
                //finish();
                Log.v("Tip and Toe Details", "**********success");
                progressDialog.dismiss();
            } /*else {
                mPasswordView.setError(getString(R.string.error_incorrect_password));
                mPasswordView.requestFocus();
            }*/
        }

    }

} // This is the end of our MyFragments Class







