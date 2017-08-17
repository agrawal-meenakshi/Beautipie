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

public class HairDetails extends Fragment {

    EditText mUptoShoulder_Hairspa;
    EditText mUptoWaist_Hairspa;
    EditText mUptoElbows_Hairspa;
    EditText mDeepU_Haircut;
    EditText mFeather_Haircut;
    EditText mKids_Haircut;
    EditText mLayer_Haircut;
    EditText mStep_Haircut;
    EditText mStraight_Haircut;
    EditText mTrim_Haircut;
    EditText mHairColouring;
    EditText mBlowDry_HairStyling;
    EditText mCurling_HairStyling;
    EditText mIroning_HairStyling;

    private SendHairDetailsToServer mSendHairDataTask = null;

    public static HairDetails newInstance() {
        HairDetails fragment = new HairDetails();
        return fragment;
    }

    public HairDetails() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.activity_hair__tab, container, false);
        mUptoShoulder_Hairspa = (EditText) rootView.findViewById(R.id.upto_shoulder_hairspa_cost);
        mUptoWaist_Hairspa = (EditText) rootView.findViewById(R.id.upto_waist_hairspa_cost);
        mUptoElbows_Hairspa = (EditText) rootView.findViewById(R.id.upto_elbows_hairspa_cost);

        mDeepU_Haircut = (EditText)rootView.findViewById(R.id.deep_u_haircut_cost);
        mFeather_Haircut = (EditText)rootView.findViewById(R.id.feather_haircut_cost);
        mKids_Haircut = (EditText) rootView.findViewById(R.id.kids_haircut_cost);
        mLayer_Haircut = (EditText) rootView.findViewById(R.id.layer_haircut_cost);
        mStep_Haircut = (EditText)rootView.findViewById(R.id.step_haircut_cost);
        mStraight_Haircut = (EditText)rootView.findViewById(R.id.straight_haircut_cost);
        mTrim_Haircut = (EditText)rootView.findViewById(R.id.trim_haircut_cost);

        mHairColouring = (EditText)rootView.findViewById(R.id.hair_colouring_cost);

        mBlowDry_HairStyling = (EditText)rootView.findViewById(R.id.blow_dry_hair_styling_cost);
        mCurling_HairStyling = (EditText)rootView.findViewById(R.id.curling_hair_styling_cost);
        mIroning_HairStyling = (EditText)rootView.findViewById(R.id.ironing_hair_styling_cost);

        return rootView;
    }
    private void attemptLogin() {
        if (mSendHairDataTask!= null) {
            return;
        }

        // Reset errors.
        mUptoElbows_Hairspa.setError(null);
        mUptoShoulder_Hairspa.setError(null);
        mUptoWaist_Hairspa.setError(null);

        mDeepU_Haircut.setError(null);
        mFeather_Haircut.setError(null);
        mKids_Haircut.setError(null);
        mLayer_Haircut.setError(null);
        mStep_Haircut.setError(null);
        mStraight_Haircut.setError(null);
        mTrim_Haircut.setError(null);

        mHairColouring.setError(null);
        mBlowDry_HairStyling.setError(null);
        mCurling_HairStyling.setError(null);
        mIroning_HairStyling.setError(null);

        // Store values at the time of the login attempt.
        String upto_shoulder_hairspa = mUptoShoulder_Hairspa.getText().toString();
        String upto_waist_hairspa = mUptoWaist_Hairspa.getText().toString();
        String upto_elbows_hairspa = mUptoElbows_Hairspa.getText().toString();

        String deepU_haircut = mDeepU_Haircut.getText().toString();
        String feather_haircut = mFeather_Haircut.getText().toString();
        String kids_haircut = mKids_Haircut.getText().toString();
        String layer_haircut = mLayer_Haircut.getText().toString();
        String step_haircut = mStep_Haircut.getText().toString();
        String straight_haircut = mStraight_Haircut.getText().toString();
        String trim_haircut = mTrim_Haircut.getText().toString();

        String hair_colouring = mHairColouring.getText().toString();
        String blow_dry_hair_styling = mBlowDry_HairStyling.getText().toString();
        String curling_hair_styling = mCurling_HairStyling.getText().toString();
        String ironing_hair_styling = mIroning_HairStyling.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            //showProgress(true);
            mSendHairDataTask = new SendHairDetailsToServer(upto_shoulder_hairspa, upto_elbows_hairspa,upto_waist_hairspa,deepU_haircut,feather_haircut,kids_haircut,layer_haircut, step_haircut,straight_haircut,trim_haircut,hair_colouring,blow_dry_hair_styling,curling_hair_styling,ironing_hair_styling);
            mSendHairDataTask.execute();
        }
    }

    public class SendHairDetailsToServer extends AsyncTask<Void, Void, Boolean> {

        private final String mUptoShoulder_Hairspa;
        private final String mUptoWaist_Hairspa;
        private final String mUptoElbows_Hairspa;
        private final String mDeepU_Haircut;
        private final String mFeather_Haircut;
        private final String mKids_Haircut;
        private final String mLayer_Haircut;
        private final String mStep_Haircut;
        private final String mStraight_Haircut;
        private final String mTrim_Haircut;
        private final String mHairColouring;
        private final String mBlowDry_HairStyling;
        private final String mCurling_HairStyling;
        private final String mIroning_HairStyling;
        private ProgressDialog progressDialog;

        SendHairDetailsToServer(String upto_shoulder_hairspa, String upto_elbows_hairspa, String upto_waist_hairspa, String deepU_haircut, String feather_haircut,String kids_haircut,String layer_haircut,String step_haircut,String straight_haircut, String trim_haircut, String hair_colouring,String blow_dry_hair_styling,String curling_hair_styling, String ironing_hair_styling){
            mUptoShoulder_Hairspa = upto_shoulder_hairspa;
            mUptoElbows_Hairspa = upto_elbows_hairspa;
            mUptoWaist_Hairspa = upto_waist_hairspa;
            mDeepU_Haircut = deepU_haircut;
            mFeather_Haircut = feather_haircut;
            mKids_Haircut = kids_haircut;
            mLayer_Haircut = layer_haircut;
            mStep_Haircut= step_haircut;
            mStraight_Haircut = straight_haircut;
            mTrim_Haircut = trim_haircut;
            mHairColouring = hair_colouring;
            mBlowDry_HairStyling = blow_dry_hair_styling;
            mCurling_HairStyling = curling_hair_styling;
            mIroning_HairStyling = ironing_hair_styling;
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

                Log.v("Hair Details", " ***********i am in try 1");
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
                param.put("upto shoulder haircut", mUptoShoulder_Hairspa);
                param.put("upto elbows haircut", mUptoElbows_Hairspa);
                param.put("upto waist haircut", mUptoWaist_Hairspa);
                param.put("deep u haircut", mDeepU_Haircut);
                param.put("feather haircut", mFeather_Haircut);
                param.put("kids haircut", mKids_Haircut);
                param.put("layer haircut", mLayer_Haircut);
                param.put("step haircut", mStep_Haircut);
                param.put("straight haircut", mStraight_Haircut);
                param.put("trim haircut", mTrim_Haircut);
                param.put("hair colouring", mHairColouring);
                param.put("blow dry hair styling", mBlowDry_HairStyling);
                param.put("curling hair styling", mCurling_HairStyling);
                param.put("ironing hair styling", mIroning_HairStyling);


//                    param.put("location", mLocation);
                // param.put("contact_number", mContact);
                DataOutputStream os = new DataOutputStream(urlc.getOutputStream());
                os.writeBytes(iCommon.getPostDataString(param));
                Log.v("Hair details", iCommon.getPostDataString(param));
                os.flush();
                os.close();
            } catch (ProtocolException e) {
                Log.v("Hair details", " i am in catch 1");
                e.printStackTrace();
            } catch (MalformedURLException e) {
                Log.v("Hair details", " i am in catch 2");
                e.printStackTrace();
            } catch (IOException e) {
                Log.v("Hair details", " i am in catch 3");
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
            mSendHairDataTask = null;
            // showProgress(false);

            if (success) {
                //finish();
                Log.v("Hair details", "**********success");
            } /*else {
                mPasswordView.setError(getString(R.string.error_incorrect_password));
                mPasswordView.requestFocus();
            }*/
        }

    }

} // This is the end of our MyFragments Class








