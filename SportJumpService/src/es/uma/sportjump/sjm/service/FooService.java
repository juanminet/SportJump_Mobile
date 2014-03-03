package es.uma.sportjump.sjm.service;

import java.util.Random;

import android.app.Application;
import android.os.Vibrator;
import android.widget.Toast;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class FooService {
	@Inject Application application;
    @Inject Vibrator vibrator;
    @Inject Random random;

    public void say(String something) {
        // Make a Toast, using the current context as returned by the Context Provider
        Toast.makeText(application, "Astroboy says, \"" + something + "\"", Toast.LENGTH_LONG).show();
    }

    public void brushTeeth() {
        vibrator.vibrate(new long[]{0, 200, 50, 200, 50, 200, 50, 200, 50, 200, 50, 200, 50, 200, 50, 200, 50, 200, 50, 200, 50, 200, 50,  }, -1);
    }

    public String punch() {
        final String expletives[] = new String[]{"POW!", "BANG!", "KERPOW!", "OOF!"};
        return expletives[random.nextInt(expletives.length)];
    }
}
