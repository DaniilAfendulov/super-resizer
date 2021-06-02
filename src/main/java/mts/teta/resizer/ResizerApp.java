package mts.teta.resizer;

import mts.teta.resizer.imageprocessor.ConsoleAttributes;
import mts.teta.resizer.imageprocessor.ImageProcessor;
import picocli.CommandLine;

import javax.imageio.ImageIO;
import java.util.concurrent.Callable;

@CommandLine.Command(name = "resizer", mixinStandardHelpOptions = true, version = "resizer 0.0.1", description = "...")
public class ResizerApp extends ConsoleAttributes implements Callable<Integer> {
    public static void main(String... args) {
        String[] arg1 = "C:\\Users\\danii\\Desktop\\MTS\\src\\test\\resources\\Good_Will_Hunting_1997.jpg adsf.jpeg --resize 100 110".split(" ");
        String[] arg2 = "C:\\Users\\danii\\Desktop\\MTS\\src\\test\\resources\\Good_Will_Hunting_1997.jpg --help adsf.jpeg".split(" ");
        String[] arg3 = "C:\\Users\\danii\\Desktop\\MTS\\src\\test\\resources\\Good_Will_Hunting_1997.jpg C:\\Users\\danii\\Desktop\\MTS\\image.jpg --blur 10".split(" ");
        String[] arg4 = "C:\\Users\\danii\\Desktop\\MTS\\src\\test\\resources\\Good_Will_Hunting_1997.jpg C:\\Users\\danii\\Desktop\\MTS\\image.jpg --resize 400 800".split(" ");
        int exitCode = runConsole(arg4);
        System.exit(exitCode);
    }

    protected static int runConsole(String[] args) {
        return new CommandLine(new ResizerApp()).execute(args);
    }

    @Override
    public Integer call() throws Exception {
        ImageProcessor imageProcessor = new ImageProcessor();
        imageProcessor.processImage(ImageIO.read(inputFile), this);
        return 0;
    }

}
