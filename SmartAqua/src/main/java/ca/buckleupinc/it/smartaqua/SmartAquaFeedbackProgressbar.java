/*  CENG-322-0NA: Group 6
    Denis Shwaloff - N01422583
    Alvaro Rodrigo Chavez Moya - N01455107
    Paolo Adrian Quezon - N01424883
    Nicholas Dibiase - N01367109            */

package ca.buckleupinc.it.smartaqua;

import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;

public class SmartAquaFeedbackProgressbar {

    private final ProgressBar progressBar;

    public SmartAquaFeedbackProgressbar(View rootView) {
        progressBar = rootView.findViewById(R.id.SmartAquaFeedbackProgressBar);
    }

    public void showProgressBarAndRunTask() {
        progressBar.setVisibility(View.VISIBLE);
        simulateTaskCompletion();
    }

    public void setProgressbarInvisible(){
        progressBar.setVisibility(View.INVISIBLE);
    }

    private void simulateTaskCompletion() {
        final int totalIterations = 4; // Adjust the number of iterations as needed
        final int progressDelayMillis = 1000; // 1000 milliseconds

        Handler handler = new Handler();
        for (int i = 1; i <= totalIterations; i++) {
            final int progress = i * 25;

            handler.postDelayed(() -> {
                progressBar.setProgress(progress);
                if (progress == 100) {
                    progressBar.setVisibility(View.INVISIBLE);
                    // Proceed with the rest of the program logic here
                }
            }, i * progressDelayMillis);
        }
    }
}
