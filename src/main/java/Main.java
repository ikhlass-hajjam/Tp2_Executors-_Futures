
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import futures.*;
import java.util.Arrays;
import java.util.concurrent.CountDownLatch;

public class Main {

    public static final int HOWMANYTHREADS = 4;

    public static void main(String[] args)
            throws InterruptedException, ExecutionException {
        // Les URL à traiter
        List<String> urls = List.of(
                "http://www.java2jee.blogspot.com",
                "http://www.univ-jfc.fr",
                "https://www.irit.fr/",
                "http://www.google.fr",
                "http://apache.org/",
                "https://sourceforge.net/"
         );
        // La liste des résultats
        List<Future<CountResult>> futures = new ArrayList<>(urls.size());
        // On crée l'exécuteur
        final ExecutorService executor = Executors.newFixedThreadPool(HOWMANYTHREADS);
        // Un compte à rebours pour attendre que tous les résultats soient disponibles
        CountDownLatch compteARebours = new CountDownLatch(urls.size());
        try {
            long start = System.nanoTime();
            // On soumet tous les travaux à l'exécuteur
            for (String url : urls) {
                // On crée la tâche à exécuter
                Callable<CountResult> task = new ByteCounter(url, compteARebours);
                // On soumet la tache à l'exécuteur, qui nous renvoie un résultat "futur"
                Future<CountResult> future = executor.submit(task);
                // On ajoute le résultat à la liste
                futures.add(future);
            }
            // On attend la fin du compte à rebours
            compteARebours.await();
            // On parcourt les résultats pour calculer le temps d'exécution
            long totalBytes = 0;
            long totalIndividualTimes = 0;
            for (Future<CountResult> future : futures) {
                CountResult result = future.get(); // Bloquant
                totalBytes += result.byteCount;
                totalIndividualTimes += result.timeTaken;
            }
            System.out.printf("Nombre total d'octets : %d%n", totalBytes);
            System.out.printf("Temps total de calcul : %d ms%n", (System.nanoTime() - start) / 1000000);
            System.out.printf("Somme des temps individuels: %d ms %n", totalIndividualTimes);
        } finally {
            executor.shutdown();
        }
    }
}
