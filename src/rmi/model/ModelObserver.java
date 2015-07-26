package rmi.model;

import java.util.*;
import rmi.AnalyserPackage;

/**
 *
 * @author Thomas Lepage thomas.lepage@hotmail.ca
 */
public interface ModelObserver {
    public void packageAnalysingUpdated(HashMap<String, AnalyserPackage> packages);
    public void loadingPackageCompleted(HashSet<AnalyserPackage> packages);
}
