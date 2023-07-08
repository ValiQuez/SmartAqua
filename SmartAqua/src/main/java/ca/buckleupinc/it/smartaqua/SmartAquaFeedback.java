/*  CENG-322-0NA: Group 6
    Denis Shwaloff - N01422583
    Alvaro Rodrigo Chavez Moya - N01455107
    Paolo Adrian Quezon - N01424883
    Nicholas Dibiase - N01367109            */

package ca.buckleupinc.it.smartaqua;

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

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SmartAquaFeedback extends Fragment {

    EditText ETname, ETemail, ETcomment, ETnumber;
    RatingBar RBrating;
    String model;
    DatabaseReference dbReference;

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

        feedbackBtn.setOnClickListener(v -> {

            String name = ETname.getText().toString();
            String email = ETemail.getText().toString();
            String comment = ETcomment.getText().toString();
            String number = ETnumber.getText().toString();
            float rating = RBrating.getRating();
            model = Build.MODEL;

            if(!name.equals("") && !email.equals("") && !comment.equals("") && !number.equals("") && rating!=0) {

                SmartAquaReviews reviews = new SmartAquaReviews(name, email, comment, model, number, rating);
                dbReference = FirebaseDatabase.getInstance().getReference("SmartAquaReviews");
                DatabaseReference childReference = dbReference.child(email.replace(".", "_"));
                childReference.setValue(reviews);

                Toast.makeText(getActivity(), R.string.reviewSent, Toast.LENGTH_SHORT).show();

                ETname.setText("");
                ETemail.setText("");
                ETcomment.setText("");
                ETnumber.setText("");
                RBrating.setRating(0);
            } else {
                Toast.makeText(getActivity(), R.string.reviewEmpty, Toast.LENGTH_SHORT).show();
            }

        });

        return view;
    }
}