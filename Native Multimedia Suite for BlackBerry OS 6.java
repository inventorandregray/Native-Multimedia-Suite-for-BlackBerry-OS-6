import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.container.MainScreen;
import net.rim.device.api.ui.component.LabelField;
import net.rim.device.api.ui.component.BitmapField;
import net.rim.device.api.ui.component.ButtonField;
import net.rim.device.api.system.Bitmap;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.TouchEvent;
import javax.microedition.media.Manager;
import javax.microedition.media.Player;
import javax.microedition.media.control.VideoControl;
import java.io.InputStream;

public class MultimediaSuiteOS6 extends UiApplication {

    public static void main(String[] args) {
        MultimediaSuiteOS6 app = new MultimediaSuiteOS6();
        app.enterEventDispatcher();
    }

    public MultimediaSuiteOS6() {
        pushScreen(new MultimediaScreenOS6());
    }
}

class MultimediaScreenOS6 extends MainScreen {

    private Player audioPlayer;
    private Player videoPlayer;
    private VideoControl videoControl;

    private String[] audioFiles = {"/audio/audio1.mp3", "/audio/audio2.mp3"};
    private String[] imageFiles = {"/images/image1.png", "/images/image2.png", "/images/image3.png"};
    private String[] videoFiles = {"/video/video1.3gp", "/video/video2.3gp"};

    private int currentAudio = 0;
    private int currentImage = 0;
    private int currentVideo = 0;

    private BitmapField imageField;

    public MultimediaScreenOS6() {
        setTitle(new LabelField("BB OS 6 Native Multimedia"));

        // Initialize Image Field
        imageField = new BitmapField();
        add(imageField);
        showImage(currentImage);

        // Image navigation buttons
        add(createButton("Next Image", new Runnable() {
            public void run() {
                currentImage = (currentImage + 1) % imageFiles.length;
                showImage(currentImage);
            }
        }));

        add(createButton("Previous Image", new Runnable() {
            public void run() {
                currentImage = (currentImage - 1 + imageFiles.length) % imageFiles.length;
                showImage(currentImage);
            }
        }));

        // Audio controls
        add(createButton("Play Audio", new Runnable() {
            public void run() {
                playAudio(audioFiles[currentAudio]);
            }
        }));

        add(createButton("Next Audio", new Runnable() {
            public void run() {
                stopAudio();
                currentAudio = (currentAudio + 1) % audioFiles.length;
                playAudio(audioFiles[currentAudio]);
            }
        }));

        // Video controls
        add(createButton("Play Video", new Runnable() {
            public void run() {
                playVideo(videoFiles[currentVideo]);
            }
        }));

        add(createButton("Next Video", new Runnable() {
            public void run() {
                stopVideo();
                currentVideo = (currentVideo + 1) % videoFiles.length;
                playVideo(videoFiles[currentVideo]);
            }
        }));
    }

    private ButtonField createButton(String label, final Runnable action) {
        return new ButtonField(label, ButtonField.CONSUME_CLICK) {
            protected boolean navigationClick(int status, int time) {
                action.run();
                return true;
            }

            protected boolean touchEvent(TouchEvent message) {
                if (message.getEvent() == TouchEvent.CLICK) {
                    action.run();
                    return true;
                }
                return false;
            }
        };
    }

    private void showImage(int index) {
        try {
            Bitmap bmp = Bitmap.getBitmapResource(imageFiles[index]);
            imageField.setBitmap(bmp);
        } catch (Exception e) {
            add(new LabelField("Error loading image: " + e.getMessage()));
        }
    }

    private void playAudio(String file) {
        try {
            stopAudio();
            InputStream is = getClass().getResourceAsStream(file);
            audioPlayer = Manager.createPlayer(is, "audio/mpeg");
            audioPlayer.realize();
            audioPlayer.prefetch();
            audioPlayer.start();
        } catch (Exception e) {
            add(new LabelField("Error playing audio: " + e.getMessage()));
        }
    }

    private void stopAudio() {
        try {
            if (audioPlayer != null) {
                audioPlayer.stop();
                audioPlayer.close();
            }
        } catch (Exception e) {}
    }

    private void playVideo(String file) {
        try {
            stopVideo();
            InputStream is = getClass().getResourceAsStream(file);
            videoPlayer = Manager.createPlayer(is, "video/3gpp");
            videoPlayer.realize();

            videoControl = (VideoControl) videoPlayer.getControl("VideoControl");
            if (videoControl != null) {
                Field videoField = (Field) videoControl.initDisplayMode(
                        VideoControl.USE_GUI_PRIMITIVE, "net.rim.device.api.ui.Field");
                add(videoField);
                videoPlayer.start();
            }
        } catch (Exception e) {
            add(new LabelField("Error playing video: " + e.getMessage()));
        }
    }

    private void stopVideo() {
        try {
            if (videoPlayer != null) {
                videoPlayer.stop();
                videoPlayer.close();
            }
        } catch (Exception e) {}
    }

    protected boolean onClose() {
        stopAudio();
        stopVideo();
        System.exit(0);
        return true;
    }
}

