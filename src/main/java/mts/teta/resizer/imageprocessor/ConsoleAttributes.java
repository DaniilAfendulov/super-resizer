package mts.teta.resizer.imageprocessor;

import mts.teta.resizer.ResizerApp;
import picocli.CommandLine.*;

import java.io.File;
import java.util.Stack;

enum ImageFormat{ JPEG, jpeg, GPG, jpg, PNG, png}

public class ConsoleAttributes {

    protected File inputFile, outputFile;
    protected int quality = -1;
    public int blurRadius = -1;
    public boolean qualityFlag, blurFlag;

    @Parameters(paramLabel = "input-file")
    public void setInputFile(File file) throws BadAttributesException {
        validateFileExtension(file);
        inputFile = file;
    }

    @Parameters(paramLabel = "output-file")
    public void setOutputFile(File file) throws BadAttributesException {
        validateFileExtension(file);
        outputFile = file;
    }

    @ArgGroup(exclusive = true, multiplicity = "1")
    public Exclusive exclusive;

    static class Exclusive {
        public ResizeArgs resizeArgs;
        @Option(names = {"--resize"}, required = true,
                description = "resize the image",
                paramLabel = "<width height>",
                parameterConsumer = ResizeArgsConsumer.class)
        public void setResizeArgs(ResizeArgs resizeArgs){
            this.resizeArgs = resizeArgs;
        }

        @Option(names = {"--crop"}, required = true,
                description = "cut out one rectangular area of the image",
                paramLabel = "<width height x y>",
                parameterConsumer = CropArgsConsumer.class)
        public CropArgs cropArgs;
    }

    static class ResizeArgs {
        int width, height;
        public ResizeArgs() {}
        public ResizeArgs(int width, int height) {
            this.width = width;
            this.height = height;
        }
        public void check() throws BadAttributesException {
            if(width < 1) throw new BadAttributesException("width must be more than 0");
            if(height < 1) throw new BadAttributesException("height must be more than 0");
        }
    }

    static class ResizeArgsConsumer implements IParameterConsumer  {
        @Override
        public void consumeParameters(Stack<String> stack, Model.ArgSpec argSpec, Model.CommandSpec commandSpec)  {
            if (stack.size() < 2) {
                throw new ParameterException(commandSpec.commandLine(),
                        "Missing arguments. Please enter 2 integer number for --resize option");
            }
            int width = Integer.parseInt(stack.pop());
            int height = Integer.parseInt(stack.pop());
            argSpec.setValue(new ResizeArgs(width, height));
        }
    }

    static class CropArgs extends ResizeArgs {
        int x, y;
        public CropArgs(int width, int height, int x, int y) {
            super(width, height);
            this.x = x;
            this.y = y;
        }
        @Override
        public void check() throws BadAttributesException {
            super.check();
            if(x < 0) throw new BadAttributesException("BadAttribute: x<0");
            if(y < 0) throw new BadAttributesException("BadAttribute: y<0");
        }
    }

    static class CropArgsConsumer implements IParameterConsumer  {
        @Override
        public void consumeParameters(Stack<String> stack, Model.ArgSpec argSpec, Model.CommandSpec commandSpec)  {
            if (stack.size() < 4) {
                throw new ParameterException(commandSpec.commandLine(),
                        "Missing arguments. Please enter 4 integer number for --crop option");
            }
            int width = Integer.parseInt(stack.pop());
            int height = Integer.parseInt(stack.pop());
            int x = Integer.parseInt(stack.pop());
            int y = Integer.parseInt(stack.pop());
            argSpec.setValue(new CropArgs(width, height, x, y));
        }
    }
    @Option(names = {"--quality"}, description = "JPEG/PNG compression level", paramLabel = "<value>")
    public void setQuality(int i) throws BadAttributesException {
        if(!(i>=1 && i<=100))
            throw new BadAttributesException("The quality value is not between 1 and 100.");
        quality = i;
        qualityFlag = true;
    }

    @Option(names = {"--blur"}, description = "reduce image noise detail levels ")
    private void setBlurRadius(int radius) throws BadAttributesException {
        if(radius < 1)
            throw new BadAttributesException("The blur radius value is less than 1");
        blurRadius = radius;
        blurFlag = true;
    }

    @Option(names = {"--format"}, description = "the image format type: ${COMPLETION-CANDIDATES}")
    public ImageFormat outputFormat;

    public void setResizeWidth(Integer reducedPreviewWidth) throws BadAttributesException {
        createResizeArgsObj();
        exclusive.resizeArgs.width = reducedPreviewWidth;
    }

    public void setResizeHeight(Integer reducedPreviewHeight) throws BadAttributesException {
        createResizeArgsObj();
        exclusive.resizeArgs.height = reducedPreviewHeight;
    }

    private void validateFileExtension(File file) throws BadAttributesException{
        if(!ImageProcessor.checkImageFormat(ResizerApp.getFileExtension(file))){
            throw new BadAttributesException("file extension must be jpeg or png");
        }
    }
    private void createResizeArgsObj(){
        if(exclusive == null) exclusive = new Exclusive();
        if(exclusive.resizeArgs == null) exclusive.resizeArgs = new ResizeArgs();
    }

}
