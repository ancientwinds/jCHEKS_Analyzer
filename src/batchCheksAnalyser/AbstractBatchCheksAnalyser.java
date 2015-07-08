package batchCheksAnalyser;

/**
 *
 * @author Michael Roussel <rousselm4@gmail.com>
 */
public abstract class AbstractBatchCheksAnalyser {
    public abstract void analyse();
    public abstract void displayResult();
    public abstract String[] getStats();
    public abstract String[] getTypes();
    public abstract String[] getNames();
}
