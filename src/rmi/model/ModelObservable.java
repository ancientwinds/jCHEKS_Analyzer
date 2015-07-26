package rmi.model;

import java.util.*;
import rmi.AnalyserPackage;

/**
 *
 * @author Thomas Lepage thomas.lepage@hotmail.ca
 */
public class ModelObservable {
    
    protected final HashSet<ModelObserver> observers = new HashSet();

    public void addObserver(ModelObserver observer) {
        this.observers.add(observer);
    }
    public void notifyPackageAnalysingUpdated(HashMap<String, AnalyserPackage> packages) {
        this.observers.stream().forEach((observer) -> {
            observer.packageAnalysingUpdated(packages);
        });
        
    }
    public void notifyLoadingPackagesCompleted(HashSet<AnalyserPackage> packages) {
        this.observers.stream().forEach((observer) -> {
            observer.loadingPackageCompleted(packages);
        });
    }
}
