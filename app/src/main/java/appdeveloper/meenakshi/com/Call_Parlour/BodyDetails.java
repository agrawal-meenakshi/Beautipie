package appdeveloper.meenakshi.com.Call_Parlour;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
 * Created by Ashish on 04/20/2017.
 */

public class BodyDetails extends Fragment {
    EditText mFull_Hands_wax;
    EditText mFull_Legs_wax;
    EditText mHalf_Legs_wax;
    EditText mUnderarms_wax;
    EditText mBack_wax;
    EditText mFull_Stomach_Polish;
    EditText mFull_Back_Polish;
    EditText mFull_Body_Polish;
    EditText mHeead_And_Shoulder_Massage;
    EditText mHands_Or_Legs_Polish;
    EditText mFace_And_Neck_Bleach;
    EditText mFull_Hands_Bleach;
    EditText mFull_Legs_Bleach;
    EditText mHalf_Legs_Bleach;
    EditText mUnderarms_Bleach;
    EditText mFull_Back_Bleach;
    EditText mFull_Body_Bleach;
    private SendBodyDetailsToServer mSendBodyDataTask = null;

    public static BodyDetails newInstance() {
        BodyDetails fragment = new BodyDetails();
        return fragment;
    }

    public BodyDetails() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.activity_body__tab, container, false);
        mFull_Hands_wax = (EditText) rootView.findViewById(R.id.full_hands_wax_cost);
         mFull_Legs_wax =(EditText) rootView.findViewById(R.id.full_legs_wax__cost);
         mHalf_Legs_wax =(EditText) rootView.findViewById(R.id.half_legs_wax_cost);
         mUnderarms_wax =(EditText) rootView.findViewById(R.id.underarms_wax_cost);
         mBack_wax =(EditText) rootView.findViewById(R.id.back_wax_cost);
         mFull_Stomach_Polish =(EditText) rootView.findViewById(R.id.full_stomach_polish_cost);
         mFull_Back_Polish =(EditText) rootView.findViewById(R.id.full_back_polish_cost);
         mFull_Body_Polish =(EditText) rootView.findViewById(R.id.full_body_polish_cost);
         mHeead_And_Shoulder_Massage =(EditText) rootView.findViewById(R.id.head_and_shoulder_massage_cost);
         mHands_Or_Legs_Polish =(EditText) rootView.findViewById(R.id.hands_or_legs_polish_cost);
         mFace_And_Neck_Bleach =(EditText) rootView.findViewById(R.id.face_and_neck_bleach_cost);
         mFull_Hands_Bleach =(EditText) rootView.findViewById(R.id.full_hands_bleach_cost);
         mFull_Legs_Bleach =(EditText) rootView.findViewById(R.id.full_legs_bleach_cost);
         mHalf_Legs_Bleach =(EditText) rootView.findViewById(R.id.half_legs_bleach_cost);
         mUnderarms_Bleach =(EditText) rootView.findViewById(R.id.underarms_bleach_cost);
         mFull_Back_Bleach =(EditText) rootView.findViewById(R.id.full_back_bleach_cost);
         mFull_Body_Bleach =(EditText) rootView.findViewById(R.id.full_body_bleach_cost);

        return rootView;
    }
    private void attemptLogin() {
        if (mSendBodyDataTask!= null) {
            return;
        }

        // Reset errors.
        mFull_Hands_wax.setError(null);
        mFull_Legs_wax.setError(null);
        mHalf_Legs_wax.setError(null);
        mUnderarms_wax.setError(null);
        mBack_wax.setError(null);

        mFull_Stomach_Polish.setError(null);
        mFull_Back_Polish.setError(null);
        mFull_Body_Polish.setError(null);
        mHeead_And_Shoulder_Massage.setError(null);
        mHands_Or_Legs_Polish.setError(null);

        mFace_And_Neck_Bleach.setError(null);
        mFull_Hands_Bleach.setError(null);
        mFull_Legs_Bleach.setError(null);
        mUnderarms_Bleach.setError(null);
        mFull_Back_Bleach.setError(null);
        mFull_Body_Bleach.setError(null);

        // Store values at the time of the login attempt.
        String full_hands_wax = mFull_Hands_wax.getText().toString();
        String full_legs_wax = mFull_Legs_wax.getText().toString();
        String half_legs_wax = mHalf_Legs_wax.getText().toString();
        String underarms_wax = mUnderarms_wax.getText().toString();
        String back_wax = mBack_wax.getText().toString();

        String full_stomach_polish = mFull_Stomach_Polish.getText().toString();
        String full_back_polish = mFull_Back_Polish.getText().toString();
        String full_body_polish= mFull_Body_Polish.getText().toString();
        String head_and_shoulder_massage = mHeead_And_Shoulder_Massage.getText().toString();
        String hands_or_legs_polish = mHands_Or_Legs_Polish.getText().toString();

        String face_and_neck_bleach = mFace_And_Neck_Bleach.getText().toString();
        String full_hands_bleach = mFull_Hands_Bleach.getText().toString();
        String full_legs_bleach = mFull_Legs_Bleach.getText().toString();
        String half_legs_bleach = mHalf_Legs_Bleach.getText().toString();
        String underarms_bleach= mUnderarms_Bleach.getText().toString();
        String full_back_bleach = mFull_Back_Bleach.getText().toString();
        String full_body_bleach= mFull_Body_Bleach.getText().toString();

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
            mSendBodyDataTask = new SendBodyDetailsToServer(full_hands_wax,full_legs_wax,half_legs_wax,underarms_wax,back_wax,full_stomach_polish,full_back_polish,full_body_polish,head_and_shoulder_massage,hands_or_legs_polish,face_and_neck_bleach,full_hands_bleach,full_legs_bleach,half_legs_bleach,underarms_bleach,full_back_bleach,full_body_bleach);
            mSendBodyDataTask.execute();
        }
    }

    public class SendBodyDetailsToServer extends AsyncTask<Void, Void, Boolean> {

        private String mFull_Hands_wax;
        private String mFull_Legs_wax;
        private String mHalf_Legs_wax;
        private String mUnderarms_wax;
        private String mBack_wax;
        private String mFull_Stomach_Polish;
        private String mFull_Back_Polish;
        private String mFull_Body_Polish;
        private String mHead_And_Shoulder_Massage;
        private String mHands_Or_Legs_Polish;
        private String mFace_And_Neck_Bleach;
        private String mFull_Hands_Bleach;
        private String mFull_Legs_Bleach;
        private String mHalf_Legs_Bleach;
        private String mUnderarms_Bleach;
        private String mFull_Back_Bleach;
        private String mFull_Body_Bleach;

        private ProgressDialog progressDialog;

        SendBodyDetailsToServer(String full_hands_wax, String full_legs_wax, String half_legs_wax, String underarms_wax, String back_wax,
                                String full_stomach_polish,String full_back_polish,String full_body_polish,String head_and_shoulder_massage,
                                String hands_or_legs_polish, String face_and_neck_bleach,String full_hands_bleach,String full_legs_bleach, String half_legs_bleach, String underarms_bleach, String full_back_bleach, String full_body_bleach ){
             mFull_Hands_wax =full_hands_wax;
             mFull_Legs_wax = full_legs_wax;
             mHalf_Legs_wax = half_legs_wax;
             mUnderarms_wax = underarms_wax;
             mBack_wax = back_wax;
             mFull_Stomach_Polish = full_stomach_polish;
             mFull_Back_Polish = full_back_polish;
             mFull_Body_Polish = full_body_polish;
             mHead_And_Shoulder_Massage = head_and_shoulder_massage;
             mHands_Or_Legs_Polish = hands_or_legs_polish;
             mFace_And_Neck_Bleach = face_and_neck_bleach;
             mFull_Hands_Bleach = full_hands_bleach;
             mFull_Legs_Bleach = full_legs_bleach;
             mHalf_Legs_Bleach = half_legs_bleach;
             mUnderarms_Bleach = underarms_bleach;
             mFull_Back_Bleach = full_back_bleach;
             mFull_Body_Bleach = full_body_bleach;
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

                Log.v("Body Details", " ***********i am in try 1");
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
                param.put("full hands wax", mFull_Hands_wax);
                param.put("full legs wax", mFull_Legs_wax);
                param.put("half legs wax", mHalf_Legs_wax);
                param.put("underarms wax", mUnderarms_wax);
                param.put("back wax", mBack_wax);
                param.put("full stomach polish", mFull_Stomach_Polish);
                param.put("full back polish", mFull_Back_Polish);
                param.put("full body polish",mFull_Body_Polish);
                param.put("head and shoulder massage", mHead_And_Shoulder_Massage);
                param.put("hands or legs polish", mHands_Or_Legs_Polish);
                param.put("face and neck bleach", mFace_And_Neck_Bleach);
                param.put("full hands bleach", mFull_Hands_Bleach);
                param.put("full legs bleach", mFull_Legs_Bleach);
                param.put("half legs bleach ", mHalf_Legs_Bleach);
                param.put("underarms bleach", mUnderarms_Bleach);
                param.put("full back bleach", mFull_Body_Bleach);
                param.put("full body bleach", mFull_Body_Bleach);

//                    param.put("location", mLocation);
                // param.put("contact_number", mContact);
                DataOutputStream os = new DataOutputStream(urlc.getOutputStream());
                os.writeBytes(iCommon.getPostDataString(param));
                Log.v("body details", iCommon.getPostDataString(param));
                os.flush();
                os.close();
            } catch (ProtocolException e) {
                Log.v("body details", " i am in catch 1");
                e.printStackTrace();
            } catch (MalformedURLException e) {
                Log.v("body details", " i am in catch 2");
                e.printStackTrace();
            } catch (IOException e) {
                Log.v("body details", " i am in catch 3");
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
            mSendBodyDataTask = null;
            // showProgress(false);

            if (success) {
                //finish();
                Log.v("Body details", "**********success");
            } /*else {
                mPasswordView.setError(getString(R.string.error_incorrect_password));
                mPasswordView.requestFocus();
            }*/
        }

    }

} // This is the end of our MyFragments Class












