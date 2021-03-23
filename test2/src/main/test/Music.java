import java.applet.Applet;
import java.applet.AudioClip;
import java.io.File;
import java.net.MalformedURLException;

/**
 * AudioClip的三个方法： 循环播放——loop()，播放一次——play()，停止播放——stop()
 */

@SuppressWarnings("deprecation")
public class Music extends Thread {
    @SuppressWarnings("unused")
    private String music_name;
    public AudioClip audioClip;

    public Music(String music_name) {
        this.music_name = music_name;
        // 获取播放文件
        File file = new File(Music.class.getResource(music_name).getFile());

        // 创建audioclip对象
        try {
            audioClip = Applet.newAudioClip(file.toURL()); // 将file转换为url
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        audioClip.loop();
    }

    public static void main(String[] args) {
        Music music = new Music("1.wav");
        music.start();
    }
}