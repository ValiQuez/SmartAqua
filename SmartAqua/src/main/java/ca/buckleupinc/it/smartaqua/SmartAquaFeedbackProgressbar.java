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
    private final static int totalIterations = 4;
    private final static int progressDelayMillis = 1000;
    private final static int twentyFivePercent = 25;
    private final static int oneHundredPercent = 100;

    public SmartAquaFeedbackProgressbar(View rootView) {
        progressBar = rootView.findViewById(R.id.SmartAquaFeedbackProgressBar);
    }

    public void showProgressBarAndRunTask() {
        progressBar.setVisibility(View.VISIBLE);
        simulateTaskCompletion();
    }

    public void setProgressbarInvisible() {
        progressBar.setVisibility(View.INVISIBLE);
    }

    private void simulateTaskCompletion() {
        Handler handler = new Handler();
        for (int i = 1; i <= totalIterations; i++) {
            final int progress = i * twentyFivePercent;

            handler.postDelayed(() -> {
                progressBar.setProgress(progress);
                if (progress == oneHundredPercent) {
                    setProgressbarInvisible();
                }
            }, i * progressDelayMillis);
        }
    }
}
