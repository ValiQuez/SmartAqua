/*  CENG-322-0NA: Group 6
    Denis Shwaloff - N01422583
    Alvaro Rodrigo Chavez Moya - N01455107
    Paolo Adrian Quezon - N01424883
    Nicholas Dibiase - N01367109            */

package ca.buckleupinc.it.smartaqua;

import android.app.AlertDialog;
import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SmartAquaFeedback extends Fragment {

    EditText ETname, ETemail, ETcomment, ETnumber;
    RatingBar RBrating;
    String model;
    DatabaseReference dbReference;
    private SmartAquaFeedbackProgressbar progressBarHandler;
    private static final String SmartAquaReviewsPath = "SmartAquaReviews";
    private static final String EmptyString = "";
    private static final String DotTarget = ".";
    private static final String CommaTargetReplacement = ",";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view;
        view = inflater.inflate(R.layout.fragment_smart_aqua_feedback, container, false);
        Button feedbackBtn = view.findViewById(R.id.SmartAquaFeedbackBtn);
        ETname = view.findViewById(R.id.SmartAquaETName);
        ETemail = view.findViewById(R.id.SmartAquaETEmail);
        ETcomment = view.findViewById(R.id.SmartAquaETComment);
        ETnumber = view.findViewById(R.id.SmartAquaETPhone);
        RBrating = view.findViewById(R.id.SmartAquaRatingBar);
        progressBarHandler = new SmartAquaFeedbackProgressbar(view);
        progressBarHandler.setProgressbarInvisible();

        feedbackBtn.setOnClickListener(v -> {

            String name = ETname.getText().toString();
            String email = ETemail.getText().toString();
            String comment = ETcomment.getText().toString();
            String number = ETnumber.getText().toString();
            float rating = RBrating.getRating();
            model = Build.MODEL;

            if (!name.equals(EmptyString) && !email.equals(EmptyString) && !comment.equals(EmptyString) && !number.equals(EmptyString) && rating != 0) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle(getResources().getString(R.string.reviewSendAlertTitle));
                builder.setMessage(getResources().getString(R.string.reviewSendAlertBody));
                builder.setIcon(R.drawable.icon_error_48px);
                builder.setPositiveButton(R.string.reviewSendAlert, (dialogInterface, i) -> {

                    Toast.makeText(getActivity(), R.string.reviewSendingReview, Toast.LENGTH_SHORT).show();
                    progressBarHandler.showProgressBarAndRunTask();

                    SmartAquaReviews reviews = new SmartAquaReviews(name, email, comment, model, number, rating);
                    dbReference = FirebaseDatabase.getInstance().getReference(SmartAquaReviewsPath);
                    DatabaseReference childReference = dbReference.child(email.replace(DotTarget, CommaTargetReplacement));
                    childReference.setValue(reviews);

                    Toast.makeText(getActivity(), R.string.reviewSent, Toast.LENGTH_SHORT).show();

                    ETname.setText(EmptyString);
                    ETemail.setText(EmptyString);
                    ETcomment.setText(EmptyString);
                    ETnumber.setText(EmptyString);
                    RBrating.setRating(0);
                });

                builder.setNegativeButton(R.string.no, (dialogInterface, i) -> {
                    Snackbar deniedBar = Snackbar.make(view, R.string.reviewSendDenied, Snackbar.LENGTH_SHORT);
                    deniedBar.show();
                });

                AlertDialog alert = builder.create();
                alert.show();

            } else {
                Toast.makeText(getActivity(), R.string.reviewEmpty, Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}