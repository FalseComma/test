
import java.applet.Applet;
import java.applet.AudioClip;
import java.io.File;
import java.net.MalformedURLException;

/**
 * 1.AudioClip的三个方法：循环播放——loop()，播放一次——play()，停止播放——stop()
 * 2.关于用相对路径读取文件的问题
 * 可以先用Music.class.getResource("")查看路径，运行结果：
 * file:/D:/IdeaProjects/test/test1/target/classes/
 * 也就是说，我们应该把音乐文件放到target/classes/文件夹下存放
 *
 */

public class Music extends Thread{
    String music_name;
    AudioClip audioClip;

    public Music(String music_name) {
        this.music_name = music_name;
        //获取播放文件
        File file = new File(Music.class.getResource("music/"+music_name).getFile());

        //创建audioclip对象
        try {
            audioClip = Applet.newAudioClip(file.toURL()); //将file转换为url
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        audioClip.loop();
    }
}
